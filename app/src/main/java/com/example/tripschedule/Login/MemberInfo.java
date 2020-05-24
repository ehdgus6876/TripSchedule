package com.example.tripschedule.Login;

import android.widget.EditText;

import java.util.ArrayList;

public class MemberInfo {
    private String name;
    private String Address;
    private String Telphone;
    private String Birthday;



    public MemberInfo(String name,String Address,String Telphone,String Birthday){
        this.name = name;
        this.Address=Address;
        this.Telphone=Telphone;
        this.Birthday=Birthday;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelphone() {
        return Telphone;
    }

    public void setTelphone(String telphone) {
        Telphone = telphone;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }


}
