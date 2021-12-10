package cn.minalz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        String name = "1";
        // \u000d name = "2";
        System.out.println(name);
    }

}
