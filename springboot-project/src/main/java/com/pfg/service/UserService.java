package com.pfg.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaceService.IUserService;
import com.pfg.interfaces.IUser;
import com.pfg.interfaces.IUserData;
import com.pfg.models.Event;
import com.pfg.models.Interest;
import com.pfg.models.Rol;
import com.pfg.models.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUser repository;

    @Autowired
    private IUserData userDataRepository;

	public List<User> findUsersByInterests(List<Interest> interests) {
        Map<User, Integer> userInterestCountMap = new HashMap<>();

        List<User> usersWithInterests = userDataRepository.findUsersByInterests(interests);

		// Crear mapa con usuarios (clave, valor)
		// User1 -> 3, User2 -> 5, User3 -> 2
        for (User user : usersWithInterests) {
            for (Interest interest : interests) {
                if (user.getUserData().getInterests().contains(interest)) {
                    userInterestCountMap.put(user, userInterestCountMap.getOrDefault(user, 0) + 1);
                }
            }
        }

		// Ordenar la lista por intereses 
		// User2 -> 5, User1 -> 3, User3 -> 2
        List<Map.Entry<User, Integer>> sortedUserList = new ArrayList<>(userInterestCountMap.entrySet());
        sortedUserList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

		// Mapeo [User2, User1, User3]
        return sortedUserList.stream()
                             .map(Map.Entry::getKey)
                             .collect(Collectors.toList());
    }
	
	@Override
	@Transactional
	public User createUser(User user) {
		Rol rol = new Rol(2L, "Usuario");
		user.setRol(rol);
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
			LocalDate date = LocalDate.parse(user.getBirthdate());	

			LocalDate actualDate = LocalDate.now();

			int age = Period.between(date, actualDate).getYears();
			Long finalAge = Long.valueOf(age);

			user.setAge(finalAge);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Override
	@Transactional(readOnly = true)
    public List<User>getUserList(List<Long> idList){
        List<User> list = new ArrayList<>();

        for(Long userA : idList){
			if(readUserId(userA) != null){
				list.add(readUserId(userA));
			}
		}

        return list;
    }

	@Override
    @Transactional(readOnly = true)
    public List<Event> getEventList(User user) {
        List<Event> eventList = new ArrayList<>();

        if (user != null && user.getEvents() != null) {
            Set<Event> events = user.getEvents();
            for (Event event : events) {
                eventList.add(event);
            }
        }

        return eventList;
    }
}
