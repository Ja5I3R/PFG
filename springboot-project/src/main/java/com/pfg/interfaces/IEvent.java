package com.pfg.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfg.models.Event;

@Repository
public interface IEvent extends JpaRepository<Event, Long>{

    List<Event> findAllByName(String name);

}
