package com.javaweb.departmentmanage;

public class Docu9 {
    /*
    spring boot 的工作原理
    1. 配置优先级
        配置文件优先级：properties > yml > yaml

        springboot除了支持配置文件进行属性配置，还支持Java系统属性和命令行参数这两种方式进行属性配置
        1. Java系统属性：如： -Dserver.port=8080
        2. 命令行参数：如： --server.port=8080

        在idea中，创建项目的时候，可以直接在配置时，指定Java系统属性或命令行参数
        找到VM options，即可指定Java系统属性
        找到program arguments，即可指定命令行参数


        项目创建之后，也可在idea右上角，运行按钮左边，下拉项目，进入edit configuration
        然后，在configuration中，即可指定Java系统属性或命令行参数
        要是找不到就点击：modify options选择add VM options 或 add program arguments即可

        整体优先级：命令行参数 > Java系统属性 > 配置文件

        如果项目文件打包成jar包以后，也可以通过执行java指令，完成项目配置的修改
        1. 执行maven打包指令，把项目打包成jar包
            需要先引入spring-boot-maven-plugin依赖（基于springboot创建的骨架的话，已经引入了）

        2. 执行Java指令，运行jar包
        java -Dserver.port=8080 -jar departmentmanage-1.0-SNAPSHOT.jar --server.port=8080
        Java属性配置写在jar包名称前面进行配置，命令行参数写在jar包名称后面进行配置


        既然，外部java属性配置或者是命令行参数设置的优先级都高于配置文件
        那么，常见场景还有，一个jar要在别的电脑上运行，且要访问该电脑的数据库
        可以通过命令行参数，指定数据库连接信息（url、username、password）



    2. Bean管理
        第一部分：Bean的作用域
        spring支持5中作用域：
        1. singleton：单例作用域
            ioc容器中，同名称的bean，只存在一个实例（默认作用域）
        2. prototype：原型作用域
            每次从ioc容器中获取bean，都会创建一个新的实例（非单例/多例）
        3. request：请求作用域
            每个请求范围内会创建新的实例(web环境中才能使用，了解即可)
        4. session：会话作用域
            每个会话范围内会创建新的实例(web环境中才能使用，了解即可)
        5. global-session：应用作用域
            每个应用范围内会创建新的实例(web环境中才能使用，了解即可)


       指定作用域：@Scope("singleton")，注解添加在bean类上

       测试单例：
       @SpringBootTest
public class BeanTest {
    // 先注入ioc容器
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test(){
        for (int i = 0; i < 1000; i++) {
            // 默认的bean名时小驼峰命名法
            Object deptController = applicationContext.getBean("deptController");
            // 打印地址，看是否单例
            System.out.println(deptController);
        }
    }
}
结果：所有地址都相同，说明是单例作用域

单例bean的创建时间：默认在项目启动时创建，创建成功后，会缓存到ioc容器中，后续每次获取都是从缓存中获取，不会重新创建

也可以通过在bean类上添加：@Lazy注解，来指定bean的创建时间：延迟创建---》懒加载，延迟到第一次使用时，才会创建bean


怎么判断该使用单例还是多例？
如果一个bean被的数据不需要保存---》无状态的bean---》使用单例即可----》节约资源
如果一个bean的数据需要保存---》使用多例即可

多例的应用场景：
比如：需要统计查询数据库中的部门数据时，需要统计每次查询的条数、错误次数等
如果使用单例来处理，统计结果都是一样。

实操：声明多例：@Scope("prototype")
定义统计属性：
private List<Dept> deptList = new ArrayList<>();//暂存数据
private Integer errorCount;//错误次数
然后再注入bean
@Autowired
private DeptMapper deptMapper;

单例：因为不保存状态信息----》线程更安全----》不存在数据共享问题
多例：因为需要保存状态信息----》线程不安全-----》可能会出现数据不一致


        第三方bean
        如果要管理的bean，是第三方提供的，那么需要在bean类上添加：@Bean注解来进行管理

        实操：
        1. 在启动类中添加@Bean注解
        2. 注解下方写一个方法，方法返回值会放入到ioc容器中，称为ioc容器中的bean对象
        比如，想要把阿里云的这个配置方法放入到bean中进行管理：
        @Bean // 将方法返回值交给ioc容器管理
        public AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties){
            return new AliyunOSSOperator(aliyunOSSProperties);
        }
        如果没有额外给这个bean起名字，那么默认的bean名就是方法名的首驼峰命名法：aliyunOSSOperator

        注意：如果第三方bean需要依赖其他bean对象，直接在bean定义方法中设置形参即可，容器会根据类型自动装配

        注意：加上了@Bean注解的方法：会在项目启动的时候自动加载，并放入到ioc容器中


        注意：
        不建议把第三方bean直接定义在启动类中，为了保持启动类中的代码简洁
        一般会把第三方bean单独定义在配置类中---》conf包中


    3. SpringBoot原理

    1. springboot的自动配置就是当spring项目启动后，一些配置类、bean对象就会自动存入到ioc容器中，
        不需要我们手动去声明，从而简化了开发、省去了繁琐的配置操作

        比如：Gson依赖：这是google提供的一个json解析工具，自动配置后，会在ioc启动时，自动加载Gson类，放入到ioc容器中，称为ioc容器中的bean对象
        pom中添加gson依赖后，只需要在类中直接@Autowired注入即可，无需手动创建对象，也无需手动管理生命周期，直接使用即可

        注意：在添加aliyun依赖的时候，已经自动配置了gson依赖

        注意：如果提示gson未在bean中时，可以直接去到配置类中，添加@Bean注解，来手动引入gson对象
        @Bean
        public Gson gson(){
            return new Gson();
        }

      2. 自定义工具包---》自定义starter----》自动配置
      第一种方案：
        先在需要导入其他包的包中，pom文件内注册其他包的依赖

        2. 在启动类上添加@ComponentScan注解，指定扫描范围
        如果，启动类上使用的是@SpringBootApplication注解，
        那么直接在@SpringBootApplication注解中，添加scanBasePackages属性，指定扫描范围即可
        如：
            @SpringBootApplication(scanBasePackages = {"com.example", "com.itheima","com.javaweb.departmentmanage"})

        原因：@springbootapplication直接已经集成了@ComponentScan注解，所以不需要手动添加@ComponentScan注解了

        如果bean类上，有@Component注解，那么就会自动配置到ioc容器中
        我们只需要手动配置，在启动类中添加@ComponentScan注解，指定扫描范围即可

        但是：
        这种手动添加的方法，如果面对项目中包含多个包，且很多类上都有component注解时，
        会比较麻烦，因为需要手动添加每个包的扫描范围，比较耗时，而且扫描范围大，性能差


      第二种方案：
        直接在启动类上添加@Import注解，注解内直接指定需要扫描的类
        如：
            @Import(CommonConfig.class)

        @Import注解中：
        既能指定普通类
        也能指定配置类

        通过@Import导入配置类的话：
        配置类中所有添加了@Bean注解的bean类都会自动加载到ioc容器中，称为ioc容器中的bean对象

      第三种方案：
        还可以通过实现ImportSelector接口，来自定义多个导入的类----》批量导入

        这样的话，直接import接口importSelector的实现类即可

        // ImportSelector接口中只有一个方法：selectImports()
// 这个方法能返回多个类的全限定名数组，这些类会被自动加载到ioc容器中
public class MyImportSelector implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 导入多个类
        return new String[]{"com.example.HeaderConfig", "com.example.TokenParser"};
    }
}


      第四种方法：
      把以上的都集成到EnableHeaderConfig注解中，来实现批量导入
      这样的话，我们只需要在启动类上添加自定义的注解：@EnableHeaderConfig即可完成批量导入
      @Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyImportSelector.class)
public @interface EnableHeaderConfig {
}

原理：
自定义的@enableHeaderConfig注解中已经import了我们需要导入的类



最推荐：第四种方式：对开发人员来说：最方便，对启动类的代码来说：最简洁

     */

    /*
    自动配置----------源码跟踪;

    源码入口：启动类的@SpringBootApplication注解中
    @SpringBootApplication
    ---@Target
    ---@Retention
    ---@Documented
    ---@Inherited
    ---@EnableAutoConfiguration-------》开启了自动配置的开关
                                -------》底层的重点：包含了@AutoConfiguration注解---》自动配置bean对象
                                -------》和@Import注解---》批量导入配置类
    ---@ComponentScan-----------------》组件扫描
    ---@SpringBootConfiguration
        ---@Target
        ---@Retention
        ---@Documented
        ---@Configuration-----》这是重点：意味着：在启动类中声明了配置类的功能，自动配置了ioc容器中的bean对象
        ---@Indexed


    要注意：
    虽然@EnableAutoConfiguration中的@Import注解批量注入了很多的配置类
    但是：这并不意味着其中所有的配置类都是注册为了ioc容器中的bean。
    事实上：只有：@ConditionalOnMissingBean这个注解的配置类，才会被注册为了ioc容器中的bean对象---》也就是带有条件的配置类

    @Conditional注解：这是一个父注解：
    作用：按照一定的条件进行判断，在满足给定条件后才会注册对应的bean对象到spring ioc容器中
    位置：方法、类

    子注解：
    ---@ConditionalOnMissingBean：判断环境中没有对应的bean（类型或名称），才会注册对应的bean对象到ioc容器中
    ---@ConditionalOnProperty：判断环境中是否有对应的属性和值，才会注册对应的bean对象到ioc容器中
    ---@ConditionalOnClass：判断环境中是否有对应的字节码文件，才会注册对应的bean对象到ioc容器中

    也就是：以上这些conditional注解，可以添加到对应的类上、方法上，来实现条件配置的bean对象

    比如：
     @Bean
    @ConditionalOnClass(name = "io.jsonwebtoken.Jwts")
    // 当环境中io.jsonwebtoken.Jwts类存在时，才会注册HeaderParser bean对象
    // 也就是给是否放入ioc容器加了一个if条件
    public HeaderParser headerParser(){
        return new HeaderParser();
    }
    这里加上了要存在io.jsonwebtoken.Jwts类，才会注册HeaderParser bean对象

    测试：
    1. pom文件中注释掉引入临近模块departmentmanage的依赖（这里面才有io.jsonwebtoken.Jwts类）
        结果就是testHeaderParser方法会报错，因为没有注册HeaderParser bean对象

   2. 放出pom文件中注释掉的部分
        结果就是testHeaderParser方法正常运行，因为有注册HeaderParser bean对象


  @Configuration
public class HeaderConfig {

    @Bean
    //@ConditionalOnClass(name = "io.jsonwebtoken.Jwts")
    // 当环境中io.jsonwebtoken.Jwts类存在时，才会注册HeaderParser bean对象
    // 也就是给是否放入ioc容器加了一个if条件
    //@ConditionalOnMissingBean 判断ioc容器中是否有这个bean，有则注册，没有则不注册
    @ConditionalOnProperty(name = "myname", havingValue = "itheima")
    // 当yml配置文件中存在一个配置项，且其值为itheima时，才会注册HeaderParser bean对象
    public HeaderParser headerParser(){
        return new HeaderParser();
    }

    @Bean
    public HeaderGenerator headerGenerator(){
        return new HeaderGenerator();
    }
}


总结;
字节定义自动配置类的核心？如何完成自动配置？
1. 定义自动配置类
2. 讲自动配置类配置在META-INF/spring
/org.springframework.boot.autoconfigure.AutoConfiguration.imports文件中

这个配置文件中：包含了所有自动配置类的全限定名（通过@AutoConfiguration注解添加）
而且，配置文件中通过@Conditional注解，来实现条件配置的bean对象的注册，从而决定哪些该注册
     */

    /*
    自定义starter

    1. 命名规范：
        spring官方的starter：以spring-boot-starter-开头
        非spring官方的starter：以开发项目或包名开头，后接spring-boot-starter
        如： mybatis-spring-boot-starter
            pagehelper-spring-boot-starter

        自定义的starter也应该遵循非spring官方的命名规范


    需求：自定义aliyun-oss-spring-boot-starter，完成阿里云oss操作工具类aliyunossoperator的自动配置
    目标：引入起步依赖移入之后，要想使用阿里云oss，注入aliyunossoperator直接使用即可

    实现步骤：
    1. 创建aliyun-oss-spring-boot-starter模块
        1. 直接在项目下新建一个模块，模块名：aliyun-oss-spring-boot-starter
        2. 创建时：创建为一个spring boot项目
        3. 不需要引入其他依赖
        4. 创建后再进入到pom文件中进行依赖管理
        5. 本项目中不需要配置任何java代码，因此，只保留pom文件，其他文件及文件夹都删除
        6. 修改版本号，修改到与父工程的版本号一致

    2. 创建aliyun-oss-spring-boot-autoconfigure模块，而且，要在starter中也引入这个模块，从而实现自动配置
        1. 本模块需要书写配置类代码，因此src文件夹不能删除，其余除了pom文件外的文件均可删除
        2. 修改版本号，修改到与父工程的版本号一致
        3. aliyun-oss-spring-boot-autoconfigure中的启动类也可以删除
        4. 测试包也可删除
        5. 配置文件也可以删除

        关键：
        在starter中引入aliyun-oss-spring-boot-autoconfigure模块的依赖，从而实现自动配置功能

        7. 在aliyun-oss-spring-boot-autoconfigure模块中，引入阿里云依赖（共有三个依赖，都要引入）
        8. 引入导入阿里云oss的工具类aliyunossoperator和aliyunossproperties---》
            直接从原来有定义好的departmentmanage模块中复制粘贴即可
        9. 在aliyun-oss-spring-boot-autoconfigure模块中，定义自动配置类AliyunOSSAutoConfiguration配置类
        10. 配置类中需要配置aliyunossoperator这个bean对象，从而实现自动配置
        11. 需要添加的注解
            类上：@EnableConfigurationProperties(AliyunOSSproperties.class)
            方法上：@Bean 和 @ConditionalOnMissingBean // 声明如果没有这个bean才会创建这个bean对象

    3. aliyun-oss-spring-boot-autoconfigure模块中定义自动配置功能，并定义自动配置文件：
        META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

        1. 在resources目录下创建META-INF目录
        2. 在META-INF目录下创建spring目录
        3. 建议逐级创建，不要想着一步多层创建
        4. 如果不知道后续名称，可以去找一下External Libraries文件夹下的非spring非官方的依赖文件写法
        5. 在spring目录下创建org.springframework.boot.autoconfigure.AutoConfiguration.imports文件
        6. 在文件中写入配置类的全类名：
            com.aliyun.oss.spring.boot.autoconfigure.autoconfigure.AliyunOSSAutoConfiguration

       完成之后，可以在测试包的pom文件中引入starter依赖，yml配置文件中写入spring、aliyunoss的配置项，从而实现自动配置

    作用：
    aliyun-oss-spring-boot-starter模块：依赖管理
    aliyun-oss-spring-boot-autoconfigure模块：自动配置功能
     */
}
