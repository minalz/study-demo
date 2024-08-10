package cn.minalz.agent.agentmain;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TransformPrintNumVisitor extends ClassVisitor {

  public TransformPrintNumVisitor(int api, ClassVisitor classVisitor) {
    super(Opcodes.ASM6, classVisitor);
   }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
    if (name.equals("getNum")) {
      return new TransformPrintNumAdapter(api, mv, access, name, descriptor);
     }
    return mv;

   }

}