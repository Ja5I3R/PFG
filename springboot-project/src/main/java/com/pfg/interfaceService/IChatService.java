package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.Chat;

public interface IChatService {

    public List<Chat>listAllChats();

	public Chat createChat(Chat event);
	
	public Chat readChatId(Long id);
	
	public void deleteChat(Long id) ;

}