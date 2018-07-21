# store
一个java原生servlet,jsp技术做的商城网站,主要用了三层架构,dao层用了jdbc连接数据库,c3p0连接池.前端用了jsp,html,js,ajax等技术,后台管理页面用的easyui
框架.
### 首页模块
  - header部分和foot部分进行了提取,通过jsp include技术引用实现复用
  - 前端页面除了index.jsp和login.jsp,其他页面都放在web-inf文件下,不能直接访问.需要通过servlet进行转发.
  - 导航条分类在页面加载完成后发送ajax请求,通过sevlet调用service查询分类表,并加入了redis进行缓存.第一次查询后会存入redis,以后都会从redis进行查询,
  减轻服务器
  压力.
  - web目录下的index.jsp页面只负责发送sevlet请求,sevlet接收请求后,会调用数据库查询最新和最热商品,并返回两个list集合,转发给前端index.jsp页面,进行展示.
  
