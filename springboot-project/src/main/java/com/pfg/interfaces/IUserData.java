package com.pfg.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfg.models.UserData;

@Repository
public interface IUserData extends JpaRepository<UserData, Long>{

}
