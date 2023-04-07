package cn.minalz;

import cn.minalz.canal.CanalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class BaseApplication implements CommandLineRunner {

//    @Autowired
    private CanalClient canalClient;
  
    @Override
    public void run(String... args) throws Exception {
        canalClient.run();
    }
}  