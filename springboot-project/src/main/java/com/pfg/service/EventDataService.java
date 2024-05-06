package com.pfg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfg.interfaceService.IEventDataService;
import com.pfg.interfaces.IEventData;
import com.pfg.models.EventData;

@Service
public class EventDataService implements IEventDataService{

    @Autowired
	private IEventData repository;

    @Override
    public EventData saveEventData(EventData ED){
        return repository.save(ED);
    }

    @Override
    public List<Long>getEventData(Long userID){
        List<Long> list = new ArrayList<>();

        List<EventData> listFor = repository.findAll();

        for(EventData eventD : listFor){
            if(eventD.getIdUser().equals(userID)){
                list.add(eventD.getIdEvent());
            }
        }

        return list;
    }
}
