<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/12/14
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>商品详细信息页</title>
    <link href="${app }/css/prodInfo.css " rel="stylesheet" type="text/css">
    <script type="text/javascript">
        function addCart(){
            window.location.href = "${app}/CartAddServlet?pid=${product.id}";
        }
    </script>
</head>
<body>
<%@include file="/_head.jsp" %>
<div id="warp">
    <div id="left">
        <div id="left_top">
            <img src="${app }ProductImgServlet?imgurl=${product.imgurl}"/>
        </div>
        <div id="left_bottom">
            <img id="lf_img" src="${app }/img/prodInfo/lf.jpg"/>
            <img id="mid_img" src="${app }/ProductImgServlet?imgurl=${product.imgurl}" width="60px" height="60px"/>
            <img id="rt_img" src="${app }/img/prodInfo/rt.jpg"/>
        </div>
    </div>
    <div id="right">
        <div id="right_top">
            <span id="prod_name">${product.name } <br/></span>
            <br>
            <span id="prod_desc">${product.description}<br/></span>
        </div>
        <div id="right_middle">
				<span id="right_middle_span">
						EasyMall 价：<span class="price_red">￥${product.price }<br/>
			            运     费：满 100 免运费<br />
			            服     务：由EasyMall负责发货，并提供售后服务<br />
			            库     存：${product.pnum }<br />
			            购买数量：
	            <a href="#" id="delNum" onclick="">-</a>
	            <input id="buyNumInp" name="" type="text" value="1" onblur="">
		        <a href="#" id="addNum" onclick="">+</a>
        </div>
        <div id="right_bottom">
            <input class="add_cart_but" type="button" onclick="addCart()"/>
        </div>
    </div>
</div>
<%@include file="_foot.jsp" %>
</body>
</html>
