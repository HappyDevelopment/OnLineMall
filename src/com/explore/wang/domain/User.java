package com.explore.wang.domain;


import com.explore.wang.exception.NullDataException;

/**
 * 注册用户的Javabean
 * Created by 王兆琦  on 2016/12/3 17.43.
 */
public class User {

    private int id ;
    private String username ;
    private String password ;

    //这里设置password2 的意义是： 在domain里面进行数据格式的校验，
    //为了功能的专一与应用层次结构的解耦
    private String password2 ;
    private String nickname ;
    private String email ;
    private String role ;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Javabean 要有空参构造函数 ，默认有，我怕后期有有参的构造，这里写出来了
    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    /**
     * 这是用户信息的校验，由servlet调用 user.checkData()
     *   因为是在本类中，所以可以不用 user.username 来确定类中属性
     *  但是其中是有错误， 那么怎么抛出呢？？
     *          因为错误的信息是因为用户输入的数据不合法，那么需要自己定义异常，来进行捕获
     *          servlet那层进行跳转会注册页面
     *
     */
    public void checkData() {
        if (username == null || "".equals(username)) {

            throw new NullDataException("用户名不能为空");
        }
        if (password == null || "".equals(password)) {

            throw new NullDataException("密码不能为空");

        }
        if (password2 == null || "".equals(password2)) {

            throw new NullDataException("确认密码不能为空");

        }
        //两次密码一致问题
        if (!password.equals(password2)) {

            throw new NullDataException("两次密码不一致");

        }

        if (nickname == null || "".equals(nickname)) {

            throw new NullDataException("昵称不能为空");

        }
        if (email == null || "".equals(email)) {

            throw new NullDataException("邮箱不能为空");

        }

        String reg = "^\\w+@\\w+(\\.\\w+)+$";
        if (!email.matches(reg)) {

            throw new NullDataException("邮箱格式不正确");

        }

    }

}
