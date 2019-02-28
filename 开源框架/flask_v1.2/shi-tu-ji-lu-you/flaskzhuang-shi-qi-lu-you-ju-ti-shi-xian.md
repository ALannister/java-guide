# 装饰器路由具体实现梳理

![](/assets/Flask路由实现代码结构.png)


Flask有两大核心：Werkzeug和Jinja2
    - Werkzeug实现路由、调试和Web服务器网关接口
    - Jinja2实现了模板。

Werkzeug是一个遵循WSGI协议的python函数库
    - 其内部实现了很多Web框架底层的东西，比如request和response对象；
    - 与WSGI规范的兼容；支持Unicode；
    - 支持基本的会话管理和签名Cookie；
    - 集成URL请求路由等。

Werkzeug库的 routing 模块负责实现 URL 解析。不同的 URL 对应不同的视图函数，routing模块会对请求信息的URL进行解析，匹配到URL对应的视图函数，执行该函数以此生成一个响应信息。

routing模块内部有：
- Rule类
    - 用来构造不同的URL模式的对象，路由URL规则
- Map类
    - 存储所有的URL规则和一些配置参数
- BaseConverter的子类
    - 负责定义匹配规则
- MapAdapter类
    - 负责协调Rule做具体的匹配的工作
    

