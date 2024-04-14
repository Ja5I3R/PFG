package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.User;

public interface IUserService {
	
	public List<User>listAllUsers();

	public User createUser(User user);
	
	public User readUserId(Long id);
	
	public User updateUser(User user);
	
	public void deleteUser(Long id) ;
}
