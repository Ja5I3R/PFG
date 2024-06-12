package com.pfg.interfaceService;

import java.util.List;
import java.util.Set;

import com.pfg.models.Event;
import com.pfg.models.Interest;

public interface IEventService {

	//METODO PARA CONSEGUIR TODOS LOS EVENTOS
    public List<Event>listAllEvents();

	//METODO PARA CONSEGUIR TODOS LOS EVENTOS EN BASE A UNOS INTERESES
	public List<Event>listEventsByList(Set<Interest> interests);

	//METODO PATRA CREAR EVENTO
	public Event createEvent(Event event);
	
	//METODO PARA ENCONTRAR EVENTO POR ID
	public Event readEventId(Long id);
	
	//METODO PARA BORRAR EVENTO
	public void deleteEvent(Long id) ;

	//METODO PARA CONSEGUIR LISTA DE EVENTOS POR LISTA DE IDS
	public List<Event>listByIndexes(List<Long> list);

}