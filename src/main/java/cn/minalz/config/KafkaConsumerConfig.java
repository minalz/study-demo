package cn.minalz.config;

import cn.minalz.listener.ScmciwhReceiveListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Autowired
  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${kafka.consumer.servers}")
  private String servers;
  @Value("${kafka.consumer.enable.auto.commit}")
  private boolean enableAutoCommit;
  @Value("${kafka.consumer.session.timeout}")
  private String sessionTimeout;
  @Value("${kafka.consumer.auto.commit.interval}")
  private String autoCommitInterval;
  @Value("${kafka.consumer.group.id}")
  private String groupId;
  @Value("${kafka.consumer.auto.offset.reset}")
  private String autoOffsetReset;
  @Value("${kafka.consumer.concurrency}")
  private int concurrency;

  private String SASL_PLAINTEXT = "SASL_PLAINTEXT";
  private String PLAIN = "PLAIN";

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      factory.setConcurrency(concurrency);
      factory.getContainerProperties().setPollTimeout(1500);
      factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
      factory.getContainerProperties().setAckOnError(false);
      // 设置重试模板
      factory.setRetryTemplate(retryTemplate());
      // 全部重试失败后，调用最终回调函数，暂停Consumer
      factory.setRecoveryCallback(context -> {
          logger.error("Maximum retry policy has been reached {}", context.getAttribute("record"));
          // todo 增加暂停代码
          Collection<MessageListenerContainer> listenerContainers = kafkaListenerEndpointRegistry
                  .getAllListenerContainers();
          ConsumerRecord<?, ?> record = (ConsumerRecord) context
                  .getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
          listenerContainers.stream()
                  .filter(x -> x.getContainerProperties().getTopics()[0].equals(record.topic()))
                  .forEach(x -> {
                      x.pause();
                  });
          return null;
      });
      return factory;
  }

  public ConsumerFactory<String, String> consumerFactory() {
      return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  public Map<String, Object> consumerConfigs() {
      Map<String, Object> propsMap = new HashMap<>();
      propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
      propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
      propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
      propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
      propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
      // propsMap.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SASL_PLAINTEXT);
      // propsMap.put(SaslConfigs.SASL_MECHANISM, PLAIN);
      return propsMap;
  }

  @Bean
  public ScmciwhReceiveListener listener() {
      return new ScmciwhReceiveListener();
  }

  /*
   * Retry template.
   */
  protected RetryPolicy retryPolicy() {
      SimpleRetryPolicy policy = new SimpleRetryPolicy();
      // 重试10次，还是错误的话，就停止consumer
      policy.setMaxAttempts(10);
      return policy;
  }

  protected BackOffPolicy backOffPolicy() {
      ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
      // 重试策略，第一次重试间隔1s，每次重试都增加间隔，最长60s
      policy.setInitialInterval(1000);
      policy.setMaxInterval(60000);
      return policy;
  }

  @Bean
  public RetryTemplate retryTemplate() {
      RetryTemplate retryTemplate = new RetryTemplate();
      retryTemplate.setRetryPolicy(retryPolicy());
      retryTemplate.setBackOffPolicy(backOffPolicy());
      return retryTemplate;
  }

}
