package cn.minalz.agent.premain;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class TimeStatisticsAdapter extends AdviceAdapter {

    protected TimeStatisticsAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }


    @Override
    protected void onMethodEnter() {
        // 进入函数时调用TimeStatistics的静态方法start
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "cn/minalz/agent/premain/TimeStatistics", "start", "()V", false);
        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int opcode) {
        // 退出函数时调用TimeStatistics的静态方法end
        super.onMethodExit(opcode);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "cn/minalz/agent/premain/TimeStatistics", "end", "()V", false);
    }
}


