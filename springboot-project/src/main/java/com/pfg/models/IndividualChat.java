package com.pfg.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_indchats")
public class IndividualChat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne
    @JoinColumn(name = "idUser1")
    private UserData user1;

    @ManyToOne
    @JoinColumn(name = "idUser2")
    private UserData user2;

    @Column(name = "contentURL", nullable = false, length = 50)
	private String contentURL;

    @Column(name = "creationDate", nullable = false, length = 50)
	private LocalDateTime creationDate;

    public IndividualChat(Long id, UserData user1, UserData user2, String contentURL) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.contentURL = contentURL;
        this.creationDate = LocalDateTime.now();
    }

    public IndividualChat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getUser1() {
        return user1;
    }

    public void setUser1(UserData user1) {
        this.user1 = user1;
    }

    public UserData getUser2() {
        return user2;
    }

    public void setUser2(UserData user2) {
        this.user2 = user2;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "IndividualChat [id=" + id + ", contentURL=" + contentURL + ", creationDate=" + creationDate + "]";
    }

    
}
