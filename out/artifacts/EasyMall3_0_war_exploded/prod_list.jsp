<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/12/13
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <link href="css/prodList.css" rel="stylesheet" type="text/css">
    <link href="css/page.css" rel="stylesheet" type="text/css">

    <script type="text/javascript">
        // 基本点击事件
        function changePageA(newtp) {
            //修改thispage的值
            document.getElementById("cp").value = newtp;
            document.getElementById("searchForm").submit();
        }

        // 输入数值跳转
        function changePageB(obj) {
            var newtp = obj.value;
            var reg = /^[1-9][0-9]*$/;
            if (!reg.test(newtp)) {
                alert("请输入正确的页码(正整数)");
                obj.value = "${page.thispage}";
                return;
            }
            if (newtp == "${page.thispage}") {
                return;
            }
            document.getElementById("cp").value = newtp;
            document.getElementById("searchForm").submit();

        }

        //上一页， 当为第一页不作为
        function changePageC(pre, curr) {

            if (curr != 1) {
                //修改thispage的值
                document.getElementById("cp").value = pre;
                document.getElementById("searchForm").submit();
            }


        }
    </script>
</head>
<body>
<%@include file="_head.jsp" %>
<div id="content">
    <div id="search_div">
        <form id="searchForm" method="post" action="${app}/ProductPageServlet">

            <%-- 隐藏域的作用 ， 来提交页码数--%>
            <input type="hidden" id="cp" name="currentPage" value="${pageList.currentPage}">
            <input type="hidden" name="pageProdNum" value="${pageList.pageProdNum}">
            <span class="input_span">商品名：<input type="text" name="name" value="${pageList.name}"/></span>
            <span class="input_span">商品种类：<input type="text" name="category" value="${pageList.category}"/></span>

            <span class="input_span">商品价格区间：<input type="text" name="minPrice" value="${pageList.minPrice}"/>
                - <input type="text" name="maxPrice" value="${pageList.maxPrice}"/></span>

            <input type="submit" value="查询">
        </form>
    </div>

    <div id="prod_content">
        <%--  利用 jstl 的 foreach进行迭代--%>
        <c:forEach items="${pageList.list}" var="prod">
            <div id="prod_div">
                <a href="${app}/ProductInfoServlet?pid=${prod.id}">
                    <img src="${app}/ProductImgServlet?imgurl=${prod.imgurl}"/>
                </a>
                <div id="prod_name_div">
                        <%--传递唯一的商品id到Serlvet中--%>
                    <a href="${app}/ProductInfoServlet?pid=${prod.id}">${prod.name}</a>
                </div>
                <div id="prod_price_div">
                    ￥${prod.price}元
                </div>
                <div>
                    <div id="gotocart_div">
                        <a href="${app}/CartAddServlet?pid=${prod.id}">加入购物车</a>
                    </div>
                    <div id="say_div">
                        库存：${prod.pnum}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div style="clear: both"></div>
</div>

<div id="fy_div">
    <a href="javascript:void(0)" onclick="changePageA(1)">首页</a>
    <a href="javascript:void(0)" onclick="changePageC(${pageList.prePage},${pageList.currentPage})">上一页</a>


    <%--  利用set 标签来保存一些分页数据--%>
    <%-- 默认为一页--%>
    <c:set var="begin" scope="page" value="1"/>
    <c:set var="end" scope="page" value="1"/>

    <c:if test="${pageList.totalPage <=5}">
        <c:set var="begin" scope="page" value="1"/>
        <c:set var="end" scope="page" value="5"/>
    </c:if>

    <%-- 总页数大于5的时候，要分当前页的情况--%>
    <c:if test="${pageList.totalPage >5}">
        <c:choose>
            <%-- 当前页小于3的时候--%>
            <c:when test="${pageList.currentPage <=3}">
                <c:set var="begin" scope="page" value="1"/>
                <c:set var="end" scope="page" value="5"/>
            </c:when>

            <%--当前页 大于等于 最大页数-2--%>
            <c:when test="${pageList.currentPage}<=${pageList.totalPage -2}">
                <c:set var="begin" scope="page" value="${pageList.totalPage -4}"/>
                <c:set var="end" scope="page" value="${pageList.totalPage}"/>
            </c:when>

            <c:otherwise>
                <c:set var="begin" scope="page" value="${pageList.totalPage -2}"/>
                <c:set var="end" scope="page" value="${pageList.totalPage + 2}"/>
            </c:otherwise>
        </c:choose>
    </c:if>

    <%--这里才是真正的显示数据--%>
    <c:forEach begin="${begin}" end="${end}" step="1" var="i">
        <c:if test="${ i != pageList.currentPage}">
            <a href="javascript:void(0)" onclick="changePageA(${i})">${i}</a>
        </c:if>
        <c:if test="${ i == pageList.currentPage}">${i}</c:if>
    </c:forEach>

    <%-- 分页逻辑结束 --%>
    <a href="javascript:void(0)" onclick="changePageA(${pageList.nextPage})">下一页</a>
    <a href="javascript:void(0)" onclick="changePageA(${pageList.totalPage})">尾页</a>
    跳转到<input type="text" value="${pageList.currentPage }" onblur="changePageB(this)"/>页
</div>
<%@include file="_foot.jsp" %>
</body>
</html>