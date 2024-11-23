> 基于SpringBoot、MybatisPlus、SpringSecurity、Swagger2、redis、七牛云OSS的个人博客项目。


- 使用安全框架**Spring Security**和**Redis** 实现用户的登录加密、校验和权限认证
- 使用**Redis**对**高频访问的信息**进行缓存，降低了数据库查询的压力，解决了缓存穿透、雪崩、击穿问题
- 使用**Spring Aop**技术在访问接口时进行指定格式的日志记录
- 使用**七牛云OSS**实现文件上传功能，完成了将文件上传至云端存储的需求
- 使用**Validation和反射机制**实现对参数的**唯一性校验**，提高代码的可复用性
- 使用定时任务实现Redis与数据库的数据一致性



JDK版本为1.8，SpringBoot版本为2.5.0



采用多模块，其中yulag-framework为yulag-admin和yulag-blog都可能用的的模块，所以service层和mapper都写在framework模块内，controller层在admin和blog模块中。



Spring Security配置在admin和blog模块中的config包下，其中用到的过滤器在各自模块的filter包下，代替默认的UsernamePasswordAuthenticationFilter，实现从Redis识别token的功能。而异常处理逻辑都是相同的，所以放在framework模块的handler.security包下，包括无权限和未登录的异常处理



Redis对浏览量进行缓存的功能在com.yulag.service.impl.ArticleServiceImpl类下进行体现，例如查询文章时先从数据库中将基本信息通过MybatisPlus读取出来，但此时读出来的浏览量并不是最新的数据，因为缓存的数据此时可能还没有写进数据库中，所以还需要对浏览量进行替换，我使用JDK8的新特性Stream流以及Lambda表达式进行替换，具体可以查看articleList方法，我进行了注释。



其实还有数据一致性的问题，但我认为对于这个项目来说浏览量并不是重要的数据，丢失一部分数据是可以接受的，点赞也是同样的思路。



Spring Aop代码在com.yulag.aspect.myLogAspect类下，是AOP比较常见的应用——日志。



七牛云OSS的使用在com.yulag.service.impl.OssUploadServiceImpl类下，以及需要填写admin和blog模块的application.yml里myoss的信息。主要是用于文章封面图片的上传。



定时任务的代码在com.yulag.cornjob.UpdateViewCount类下，很简单的功能，主要是学习一下定时任务的使用，其实学习过linux基本都能看懂。



Validation的自定义注解在com.yulag.annotation包下的UniqueField和UniqueType以及com.yulag.core.validator包下的UniqueExtend接口和UniqueValidator类，一开始是想着增加用if语句判断字段在数据库中是否存在，但后面发现有很多唯一性的字段需要判断，且逻辑基本都相似，故想着进行抽象。找到validation封装了字段校验的很多方法，且能进行自定义，故找到自定义注解的例子进行学习并修改。

引用自 [自定义校验-数据库字段唯一性校验_mybatisplus 唯一性校验-CSDN博客](https://blog.csdn.net/AI_study/article/details/134088328)

主要修改了doValid方法对本项目进行适配。



还有全局异常处理类、字段自动填充、EasyExcel、Swagger2以及MybatisPlus的使用都是比较常规的操作，这里就不一一展开了。





















