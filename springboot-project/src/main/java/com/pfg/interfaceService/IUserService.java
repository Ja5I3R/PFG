package com.pfg.interfaceService;

import java.util.List;
import java.util.Set;

import com.pfg.models.Chat;
import com.pfg.models.Event;
import com.pfg.models.Interest;
import com.pfg.models.User;

public interface IUserService {
	
	//METODO PARA CONSEGUIR TODOS LOS USUARIOS
	public List<User>listAllUsers();

	//METODO PARA CREAR UN USUARIO
	public User createUser(User user);
	
	//METODO PARA CONSEGUIR UN USUARIO POR ID
	public User readUserId(Long id);
	
	//METODO PARA ACTUALIZAR UN USUARIO
	public User updateUser(User user);
	
	//METODO PARA BORRAR UN USUARIO 
	public void deleteUser(Long id) ;

	//METODO PARA CONSEGUIR UN USUARIO POR NOMBRE
	public User readUserName(String name);

	//METODO PARA CONSEGUIR UN USUARIO POR EMAIL
	public User readEmail(String email);

	//METODO PARA CONSEGUIR UNA LISTA DE USUARIOS POR UNA LISTA DE IDS
	public List<User> getUserList(List<Long> idList);

	//METODO PARA CONSEGUIR LISTA DE EVENTOS DE UN USUARIO
	public List<Event> getEventList(User user);

	//METODO PARA CONSEGUIR LISTA DE USUARIOS EN BASE A UNA LISTA DE INTERESES
	public List<User> findUsersByInterests(List<Interest> interests);

	//METODO PARA CONSEGUIR UNA LISTA DE USUARIOS AMIGOS
	public Set<User> getFriends(User currentUser, Set<Chat> chats);
	
}
