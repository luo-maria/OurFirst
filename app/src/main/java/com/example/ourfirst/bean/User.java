package com.example.ourfirst.bean;

public class User {

    private int student_id;
    private String username;            //用户名
    private String number;
    private String password;
    private String student_class;
    private String signature;
    private String gender;
    private String realname;
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(int student_id,String username, String number, String password,String student_class,
                String signature,String gender, String realname ) {
        super();
        this.student_id=student_id;
        this.username = username;
        this.number = number;
        this.password = password;
        this.student_class=student_class;
        this.signature=signature;
        this.gender=gender;
        this.realname=realname;
    }
    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "student_name='" + student_name + '\'' +
//                ", number='" + number + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}
