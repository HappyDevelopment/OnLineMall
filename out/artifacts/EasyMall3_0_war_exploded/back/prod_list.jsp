<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <style type="text/css">
        body {
            background: #F5F5F5;
            text-align: center;
        }

        table {
            text-align: center;
            margin: 0px auto;
        }

        th {
            background-color: silver;
        }
    </style>

<script type="text/javascript" src="${app}/js/ajaxFun.js"></script>
    <script type="text/javascript">

        <%-- 首先是页面内可以进行商品数量的修改--%>
        function changePnum (pid , obj) {
            var numPnum = obj.value;
            var  reg = /^[1-9][0-9]*$/;

            //首先校验数据的合法性
            if(!reg.test(numPnum)){
                alert("输入数据不合法，请输入正整数");
                // 更改为上一次改变的值
                obj.value = document.getElementById("hd_"+pid).value;
                return;
            }else if (numPnum == document.getElementById("hd_"+pid).value){
                // 值没有改变的情况， 不做处理
                return ;
            }

            //1 获取 xmlHttpRequest 对象
            var xmlHttp = ajaxFunction();

            //2 ,获取连接
            xmlHttp.open("POST", "${app}/AjaxChangePnumServlet", true);

            // send 3 , 发送
            xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xmlHttp.send("id=" + pid + "&newPnum=" + numPnum);

            //4 ， 注册监听
            xmlHttp.onreadystatechange = function () {
                if(xmlHttp.readyState == 4 ) {
                    if(xmlHttp.status == 200) {
                        //获取成功
                        var result = xmlHttp.responseText;
                        if(result=="true"){
                            document.getElementById("hd_" + pid).value = numPnum;
                            alert("修改成功");
                        }else {
                            alert("修改失败");
                        }

                    }
                }
            }
        }

        function deleteProd(pid) {
            if(window.confirm("确定要删除吗？")){
                //由js进入 Serlvet中操作
                window.location.href = "${app }/BackProdDelServlet?id="+pid;
            }
        }

        function checkAll() {
            //实现点击全选
            var flag = document.getElementById("checkAll").checked;

            //得到所有复选框的元素
            var checks = document.getElementsByName("check");

            //循环给每个复选框赋值
            for(var i = 0 ; i < checks.length ; i++) {
                checks[i].checked = flag ;
            }
        }
        //批量删除
        function delAllBooks(){
            //得到所有复选框元素
             var ids = document.getElementsByName("ids");
             var str="";
             //循环得到选中的复选框的id
             for(var i=0;i<ids.length;i++){
             if(ids[i].checked){
             str+="ids="+ids[i].value+"&";
             }
             }
             str = str.substring(0, str.length-1); //ids=1001&ids=1002&ids=1003
             //alert(str);
             if(str!="")
                     //进行提交删除
             location.href="${pageContext.request.contextPath }/servlet/delAllBooksServlet?"+str;
            <%--//var form2 = document.getElementById("form2");--%>
            <%--var form2 = document.forms[1];//同上。得到页面中第二个表单--%>
            <%--//给当前表单中的action属性赋值--%>
            <%--//form2.action="${pageContext.request.contextPath }/servlet/delAllBooksServlet";--%>
            <%--form2.submit();//相当于点击submit按钮--%>
        }
    </script>

</head>
<body>

<h1>商品管理</h1>
<a href="${app}/back/prod_add.jsp">添加商品</a>
<hr>
<table bordercolor="black" border="1" width="95%" cellspacing="0px" cellpadding="5px">
    <tr>
        <th>全选<input type="checkbox" id="checkAll" onclick="checkAll()"></th>
        <th>商品图片</th>
        <th>商品id</th>
        <th>商品名称</th>
        <th>商品种类</th>
        <th>商品单价</th>
        <th>库存数量</th>
        <th>描述信息</th>
        <th nowrap="nowrap">&nbsp;&nbsp;操&nbsp;&nbsp;&nbsp;&nbsp;作&nbsp;&nbsp;</th>
    </tr>

    <%-- 利用 jstl标签 来进行迭代显示所有的商品信息
         其中 items 为结果集对象
    --%>
    <c:forEach items="${productList}" var="prod" varStatus="stat">
        <tr>
            <td><input type="checkbox" name="check" >  </td>
            <td><img width="120px" height="120px" src="${app}/ProductImgServlet?imgurl=${prod.imgurl}"/></td>
            <td>${prod.id}</td>
            <td>${prod.name}</td>
            <td>${prod.category}</td>
            <td>${prod.price}</td>

                <%-- 商品数量做成一个可以更改的text文档
                        利用隐藏域来实现每次都存储起来上一次更改后的值。
                        因为 js方法是传入的是 oldPnum 更改一次后，
                         是异步更改，不会访问BackProdListServlet ，不会刷新ProductList
                         所以要注意这点
                --%>
            <td>
                <input type="hidden" id="hd_${prod.id}" value="${prod.pnum}"/>
                <input type="text" id="${prod.id}" value="${prod.pnum}"
                       style="width: 50px" onblur="changePnum('${prod.id}',this)"/></td>
            <td>${prod.description}</td>
            <td><a href="javascript:void(0)" onclick="deleteProd('${prod.id}+')">删除</a>
                <a href="javascript:void(0)" onclick="changeProd()">修改</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
