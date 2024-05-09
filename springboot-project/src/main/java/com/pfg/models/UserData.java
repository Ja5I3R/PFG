package com.pfg.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_userdata")
public class UserData {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="user1")
    private Set<IndividualChat> indChat1;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="user2")
    private Set<IndividualChat> indChat2;

    @Column(name = "id_interest1", nullable = false)
    private Long interest1_id;

    @Column(name = "id_interest2", nullable = false)
    private Long interest2_id;

    @Column(name = "id_interest3", nullable = false)
    private Long interest3_id;

    @Column(name = "id_interest4", nullable = false)
    private Long interest4_id;

    @Column(name = "id_interest5", nullable = false)
    private Long interest5_id;

    @Column(name = "report_number", nullable = false, columnDefinition = "bigint default 0")
    private Long report_number;

    public UserData() {
    }

    public UserData(Long id, User user, Long interest1_id, Long interest2_id, Long interest3_id, Long interest4_id,
            Long interest5_id, Long report_number) {
        this.id = id;
        this.user = user;
        this.interest1_id = interest1_id;
        this.interest2_id = interest2_id;
        this.interest3_id = interest3_id;
        this.interest4_id = interest4_id;
        this.interest5_id = interest5_id;
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

    public Long getInterest1_id() {
        return interest1_id;
    }

    public void setInterest1_id(Long interest1_id) {
        this.interest1_id = interest1_id;
    }

    public Long getInterest2_id() {
        return interest2_id;
    }

    public void setInterest2_id(Long interest2_id) {
        this.interest2_id = interest2_id;
    }

    public Long getInterest3_id() {
        return interest3_id;
    }

    public void setInterest3_id(Long interest3_id) {
        this.interest3_id = interest3_id;
    }

    public Long getInterest4_id() {
        return interest4_id;
    }

    public void setInterest4_id(Long interest4_id) {
        this.interest4_id = interest4_id;
    }

    public Long getInterest5_id() {
        return interest5_id;
    }

    public void setInterest5_id(Long interest5_id) {
        this.interest5_id = interest5_id;
    }

    public Long getReport_number() {
        return report_number;
    }

    public void setReport_number(Long report_number) {
        this.report_number = report_number;
    }

    @Override
    public String toString() {
        return "UserData [id=" + id + ", indChat1=" + indChat1 + ", indChat2=" + indChat2 + ", interest1_id="
                + interest1_id + ", interest2_id=" + interest2_id + ", interest3_id=" + interest3_id + ", interest4_id="
                + interest4_id + ", interest5_id=" + interest5_id + ", report_number=" + report_number + "]";
    }
    
}
