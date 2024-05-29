package com.pfg.models;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    public boolean checkSession(HttpSession session){
        if(session != null){
            return true;
        }
        else{
            return false;
        }
    }
}
