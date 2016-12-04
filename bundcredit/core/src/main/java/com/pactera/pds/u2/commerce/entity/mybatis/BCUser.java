package com.pactera.pds.u2.commerce.entity.mybatis;

import com.pactera.pds.u2.commerce.entity.User;


public class BCUser extends User {
    private String insCode;

    public String getInsCode() {
        return insCode;
    }
    
    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }
}
