package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.User;
import com.pfg.models.UserData;

public interface IUserDataService {

    //METODO PARA GUARDAR LAS PREFERENCIAS DE UN USUARIO
    public UserData saveUserPreferences(UserData UD);

    //METODO PARA CONSEGUIR LA LISTA DE INTERESES EN BASE A UN USUARIO
    public List<Long> getInterestList(User user);

}
