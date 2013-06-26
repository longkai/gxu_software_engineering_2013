## 2013学年度软件工程项目
这是广西大学计算机与电子信息学院计科102班软件工程课程[神舟10号]小组课程设计。

这里包含所有的源代码，资源文件，文档，以及IBM RSA uml图等。

__Android客户端__源码请见[这里][]

## 课设题目
校园二手交易市场。

## 解决方案
构造一个客户端，以及Android客户端来实现。

## 最低要求
jdk 1.6+ (1.5貌似也可以，但没有测试过)

maven 3+ (或者包含maven插件的IDE，推荐使用[STS][])

MySQL 5+ (推荐5.5.x+)

## 开发环境
JDK 1.7.10

[RSA][] 8.0.4

Eclipse 3.7.2 (包含maven插件)

MySQL 5.5.29

## 开发技术
服务端：Java ee（主要是springmvc，jpa-hibernate）

数据库：MySQL，需要将sql文件导入到数据库中

服务器：嵌入式 jetty（可以换到任何你喜欢的web容器或者服务器，比如tomcat，glassfish等）

## how to build
本项目使用[maven][]来构建，所以，必须有maven才能从本源代码构建，
推荐使用包含maven（m2e)插件的eclipse等集成开发环境。

下面以安装了maven插件的eclipse为例：

`File -> Import -> Maven -> Existing Maven Projects -> 选择本源代码的根目录导入`

等待，注意，可能时间会比较久，因为这个过程可能是maven去下载项目所需要的第三方依赖了，
所以，喝茶时间到。

ok，接下来将数据库文件导入MySQL，scheme文件在项目根目录下的
`src/main/resources/db/market.sql`

__注意__ 脚本首先会删除原来的market数据库然后重建，请检查你的是否有一个market的数据，
避免无不要的数据丢失！

然后进入 `src/main/resources/db/dataSource.properties` 修改你自己的数据库用户名密码等信息。

好，准备完毕，接下来，右击项目
`Run As -> Maven Build... ->` 在Goals输入框输入 `clean jetty:run -> Run`

ok, 如果没有问题的话，就会在控制台上看到日志，
最后当你看到`[INFO] Start Jetty Server`就表示应用成果启动了。

验证一下，浏览器输入`http://localhost:8080/categories.json`，当你看到某几个类别就表示ok！


## 不使用MySQL?
如果你使用的不是MySQL，那么你需要查看sql文件，将一些sql转换为你使用的数据库相关的sql方言。
另外，还需要修改一个很重要的配置文件，
`gxu.software\_engineering.shen10.market.core.SpringBeans.java`
这个应用程序的核心配置文件，目前是`154`行的那个方法。

以Oracle10g为例：
你需要将`Database.MYSQL`修改为`Database.ORACLE`
并且`org.hibernate.dialect.MySQL5InnoDBDialect.class.getName()`修改为
`org.hibernate.dialect.Oracle10gDialect.class.getName()`

同样也需要修改上面`dataSource.properties`文件.

此外，hibernate可以自动生成ddl建表语句，只需将SpringBeans的`147`行的注释打开即可。

## RSA 导入
如果想查看本项目的RSA模型，请确认你已经安装了RSA 8.0.4+，然后打开它，把本项目作为一个java项目
导入即可(类如可能会有很多错误，不过无关紧要了，因为是缺乏相关的jar引起的，但是我们看的是图)

## Contact us
If you hava any questions, please feel free to drop us a line!

Email: im.longkai@gmail.com

## License
Copyright 2013 Department of Computer Science and Technology, Guangxi University

This project is released under the [MIT License][].

[MIT license]: http://opensource.org/licenses/mit-license.php
[maven]: http://maven.apache.org
[RSA]: http://www.ibm.com/developerworks/cn/downloads/r/architecct/
[STS]: http://www.springsource.org/downloads/sts-ggts
[这里]: https://github.com/longkai/market-android
