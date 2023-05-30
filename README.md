# lms-utils
通用工具库，通用全局异常，redis配置，统一返回，分页设置

使用：
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
    
    
