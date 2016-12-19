<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>欢迎注册EasyMall</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="${app}/css/regist.css"/>

    <script type="text/javascript" src="${app}/js/ajaxFun.js">
    </script>
    <%--js 校验 ， 要写上 text/javascript  --%>
    <script type="text/javascript" src="${app}/js/checkFun.js">
    </script>

    <%-- 涉及到路径问题， 这个ajax 还是放到jsp中把--%>
    <script type="text/javascript">
        /* AJAX 异步加载判断用户名存在问题*/
        function checkUsername(username) {

            if (!checkNull(username, "用户名不能为空")) {
                // 有空是错误的， 那么取反为true，是用户输入有为空
                // 直接return

                return;
            } else {

                var name = document.getElementsByName(username)[0].value;
                //利用ajax来完成异步判断

                // 1 后去AJAXHttp对象
                var xmlHttp = ajaxFunction();

                // 2  获得对象了，就开始打电话问服务器，--- 获取连接 method, url, async
                xmlHttp.open("POST", "${app}/CheckUsernameServlet", true);

                //3 发送数据
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");	//通知服务器发送的数据是请求参数
                xmlHttp.send("username=" + name);

                //4 处理结果 onreadystatechange事件监听
                xmlHttp.onreadystatechange = function () {
                    // 4 表示服务器处理完了
                    if (xmlHttp.readyState == 4) {

                        //获取到服务器数据成功 , 304 使用缓存资源
                        if (xmlHttp.status == 200 || xmlHttp.status == 304) {

                            //获取到的是 String  数据
                            var data = xmlHttp.responseText;

                            if (data == "true") {
                                //用户已存在啊
                                setMsg(username, "用户名已存在");

                            } else {
                                setMsg(username, "用户名可以使用");

                            }

                        } else {
                            //服务器异常

                        }
                    }
                }
            }
        }

        /* 点击换一张图片, 传递时，传递一个每次本机的时间来确保每次获取的图片不一致*/
        function changImg(thisObj) {

            thisObj.src = "${app}/ValiImageServlet?time=" + new Date().getTime();
        }
    </script>
</head>
<body>
<h1>欢迎注册EasyMall</h1>
<form action="${app}/RegistServlet" method="POST" onsubmit="checkFrom()">
    <table>

        <%--  这里加一个表格来显示用户输入的信息提交后的显示信息， 为了不让他显示为null, 来进行判断一次  --%>

        <tr>
            <td class="tds" colspan="2" style="color:red; font-size: 14px;text-align:center">
                ${requestScope.msg}
            </td>
        </tr>

        <tr>
            <td class="tds">用户名：</td>
            <%-- value 的作用是提交信息有误后，还保留用户输入的信息--%>
            <%--  接下来是 AJAX 来异步判断用户名是否存在--%>
            <td><input type="text" name="username" onblur="checkUsername('username')"
                       <%--value="<%= request.getParameter("username")==null ? "": request.getParameter("username")%>">
                       回显用户输入的账号等信息， 这些信息是存储在 request.Parameter中的
                       --%>
                       value="${ param.username }">
                <span id="username_msg"></span>
            </td>

        </tr>
        <tr>
            <td class="tds">密码：</td>
            <%--   这里 name  设置为 password 获取时候要注意啊 --%>
            <td><input type="password" name="password" onblur="checkNull('password', '密码不能为空')"
                       value="${ param.password }"><span
                    id="password_msg"></span></td>

        </tr>
        <tr>
            <td class="tds">确认密码：</td>
            <td><input type="password" name="password2" onblur="checkPassword('password', '两次密码不一致')"
                       value="${ param.password2 }"><span
                    id="password2_msg"></span></td>

        </tr>
        <tr>
            <td class="tds">昵称：</td>
            <td><input type="text" name="nickname" onblur="checkNull('nickname', '昵称不能为空')"
                       value="${ param.nickname }"><span
                    id="nickname_msg"></span></td>

        </tr>
        <tr>
            <td class="tds">邮箱：</td>
            <td><input type="text" name="email" onblur="checkEmail('email', '邮箱格式不正确')"
                       value="${ param.email }"> <span
                    id="email_msg"></span></td>

        </tr>
        <tr>
            <td class="tds">验证码：</td>
            <td><input type="text" name="valistr" onblur="checkNull('valistr', '验证码不能为空')">

                <%-- 这里图片路径不是死的，而是每次由Serlvet来动态生成的 , 方法传入this是来换src的--%>
                <%-- 通过转发 ， 来访问Serlvet中的图片--%>
                <img id="yzm_img" src="${app}/ValiImageServlet"
                     onclick="changImg(this)" style="cursor: pointer"/><span id="valistr_msg"></span></td>

        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="注册用户"/>
            </td>

        </tr>


    </table>
</form>
</body>
</html>
