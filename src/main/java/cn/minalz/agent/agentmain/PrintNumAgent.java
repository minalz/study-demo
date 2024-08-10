package cn.minalz.agent.agentmain;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class PrintNumAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("agentmain");
        inst.addTransformer(new PrintNumTransformer(), true);

        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        for (Class allLoadedClass : allLoadedClasses) {
            if (allLoadedClass.getSimpleName().equals("PrintNumTest")) {
                System.out.println("Reloading: " + allLoadedClass.getName());
                inst.retransformClasses(allLoadedClass);
                break;
            }
        }
    }
}