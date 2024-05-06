package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.Event;

public interface IEventService {

    public List<Event>listAllEvents();

	public Event createEvent(Event event);
	
	public Event readEventId(Long id);
	
	//public Event updateEvent(Event event);
	
	public void deleteEvent(Long id) ;

	public List<Event>listByIndexes(List<Long> list);

}