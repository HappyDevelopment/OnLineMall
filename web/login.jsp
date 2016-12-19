<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/11/31
  Time: 11.35

    用户登录时，勾选记录用户名，

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" buffer="0kb" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="${app}/css/login.css"/>
    <title>EasyMall欢迎您登陆</title>

   <%-- 现在要页面一加载就获取正确编码的cookie中的用户名
    获取用户名(url编码后的用户名), 通过js进行url解码--%>
    <script type="text/javascript">
        window.onload = function () {

            // 要用对象阿，  用值是不会改变的阿， 要显示改变啊
            var username = document.getElementsByName("username")[0];
            var value = decodeURI(username.value);

            username.value = value;

        }
    </script>
    <script type="text/javascript" src="${app}/js/checkFun.js"></script>
</head>
<body>
<h1>欢迎登陆EasyMall</h1>
<form action="${app}/LoginServlet" method="POST">
    <table>
        <tr>
            <td class="tdx">用户名:</td>
            <%-- 有Username的cookie 那么下次就显示这个信息--%>
            <td><input type="text" name="username" value="${cookie.remName.value}"/>
                <span style="font-size: 14px;color: red">${requestScope.msg}</span>
            </td>
        </tr>
        <tr>
            <td class="tdx">密码:</td>
            <td><input type="password" name="password" onblur="checkNull('password', '密码不能为空')"/>
                <span id="password_msg"></span></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="checkbox" name="remname" value="true"
                        ${cookie.remName != null ? "checked='checked'"  : ""}
                />记住用户名
                <input type="checkbox" name="autologin" value="true"/>30天内自动登陆
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="登陆"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

