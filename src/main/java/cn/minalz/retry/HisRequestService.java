package cn.minalz.retry;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface HisRequestService {

    <T> JsonNode post(String apiPath, T req);

    <T1,T2> T2 postForObject(String apiPath, T1 req, Class<T2> beanType);

    <T1,T2> T2 postForObjectByKey(String apiPath, T1 req, Class<T2> beanType, String key);

    <T1,T2> List<T2> postForList(String apiPath, T1 req, Class<T2> beanType, String key);

}
