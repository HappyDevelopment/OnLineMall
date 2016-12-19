<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2016/12/8
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType=" text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>

<form action="${app}/FileUpLoadServlet" enctype="multipart/form-data" method="post">

    <input type="text" name="desc"/>

    <input type="file" name="file"/> <br/>

    <input type="submit" value="提交"/>

</form>
</body>
</html>
