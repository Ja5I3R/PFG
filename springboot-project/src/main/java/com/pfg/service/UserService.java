package com.pfg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IUserService;
import com.pfg.interfaces.IUser;
import com.pfg.models.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUser repository;

	@Override
	@Transactional
	public User createUser(User user) {
		user.setId_rol(1L);
		return repository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> listAllUsers() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User readUserId(Long id) {
		return repository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
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
	@Transactional
	public User updateUser(User user) {
		return repository.save(user);
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		Optional<User> userDB = repository.findById(id);
		if (userDB.isPresent()) {
			repository.deleteById(id);
		}
	} 

}
