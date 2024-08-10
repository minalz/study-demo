package cn.minalz.agent.premain;

import java.lang.instrument.Instrumentation;

/**
 * agent premain
 * @author zhouwei
 * @date 2024/8/6 11:52
 */
public class PreMain {

    // premain()函数中注册MyClassFileTransformer转换器
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("premain方法");
        instrumentation.addTransformer(new MyClassFileTransformer(), true);
    }
}
