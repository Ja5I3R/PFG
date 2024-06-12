package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.Interest;

public interface IInterestService {

    //METODO PARA CONSEGUIR UN INTERES POR ID
    public Interest getInterestById(Long id);

    //METODO PARA CONSEGUIR LISTA DE TODOS LOS INTERESES
    public List<Interest>listAllInterest();

    //METODO PARA CONSEGUIR LISTA DE INTERESES EN BASE A UNA LISTA DE IDS
    public List<Interest>listByIndexes(List<Long> list);

    public Interest findById(Long id);

}
