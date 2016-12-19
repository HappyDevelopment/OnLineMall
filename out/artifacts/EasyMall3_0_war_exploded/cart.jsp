<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/12/14
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <link href="css/cart.css" rel="stylesheet" type="text/css">
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript">
        function changeBuyNum(id, oldBuyNum, obj) {
            if( !/^[1-9][0-9]*$/.test(obj.value)){
                alert("请输入正整数");
                obj.value = oldBuyNum;
                return ;
            }
            if(obj.value != oldBuyNum) {
                window.location.href = "${app}/UpdateCartServlet?id=" + id + "&newBuyNum=" + obj.value;
            }
        }
        function delNum(id, obj) {
             var input = obj.parentNode.getElementsByTagName("input")[0];
            var newBn = parseInt(input.value)-1;
            if(newBn>0){
                window.location.href="${app}/UpdateCartServlet?id="+id+"&newBuyNum="+newBn;
            }else {  //修改为0或负数 == 删除
                window.location.href = "${app}/CartDeleteServlet?id="+id;
            }
        }
        function addNum(id,obj){
            var input = obj.parentNode.getElementsByTagName("input")[0];
            var newBn = parseInt(input.value)+1;
            window.location.href="${app}/UpdateCartServlet?id="+id+"&newBuyNum="+newBn;
        }

    </script>
</head>
<body>
<%@include file="_head.jsp"%>
<div class="warp">
    <%--  呃呃呃，  订单没有货的话， 返回一句话 ，，--%>
    ${msg}
    <!-- 标题信息 -->
    <div id="title">
        <input name="allC" type="checkbox" value="" onclick=""/>
        <span id="title_checkall_text">全选</span>
        <span id="title_name">商品</span>
        <span id="title_price">单价（元）</span>
        <span id="title_buynum">数量</span>
        <span id="title_money">小计（元）</span>
        <span id="title_del">操作</span>
    </div>

    <!-- 购物信息 -->
    <c:set var="money" value="0" scope="page"/>
    <c:forEach items="${cart}" var="entry">
    <div id="prods">
        <input name="prodC" type="checkbox" value="" onclick=""/>
        <img src="${app}/ProductImgServlet?imgurl=${entry.key.imgurl}" width="90" height="90" />
        <span id="prods_name">${entry.key.name}</span>
        <span id="prods_price">${entry.key.price}</span>
                    <span id="prods_buynum">
                        <a href="javascript:void(0)" id="delNum"
                           onclick="delNum('${entry.key.id}',this)">-</a>
                        <input id="buyNumInp" type="text" value="${entry.value }"
                               onblur="changeBuyNum('${entry.key.id}',${entry.value },this)">
                        <a href="javascript:void(0)" id="addNum"
                           onclick="addNum('${entry.key.id}',this)" >+</a>
                    </span>
        <span id="prods_money">${entry.key.price * entry.value}</span>
        <span id="prods_del"><a href="${app}/CartDeleteServlet?id=${entry.key.id}">删除</a></span>

        <%-- 保存 总价格 --%>
        <c:set var="money" scope="page" value="${money+entry.key.price*entry.value}"></c:set>
    </div>
    </c:forEach>
    <!-- 总计条 -->
    <div id="total">
        <div id="total_1">
            <input name="allC" type="checkbox" value=""/>
            <span>全选</span>
            <a id="del_a" href="#">删除选中的商品</a>
            <span id="span_1">总价：</span>
            <span id="span_2">${money}</span>
        </div>
        <div id="total_2">
            <a id="goto_order" href="${app}/addOrder.jsp">去结算</a>
        </div>
    </div>
</div>

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<%@include file="_foot.jsp"%>
</body>
</html>