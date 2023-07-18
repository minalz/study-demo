package cn.minalz.retry;

import java.util.List;

public interface CallbackService {
    <T1, T2> T2 onComplete(String apiPath, T1 req, Class<T2> beanType);

//    <T1,T2> T2 onCompleteByKey(String apiPath, T1 req, Class<T2> beanType, String key);
//
//    <T1,T2> List<T2> onCompleteForList(String apiPath, T1 req, Class<T2> beanType, String key);
}
