package com.pfg.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	@JsonIgnore
    private UserData userData;

	@ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

	@Column(name = "username", nullable = false,length = 50)
	private String username;
	
	@Column(name = "password", nullable = false,length = 50)
	private String password;
	
	@Column(name = "name", nullable = false,length = 50)
	private String name;	

	@Column(name = "surname", nullable = false,length = 50)
	private String surname;

	@Column(name = "email", nullable = false, length = 50, unique=true)
	private String email;

	@Column(name = "age", nullable = false)
	private Long age;

	@Column(name = "birthdate", nullable = false)
	private String birthdate;

	@Column(name = "gender", nullable = false)
	private Long gender;

	@Column(name = "image_url", nullable = false)
	private String image_url;

	@Column(name = "profile_id", nullable = false)
	private Long avatar_id;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "t_chat_users", 
		joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "id_chat", referencedColumnName = "id"),
		uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_chat"})})
	private Set<Chat> chats;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "t_events_users", 
		joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "id_event", referencedColumnName = "id"),
		uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_event"})})
	private Set<Event> events;
	
	public User() {
	}

	public User(Long id, Rol rol, String username, String password, String name, String surname, String email, Long age,
			String birthdate, Long gender, String image, Long avatar, Set<Event> events) {
		this.id = id;
		this.rol = rol;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.birthdate = birthdate;
		this.gender = gender;
		this.image_url = image;
		this.avatar_id = avatar;
		this.events = events;
	}

	public User(Long id, UserData userData, Rol rol, String username, String password, String name, String surname,
			String email, Long age, String birthdate, Long gender, String image, Long avatar) {
		this.id = id;
		this.userData = userData;
		this.rol = rol;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.birthdate = birthdate;
		this.gender = gender;
		this.image_url = image;
		this.avatar_id = avatar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public void setUserData(UserData userData) {
        this.userData = userData;
        userData.setUser(this);
    }

	public UserData getUserData() {
		return userData;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Long getAvatar_id() {
		return avatar_id;
	}

	public void setAvatar_id(Long avatar_id) {
		this.avatar_id = avatar_id;
	}

	public Set<Event> getEvents() {
		return events;
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

	public String getFormattedBirthdate() {
        if (this.birthdate != null && !this.birthdate.isEmpty()) {
            try {
                DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(this.birthdate, originalFormat);
                return date.format(targetFormat);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                return "Formato de fecha no válido";
            }
        } else {
            return "Fecha no disponible";
        }
    }
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userData=" + userData + ", rol=" + rol + ", username=" + username + ", password="
				+ password + ", name=" + name + ", surname=" + surname + ", email=" + email + ", age=" + age
				+ ", birthdate=" + birthdate + ", gender=" + gender + "]";
	}


}
