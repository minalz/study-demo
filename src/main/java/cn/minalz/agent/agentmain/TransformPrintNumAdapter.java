package cn.minalz.agent.agentmain;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class TransformPrintNumAdapter extends AdviceAdapter {

  protected TransformPrintNumAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
    super(api, methodVisitor, access, name, descriptor);
   }

  @Override
  protected void onMethodEnter() {
    super.visitIntInsn(BIPUSH, 50);
    super.visitInsn(IRETURN);
   }
}