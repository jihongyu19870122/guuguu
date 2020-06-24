百度、360搜索、搜狗搜索引擎快速收录、快速排名 [获取源码>>](http://www.hxdwe.cn:8001/product/seoz)

### 背景：

我是2019年左右接触的seo这个事情，之前虽然混迹互联网圈子，也听过关于seo的事情，但是一直没什么动力，并没有什么实际性需求，所以一直也未曾深入，而且对技术的了解，让我傲慢的以为seo不就是tdk的事情么;直到最后研究测试才发现，其中还是很复杂的，还是有很大的门道，尤其是在19年算法调整之后，之后通过不断的测试，不断的研究，中间研究测试过、快排、蜘蛛池、外链、秒拍、站群等；涉及百度、360、和搜狗，才测试出效果.

#### 功能支持：

| 引擎 | 是否支持快排 | 是否支持查看关键词排名 | 上词周期 | 上次率 |
| ---- | ------------ | ---------------------- | -------- | ------ |
| 360  | 支持         | 支持                   | 3-7/天   | 50%    |
| 搜狗 | 支持         | 支持                   | 3-15/天  | 82.5%  |
| 百度 | 支持         | 不支持                 | 3-30/天  | 32.7%  |

##### 亮点：

- 百度ct参数的算法；（但是现在好多已经不用这个参数了。我也是掺杂了一些其他参数）

- 搜狗快排参数的算法；

- 360快排参数的算法；这个我一直没找到什么高端参数，就是还是用最普通的方式，但是也是有效果；

  [获取最新版本程序源码+文档+快排参数>>](http://www.hxdwe.cn:8001/product/seoz)

##### 限制：

- 搜狗无需任何排名，只有收录即可，如果连收录也没有；请联系我“如何通过改站方式2天左右，被收录”；
- 360有排名限制，限制是在64页之内；如果你想优化的词没有基础排名；请联系我“如何提高初始收录既有排名的帖子”
- 百度、这个我测试上次率也比较低，而且周期有波动，我最后判断可能并不是
- 我的算法问题，可能是ip问题;

**运行后效果图：**

![](https://camo.githubusercontent.com/bcfeb03f06d98fdf877f8608ea2c33e6e03587b5/68747470733a2f2f73312e617831782e636f6d2f323032302f30352f32352f74397a7379562e706e67)

**测试效果图：**

![](https://camo.githubusercontent.com/cd875ac55c674d4096785beeb84b7a9596fe53ab/68747470733a2f2f73312e617831782e636f6d2f323032302f30352f32352f74397a7251302e706e67)

#### 技术选型：

- spring-boot
- Maven
- Jxbrowser

#### 常见问题&解答：

1. selenium 没有选，因为算法升级大部分都可以被识别出来（当然你也可以改造成不能识，别但是比起jxbrowser我觉得更麻烦），而且要模拟鼠标真实点击有点麻烦；
2. 没有选择前后端方案，只实现最核心的关键点；
3. http方案没有选，因为有点折腾；并且对效率要求不高，模拟度才是真的
4. 关于ip；ipip.net 大家可以去用自己的代理测试测试，国内能找得到的，基本上都能被识别出来、我测试了我能找到的所有资源；
5. 关于cookie，关于鼠标滚动轨迹，页面停留时长（搜狗，360实际测试效果后发现，目前不需要）
6. 关于refer，经过测试发现，关系不大；
7. 关于http header 欺骗方案，经过测试，发现意义不大；
8. 关于进入后点击内页，什么滚动，什么点击，我自己觉得纯属忽悠不懂的人（除非你用了官方统计代码或者收录代码等，不然官方能统计出来？纯扯淡，别跟我刚，是不是没挨过程序员的毒打）；
9. 关于点击时间分布、（360、搜狗，经过实际测试，发现不需要）

##### 其他问题

- 为什么百度不能每天收录几万条，为什么我的网站不收录
- 为什么我的网站不收录，（360、百度、搜狗）我都遇到过；
- 为什么我的网站被k站了，
- 为什么我的初始排名很低，甚至没有；（百度基本上就没有，360和搜狗还好当时）
- 当时听了各种教程，什么长尾词理论，什么外链，什么蜘蛛池、什么外链池、什么链轮、什么站内布局、乱七八糟的弄了一大堆，也花了一些钱，发现几乎效果微乎其微；包括还有一些所谓的大神的各种教程也都看了个遍，最后我是发现了。大神才不会把真正的东西告诉你；给钱都不行；

#### 目前快排现状：

- 百度可以找到的所有培训类seo方面、这个不多做评价，大部分自己还是在做百度付费推广，还有极个别是pz；
- 常见的 快排工具、大部分已经失效，少部分无论是按照点击付费，还是按照词收费，还是不上排名不收费，几乎都不会告诉你快排参数，和其中的关键点；
- 包括tb找到的商家，但是经过一一付款测试，发现大部分均未见明显效果，或者直接没有效果；
- 常见的蜘蛛池、外链交换工具，经过测试并未发现有明显效果；（不排除是自己的问题，我只是说我的测试结论）
- 各种论坛散落的大神、也不多做评价；反正一句话，是不可能说关键点的；
- 关于大小词问题；是有效果的；

#### 部署

linux环境需要以下基础（以centos7.6 64位为例）:

- yum install GConf2 -y
- yum install Xvfb -y
- yum install java-1.8.0-openjdk.x86_64 -y
- (安装chrome浏览器，版本不限)
- 如果yum源没有这些，就换成阿里的yum源（自行百度）

window环境&mac环境：

- (安装chrome浏览器，版本不限)
- jdk1.8 或以上

将配置文件放在项目根目录外:例如如项目在/usr/loc/soft 这个文件夹下；那么配置文件config.txt 就放在/usr/loc/config.txt

1. 启动命令： xvfb-run java -jar yourApplication.jar（yourApplication，这个名字，你自己down下来，后打包，的名字）
2. 后端启动: nohup xvfb-run java -jar yourApplication.jar >dev/null 2>&1 &

###### 开发环境部署测试：

- 前提是有自己的IDE如IDEA eclipse等；
- 安装有jdk1.8或以上
- 导入项目、项目入口在 main.Main

交流QQ：27789007
技术交流QQ群：1055802770

[获取源码>>](http://www.hxdwe.cn:8001/product/seoz)
