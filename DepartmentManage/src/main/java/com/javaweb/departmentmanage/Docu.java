package com.javaweb.departmentmanage;

public class Docu {
    /*
    工程搭建
1.	创建spring boot工程，引入webstarter、mybatis、mysql、lombok依赖
2.	创建数据库表dept，并在application.yaml中配置数据库的基本信息
3.	准备基础代码结构，并引入实体类dept及同一的响应结果封装类Result

    实际操作
    原理：还是依据最终实现的页面功能来设计代码
    1. 查询部门功能实现
            1.1 接口开发
                页面原型：查询部门列表页面
                显示内容：序号、部门名称、最后操作时间、操作（包括两个按钮：编辑、删除）

                规则：
                1. 由于部门的数据量较少，不考虑分页展示
                2. 对查询的结果，根据最后修改时间倒序排序

                1. 点击新增部门，对打开新增部门的页面
                2. 部门名称，必填，唯一，长度为2-10个字符之间

                1. 删除部门：弹出确认框，提示：“你确定要删除该部门的信息吗” 如果选择确定，则删除该部门，删除成功后，重新刷新
                    列表页面，如果选择了取消按钮，则不执行任何操作。

                1.1 部门列表查询
                1.1.1 基本信息
                请求路径：/depts
                请求方式：GET
                接口描述：该接口用于部门列表数据查询

                1.1.2 请求参数
                无

                1.1.3 响应数据
                参数格式：application/json
            1.2 前后端联调测试


        写好查询的三层结构代码后（controller、service、mapper）
        发现Dept封装的id和name字段能正常请求数据
        但是使用驼峰命名的createTime和updateTime字段不能正常请求数据
        原因：
        实体类使用了驼峰命名，而数据库表中使用的字段是下划线命名，导致查询结果中没有这两个字段

        解决办法：
        1. 手动结果映射
            通过@Results及@Result注解，手动映射查询结果中的字段到实体类的属性
            在mapper接口中添加@Results注解，指定映射关系
            @Results({
                @Result(property = "createTime", column = "create_time"),
                @Result(property = "updateTime", column = "update_time")
            })
        2. 在sql文中对使用驼峰命名的字段采用起别名的方式进行映射

        3. 在yml配置文件中开启驼峰命名开关即可
            mybatis:
              configuration:
                # 开启驼峰命名开关--->mybatis自动封装
                map-underscore-to-camel-case: true


     前后端联调测试
     安装nginx，把自带的html文件夹中的50x和index文件删除，然后都放入需要测试的assets文件夹，同时把自己需要测试的前端
     引导文件index放入到html文件夹中，开启nginx即可进行测试
     如果localhost:90无法显示，那么就去conf文件夹中修改nginx文档，把端口修改为90

     直到localhost:90能正确显示前端页面

     注意，localhost:90能显示前端页面不代表着联调测试成功，
     还需要点击部门管理按钮，看能否成功跳转到后端查询的部门信息列表。如果不行
     需要手动前往nginx的conf中修改nginx.conf文件，把路径进行修改
     修改的核心：
     server {
        listen       90;
        server_name  localhost;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location  /api/ {
            proxy_pass http://localhost:8080/;
        }
        修改为后端提供的localhost:8080/api/路径



        nginx的反向代理：
        当我们进行前后端联调测试的时候，数据流程大概是前端发送请求------》后端服务器接收到请求------》返回响应数据

        但是实际业务逻辑中，一个前端可能会面对多个后端服务器，每个后端服务器都有自己的接口路径
        如果所有的后端服务器都暴露出来，都能够让前端直接进行访问，这对于后端服务器来说是很不安全的

        因此，nginx在中间作为一个代理服务器，前端的请求都同一发送到nginx代理服务器上，nginx代理服务器会根据请求的路径，
        将请求分发给对应的后端服务器，这样后端服务器就不需要暴露出来，增强了安全性。

        此外：通过代理服务器来处理，后续后端服务器独立进行扩展也更灵活，不会影响到前端的正常访问

        还有，如果对于一群后端服务器采用集群管理，还可以实现后端服务器的负载均衡，
        这样可以提高后端服务器的并发处理能力，避免单点故障。----》javaSE讲过的权重分配算法


        location  /api/ {
            proxy_pass http://localhost:8080/;
        }这一部分其实就是nginx所配置的反向代理信息


        也可以写作：
        location ^~ /api/ {

            rewrite ^/api/(.*)$ /$1 break;

            proxy_pass http://localhost:8080;
        }

        上面这种写法，是因为proxy_pass http://localhost:8080/后面带的这个斜杠，代替了rewrite中的写法

        教程中使用^~ 这个标记：在实际业务中会经常使用，因为这样可以提高匹配优先级，让配置更健壮（防止后续增加静态资源正则匹配时冲突）

     */

    /*
    删除部门

    1. 通过部门表中的id字段对部门进行删除操作----》id是主键，带有唯一标识

    2. 链接：/depts
    3. 请求方式：delete

    4. 请求参数：id---》传入谁的id就删除对应的部门

    请求样式：/delete?id=1

    响应数据：application/json
    ---》实际上，返回的都是一个标准的result实体类对象


    实际操作：
    在mapper接口中增加delete方法，声明@delete注解用于接收sql语句
    在service层中增加deleteById方法，调用mapper接口中的deleteById方法
    在controller层中增加deleteById方法，调用service层中的deleteById方法

    本质上的数据流程：
    前端页面点击部门管理页面中的删除按钮时
    -----》把对应的部门id发送给后端的controller层
    -----》controller层调用service层中的deleteById方法，删除部门
    -----》service层调用mapper接口中的deleteById方法，删除部门
    -----》返回成功结果：删除成功

     */

    /*
    新增部门
    业务逻辑：
    1. 前端页面点击新增部门按钮，弹出新增部门的弹窗
    2. 前端页面填写新增部门的名称和描述-----》用户填入的信息作为sql语句返回给后端的controller层
    3. 后端的controller层调用service层中的add方法，调用service层中的add方法，调用mapper接口中的insert方法，新增部门
    4. 后端的controller层返回成功结果：新增成功


    基本接口信息：
    1. 请求路径：/depts
    2. 请求方式：post
    3. 请求参数的格式：application/json
        样式： name: "教研部"
    4. 响应数据格式：result实体类对象


    其中，后端能接收到的参数是：name的关键是：
    controller层中的@requestbody注解，这个注解把前端发送的json字符串，转换为对应的实体类对象，再赋值给controller层中的参数

    后端测试：
    idea中启动程序后
    在postman中选择post方式，
    在postman的body中：
    填写请求路径：/depts
    填写请求体：(注意选择json格式)
    { "name": "教研部" }


    前后端联调测试：
    启动程序后，打开localhost:90页面，进入到部门管理页面后，点击新增部门按钮
    确认后，即可完成新增部门操作

    补充：
    不管是post请求方式，还是put请求方式，都需要在请求体中填写请求参数
    都需要使用到@requestbody注解，把前端发送的json字符串，转换为对应的实体类对象，再赋值给controller层中的参数
     */

    /*
    修改部门
    修改部门需要的流程相比之前更为复杂
    分两步：
    一： 前端页面先点击部门管理页面中的修改按钮，弹出修改部门的弹窗
        弹窗中显示当前部门的名称
        ---》这需要查询回显方法---》也就是根据用户的点击，查询当前部门的名称


    通过id查询的基本数据：
    基本接口信息：
    1. 请求路径：/depts/{id}
    2. 请求方式：get
    3. 请求参数的格式：application/json
        样式：  /depts/1
        关键，/depts/后面不能采用硬编码，必须采用路径参数id
         // 必须使用@PathVariable注解，来接收路径参数id
        // 当然，也可以不使用@PathVariable注解，直接使用id参数，但是这样写的话，路径参数的名称必须和形参名一致
    4. 响应数据格式：result实体类对象

    二：用户输入新的部门名称，点击确认按钮，提交修改请求
        这与上面的新增部门的流程相同，只是请求路径不同，请求方式不同，请求体中的参数不同，响应数据格式不同
        基本接口信息：
    1. 请求路径：/depts
    2. 请求方式：put
    3. 请求参数的格式：application/json
        样式： { "id": 1, "name": "教研部" }
        注意：service层中需要写入更新时间（通过获取now()方法）
    4. 响应数据格式：result实体类对象


    注意点：
    因为请求样式是：{ "id": 1, "name": "教研部" }
    因此，在postman中进行前端测试的时候，需要在body的请求体中填写 { "id": 1, "name": "教研部" }
    这样的请求参数
    但是，在前后端联调测试中，因为我们已经通过get请求，查询到了当前部门的id，所以不需要像postman那样填写id参数
     */

    /*
    RESTful规范：
    因为controller层中所有的方法都有共同的请求路径/depts

    因此，我们可以直接在controller实现类的开头位置，声明一个公用的@RequestMapping("/depts")注解
    这样，所有的方法的请求路径都就会以/depts开头
    不需要下面的各个方法再重复书写路径
     */

    /*
    日志技术

    在我们现在书写的controller层中，是通过打印，直接把获取的数据、成功与否直接打印出来

    但是：打印的结果只能输出到控制台，对于用户来说，是看不到的
    同时，也不便于我们对程序的扩展、维护

    好比生活中的日记，可以记录生活中的点点滴滴

    日志可以记录程序的运行信息、状态信息、错误信息等

    通过日志，可以进行：
    数据追踪、性能优化、问题排查、系统监控

    技术一：
    使用sun的原生日志类：java.util.logging
    简称：JUL，是javaSE平台提供的官方日志框架，但是功能比较简单，不建议使用

    比较流行的日志框架：Log4j
    简称：Log4j，是一个基于java的日志框架，功能强大，支持自定义日志级别、日志格式、日志输出等

    现在主流的日志框架：Logback
    基于Log4j升级而来，提供了更多的功能和配置选项，性能由于Log4j


    其他：Slf4j
    全称：simple logging facade for java：简单日志门面，提供了一套日志操作的标准接口及抽象类，允许应用程序
    使用不同的底层日志框架


    那就是说：我们先使用Slf4j接口，规定项目需要使用什么类型的日志框架，然后项目中就只能实现该规定的日志框架

    最终结构：
    1. 日志接口：Slf4j --- 》规定项目需要使用Logback日志框架
    2. 日志实现：Logback --- 》实现Slf4j接口，提供日志记录功能



    logback快速入门：
    1. 准备工作：引入logback依赖，但是实际上并不需要，因为现在使用的是spring boot，spring boot已经集成了logback日志框架
    2. 配置文件：logback.xml
    3. 记录日志：定义日志记录对象Logger，记录日志。
            日志测试的时候，需要先声明一个Logger常量（日志记录器），来记录日志
            日志记录对象：org.slf.slf4j.LoggerFactory.getLogger(类名.class)



    这里是日志开关：
    <!-- 日志输出级别 -->
    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>

    如果测试通过了，可以把开关里的debug修改为off，来关闭日志记录
     */

    /*
    日志配置文件：logback.xml
    这个配置文件是对logback日志框架输出的日志进行控制的，可以来配置输出的格式、位置及日志开关等

    1. 输出位置：
            有两处：一是控制台输出，二是文件输出，三是数据库输出（不常用，太耗性能）
            控制台输出：STDOUT
                <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            文件输出：FILE
   2. 开关：
            开启日志：ALL
            关闭日志：OFF
            <root level="ALL">
                <appender-ref ref="STDOUT" />
                <appender-ref ref="FILE"/>
            </root>

   3.日志级别（由低到高）：
            TRACE：追踪：记录程序运行轨迹（很少使用）， 记录方式：log.trace("...");
            DEBUG：调试，记录程序调试过程中的信息，实际应用中一般将debug视为最低级别（较多使用），记录方式：log.debug("...");
            INFO：记录一般信息，描述程序运行的关键时间，如：网络链接、io操作（使用较多），记录方式：log.info("...");
            WARN：警告信息，记录潜在有害的情况（使用较多），记录方式：log.warn("...");
            ERROR：错误信息，记录程序运行中的错误信息（使用较多），记录方式：log.error("...");

     比如：
     <!--  日志输出级别  -->
    <root level="info">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>
    这里定义的是info级别：就是意味着，只有info级别及以上的日志，才会被记录到日志文件中
    像trace、debug级别的日志，就不会被记录到日志文件中


    最后，我们前往controller层中添加日志对象，然后把打印输出更改为日志记录
     */
}