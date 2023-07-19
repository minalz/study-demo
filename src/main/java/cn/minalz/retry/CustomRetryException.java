package cn.minalz.retry;

public class CustomRetryException extends Exception{
    public CustomRetryException(String error){
        super(error);
    }
}