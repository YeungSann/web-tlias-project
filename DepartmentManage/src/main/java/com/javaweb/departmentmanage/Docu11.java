package com.javaweb.departmentmanage;

public class Docu11 {
    /*
    前端实战
    vue：基本使用：vue核心包的使用
        包括：声明式渲染（基于json格式）
        组件系统
        以上可以通过会看第二天的课程

    vue核心包 + vue工程化开发-----》 整站开发
    使用vue的核心包 + vueRouter + vuex + webpack



    谨记：vue是一个框架，也是一个生态


     */
    /*
    现在企业的前端项目开发要求：
            与之前入门所了解的完全不一样
    而是，要求：
        1. 模块化
            把项目拆分成多个模块，每个模块负责不同的功能
            单独开发、维护，提高效率
        2. 组件化
            将页面的各个组成部分封装为一个一个的组件，提高代码的复用性
        3. 代码规范
            提供标准统一的目录结构、编码规范、开发流程
        4. 自动化
            项目的构建、开发、测试、打包、部署等流程，实现自动化，提高开发效率

     以上四项统称： 前端的工程化

     优点：
        统一开发
        提高复用
        便于维护
     */
    /*
    环境准备
    1. create-vue是vue官方提供的最新的脚手架工具，用于快速生成一个工程化vue项目

    create- vue提供了以下功能：
        1. 统一的目录结构
        2. 本地调试
        3. 热部署
        4. 单元测试
        5. 集成打包上线

     create-vue脚手架的依赖环境：nodeJs

     nodeJS是一个免费、开源、跨平台的JavaScript运行时环境

     先安装nodeJs，配置node的环境变量，用户变量和系统变量中都要配置

     NPM是nodeJs的包管理器，用于安装和管理项目依赖的包------》类似与maven

     安装create-vue脚手架工具：
     命令行中输入：npm create vue @3.3.4
     系统会自动下载并安装create-vue脚手架工具，安装时，几项询问均选择no即可

     安装成功标志：Local:   http://localhost:5173/

     vue工程化项目目录结构
     1. vite.config.js: 项目的配置文件，包含了项目的构建配置、插件配置等信息
     2. package.json: 项目的配置文件，包含了项目的依赖、脚本、配置等信息----->等价于maven的pom.xml文件
     3. node_modules: 项目的依赖包目录，包含了项目的所有依赖包
     4. src: 项目的源代码目录，包含了项目的的所有源代码文件
        1. main.js: 项目的入口文件，包含了项目的全局配置、路由配置、组件注册等
        2. App.vue: 项目的根组件
            *.vue
            这是核心文件：包括了js、css、html三部分结构
            在vue项目中也成为单文件组件（SFC）

            在其他地方，要是想要使用组件，直接import即可使用

        3. components： 项目的组件目录，包含了项目的的所有组件（通用组件）
        4. assets： 项目的静态目录，包含了项目的的所有静态资源，如图片、字体、视频等

    vscode中，点击打开的文件目录右上方三个点：勾选：NPM Scripts
    这样就能显示出项目的脚本命令

    后续，直接点击run，就可以启动项目
     */
    /*
    vue项目的访问：直接在浏览器中访问http://localhost:5173/即可

    vue的组件有两种不同的风格：选项式API 和 组式API

    选项式API：基于vue的核心包，用于创建vue组件
                其中包含：data()、methods()、mounted()等方法

    组合式API：定义的变量、函数、方法不需要在created()方法中调用

    自己开发
    先新建一个.vue文件
    结构：
    <script></script>
    <template></template>
    <style scoped></style>
    构建这三个组件结构，分别对应js、html、css三部分

    <!-- 定义JS，控制模板部分的数据和行为（JS文） -->
 <!-- 加上setup，告诉vue我们使用的是组合式api，所以vue可以对组件先进行预处理 -->
<script setup>
// 引入ref函数、onMounted函数，用于响应式数据和组件挂载时的回调
import { ref, onMounted } from 'vue'

// 声明一个响应式数据 count
const count = ref(0)

// 声明函数 ---> 注意，在组合式api中没有this，而每一个数据都只有一个value属性
function increment(){
    count.value++
}

// 导入钩子函数onMounted
onMounted(()=>{
    console.log('Vue mounted ...')
})

</script>

<!-- 模板部分，控制的是页面的结构（HTML） -->
 <template>
<!-- 直接输出上面定义的count变量的值 -->
 <!-- 每点击一次这个按钮，count变量的值就会增加1 -->
 <button @click="increment">点击自增: {{ count }}</button>
 </template>

 <!-- 样式部分，控制的是页面的样式（CSS） -->
<style scoped>

</style>



     */
    /*
    案例：基于组合式api完成用户列表数据渲染
    文件名：EmpList.vue
     */
    /*
    ElementPlus组件库
    ：一个基于vue的组件库，包含了丰富的组件，提供了很多设计好的，更美观的组件

    实操：elementplus快速入门：
    1. 创建一个新的vue项目----直接在vscode打开VUE文件夹，输入：npm create vue @3.3.4
        创建一个新的vue项目，项目名称：vue-priject2
        然后安装依赖：npm install
        继续启动项目：npm run dev

    2. 参照官方文档，安装elementplus组件库（安装在当前工程的目录下）
        在vscode中打开终端，输入：npm install element-plus@2.4.4 --save进行安装

    3. main.js中引入elementplus组件库（参照官方文档）
        引入element-plus组件库
        import ElementPlus from 'element-plus'
        import 'element-plus/dist/index.css'


    完成以上准备工作后，前往element官网，查看如何制作组件


    element中的常见组件：
    1. 表格组件
        练习以带边框的表格为例
        直接把<template></template>标签和<script></script>这两个标签之间的代码复制到项目中即可

    2. 分页条组件
    3. 对话框组件
        效果：对话框浮现于页面上，用户可以点击确认按钮或点击取消按钮，也可以往对话框中输入内容

    4. 表单组件
     */
}
