package cn.minalz.agent.agentmain;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class MyAttachMain {

  public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
    VirtualMachine virtualMachine = VirtualMachine.attach(args[0]);

    try {
      virtualMachine.loadAgent("D:/BBBWorkSpace/study-demo/target/study-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
     } finally {
      virtualMachine.detach();
     }

   }

}