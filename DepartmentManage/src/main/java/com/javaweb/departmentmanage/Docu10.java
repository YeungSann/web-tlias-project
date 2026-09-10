package com.javaweb.departmentmanage;

public class Docu10 {
    /*
    maven高级
    1。 分模块设计与开发
        在现代的软件开发中，一个大型项目往往会有多个模块组成
        每个小组只负责一个模块的开发，其他小组的代码不会被修改
        如何确保每个模块之间的依赖关系清晰，不会出现循环依赖

        同时为了代码后续的：维护、复用、扩展

        解决：分模块设计与开发

        拆分为若干个子模块，子模块之间引入依赖关系，实现模块之间的解耦

        项目拆分策略：
        策略一：按照功能模块拆分，比如：公共组件、商品模块、搜索模块、购物车模块、订单模块等
                mall-common、mall-product、mall-search、mall-cart、mall-order

        策略二：按层拆分，比如：公共组件、实体类、控制层、业务层、数据访问层
                mall-common、mall-pojo、mall-controller、mall-service、mall-mapper

        策略三：按照功能模块 + 层拆分
                mall-common、mall-product-controller、mall-product-service、mall-product-mapper
                mall-search-controller、mall-search-service、mall-search-mapper

      实操任务：
      按照策略一将Tlias项目进行拆分：
      tlias-pojo、tlias-utils、tlias-web-management（这个模块包含主要的业务逻辑、数据库操作、页面展示等）
      拆分后，只需要在tlias-web-management模块中引入其他模块的依赖关系，即可实现模块之间的解耦

      要注意：
      实际开发中：
      一定是先针对模块功能进行实际，再进行编码。一定不会先将工程开发完成，再进行拆分。


      第一步：迁移
      1. 先创建一个新的、空项目
      2. 在项目窗口中设置项目的sdk版本和语言，与拆分前的项目一致
      3. 设置新项目路径中maven的设置
      4. 打开旧项目的文件目录
      5. 直接把旧项目整个文件夹复制粘贴到新项目的文件目录中
      6. 关闭旧项目
      7. 因为旧项目是作为一个子工程来创建，而在新项目中，是要作为父工程来管理的
         因此迁移之后，需要重写写入pom中的parent以及springboot和mybatis的版本号
      8. 测试后，确保项目能够正常运行---》然后才可以进行拆分

      第二步：拆分pojo
      1. 在web-tlias-project工程下，新建一个模块：java、maven、不用指定父工程，名称：tlias-pojo
      2. 建同名包：com.javaweb.departmentmanage.pojo
      3. 直接把原项目的pojo文件夹复制粘贴到tlias-pojo模块中
      4. 在tlias-pojo模块中，引入lombok依赖（版本号参考maven面板中的版本号）
      5. 删除原项目中的pojo文件夹
      6. 在原项目的pom中引入tlias-pojo模块的依赖关系
      7. 刷新后，运动启动类，确保项目能够正常运行

      第三步：拆分utils
      要添加的依赖：lombok、jjwt、spring-boot-starter核心包依赖、阿里云的三个依赖


    2. 继承与聚合
        maven的继承概念：描述的是两个工程之间的关系，与java中的继承相似，子工程可以继承父工程中的配置信息，常见于依赖
                        关系的继承
       作用：简化依赖配置、统一管理依赖
       实现：<parent></parent>

       打包方式：
       jar：常见：内嵌有tomcat服务器运行
       war：基本不使用：需要部署到tomcat服务器上运行
       pom：父工程或聚合工程，该模块不写代码，只进行依赖管理

       实操：
       1. 创建maven模块tlias-parent，该工程为父工程，设置打包方法为pom（默认jar）
       2. 注意：
                maven跟java一样，不支持多继承：也就是一个子工程只能有一个父工程
       3. 在子工程的pom文件中，配置parent标签，指定父工程的坐标信息
       4. 在父工程中配置各个工程共有的依赖（子工程会自动继承父工程的依赖）
       5. 依赖管理：要注意：只抽取公共的依赖，不要抽取子工程的依赖
       依赖管理的区分，两种
       第一种：<dependencies></dependencies>
                这种方式，是强制所有子工程都必须继承的依赖

       第二种：<dependencyManagement></dependencyManagement>
                这种方式，父工程只定义，子工程可以根据需要引入，也可以不引入
                只管理依赖的版本号，子工程想要引入还是需要自己引入但不需要指定版本号

       6. <dependencyManagement>中管理的依赖数量比较多的时候，要修改版本号也会变得麻烦
            可以把版本号抽取出来，放到<properties></properties>中进行全部依赖的版本号管理，
            子工程在引入依赖时，只需要指定依赖的坐标信息，以及引用<properties></properties>中的版本号即可
            引用方式：<version>${依赖名.version}</version>


       7. 这样处理完之后，子工程之间虽然导入了各自的依赖（主要是departmentmanage导入了utils和pojo的依赖）
            但是真正运行的时候，并不能识别tuils和pojo这两个依赖，原因：还未被打包发送到本地仓库中
            解决办法：
                把utils、pojo、父工程都进行maven的安装
            安装之前，为了确保顺利，需要先进行clean操作
            然后再install

   聚合：
   将多个模块组织成一个整体，同时进行项目的构建

   聚合工程：
        一个不具有业务功能的 空 工程（有且仅有一个pom文件）---- 》 类似于父工程

        因此：我们一般就会把 聚合工程 和 父工程 合为一个整体

        作用：快速构建项目 （ 无需根据依赖关系手动构建， 直接在聚合工程上构建即可）

        实现：
            <modules></modules>设置当前聚合工程所包含的子模块名称（无序）


   聚合于继承的区别：
        1. 继承用于简化依赖管理、统一管理依赖版本。是在子工程中配置继承关系
        2. 聚合用于快速构建项目，是在父工程中配置聚合的模块



    3. 私服

    公司内部如何实现资源共享：也就是自定义项目jar共享

    maven中jar包的查找顺序：本地仓库----》maven中央仓库
    但是公司内部的jar包：不能上传到maven的中央仓库中

    解决办法：公司内部局域网中，创建一个私服，公司内部的jar包，都可以上传到私服中
    只有本地仓库中没有的jar包，才会从私服中下载，私服中也没有的jar包，才会从maven中央仓库中下载

    而且，还可以把需要共享的jar包上传到私服中，其他工程可以直接引入私服中的jar包

    关键：如何把资源上传到私服， 如何从私服中下载资源

    版本说明（仓库目录）：
    1. releasse：发行版本，功能趋于稳定、当前更新停止，可以用于发行的版本，存储在私服的release仓库中
    2. snapshot：快照版本，功能还在更新中、尚处于开发中的版本，可以用于测试的版本，存储在私服的snapshot仓库中

    1. 上传资源到私服
        1. 先把模块进行install，安装在本地仓库中
            idea：设置上传资源的位置（私服的url）
            私服：用户名/密码
            私服内部：分为release仓库和snapshot仓库

            在maven的setting.xml文件中，servers配置私服仓库、用户名、密码
            在maven的pom文件中配置私服仓库的url
                注意：
                url的后缀标识是maven-release或maven-snapshot版本
            在setting.xml文件中设置jar包下载的镜像地址

            同时pom文件中要设置是否允许对snapshot目录的访问

        2. 通过deploy调度指令，把本地仓库中的jar包上传到私服中
    2. 从私服中下载资源

    实操：
    直接在d盘中解压nexus，在bin文件夹中双击运行start.bat，等待启动私服
    端口号：8081
    访问路径：http://localhost:8081

    找到maven的安装包/conf/setting.xml文件
    找到文件中<servers></servers>标签
   粘贴配置的私服仓库目录和用户名、密码
    <server>
    <id>maven-releases</id>
    <username>admin</username>
    <password>admin</password>
</server>

<server>
    <id>maven-snapshots</id>
    <username>admin</username>
    <password>admin</password>
</server>

    然后：同样是在setting文件中，继续设置私服一路来下载的仓库地址
    找到<mirror>标签
    粘贴：
    <mirror>
    <id>maven-public</id>
    <mirrorOf>*</mirrorOf>
    <url>http://localhost:8081/repository/maven-public/</url>
</mirror>
粘贴之前，需要先把原来阿里云的私服仓库注释掉

继续在setting中设置对仓库的访问权限（默认只能访问release仓库）
找到<profiles></profiles>标签
粘贴：
<profile>
    <id>allow-snapshots</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <repositories>
        <repository>
            <id>maven-public</id>
            <url>http://localhost:8081/repository/maven-public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
</profile>


最后，在maven父工程的pom文件，配置私服的地址，已经上传的仓库目录、url
<distributionManagement>
    <!-- release版本的发布地址 -->
    <repository>
        <id>maven-releases</id>
        <url>http://localhost:8081/repository/maven-releases/</url>
    </repository>
    <!-- snapshot版本的发布地址 -->
    <snapshotRepository>
        <id>maven-snapshots</id>
        <url>http://localhost:8081/repository/maven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>


以上都配置完成后，刷新maven
在父工程中，声明周期执行deploy指令，把本地仓库中的jar包上传到私服中


下载：
在jar上传到私服之后，只需要在maven中写入依赖坐标和版本号，就可以从私服中下载到本地仓库中


演示完毕之后，直接把的所有配置注释掉。以免后面学习时，影响到其他工程
     */
}
