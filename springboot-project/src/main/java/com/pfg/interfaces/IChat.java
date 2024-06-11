package com.pfg.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfg.models.Chat;

@Repository
public interface IChat extends JpaRepository<Chat, Long>{

}