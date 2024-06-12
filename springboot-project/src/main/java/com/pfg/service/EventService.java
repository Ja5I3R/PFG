package com.pfg.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IEventService;
import com.pfg.interfaces.IEvent;
import com.pfg.models.Event;
import com.pfg.models.Interest;

@Service
public class EventService implements IEventService{

    @Autowired
	private IEvent repository;

    //METODO PARA CONSEGUIR TODOS LOS EVENTOS
    @Override
    @Transactional(readOnly = true)
    public List<Event>listAllEvents(){
        return repository.findAll();
    }

    //METODO PARA CONSEGUIR TODOS LOS EVENTOS EN BASE A UNOS INTERESES
    @Override
    @Transactional(readOnly = true)
    public List<Event>listEventsByList(Set<Interest> interests){
        List<Event> recomendedList = new ArrayList<>();
        for(Event event : repository.findAll()){
            for(Interest interst : interests){
                if(interst.getId().equals(event.getInterest().getId())){
                    recomendedList.add(event);
                }
            }
        }
        return recomendedList;
    }

    //METODO PATRA CREAR EVENTO
    @Override
    @Transactional
	public Event createEvent(Event event){
        LocalDate actualDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = actualDate.format(formatter);
        event.setCreationDate(formattedDate);
        return repository.save(event);
    }
	
    //METODO PARA ENCONTRAR EVENTO POR ID
    @Override
    @Transactional(readOnly = true)
	public Event readEventId(Long id){
        return repository.findById(id).get();
    }
	
    //METODO PARA BORRAR EVENTO
    @Override
    @Transactional
	public void deleteEvent(Long id){
        repository.deleteById(id);
    }

    //METODO PARA CONSEGUIR LISTA DE EVENTOS POR LISTA DE IDS
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
