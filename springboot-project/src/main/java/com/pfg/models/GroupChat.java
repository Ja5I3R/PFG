package com.pfg.models;

import java.time.LocalDateTime;
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

@Entity
@Table(name = "t_grupalchats")
public class GroupChat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "idInterest")
    private Interest groupInterest;

	@ManyToMany(mappedBy = "groupchats")
    private Set<User> users;

	@Column(name = "contentURL", nullable = false, length = 50)
	private String contentURL;

    @Column(name = "creationDate", nullable = false, length = 50)
	private LocalDateTime creationDate;

	@ManyToOne
	@JoinColumn(name = "idCreator")
	private User creator;

	public GroupChat() {
	}

	public GroupChat(Long id, Interest groupInterest, String contentURL, User creator) {
		this.id = id;
		this.groupInterest = groupInterest;
		this.contentURL = contentURL;
		this.creator = creator;
		this.creationDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Interest getGroupInterest() {
		return groupInterest;
	}

	public void setGroupInterest(Interest groupInterest) {
		this.groupInterest = groupInterest;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "GroupChat [id=" + id + ", contentURL=" + contentURL + ", creationDate=" + creationDate + "]";
	}

}
