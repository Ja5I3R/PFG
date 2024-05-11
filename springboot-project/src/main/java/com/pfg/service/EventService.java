package com.pfg.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IEventService;
import com.pfg.interfaces.IEvent;
import com.pfg.models.Event;

@Service
public class EventService implements IEventService{

    @Autowired
	private IEvent repository;

    @Override
    @Transactional(readOnly = true)
    public List<Event>listAllEvents(){
        return repository.findAll();
    }

    @Override
    @Transactional
	public Event createEvent(Event event){
        LocalDate actualDate = LocalDate.now();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //String formattedDate = actualDate.format(formatter);
        event.setCreationDate(actualDate);
        return repository.save(event);
    }
	
    @Override
    @Transactional(readOnly = true)
	public Event readEventId(Long id){
        return repository.findById(id).get();
    }
	
	//public Event updateEvent(Event event);
	
    @Override
    @Transactional
	public void deleteEvent(Long id){
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
	public List<Event>listByIndexes(List<Long> list){
		List<Event> result = new ArrayList<>();

		for(Long id : list){
			Event actualEvnt = repository.findById(id).get();
			result.add(actualEvnt);
		}

		return result;
	}
}
