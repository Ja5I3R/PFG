package com.pfg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IChatService;
import com.pfg.interfaces.IChat;
import com.pfg.models.Chat;

@Service
public class ChatService implements IChatService{

    @Autowired
	private IChat repository;

    @Override
    @Transactional(readOnly = true)
    public List<Chat>listAllChats(){
        return repository.findAll();
    }

    @Override
    @Transactional
	public Chat createChat(Chat chat){
        return repository.save(chat);
    }
	
    @Override
    @Transactional(readOnly = true)
	public Chat readChatId(Long id){
        return repository.findById(id).get();
    }
	
    @Override
    @Transactional
	public void deleteChat(Long id){
        repository.deleteById(id);
    }
}
