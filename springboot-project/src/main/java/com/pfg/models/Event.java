package com.pfg.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_events")
public class Event {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "name", nullable = false,length = 50)
	private String name;

    @Column(name = "initial_date", nullable = false)
	private LocalDate initialDate;

    @Column(name = "end_date", nullable = false)
	private LocalDate endDate;

    @Column(name = "id_creator", nullable = false)
	private Long idCreator;

    @Column(name = "create_date", nullable = false)
	private LocalDate creationDate;

    @Column(name = "location", nullable = false,length = 50)
	private String location;

    @Column(name = "description", nullable = false,length = 50)
	private String description;

    @Column(name = "id_interest", nullable = false)
	private Long idInterest;

    public Event() {
    }

    public Event(String name, LocalDate initialDate, LocalDate endDate, Long idCreator, LocalDate creationDate,
            String location, String description, Long idInterest) {
        this.name = name;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.idCreator = idCreator;
        this.creationDate = creationDate;
        this.location = location;
        this.description = description;
        this.idInterest = idInterest;
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

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(Long idCreator) {
        this.idCreator = idCreator;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    public Long getIdInterest() {
        return idInterest;
    }

    public void setIdInterest(Long idInterest) {
        this.idInterest = idInterest;
    }

    
}
