package cn.minalz;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.*;

/**
 * Create by PSY
 *
 * @date: 2018/10/22 上午9:30
 * @version:4.0
 * @Description:模拟高并发访问controller
 */
 
public class NoRepeatTest {
 
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2000, 5000, 2, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(5000));
        //模拟100人并发请求
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //模拟100个用户
        for (int i = 0; i < 100; i++) {
            String tokenId = "ceshi - " + i;
            AnalogUser analogUser = new AnalogUser(countDownLatch, tokenId);
            executor.execute(analogUser);
        }
        //计数器減一  所有线程释放 并发访问。
        countDownLatch.countDown();
    }
 
    @Test
    public void test1(){
        String url = "http://localhost:8900/scmciwh/api/stock/ceshitest";
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJ3ZXJrcyI6IjE0MDAiLCJleHAiOjE2MjAzMjIyMjgsInVzZXJuYW1lIjoyOTZ9.MzbFyVdCwglsbaCR-mixkNoaxBzP6kKpoKQCxE6tJuDiQx-M6-uN7WDvhv--73CHou5mzY8p0cvTFCXy5xK-4g";
        String tokenId = "ceshi - " + 1;
        JSONObject jsonObject = httpPost(url, "token="+token+"&tokenId="+tokenId);
        System.out.println("返回的状态码为："+jsonObject.get("code"));
        System.out.println("返回的内容为："+jsonObject.get("msg"));
        System.out.println("Thread:" + Thread.currentThread().getName());

    }


    /*@Test
    public void test2(){
        System.out.println("执行多线程测试");
        long starTime = System.currentTimeMillis();;
        String url = "http://localhost:8900/scmciwh/api/stock/ceshitest";
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJ3ZXJrcyI6IjE0MDAiLCJleHAiOjE2MjAzMjIyMjgsInVzZXJuYW1lIjoyOTZ9.MzbFyVdCwglsbaCR-mixkNoaxBzP6kKpoKQCxE6tJuDiQx-M6-uN7WDvhv--73CHou5mzY8p0cvTFCXy5xK-4g";
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            String tokenId = "ceshi - " + i;
            long finalStarTime = starTime;
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("Thread:" + Thread.currentThread().getName() + ", time:" + finalStarTime);
                    JSONObject jsonObject = httpPost(url, "token="+token+"&tokenId="+tokenId);
                    System.out.println("返回的状态码为："+jsonObject.get("code"));
                    System.out.println("返回的内容为："+jsonObject.get("msg"));
                    System.out.println("Thread:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
            countDownLatch.countDown();

        long endTime = System.currentTimeMillis();
        Long t = endTime - starTime;
        System.out.println(t / 1000F + "秒");
    }*/
 
 
    static class AnalogUser implements Runnable {

        CountDownLatch countDownLatch;
        String tokenId;

        public AnalogUser(CountDownLatch countDownLatch, String tokenId) {
            this.countDownLatch = countDownLatch;
            this.tokenId = tokenId;

        }
 
        @Override
        public void run() {
            long starTime = System.currentTimeMillis();
            String url = "http://localhost:8900/scmciwh/test";
            String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJ3ZXJrcyI6IjE0MDAiLCJleHAiOjE2MjAzMjIyMjgsInVzZXJuYW1lIjoyOTZ9.MzbFyVdCwglsbaCR-mixkNoaxBzP6kKpoKQCxE6tJuDiQx-M6-uN7WDvhv--73CHou5mzY8p0cvTFCXy5xK-4g";
//            String opid = "00019E3EE02A489DBE9B71494135DE13-ceshitest";
            String opid = "00019E3EE02A489DBE9B71-ceshitest";
            String uuid = "uuid9E3EE02A489DBE9B71-ceshitest";
            try {
                countDownLatch.await();
                JSONObject jsonObject = httpPost(url, "token="+token+"&tokenId="+tokenId+"&opid="+opid+"&uuid="+uuid);
                System.out.println("============================================");
                System.out.println("---Thread:" + Thread.currentThread().getName());
                System.out.println("---返回的状态码为："+jsonObject.get("code"));
                System.out.println("---返回的内容为："+jsonObject.get("msg"));
                long endTime = System.currentTimeMillis();
                Long t = endTime - starTime;
                System.out.println("---" + t / 1000F + "秒");
                System.out.println("============================================");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
 
 
    }
 
    static JSONObject httpPost(String url, String strParam) {
 
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(2000).setConnectTimeout(2000).build();
//        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            //请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == 200) {
                String str;
                try {
                    //读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    //把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
 
                }
            }
        } catch (IOException e) {
 
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }


    @Test
    public void threadTest() throws InterruptedException{
        //访问数
        int threadSize = 1000;
        //最大并发数量
        int poolSize = 100;
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadSize);
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, poolSize,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        //开始时间
        long beginTime = System.currentTimeMillis();
        boolean isBeginToRun = false;
        for (int i = 0; i < threadSize; i++) {
            //创建子线程并使其阻塞
            executorService.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"---bigin");
                    //阻塞到countDownLatch1为0时在开始执行
                    countDownLatch1.await();
                    //需要执行的操作
//                    httpSend();
                    System.out.println(Thread.currentThread().getName()+"---END");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch2.countDown();
                }
            });
            System.out.println("结束--"+i);

        }
        //使所有的子线程开始并发执行
        countDownLatch1.countDown();
        System.out.println("线程开始执行");
        //阻塞主线程
        countDownLatch2.await();
        //结束时间
        long endTime = System.currentTimeMillis();
        //执行花费时间
        System.out.println("thread size : "+threadSize+", use pool :"+(endTime-beginTime));
        executorService.shutdown();
    }
 
 
}