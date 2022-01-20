package cn.minalz.generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * mybatis-plus代码生成器
 */
public class CodeGenerator {

    public static void main(String[] args) {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // set beetl engine
/*        mpg.setTemplateEngine(new BeetlTemplateEngine());

        //指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        TemplateConfig tc = new TemplateConfig();
        tc.setController("templates/controller.java");
        tc.setService("templates/service.java");
        tc.setServiceImpl("templates/serviceImpl.java");
        tc.setEntity("templates/entity.java");
        tc.setMapper("templates/mapper.java");
        tc.setXml("templates/mapper.xml");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        mpg.setTemplate(tc);*/
         
        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOutputDir("/Users/zhouwei/Documents/generatecode");
        gc.setAuthor("minalz");
        //生成后是否打开资源管理器
        gc.setOpen(false);
        //重新生成时文件是否覆盖
        gc.setFileOverride(true);
//        gc.setServiceName("%sService"); //去掉Service接口的首字母I
//        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
//        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        //开启Swagger2模式
        gc.setSwagger2(true);

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/study-demo?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //模块名
        pc.setModuleName(null);
        pc.setParent("cn.minalz");
        pc.setController("controller");
        pc.setEntity("entity");
//        pc.setServiceImpl("service.impl");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        //对哪一张表生成代码
        strategy.setInclude("user");
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //生成实体时去掉表前缀
        strategy.setTablePrefix(pc.getModuleName() + "_");

        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);

        //restful api风格控制器
        strategy.setRestControllerStyle(true);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}
