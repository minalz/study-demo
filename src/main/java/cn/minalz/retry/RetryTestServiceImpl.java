package cn.minalz.retry;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class RetryTestServiceImpl implements RetryTestService {

    private static final int MAX_RETRIES = 5; // 最大重试次数
    private static int count = 1;

    @Override
    public String submit() throws CustomRetryException {
        log.info("retry{},throw CustomRetryException in method retry",count);
        count ++;
        throw new CustomRetryException("throw custom exception");
    }

    @Recover
    public String recover(Throwable throwable) {
        log.info("Default Retry service test");
        return "Error Class :: " + throwable.getClass().getName();
    }

}
