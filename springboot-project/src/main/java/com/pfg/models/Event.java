package com.pfg.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_events")
public class Event {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne
    @JoinColumn(name = "id_event")
    @JsonIgnore
    private Interest eventInterest;

    @ManyToMany(mappedBy = "events")
    @JsonIgnore
    private Set<User> users;

    @Column(name = "name", nullable = false, length = 50)
	private String name;

    @Column(name = "initial_date", nullable = false)
	private String initialDate;

    @Column(name = "end_date", nullable = false)
	private String endDate;

    @Column(name = "id_creator", nullable = false)
	private Long idCreator;

    @Column(name = "create_date", nullable = false)
	private String creationDate;

    @Column(name = "location", nullable = false, length = 50)
	private String location;

    @Column(name = "description", nullable = false, length = 50)
	private String description;

    @Column(name = "image_url", nullable = false,length = 50)
	private String image_url;

    public Event() {
    }

    public Event(Long id, Interest eventInterest, Set<User> users, String name, String initialDate, String endDate,
            Long idCreator, String creationDate, String location, String description, String image_url) {
        this.id = id;
        this.eventInterest = eventInterest;
        this.users = users;
        this.name = name;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.idCreator = idCreator;
        this.creationDate = creationDate;
        this.location = location;
        this.description = description;
        this.image_url = image_url;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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


    public Long getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(Long idCreator) {
        this.idCreator = idCreator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Interest getInterest() {
        return eventInterest;
    }

    public void setInterest(Interest eventInterest) {
        this.eventInterest = eventInterest;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", interest=" + eventInterest + ", name=" + name + ", initialDate=" + initialDate
                + ", endDate=" + endDate + ", idCreator=" + idCreator + ", creationDate=" + creationDate + ", location="
                + location + ", description=" + description + "]";
    }

}
