# lms-utils
本工具库提供了通用全局异常，redis配置，统一返回，分页设置，后端代码生成器

使用：
```yaml

//开启swagger配置
swagger:
  open: true
  scanPackage: com.lms.cloudpan
  title: cloudpan-server
  version: 1.0.0
  description: 简易网盘
  
//
#自设计的工具库starter,enableGlobalResponse开启全局响应，enablePage开启分页配置
lms:
  global:
    enableGlobalResponse: true   //开启全局返回
    enablePage: true   //开启分页
    enableRedis: true  //开启redis配置
    enableGlobalExceptionHandler: true //开启全局异常配置
```

​    我们也可以使用代码生成器:

```java
// 参数分别为 生成代码的包名  要生成代码的建表SQL DDL  生成代码输出的路径
LmsCodeGenerator.doGenerate("xxxx","sql","path");
```

