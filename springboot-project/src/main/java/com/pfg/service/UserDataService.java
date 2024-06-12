package com.pfg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaces.IUserData;
import com.pfg.models.Interest;
import com.pfg.models.User;
import com.pfg.models.UserData;

@Service
public class UserDataService implements IUserDataService {

    @Autowired
    private IUserData repository;

    //METODO PARA GUARDAR LAS PREFERENCIAS DE UN USUARIO
    @Override
    @Transactional
    public UserData saveUserPreferences(UserData UD) {
        return repository.save(UD);
    }

    //METODO PARA CONSEGUIR LA LISTA DE INTERESES EN BASE A UN USUARIO
    @Override
    @Transactional(readOnly = true)
    public List<Long> getInterestList(User user) {
        List<Long> interestIds = new ArrayList<>();

        if (user != null && user.getUserData() != null && user.getUserData().getInterests() != null) {
            Set<Interest> interests = user.getUserData().getInterests();
            for (Interest interest : interests) {
                interestIds.add(interest.getId());
            }
        }

        return interestIds;
    }
}
