<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/12/16
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <link href="${app}/css/orderList.css" rel="stylesheet" type="text/css">
</head>
<body style="text-align:center;">
<%@ include file="_head.jsp" %>
<c:forEach items="${ordersList}" var="orderInfo">
    <dl class="Order_information">
        <dt>
        <h3>订单信息</h3>
        </dt>
        <dd>
            订单编号：${orderInfo.order.id}<br/>
            下单时间：${orderInfo.order.ordertime}<br/>
            订单金额：${orderInfo.order.money}<br/>
            支付状态：
            <c:if test="${orderInfo.order.paystate == 0}">
                <font color="red">未支付</font>&nbsp;&nbsp;&nbsp;
                <%-- 删除订单--%>
                <a href="${app}/OrderDeleteServlet?id=${orderInfo.order.id }">
                    <img src="img/orderList/sc.jpg" width="69" height="19"></a>
                <%-- 传过去的参数只是作为看的， 真是湖区数据要在隐藏域中--%>
                <a href="${app}/pay.jsp?id=${orderInfo.order.id}&money=${orderInfo.order.money}">
                    <img src="img/orderList/zx.jpg" width="69" height="19"></a><br/>
            </c:if>>

            <c:if test="${orderInfo.order.paystate == 1}">
                <font color="blue">已支付</font>
            </c:if>
            收货地址：${orderInfo.order.receiverinfo}<br/>
            支付方式：在线支付
        </dd>
    </dl>

    <table width="1200" border="0" cellpadding="0"
           cellspacing="1" style="background:#d8d8d8;color:#333333">
        <tr>
            <th width="276" height="30" align="center" valign="middle" bgcolor="#f3f3f3">商品图片</th>
            <th width="247" align="center" valign="middle" bgcolor="#f3f3f3">商品名称</th>
            <th width="231" align="center" valign="middle" bgcolor="#f3f3f3">商品单价</th>
            <th width="214" align="center" valign="middle" bgcolor="#f3f3f3">购买数量</th>
            <th width="232" align="center" valign="middle" bgcolor="#f3f3f3">小计</th>
        </tr>
        <c:forEach items="${orderInfo.map}" var="entry">
            <tr>
                <td align="center" valign="middle" bgcolor="#FFFFFF">
                    <img src="${app}/ProductImgServlet?imgurl=${entry.key.imgurl}" width="90" height="105">
                </td>
                <td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.name }</td>
                <td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.price}元</td>
                <td align="center" valign="middle" bgcolor="#FFFFFF">${entry.value }件</td>
                <td align="center" valign="middle" bgcolor="#FFFFFF">${entry.key.price * entry.value}元</td>
            </tr>
        </c:forEach>
    </table>
    <div class="Order_price">${orderInfo.order.money}元</div>
</c:forEach>
<%@include file="/_foot.jsp" %>
</body>
</html>
