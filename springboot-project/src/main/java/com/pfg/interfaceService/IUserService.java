package com.pfg.interfaceService;

import java.util.List;
import java.util.Set;

import com.pfg.models.Chat;
import com.pfg.models.Event;
import com.pfg.models.Interest;
import com.pfg.models.User;

public interface IUserService {
	
	public List<User>listAllUsers();

	public User createUser(User user);
	
	public User readUserId(Long id);
	
	public User updateUser(User user);
	
	public void deleteUser(Long id) ;

	public User readUserName(String name);

	public User readEmail(String email);

	public List<User> getUserList(List<Long> idList);

	public List<Event> getEventList(User user);

	public List<User> findUsersByInterests(List<Interest> interests);

	public Set<User> getFriends(User currentUser, Set<Chat> chats);
}
