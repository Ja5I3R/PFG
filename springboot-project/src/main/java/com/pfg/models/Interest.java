package com.pfg.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_interest")
public class Interest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="eventInterest")
    private Set<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="chatInterest")
    @JsonIgnore
    private Set<Chat> chats;

    @ManyToMany(mappedBy = "interests")
    @JsonIgnore
    private Set<UserData> userDatas;

    public Interest() {
    }

    public Interest(String name) {
        this.name = name;
    }

    public Interest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Set<Event> getOnlyEvent() {
        Set<Event> eventsA = events;
        for(Event event : eventsA){
            event.setUsers(null);
            event.setInterest(null);
        }
        return eventsA;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Set<UserData> getUserDatas() {
        return userDatas;
    }

    public void setUserDatas(Set<UserData> userDatas) {
        this.userDatas = userDatas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Interest [id=" + id + ", name=" + name + "]";
    }

}