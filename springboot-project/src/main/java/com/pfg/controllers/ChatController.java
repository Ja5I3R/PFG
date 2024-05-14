package com.pfg.controllers;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfg.interfaceService.IChatService;
import com.pfg.interfaceService.IUserService;
import com.pfg.models.Chat;
import com.pfg.models.Message;
import com.pfg.models.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;



@Controller
public class ChatController {

    @Autowired
    private IChatService service;

    @Autowired
    private IUserService userService;

    @GetMapping("/chat/create/{id}")
    public String createEvent(Model model, @PathVariable Long id) {
        User userWith = userService.readUserId(id); //USUARIO CON QUIEN HABLAS
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user"); //USUARIO DE SESION
        Chat chat = new Chat();
        chat.setContentURL(sessionUser.getId() + "_" + userWith.getId());
        Path directory = Paths.get("src//main//resources//static/chats");
        String absolutePath = directory.toFile().getAbsolutePath() + "//chat" + sessionUser.getId() + "_" + userWith.getId();
        File file = new File(absolutePath);
        try{
            if(!file.exists())
            {
                file.createNewFile();
            }

            LocalDateTime actualDate = LocalDateTime.now();
            chat.setCreationDate(actualDate);

            userWith.getChats().add(chat);
            sessionUser.getChats().add(chat);
            service.createChat(chat);
            userService.createUser(userWith);
            userService.createUser(sessionUser);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/chat/view/" + chat.getId();
    }
    
    @GetMapping("/chat/view/{id}")
    public String viewEvent(Model model, @PathVariable Long id, ResourceLoader resourceLoader) {
        Chat actualChat = service.readChatId(id); //CHAT A ENVIAR
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user"); //USUARIO PARA ENVIAR MENSAJES

        Set<User> chatUserList = actualChat.getUsers(); //LISTA DE USUARIOS DEL CHAT

        // Construir la ruta al archivo JSON en resources/static/chats
        String filePath = "/springboot-project/src/main/resources/static/chats/" + actualChat.getContentURL();

        String jsonContent;
        try {
            Resource resource = resourceLoader.getResource(filePath);
            jsonContent = new String(Files.readAllBytes(resource.getFile().toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to read chat content.");
            return "error"; // Return error view (ensure you have an error.html page)
        }

        // Parsear el JSON a una lista de mensajes
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> messageList = new ArrayList<>();
        try {
            messageList = objectMapper.readValue(jsonContent, new TypeReference<List<Message>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("messages", messageList);
        model.addAttribute("usersession", sessionUser);

        return "chat";
    }
    
}
