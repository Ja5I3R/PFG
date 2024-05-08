package com.pfg.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_events_users")
public class EventData {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "id_user", nullable = false)
	private Long idUser;

    @Column(name = "id_event", nullable = false)
	private Long IdEvent;

    public EventData() {
    }

    public EventData(Long idUser, Long idEvent) {
        this.idUser = idUser;
        IdEvent = idEvent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdEvent() {
        return IdEvent;
    }

    public void setIdEvent(Long idEvent) {
        IdEvent = idEvent;
    }

    
}
