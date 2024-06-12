package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.Chat;

public interface IChatService {

	//METODO PARA CONSEGUIR TODOS LOS CHATS
    public List<Chat>listAllChats();

	//METODO PARA CREAR CHAT
	public Chat createChat(Chat event);
	
	//METODO PARA LEER CHAT EN BASE A UN ID
	public Chat readChatId(Long id);
	
	//METODO PARA BORRAR CHAT EN BASE A UN ID
	public void deleteChat(Long id);

}