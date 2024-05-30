package com.pfg.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfg.models.Interest;
import com.pfg.models.User;
import com.pfg.models.UserData;

@Repository
public interface IUserData extends JpaRepository<UserData, Long>{

    @Query("SELECT ud.user FROM UserData ud JOIN ud.interests i WHERE i IN :interests")
    List<User> findUsersByInterests(@Param("interests") List<Interest> interests);

}

