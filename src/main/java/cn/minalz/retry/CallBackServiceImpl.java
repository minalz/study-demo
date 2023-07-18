package cn.minalz.retry;

import java.util.List;

public class CallBackServiceImpl {

    public <T1, T2> T2 add(String apiPath, T1 req, Class<T2> beanType, CallbackService callbackService) {
        return callbackService.onComplete(apiPath, req, beanType);
    }

//    public <T1,T2> T2 onCompleteByKey(String apiPath, T1 req, Class<T2> beanType, String key, CallbackService callbackService) {
//        return callbackService.onCompleteByKey(apiPath, req, beanType, key);
//    }
//
//    public <T1,T2> List<T2> onCompleteForList(String apiPath, T1 req, Class<T2> beanType, String key, CallbackService callbackService) {
//        return callbackService.onCompleteForList(apiPath, req, beanType, key);
//    }

}
