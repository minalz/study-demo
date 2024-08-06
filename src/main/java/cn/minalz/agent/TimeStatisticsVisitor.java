package cn.minalz.agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TimeStatisticsVisitor extends ClassVisitor {

  public TimeStatisticsVisitor(int api, ClassVisitor classVisitor) {
    super(Opcodes.ASM6, classVisitor);
   }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
    if (name.equals("<init>")) {
      return mv;
     }
    return new TimeStatisticsAdapter(api, mv, access, name, descriptor);
   }
}
