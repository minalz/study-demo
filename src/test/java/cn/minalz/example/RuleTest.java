package cn.minalz.example;

import org.junit.Test;

public class RuleTest {

    @Test
    public void test01() {
        Integer a = 1;
        Integer b = 2;
        Integer c = null;
        Boolean flag = false;
        // a*b 的结果是 int 类型，那么 c 会强制拆箱成 int 类型，抛出 NPE 异常
        Integer result = flag ? a * b : c;
    }
}
