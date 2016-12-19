/**
 * Created by User on 2016/11/30.
 */

function checkNull(name, msg) {

    var vlaue = document.getElementsByName(name)[0].value;

    /*  需要每次清除之前的数据*/
    setMsg(name, "");

    if(vlaue.split() == "" ) {
        setMsg(name, msg);
        return false ;
    }
    return true ;
}

function setMsg(name , msg) {
    document.getElementById(name + "_msg").innerHTML =
        "<font style='color: red ; font-size: 14px';>" + msg + "</font>";
}
function checkFrom() {
    var flag = true ;

    var flag = checkNull("username", "用户名不能为空") && flag;
    var flag = checkNull("password", "密码不能为空") && flag;
    var flag = checkNull("password2", "确认密码不能为空") && flag;
    var flag = checkNull("nickname", "昵称不能为空") && flag;
    var flag = checkNull("email", "邮箱不能为空") && flag;
    var flag = checkNull("valistr", "验证码不能为空") && flag;
    var flag = checkPassword("password", "两次密码不一致") && flag;
    var flag = checkEmail("email", "邮箱格式不正确") && flag;


    return flag ;
}

function checkPassword(name, msg) {
    var v1 = document.getElementsByName(name)[0].value;
    var v2 = document.getElementsByName(name+"2")[0].value;

    checkNull(name+"2", "确认密码不能为空");
    if(v1 != v2) {
        setMsg(name+"2", msg);
        return false;
    }

    return true;
}

function checkEmail(name, msg) {

    var v = document.getElementsByName(name)[0].value;

    checkNull(name, msg);
    
    var reg = /^\w+@\w+(\.\w+)+$/;
    if( v!="" &&  !reg.test(v)){
        setMsg(name, msg);
        return false;
    }

    return true ;
}




