package com.pfg.interfaces;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfg.models.Interest;
import com.pfg.models.User;

@Repository
public interface IUser extends JpaRepository<User, Long>{

}
