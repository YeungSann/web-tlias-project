package com.javaweb.departmentmanage;

public class Docu8 {
    /*
    AOP：Aspect Oriented Programming，面向切面编程、面向方面编程：可简单理解为就是面向特定方法编程

    应用一： 在一个项目中，会有数十上百乃至上前个方法，每个方法的耗时都不相同
            我们可以通过记录方法的耗时，统计耗时，判断哪些方法需要进行优化

    记录耗时的思路：
    1. 在方法的开头，记录当前时间----》start
    2. 在方法的结尾，记录当前时间----》end
    3. 计算耗时：end - start

    但是：如果每一个方法都需要记录耗时，会非常麻烦，而且代码重复度高
        而且需要去原代码中进行改动

    这个时候：引入AOP类，对方法进行拦截，记录耗时

    AOP类的实现：
    1. Aspect注解，标识这是一个切面类
    2. component注解，放入到ioc容器中
    3. 类中：around注解：指定添加aop类的范围（可以指定一整个类、一整个包、一整个模块）
    4. 写一个recordTime方法，用来记录时间，这个时候，把方法作为参数传入-----》形参是：ProceedingJoinPoint pjp
        方法中直接写一个start时间
        方法中调用pjp.proceed()方法，执行方法体
        方法中写一个end时间
        方法中计算耗时：end - start
        返回耗时结果


     AOP类的优点：
     如果我们需要对多个方法进行操作，只需要在aop类中统一写处理方法即可
     优点：
     1. 减少重复代码
     2. 代码无侵入
     3. 提高开发效率
     4. 维护方便


     实操;
     1. 引入aop依赖-----》新版的不叫aop，叫aspectj
     <!-- 导入aop依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aspectj</artifactId>
        </dependency>

     2. aop类应当建在aop包中
     3. aop的应用场景：
        记录系统的操作日志
        事务管理
        权限管理
        等等
        等等
        等等

     */
    /*
    aop核心概念：
    1. 连接点：JoinPoint：可以被aop控制的方法（按含方法执行时的相关信息）
    2. 通知：Advice：指的是aop类中写的方法中重复的逻辑（或者说：是如果不使用aop，那么我们需要在每个方法中重复书写的代码）
            也就是共性功能----》最终集中到一个aop方法中
    3. 切入点：Pointcut：匹配连接点的条件，通知仅会在切入点方法执行时被应用
            也就是是通过@around注解，来指定切入点的范围
    4. 切面：Aspect：由切入点和通知这两部分构成的范围----》其实就是一个aop类
    5. 目标对象：Target ：通知所应用的对象---》就是aop类产生了作用，且把作用结果返回的那些对象

    1. 为什么“连接点 > 切入点”？
连接点（JoinPoint）—— 候选人：
在 Spring AOP 中，一个 Bean 类里所有可以被增强的方法，在客观上都是“连接点”。无论你写不写 @Around，这些方法本身都具备“可以被切入”的物理属性。

切入点（Pointcut）—— 被选中的候选人：
切入点是你通过表达式（如 @Around("...")）主动筛选出来的条件。

概念,角色定义,你的项目中的实际映射
连接点 (JoinPoint)：所有可能被拦截的方法：DeptServiceImpl 里的 list()、delete()、save() 等所有公开方法

切入点 (Pointcut)：实际决定要拦截的方法：表达式 @Around(""...delete(..)"") 所确定的那个 delete() 方法"

目标对象 (Target)：被代理的原始对象：未被织入切面逻辑前的 DeptServiceImpl 实例


aop的执行流程：
1. aop类中确定了切入点
2. 切入点匹配到连接对象----》确定为目标对象----》比如是：DeptController类
3. 创建目标对象的代理对象----》也就是创建DeptController类的代理对象：DeptControllerProxy
4. 然后：把aop类中的重复方法：start开始时间、目标对象中的方法体，end结束时间，耗时：end - start
5. 以上这些都放入到DeptControllerProxy动态代理类中，作为方法的拦截
6. 实际执行的时候，都是执行DeptControllerProxy类的方法，而不是DeptController类的方法

因此：aop的底层原理是：动态代理

工作流程：
创建了aop类后---把目标对象bean类和aop形成的动态代理类都放入到ioc容器中
往下，直接执行动态代理类中的方法，只有执行到不重复的方法体部分，才会调用目标对象的方法体

原理：aop是spring中的一个功能，底层默认aop动态代理类要先于目标对象bean类执行
修正：IOC 容器里到底放了什么？
原理解： “把目标对象 bean 类和 aop 形成的动态代理类都放入到 ioc 容器中”

实际情况： IOC 容器中最终只会暴露“代理对象”！

原理： Spring 在初始化 Bean 的生命周期后期（BeanPostProcessor），如果发现这个 Bean 被 AOP 切中了，就会直接用动态代理对象替换掉原始的目标对象。

结果： 其他组件（比如 Service 或 Controller）通过 @Autowired 注入进来的，直接就是那个代理对象，目标对象被隐蔽在代理对象内部（作为代理对象的一个属性）。
     */

    /*
    AOP进阶:
    1. 通知类型
        1. around：环绕通知：表示在目标方法执行前后aop动态代理类都会被执行
        2. before：前置通知：表示在目标方法执行前aop动态代理类会执行
        3. after：后置通知：表示在目标方法执行后aop动态代理类会执行---》无论有无异常都会执行---》事务嵌套
        4. afterThrowing：异常通知：表示在目标方法执行异常时aop动态代理类会执行
        5. afterReturning：返回通知：表示在目标方法执行正常返回时aop动态代理类会执行

        注意点：around环绕通知：必须自己调用proceedJoinPoint.proceed()方法，来让目标对象方法执行，
        其他通知不需要考虑目标对象方法执行

        另外：around环绕通知：返回值必须要设置为object类型，来接收原始方法的返回值（不能指定具体类型，以免类型不匹配）

        实操：springboot-aop-quickstart模块中的MyAspect类

        如果切入点表达式需要重复书写：
        可以定义一个@Pointcut注解来声明公共切入点的pt()方法即可
        @Pointcut("execution(* com.itheima.service.impl.DeptServiceImpl.*(..))")
        public void pt(){}

        后面只需要引用pt()方法：
        @Around("pt()")



    2. 通知顺序
        当有多个切面的切入点都匹配到了目标方法，目标方法运行时，多个通知方法都会被执行

        执行顺序：
        * 不同切面类中，默认按照切面类的类名字母排序
            目标方法前的通知方法（before、around的前半段）：字母排名靠前的先执行
            目标方法后的通知方法：（after、around的后半段）：字母排名靠前的后执行

        例如，有四个类：
        MyAspect1，MyAspect2，MyAspect3，MyAspect4.4个类中都对同一个切点定义了一个before方法和after方法

        before的执行顺序：MyAspect1 > MyAspect2 > MyAspect3 > MyAspect4
        after的执行顺序：MyAspect1 < MyAspect2 < MyAspect3 < MyAspect4

        但是这样的话，实在是不方便，因此spring提供了通过@Order(数字)加在类上来实现排序
        规则：
        目标方法前的通知方法：order中相对数字越小的越先执行
        目标方法后的通知方法：order中相对数字越大的越先执行

        如：
            MyAspect1---》@Order(5)
            MyAspect2---》@Order(3)
            MyAspect3---》@Order(16)
            MyAspect4---》@Order(2)

            before:MyAspect4 > MyAspect2 > MyAspect1 > MyAspect3

            after: MyAspect3 > MyAspect1 > MyAspect2 > MyAspect4

    3. 切入点表达式
        两种形式书写：
        1. 根据方法的签名：@Before("execution(public void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
                         public void before(){}
        2. 根据注解匹配：@Before("@annotation(com.itheima.annotation.Log)")
                         public void before(){}

       execution:根据方法的修饰符、返回值、包名、类名、方法名、方法参数等信息来匹配
                标准格式：
                例如：execution(public void 类名.类型名(参数1,参数2,参数3...))

                其中：修饰符、包名.类名、throws这三部分可省略

                通配符：
                * ：单个独立的任意符号，可以统配：返回值、包名、类名、方法名、任意类型的一个参数
                        但是，我们一般不会省略包名，不然，代码阅读性很差，而且会导致全包进行扫描寻找方法，性能很差
                        还可以跟名称组合，比如：* com.itheima.service.impl.DeptServiceImpl.del*()
                        或者是：* com.itheima.service.impl.DeptServiceImpl.*ById()
                        也就是通过*可以进行模糊匹配

               .. ：多个连续的任意符号，可以统配任意层级的包，或任意类型、任意个数的参数
                        其中：方法名(..)表示方法参数可以是任意类型、任意个数

               切入点除了可以匹配类，还可以匹配接口----》会默认寻找该接口的实现类方法

               要确认是否书写正确，可以通过点击左侧的m标志看是否能跳转到目标方法

               建议：
               1. 所有业务方法名在命名时尽量规范，方便切入点表达式快速匹配，比如：findxx，updatexx
               2. 描述切入点方法通常基于接口描述，而不是直接描述实现类，增强代码的可维护性和拓展性
               3. 在满足业务需要的前提下，尽量缩小切入点的匹配范围。如：包名尽量不是用..
                        而是使用单个* 来匹配单个包，避免进行大范围扫描


        2. 根据注解匹配
        annotation：先创建一个anno包，然后自己定义一个注解类，比如：LogOperation类
        实操：把我们自己定义的LogOperation注解类的全类名，复制粘贴到@Before注解中
        @Before("@annotation(com.itheima.anno.LogOperation)")
                         public void before(){}

          第二步：在目标方法上添加@LogOperation注解
          @LogOperation
          public void delete(Integer id) {
              deptMapper.delete(id);
          }


      总结：优先根据execution表达式匹配。如果无法解决，才使用基于注解的匹配。

    4. 连接点

        在spring中用joinpoint抽象了连接点，用它可以获得方法执行时的相关信息，如目标类名、方法名、方法参数等
        对于@around通知，获取连接点信息只能使用：proceedingJoinPoint对象
        对于其他四种通知，获取连接点信息只能使用JoinPoint，它是ProceedingJoinPoint的父类型
        joinPoin父类中，定义了4个方法：
        1. joinPoint.getTarget().getClass().getName():获取目标类名
        2. joinPoint.getSignature():获取目标方法签名
        3. joinPoint.getSignature().getName():获取目标方法名
        4. joinPoint.getArgs():获取目标方法的参数

        在before、after、afterThrowing、afterReturning这四种通知中，都可以使用joinPoint对象来获取连接点信息
        如：
         // 通过joinPoint来获取连接点信息
    @Before("@annotation(com.itheima.anno.LogOperation2)")
    public void before4(JoinPoint joinPoint) {
        log.info("before4，方法执行前执行");
        // 获取目标对象
        Object target = joinPoint.getTarget();
        log.info("目标对象：{}", target);

        // 获取目标类名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("目标类名：{}", className);

        // 获取目标方法名
        String methodName = joinPoint.getSignature().getName();
        log.info("目标方法名：{}", methodName);

        // 获取目标方法参数
        Object[] args = joinPoint.getArgs();
        log.info("目标方法参数：{}", Arrays.toString(args));
    }

    而在around通知中，获取连接点信息只能使用：proceedingJoinPoint对象
    它是JoinPoint的子类，所以，也可以使用JoinPoint的方法，也可以使用proceedingJoinPoint的方法

     */

    /*
    实操1. 利用aop记录日志，并把日志记录到数据库中
    准备工作：创建Logger实体类，用来记录日志信息
    创建log表，字段对应Logger实体类的属性

    创建logmapper接口，用来操作log表

    创建aop通知类，注意，按照需求，把logger实体类中的属性进行set

    最后把调用mapper的insert方法（传入logger对象），把日志记录到数据库中

    然后调用log进行打印输出到控制台

    后续需要完善的工作：操作人id这一个属性，需要通过调用jwt工具类中的令牌解析方法，获取当前登录用户的id，并把这个id赋值给operateEmpId

    思路：不要直接调用filter和拦截器中的方法，因为这两个类中定义的方法都只是用来判断令牌是否合法，最终决定是否需要放行

    但是，通知方法中，已经是对数据进行增删改，因此，令牌肯定已经是合法的，无需再对令牌的合法性进行判断，直接调用jwt工具类中的解析方法，
    获取当前登录用户的id

    令牌中记录的json数据格式：
    {"id":1,"username":"admin"}
    这样的话我们可以直接解析令牌，获取到json数据，然后读取map中的key=id的value，就是当前登录用户的id

    实操：
    1. 在aop通知类中先获取token---》token在header中
    2. 调用jwt工具类中的解析方法，把token传入解析方法，json数据对象（map）
    3. 对json数据对象调用get方法，获取key=id的value，就是当前登录用户的id
    4. 把当前登录用户的id赋值给operateEmpId

    更简单的实现办法：不要解析两次，而是在拦截器或过滤器中调用解析方法时，就先保存解析出来的json数据
    然后，在aop的通知方法中，获取保存的json数据即可

    两种实现方法：
    1. 把解析出来的json数据保存到request对象中，然后aop类中从request对象中获取json数据
        实操：
        第一步：拦截器中把解析出来的json数据传入request对象中：
        // 令牌解析成功
        Claims claims = JwtUtils.parseToken(token);

        // 从 claims 中取出 id 和 username
        Integer empId = (Integer) claims.get("id");
        String username = (String) claims.get("username");

        // 存入 request 的属性域（Attribute）中
        request.setAttribute("operatorId", empId);
        request.setAttribute("operatorName", username);

        log.info("令牌解析成功，放行请求");
        return true;

        第二步：aop类中创建request对象并获取其中的json数据
        // 通过 RequestContextHolder 拿到当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 直接取出拦截器刚才存入的数据，无需再次解析 Token！
        Integer operateEmpId = (Integer) request.getAttribute("operatorId");



    2. 把解析出来的json数据保存到threadlocal中，然后aop类中从threadlocal中获取json数据
        1. 定义一个线程上下文工具类 BaseContext
            类中，需要先创建线程对象
            然后写一个set方法、一个get方法、一个释放资源方法

        2. 在拦截器（或过滤器） 中，在解析token成功后，把解析出来的json中的id(结果.get("id")),要记得强转为id对应的类型
            通过BaseContext类中的set方法，保存到threadlocal中

        3. 在aop类中，从BaseContext类中get方法，获取保存的json数据


        threadLocal解析:
        threadLocal并不是一个Tread，而是Thread的局部变量
        threadLocal为每个线程提供一份单独的存储空间，具有线程隔离的效果，不同的线程之间不会互相干扰。
        如：可以创建threadLocal1，threadLocal2，threadLocal3，这样的三个类
        这样的话，这三个类都可以分别存储一个线程本地变量（ThreadLocalMap），而且不会互相干扰

        threadLocal中常用方法：
        1. public void set(T value)：设置当前线程局部变量（泛型T）的值
        2. public T get()：获取当前线程局部变量（泛型T）的值
        3. public void remove()：移除当前线程局部变量（泛型T）的值，释放资源


        threadlocal的应用场景：
        在同一个线程中/请求中，进行数据共享

     */
}
