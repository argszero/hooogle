hooogle
=======

## 简介

这是一个运维工具,他有如下特性：
* 基于SSH远程调用，不需要在应用主机上安装Agent。
* 基于Web，在浏览器上操作
* 提供简单的命令执行窗口，可以代替SSH工具执行一些简单的命令
* 内置对常用诊断命令的返回结果的智能解释
* 提供主机健康诊断功能

# 前端框架

* 前端框架的组件不好用啊

## 理想的前端框架

* 定义一个组件，shell.js
* 直接访问shell.js,显示这个Shell
* 定义另外一个组件，shells.js
* 在shells.js ,var Shell = require('shell'); new Shell().appendTo($('body'));

基于以上例子

* 组件应该是js文件。
* 组件可以引用某个html文件，引用方式是作为字符串，类似dojo的方式。
* 组件可以直接访问（如何做到？）
 * 定义一个component.html
 * 访问comonent.html?component=xxx

