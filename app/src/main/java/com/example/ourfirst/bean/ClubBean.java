package com.example.ourfirst.bean;

import android.provider.ContactsContract;

import java.security.PrivateKey;

public class ClubBean {
//    private static String club_name;
    private int club_id;            //用户名
    private String club_name;
    private String club_leader;//社团负责人
    private int club_create_id;
    private String level;
    private String campus;
    private String kind;
    private String club_logo;
    private String club_leader_call;
    private String club_intro;
    private ContactsContract.Data time;
    public ClubBean(int club_id,String club_name, String club_leader,int club_create_id,
                    String club_logo,String club_leader_call,String club_intro,ContactsContract.Data time) {
        this.club_id=club_id;
        this.club_name=club_name;
        this.club_create_id=club_create_id;
        this.club_logo=club_logo;
        this.club_leader=club_leader;
        this.club_leader_call=club_leader_call;
        this.club_intro=club_intro;
        this.time=time;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public  String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getClub_leader() {
        return club_leader;
    }

    public void setClub_leader(String club_leader) {
        this.club_leader = club_leader;
    }

    public String getClub_logo() {
        return club_logo;
    }

    public void setClub_logo(String club_logo) {
        this.club_logo = club_logo;
    }

//    public String getClub_time() {
//        return club_time;
//    }
//
//    public void setClub_time(String club_time) {
//        this.club_time = club_time;
//    }

    public String getClub_leader_call() {
        return club_leader_call;
    }

    public void setClub_leader_call(String club_leader_call) {
        this.club_leader_call = club_leader_call;
    }
    public String getClub_intro() { return club_intro; }

    public void setClub_intro(String club_intro) {
        this.club_intro = club_intro;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.level = kind;
    }

}
