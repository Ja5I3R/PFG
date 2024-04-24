package com.pfg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfg.interfaceService.IUserService;
import com.pfg.interfaces.IUser;
import com.pfg.models.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUser repository;

	@Override
	public List<User> listAllUsers() {
		return repository.findAll();
	}

	@Override
	public User createUser(User user) {
		user.setId_rol(1L);
		return repository.save(user);
	}

	@Override
	public User readUserId(Long id) {
		return repository.findById(id).get();
	}
	
	@Override
	public User readUserName(String name) {
		User result = null;
        List<User> userList = repository.findAll();
        for (User user : userList) {
            if (user.getUsername().equals(name)) {
                result = user;
            }
        }
		return result;
    }

	@Override
	public User readEmail(String email) {
		User result = null;
        List<User> userList = repository.findAll();
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                result = user;
            }
        }
		return result;
    }

	@Override
	public User updateUser(User user) {
		return repository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		repository.deleteById(id);
	}

}
