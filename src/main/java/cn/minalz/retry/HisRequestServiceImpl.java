package cn.minalz.retry;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * HIS-请求方法
 * @author zhouwei
 * @date 2023/3/23 19:55
 */
@AllArgsConstructor
@Service
@Slf4j
public class HisRequestServiceImpl implements HisRequestService {

    @Override
    public <T> JsonNode post(String apiPath, T req) {
        String uuid = IdWorker.get32UUID();
        return null;
    }

    @Override
    public <T1,T2> T2 postForObject(String apiPath, T1 req, Class<T2> beanType) {
        JsonNode jsonNode = post(apiPath, req);
        if (jsonNode != null) {
            return JacksonUtils.jsonToObject(String.valueOf(jsonNode), beanType);
        }
        return null;
    }

    @Override
    public <T1,T2> T2 postForObjectByKey(String apiPath, T1 req, Class<T2> beanType, String key) {
        JsonNode jsonNode = post(apiPath, req);
        if (!jsonNode.hasNonNull(key)) {
            return null;
        }
        JsonNode jsonNode1 = jsonNode.with(key);
        if (jsonNode1 != null) {
            return JacksonUtils.jsonToObject(String.valueOf(jsonNode1), beanType);
        }
        return null;
    }

    @Override
    public <T1,T2> List<T2> postForList(String apiPath, T1 req, Class<T2> beanType, String key) {
        JsonNode jsonNode = post(apiPath, req);
        if (!jsonNode.hasNonNull(key)) {
            return null;
        }
        JsonNode jsonNode1 = jsonNode.withArray(key);
        if (jsonNode1 != null) {
            return JacksonUtils.jsonToList(String.valueOf(jsonNode1), beanType);
        }
        return Collections.emptyList();
    }

}
