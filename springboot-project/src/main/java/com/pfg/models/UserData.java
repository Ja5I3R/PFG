package com.pfg.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_userdata")
public class UserData {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "id_user", nullable = false)
    private Long user_id;

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

    public UserData() {
    }

    public UserData(Long id, Long user_id, Long interest1_id, Long interest2_id, Long interest3_id, Long interest4_id,
            Long interest5_id) {
        this.id = id;
        this.user_id = user_id;
        this.interest1_id = interest1_id;
        this.interest2_id = interest2_id;
        this.interest3_id = interest3_id;
        this.interest4_id = interest4_id;
        this.interest5_id = interest5_id;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    
}
