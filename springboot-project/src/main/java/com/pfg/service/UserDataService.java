package com.pfg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaces.IUserData;
import com.pfg.models.UserData;

@Service
public class UserDataService implements IUserDataService{

    @Autowired
	private IUserData repository;

    /* OTRA MANERA
    @Override
    public void saveUserPreferences(Long userID, Long intID1, Long intID2, Long intID3, Long intID4, Long intID5){
        UserData UD = new UserData();
        UD.setUser_id(userID);
        UD.setInterest1_id(intID1);
        UD.setInterest2_id(intID2);
        UD.setInterest3_id(intID3);
        UD.setInterest4_id(intID4);
        UD.setInterest5_id(intID5);
        repository.save(UD);
    } */

    @Override
    public UserData saveUserPreferences(UserData UD){
        return repository.save(UD);
    }

    @Override
    public List<Long>getInterestList(Long userID){
        List<Long> list = new ArrayList<>();

        List<UserData> listFor = repository.findAll();

        for(UserData userD : listFor){
            if(userD.getUser_id().equals(userID)){
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
