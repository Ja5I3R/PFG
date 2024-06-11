package com.pfg.models;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtils {
    public boolean checkSession(HttpSession session){
        if(session != null){
            return true;
        }
        else{
            return false;
        }
    }

    public User getUserSession(HttpSession session){
        User actualUser = (User) session.getAttribute("user");
        return actualUser;
    }
}
