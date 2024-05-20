package com.pfg.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "t_userdata")
public class UserData {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "t_userdata_interests", 
		joinColumns = @JoinColumn(name = "id_userdata", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "id_interest", referencedColumnName = "id"),
		uniqueConstraints = {@UniqueConstraint(columnNames = {"id_userdata", "id_interest"})})
	private Set<Interest> interests;

    @Column(name = "report_number", nullable = false, columnDefinition = "bigint default 0")
    private Long report_number;

    public UserData() {
    }

    public UserData(Long id, User user, Set<Interest> interests, Long report_number) {
        this.id = id;
        this.user = user;
        this.interests = interests;
        this.report_number = report_number;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getReport_number() {
        return report_number;
    }

    public void setReport_number(Long report_number) {
        this.report_number = report_number;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterest(Set<Interest> userInterest) {
        this.interests = userInterest;
    }

    @Override
    public String toString() {
        return "UserData [id=" + id + ", interests=" + interests + ", report_number=" + report_number + "]";
    }
    
}
