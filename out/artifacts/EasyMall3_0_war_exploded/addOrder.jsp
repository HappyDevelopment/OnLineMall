<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/12/15
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>订单添加页面</title>
    <link href="${app }/css/addOrder.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="/_head.jsp" %>
<div class="warp">
    <form name="form1" method="post" action="${app }/AddOrderServlet">
        <h3>增加订单</h3>
        <div id="forminfo">
            <span class="lf">收货地址：</span> <label for="textarea"></label>
            <textarea name="receiverinfo" id="textarea" cols="45" rows="5"></textarea>
            <br> 支付方式：<input name="" type="radio" value="" checked="checked">&nbsp;在线支付
        </div>
        <table width="1200" height="80" border="1" cellpadding="0" cellspacing="0" bordercolor="#d8d8d8">
            <tr>
                <th width="276">商品图片</th>
                <th width="247">商品名称</th>
                <th width="231">商品单价</th>
                <th width="214">购买数量</th>
                <th width="232">小计</th>
            </tr>
            <c:set var="money" scope="page" value="0"/>
            <c:forEach items="${sessionScope.cart}" var="entry">
                <tr>
                    <td><img src="${app }/ProductImgServlet?imgurl=${entry.key.imgurl}" width="90" height="90" /></td>
                    <td>${entry.key.name }</td>
                    <td>${entry.key.price}元</td>
                    <td>${entry.value}件</td>
                    <td>${entry.value*entry.key.price}元
                        <c:set var="money" scope="page" value="${money+entry.value*entry.key.price}"/>
                    </td>
                </tr>
            </c:forEach>

        </table>
        <br/><br/>
        <div class="Order_price">总价：${money }元</div>

        <div class="add_orderbox">
            <input name="" type="submit" value="增加订单" class="add_order_but">
        </div>
    </form>
</div>
<%@include file="/_foot.jsp" %>
</body>
</html>