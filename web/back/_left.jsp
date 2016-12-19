<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<style type="text/css">
  		body{
  		.
  			background: #6495ED;
  			text-align: center;
  			font-size: 25px;
  		}
  		a{
  			text-decoration: none;
  		}
  	</style>
  </head>
  <body>

  <%-- 点击的逻辑就是查询所有的商品， 把他们存放在域中， 然后写一个界面去处理这些数据--%>
	<a target="_right" href="${app}/BackProdListServlet">商品管理</a><br>
	<a target="_right" href="#">用户管理</a><br>
	<a target="_right" href="#">权限管理</a><br>
	<a target="_right" href="${app}/SaleInfoListServlet">销售榜单</a><br>
  </body>
</html>
