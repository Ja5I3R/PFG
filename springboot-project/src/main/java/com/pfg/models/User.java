package com.pfg.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", nullable = false,length = 50)
	private String username;
	
	@Column(name = "password", nullable = false,length = 50)
	private String password;
	
	@Column(name = "name", nullable = false,length = 50)
	private String name;	

	@Column(name = "surname", nullable = false,length = 50)
	private String surname;

	@Column(name = "email", nullable = false,length = 50,unique=true)
	private String email;

	@Column(name = "age", nullable = false)
	private Long age;

	@Column(name = "birthdate", nullable = false)
	private LocalDate birthdate;

	@Column(name = "gender", nullable = false)
	private Long gender;

	@Column(name = "id_rol", nullable = false)
	private Long id_rol;
	
	public User() {		
	}

	public User(String username, String password, String name, String surname, String email, Long age, Long gender, Long id_rol, LocalDate birhDate) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.id_rol = id_rol;
		this.birthdate = birhDate;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password" + password + ", surname=" + surname + ", age=" + age + ", gender" + gender + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId_rol(){
		return id_rol;
	}

	public void setId_rol(Long id_rol){
		this.id_rol = id_rol;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	
}
