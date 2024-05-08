package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.EventData;

public interface IEventDataService {

    public EventData saveEventData(EventData ED);

    public List<Long>getEventData(Long userID);

    public List<Long>getUsersToEvent(Long eventID);
    
}
