package cn.minalz.agent.agentmain;

public class PrintNumTest {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println(getNum());
            Thread.sleep(3000);
        }
    }

    private static int getNum() {
        return 100;
    }

}