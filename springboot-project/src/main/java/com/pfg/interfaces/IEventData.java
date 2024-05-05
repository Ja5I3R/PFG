package com.pfg.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfg.models.EventData;

@Repository
public interface IEventData extends JpaRepository<EventData, Long>{

}
