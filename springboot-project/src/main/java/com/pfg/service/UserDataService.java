package com.pfg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaces.IUserData;
import com.pfg.models.User;
import com.pfg.models.UserData;

@Service
public class UserDataService implements IUserDataService{

    @Autowired
	private IUserData repository;

    @Override
    @Transactional
    public UserData saveUserPreferences(UserData UD){
        return repository.save(UD);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getInterestList(User user){
        List<Long> list = new ArrayList<>();
        List<UserData> listFor = repository.findAll();

        for(UserData userD : listFor){
            if(userD.getUser().equals(user)){
                list.add(userD.getInterest1_id());
                list.add(userD.getInterest2_id());
                list.add(userD.getInterest3_id());
                list.add(userD.getInterest4_id());
                list.add(userD.getInterest5_id());
            }
        }
        return list;
    }
}
