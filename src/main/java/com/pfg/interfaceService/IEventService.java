package com.pfg.interfaceService;

import java.util.List;
import java.util.Set;

import com.pfg.models.Event;
import com.pfg.models.Interest;

public interface IEventService {

    public List<Event>listAllEvents();

	public List<Event>listEventsByList(Set<Interest> interests);

	public Event createEvent(Event event);
	
	public Event readEventId(Long id);
	
	public void deleteEvent(Long id) ;

	public List<Event>listByIndexes(List<Long> list);

}