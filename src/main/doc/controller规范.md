# controller规范
## 背景
一个复杂的系统，对controller的返回要有一定的规范，这样会极大减少代码量。
## 规范
### controller返回值规范
使用统一的ResultBean作为返回值，比单纯的普通数据对象包含了更加丰富的内容，功能也更强。比如包括数据，接口状态码，接口返回message。同时，程序内发生的任何异常也能够通过这个统一的接口定义返回给调用者。
### 统一异常处理
一般定义运行时的业务异常，程序内部要尽量少的catch异常，如果发生异常就向上抛，在AOP层进行统一异常catch并进行处理。
通过定义切面，可以在controller层统一处理异常，比如请求`http://localhost:8085/student?name=hihh&passportNumber=`，业务会判断passportNumber
不能为空，为空就抛出异常，在controller统一处理后，返回数据为：
   {
   "msg": "BusineseException(code=1012, message=护照号不能为空)",
   "code": 1,
   "data": null
   }
### 全局日志跟踪
当业务复杂时，多次请求打出的日志糅杂在一起，这个时候可以通过给每次请求生成一个唯一的id，并打印在日志中，就可以通过该id抽取出当次请求的所有日志，从而便于问题的查找。
