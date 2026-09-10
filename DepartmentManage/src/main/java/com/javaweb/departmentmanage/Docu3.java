package com.javaweb.departmentmanage;

public class Docu3 {
    /*
    第三部分：新增员工功能

    在前端页面中，有一个新增员工按钮，点击该按钮后，会弹出一个对话框
    框中要填入：用户名、姓名、性别、手机号、职位、薪资、所属部门、入职日期、头像、工作经历（外挂到empExpr实体类）

    基本信息：
    请求路径：/emps
    请求方式：post
    请求参数格式：application/json
    必须参数：username、name、gender
    非必须参数：image、deptId、job等等

    响应参数：application/json
    响应数据：null

    sql语句：
    1. 保存员工的基本信息
    insert into emp (username,name,gender,phone,job,salary,entry_date,dept_id,create_time,update_time)
        values (?,?,?,?,?,?,?,?,?,?)

    2. 批量保存员工的工作经历信息
    insert into emp_exp (emp_id,begin,end,company,job) values
        (?,?,?,?,?,?),
        (?,?,?,?,?,?),
        (?,?,?,?,?,?)


    实操：
    1. 现在emp实体类中增加一个属性：
    private List<EmpExp> exps;
    作用：请求参数的时候，需要传入工作经历信息，但是emp实体类中没有工作经历属性
    因此需要导入empExpr实体类对象，来接收工作经历信息

    2. 三层架构中写入
    controller：save方法，接收请求参数（员工信息，直接通过员工对象传入）、调用service、响应结果（result）

    service：保存员工信息、批量保存员工的工作经历信息、然后把两个合并起来返回给controller

    mapper：两个insert into语句，分别对应保存员工基本信息和批量保存员工的工作经历信息
            其中EmpMapper写入插入员工信息
            EmpExprMapper写入批量插入员工的工作经历信息

    难点：
    1. 批量插入员工的工作经历信息
    2. 使用mybatis的批量插入功能，来批量插入员工的工作经历信息
    3. 语法：foreach标签，来批量插入员工的工作经历信息

    <insert id="saveBatchExpr" >
        insert into emp_expr (emp_id,begin,end,company,job) values
        <!--   collection属性指定我们要遍历的集合对象
             item：集合遍历出来的元素
             separator：每个元素之间的分隔符
              open：遍历开始前的括号
               close：遍历结束后的括号 -->
        <foreach collection="exprList" item="expr" open="(" close=")" separator=",">
            (#{expr.empId},#{expr.begin},#{expr.end},#{expr.company},#{expr.job})
        </foreach>
    </insert>


    但是：
     这里有一个问题，就是插入的emp_id是emp的id，
     因此需要使用mybatis提供的一个注解：@Options，注解中指定两个参数
     1是useGeneratedKeys，指定是否使用自动生成的主键，默认值为false----改为true
     2是keyProperty，指定主键的属性名（谁做主键）

     @Options需要添加在EmpMapper的save方法之上，来指定自动生成的主键的属性名是id
     然后我们就可以在service层中对非空的list进行遍历，同时利用getId方法把id的值赋值给empExpr对象的empId属性

     在service层中，需要使用@Transactional注解，来开启事务，提交事务，回滚事务
     确保save中的两个调用，要么同时成功，要么同时失败
     添加注解之后，还需要在yml配置文件中，配置事务管理日志为debug

    可以添加@Transactional注解的地方：类、方法、接口
    实际业务中：一般都加在方法上
    尤其是：需要对数据进行多次操作，比如：保存员工基本信息、批量保存员工的工作经历信息

    @Transactional注解的高级用法
    1. rollbackFor属性：用于控制出现异常类型，回滚事务

        不添加这个属性的时候，常见可能：
        我们在service中手动抛出一个异常，再运行，结果是：直接提交事务，没有回滚事务
        因此，我们需要添加rollbackFor属性，来指定出现异常类，回滚事务
        因为，如果不指定rollbackFor属性，那么，那么，默认是回滚运行时异常，不回滚编译时异常

    2. propagation属性：用于控制事务的传播行为
    也就是：当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行事务控制
    此处：至少出现了两个事务方法
    propagation中的属性值
    1. required：默认值，需要事务，有则加入，无则创建新事务--------------常用
    2. requires_new：需要新事务，无论有无，总是创建新事务--------------常用
    3. supports：支持事务，有则加入，无则在无事务状态中运行
    4. not_supported：不支持事务，无论是否有事务，都以非事务方式运行
    5. mandatory：必须事务，有则加入，无则抛出异常
    6. never: 必须没有事务，有则抛出异常

    也就是：比如我们在一个service层中
    @Transactional
    @Override
    public void 方法一(){
        //想要必须实现方法二----》使用try-finally
        try{
            方法一的主逻辑
        }finally{
            调用方法二
        }
    }
     正常来看，这似乎没有问题，好像方法二是一定会被执行

     实际：假如在try中出现了异常，finally中的方法二并不能被执行，原因：try中的异常导致事务回滚，finally中的方法二不会被执行

     解决办法：方法二也添加为事务,并指定propagation属性为requires_new
     这样，方法二也会被事务管理（无论如何也会创建方法二的事务），确保方法二的主逻辑不会被回滚
     @Transactional(propagation = Propagation.REQUIRES_NEW)
     @Override
     public void 方法二(){
        //方法二的主逻辑
     }
     这样，方法二也会被事务管理，确保方法二的主逻辑不会被回滚

     最终达到的效果：
     虽然方法一调用了方法二（实际应用场景也是，一个事务方法调用了另一个事务方法）
     而如果我们想要被调用的方法二，无论在什么样的情况下都能完成其事务逻辑，那么我们就可以给被调用的方法二
     添加上@Transactional注解且指定propagation属性为requires_new
     */

    /*
    文件上传
    这里需要我们上传一个image文件给服务器，因此涉及文件上传功能

    操作：
    1. 前端的三要素：
    from 中的提交方式必须为post
    enctype属性必须为multipart/form-data--------如果不设置，将只能提交文件名，而不是文件内容
    input标签的type属性必须为file

        <form action="/upload" method="post" enctype="multipart/form-data">
            姓名：<input type="text" name="name"><br>
            年龄：<input type="number" name="age"><br>
            图像：<input type="file" name="image"><br>
            <input type="submit" value="上传" name = "submit">
         </form>

         前端文件放在resources/static目录下

    2. 服务器端处理：
        定义一个上传的controller，post路径是/upload（与前端的action属性值一致）
        而且，传入的形参，名称必须与前端中定义的file的name属性值一致

        mybatis默认上传文件最大是1MB
        另外，还可以在yml配置文件中，配置上传文件的最大大小
       spring:
        servlet:
          multipart:
            max-file-size: 10MB
            max-request-size: 10MB

      执行上传文件操作后，idea会创建临时文件存储在c盘，一旦请求结束，就会删除临时文件


      文件存储的两种方式

      1. 本地存储
            直接在controller中，
            定义一个生成唯一文件名的方法

            然后在文件上传方法中，调用生成唯一文件名，

            然后指定文件路径，将文件存储到指定路径下
@PostMapping("/upload")
    // 方法形参中声明前端from表单中的变量，文件必须使用multipartFile类型
    public Result upload(@RequestParam("name") String name,
                         @RequestParam("age") Integer age,
                         @RequestParam("image") MultipartFile image) throws IOException {
        // 测试一下能否接收到参数
        log.info("name: {}, age: {}, image: {}", name, age, image);

        // 调用MultipartFile的getOriginalFilename方法，获取原始文件名
        String originalFileName = image.getOriginalFilename();

        // 保存文件
        // 调用MultipartFile的transferTo方法，将文件保存到指定路径
        image.transferTo(new File("D:/images/" + originalFileName));

        //返回结果
        return Result.success();
    }

    这样写，能够直接把文件按照原来的文件名存储在指定路径
    缺点：如果文件重名，会覆盖之前的文件

    解决：利用uuid 生成唯一文件名，但是要先去掉中间的-，
    同时还要获取原始文件名的后缀名，将uuid和后缀名拼接起来，作为新的文件名
    // 调用MultipartFile的getOriginalFilename方法，获取原始文件名
        String originalFileName = image.getOriginalFilename();
        // 随机生成uuid，并去掉中间的-
        String randomStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取原始文件扩展名
        String extension = originalFileName.substring((originalFileName.lastIndexOf(".")));
        // 拼接uuid和后缀名，作为新的文件名
        String newFileName = randomStr + extension;

        // 保存文件
        // 调用MultipartFile的transferTo方法，将文件保存到指定路径
        image.transferTo(new File("D:/images/" + newFileName));


      2. 云存储
      阿里云对象存储OSS（object storage service）

      使用第三方服务的通用思路：
      1. 准备工作:注册账户、实名认证、登录后台、根据需要购买服务

      2. 编写入门程序：参照官方sdk（软件开发工具包），sdk包括依赖jar包、代码示例等

      3. 继承使用：在项目中引入oss的sdk，调用sdk的方法，实现文件上传、下载等操作

      阿里云的实操使用步骤：
      1. 注册+实名认证
      2. 充值
      3. 开通对象存储服务OSS
      4. 创建bucket（存储空间，存储容器）
            bucket名称：java-spring-upload
      5. 获取并配置accessKey（密钥）
            获取之后，一定要保存好，同时要在自己电脑配置好环境变量
      6. 参照官方sdk，引入依赖jar包，编写入门程序
            一定是要参考官方sdk的文档，才能正确引入依赖jar包，编写入门程序

            从官方的示例文档（简单文件上传即可）中，直接粘贴到idea，并修改为自己的路径、bucket名称即可，完成文件上传


      7. 案例集成oss，实现文件上传、下载等操作

      url的传递：
      上传：本地路径 ------》 通过controller ------》 云上的url
      获取url：云上的url-------》 通过controller ------》 前端------》展示文件

      基本
      请求方式：post
      请求路径：/upload
      请求参数：multipart/form-data
      参数类型：file
      响应参数：json---》响应回来的data要指定一个url

      实操
      第一步： 把刚才测试的Demo文件上传示例文档，改造为一个文件上传的工具类
      第二步：上传文件接口开发----uploadController

      注意：
      这里有一个bug，就是前端的js文件中，定义的名称是file
      而后端定义的名称是image
      因此，需要在前端的js文件中，找到：

F(oe, {
  class: "avatar-uploader",
  action: "/api/upload",
  headers: { token: o.value }, // 这里的 o.value 对应源码中的 token
  "show-file-list": !1,        // !1 就是 false
  "on-success": x,
  "before-upload": T
})

然后改为：
F(oe, {
  class: "avatar-uploader",
  action: "/api/upload",
  name: "image",
  headers: { token: o.value }, // 这里的 o.value 对应源码中的 token
  "show-file-list": !1,        // !1 就是 false
  "on-success": x,
  "before-upload": T
})

但是，在前端中修改是比较麻烦的，最直接快捷的修改是直接在后端的uploadController中，修改参数的名称为file，让
后端名称跟前端名称一致即可
     */

    /*
    upload代码优化：
    原有代码的缺点：
    在阿里云工具类中，把云服务的机房地址、bucket名称、云路径都写死在了类中，后续要是变更机房地址、bucket名称、云路径，需要修改类中的代码

    更优的做法：
    1. 把云服务的机房地址、bucket名称、云路径都写到配置文件中
    2. 在工具类中，读取配置文件中的参数，而不是写死在类中
# 配置云服务的三个信息
aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    bucket-name: java-spring-upload
    region: cn-beijing


    但是这样还是有一个问题：
    如果添加的配置信息过多，每一次引入value时都需要写yml文件中的key名称，比较麻烦

    因此：
    进一步优化：
    把配置文件中的key名称，集中写到一个工具类中，

工具类：
    @Data
// 添加component注解，将类添加到spring容器中
@Component
// 添加配置文件中的前缀，用来接收前缀下的值
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSproperties {

    // 这是一个实体类，用来接收配置文件中的参数
    private String endpoint ;
    private String bucketName ;
    private String region ;
}

然后把工具类放入到IOC容器中，然后注入到AliyunOSSOperator类中，就可以在AliyunOSSOperator类中，直接使用yml文件中的参数值了
// 获取设置了yml参数的实体类，注入到本类中
    @Autowired
    private AliyunOSSproperties aliyunOSSproperties;

    // 因为这个文件是从网络上上传的，因此传入的对象是from表单中的multipart对象
    public String upload(MultipartFile file) throws Exception {
        // 获取yml文件中的参数值
        String endpoint = aliyunOSSproperties.getEndpoint();
        String bucketName = aliyunOSSproperties.getBucketName();
        String region =  aliyunOSSproperties.getRegion();
     */
}
