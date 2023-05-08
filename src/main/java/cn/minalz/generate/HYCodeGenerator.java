package cn.minalz.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;


class HYCodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://xxx:3306/xxx?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "xxx", "xxx")
                .globalConfig(builder -> {
                    builder.author("zhouwei") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\test"); // 指定输出目录

                })
                .packageConfig(builder -> { //包配置
                    builder.parent("cn.minalz") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .controller("controller")
                            .entity("entity")
                            .service("service")
                            .mapper("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\test\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> { // 策略配置
                    builder
//                            .addInclude("","","")
                            .likeTable(new LikeTable("todo"))
                            .entityBuilder()
                            .enableTableFieldAnnotation()
                            .enableLombok()
                            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl")
//                            .likeTable(new LikeTable(""))
                    ; // 设置过滤表前缀
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
