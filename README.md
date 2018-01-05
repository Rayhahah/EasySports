[toc]
# EasySports

## 声明
本项目部分API来自NBA中文官网与虎扑体育，纯练手之作，个人未从中获取任何利益，其所有内容均可在NBA中文官网与虎扑体育获取。
数据的获取与共享可能会侵犯到NBA中文官网与虎扑体育的权益，若被告知需停止共享与使用，本人会立即删除整个项目。

## 简介
项目采用 MaterialDesign + MVP + Retrofit2 + RxJava开发

大部分功能已经完成，如果对你有帮助的话不妨star一个o(*￣▽￣*)ブ

下面我就来总结一下吧哈哈哈哈

## 下载地址
现在已经在酷安网上架啦
召唤术：[传送门！！](https://www.coolapk.com/apk/173213)

## 应用部分截图
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/match.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/match_data.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/match_live.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/news.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/mine.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/form_detail.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/night_form.png?raw=true" width="280"/>
<img src="https://github.com/Rayhahah/EasySports/blob/master/screenshot/night_news_detail.png?raw=true" width="280"/>

## 一些收获
移动端和后端一把抓，学习了很多

- 尽可能简洁干净的编码规范
- 自认为良好的业务分包与功能解耦，清晰的网络请求
- 对三方服务和框架的二次封装
- 符合Material Design的界面，良好的动画过度

### 封装
对方法的使用或者第三方服务或是框架的使用都用到了封装
这里只说其中几点（也包括一下自定义View）

- 一个基于MVP的快速开发基类库`rbase`
    - Activity和Fragment的常用封装，结合RxJava
    - 异常收集类的封装并提供对外的服务器上传处理接口
    - 工具类的封装：`PermissionManager` 、`SPManager`等
    - 网络请求的统一管理
- 好用的弹窗库的封装`dialoglib`
- 网页浏览的封装`RWebActivity`,其内部集成了腾讯开源框架[VasSonic](https://github.com/Tencent/VasSonic)
- 对ZXing库的二次封装和自定义实现
- `ProgressLayout` 常用的加载、错误布局
- `TitleItemDecoration` 好用的粘性头部


### 第三方服务
- [ShareSDK](http://www.mob.com/downloadDetail/ShareSDK/android)
- [阿里热修复](https://www.aliyun.com/product/hotfix?spm=5176.8142029.388261.353.152a1d0fzhdgPS)
- [腾讯直播](https://cloud.tencent.com/product/LVB)

ps:本来也引入了Bmob构建用户系统和异常反馈系统，在引入自己搭建的后台接口以后就废弃掉了，bmob还是有点坑爹的。



### 框架
感谢这些开源框架的大力支持

- [BRAVH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) ： 功能强大的RecyclerViewAdapter封装库
- [glide](https://github.com/bumptech/glide) : 图片加载
- [GreenDAO](https://github.com/square/retrofit) : 数据库框架
- [Retrofit](https://github.com/square/retrofit) : 代码简洁，接口解耦
- [OkHttp](https://github.com/square/okhttp) : 网络请求
- [RxJava](https://github.com/ReactiveX/RxJava) : 快捷的线程切换，简洁的代码，清晰的逻辑，和Retrofit配合很爽
- [photoView](https://github.com/chrisbanes/PhotoView) : 图片的操作
- [eventbus](https://github.com/greenrobot/EventBus) ：时间总线，组件之间的简便通信
- [gson](https://github.com/google/gson) ： JSON序列化
- [VasSonic](https://github.com/Tencent/VasSonic) : Tecent开源，提升web的首屏请求速度
- [stetho](https://github.com/facebook/stetho) : Facebook开源的非常好用调试框架
- [BottomNavigation](https://github.com/Ashok-Varma/BottomNavigation) : 底部导航栏
- [JieCaoPlayer](https://github.com/lipangit/JiaoZiVideoPlayer) : 播放器，引入到项目中，并在其基础上自定义功能
### 接口
应用的数据分为两部分，自己搭建和数据抓取（[我的博客的抓取教程](http://rayhahah.com/2017/08/30/fiddler/)）

- 自己搭建的SSM后台系统[传送门](https://github.com/Rayhahah/Raymall)，并且已经发布在阿里云服务器上啦，干活满满的哟（要脸(✿◡‿◡)）
    - 直播接入
    - 版本更新
    - 用户管理系统
    - 崩溃信息采集
    - 用户信息反馈
- 剩下的接口是从虎扑体育、腾讯视频中爬取的，相当零散，相当繁杂，具体可以看`JsonParser.java`，里面有一些十分刁钻的Json数据的处理


## TODO
- 极光推送前端和后端的集成
- 视频播放器的替换
- 插件化

## 最后
[个人博客](http://rayhahah.com/)
[本项目的后台](https://github.com/Rayhahah/Raymall)

