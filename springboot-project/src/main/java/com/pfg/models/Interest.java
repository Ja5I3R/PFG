package com.pfg.models;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_interest")
public class Interest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false,length = 50)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="interest")
    private Set<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="groupInterest")
    private Set<GroupChat> groupChats;

    public Interest() {
    }

    public Interest(String name) {
        this.name = name;
    }

    public Interest(Long id, String name) {
        this.id = id;
        this.name = name;
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
