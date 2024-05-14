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
@Table(name = "t_chats")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "id_interest")
    private Interest chatInterest;

	@ManyToMany(mappedBy = "chats")
    private Set<User> users;

	@Column(name = "content_url", nullable = false, length = 50)
	private String contentURL;

    @Column(name = "creation_date", nullable = false, length = 50)
	private LocalDateTime creationDate;

	@ManyToOne
	@JoinColumn(name = "id_creator")
	private User creator;

	public Chat() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Interest getChatInterest() {
		return chatInterest;
	}

	public void setChatInterest(Interest chatInterest) {
		this.chatInterest = chatInterest;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
