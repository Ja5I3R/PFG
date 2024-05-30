package com.pfg.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfg.interfaceService.IChatService;
import com.pfg.interfaceService.IInterestService;
import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaceService.IUserService;
import com.pfg.models.Chat;
import com.pfg.models.Interest;
import com.pfg.models.Message;
import com.pfg.models.MessageRequest;
import com.pfg.models.User;
import com.pfg.service.UploadFileService;

import org.springframework.ui.Model;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatService service;

    @Autowired
    private IUserService userService;

    @Autowired
    private UploadFileService uploadService;

    @Autowired
    private IUserDataService udService;

    @Autowired
    private IInterestService intService;

    private String[] getCommonInterests(User user1, User user2) {
        List<Interest> user1Interests = intService.listByIndexes(udService.getInterestList(user1));
        List<Interest> user2Interests = intService.listByIndexes(udService.getInterestList(user2));
    
        long commonInterestsCount = user1Interests.stream()
                .filter(user2Interests::contains)
                .count();
    
        String fraction = commonInterestsCount + "/" + user1Interests.size();
    
        StringBuilder commonInterestsBuilder = new StringBuilder();
        for (Interest interest : user1Interests) {
            if (user2Interests.contains(interest)) {
                if (commonInterestsBuilder.length() > 0) {
                    commonInterestsBuilder.append(", ");
                }
                commonInterestsBuilder.append(interest.getName());
            }
        }
        String commonInterests = commonInterestsBuilder.toString();
    
        return new String[] { fraction, commonInterests };
    }
    

    @PostMapping("/create/group")
    public String createGroup(@RequestParam("name") String name,
            @RequestParam("participants") List<Long> participantIds, @RequestParam("interest") Long interestId,
            Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        Chat chat = new Chat();
        chat.setName(name);
        chat.setChatType(2L);
        chat.setCreationDate(LocalDateTime.now());
        chat.setContentURL("chat_group_" + name + ".json");
        chat.setChatInterest(intService.findById(interestId));

        String filePath = "data/chats/" + chat.getContentURL();
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);

                BufferedWriter writer = Files.newBufferedWriter(path);
                writer.write("[{}]");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<User> chatUsers = new HashSet<>();
        chatUsers.add(sessionUser);

        for (Long userId : participantIds) {
            User participant = userService.readUserId(userId);
            chatUsers.add(participant);
        }
        chat.setUsers(chatUsers);

        service.createChat(chat);

        List<User> usersToUpdate = new ArrayList<>(chatUsers);

        for (User user : usersToUpdate) {
            user.getChats().add(chat);
            userService.createUser(user);
        }

        return "redirect:/chat/view/" + chat.getId();
    }

    @GetMapping("/create/group")
    public String createGroupPage(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return "redirect:/login";
        }

        Set<User> friends;
        Set<Chat> chats = sessionUser.getChats();
        friends = userService.getFriends(sessionUser, chats);
        List<Interest> interestList = intService.listByIndexes(udService.getInterestList(sessionUser));

        model.addAttribute("friends", friends);
        model.addAttribute("interestList", interestList);

        return "create_group";
    }

    @GetMapping("/create/{id}")
    public String createChat(Model model, @PathVariable Long id) {
        User userWith = userService.readUserId(id); // USUARIO CON QUIEN HABLAS
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user"); // USUARIO DE SESION
        Chat chat = new Chat();
        chat.setContentURL("chat" + sessionUser.getId() + "_" + userWith.getId() + ".json");

        String filePath = "data/chats/" + chat.getContentURL();

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);

                BufferedWriter writer = Files.newBufferedWriter(path);
                writer.write("[{}]");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDateTime actualDate = LocalDateTime.now();
        chat.setCreationDate(actualDate);
        chat.setChatType(1L);

        userWith.getChats().add(chat);
        sessionUser.getChats().add(chat);
        service.createChat(chat);
        userService.createUser(userWith);
        userService.createUser(sessionUser);
        return "redirect:/chat/view/" + chat.getId();
    }

    @GetMapping("/view/{id}")
    public String viewEvent(Model model, @PathVariable Long id) {
        Chat actualChat = service.readChatId(id); // CHAT A ENVIAR
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user"); // USUARIO PARA ENVIAR MENSAJES

        Set<User> chatUserList = actualChat.getUsers(); // LISTA DE USUARIOS DEL CHAT

        try {
            String filePath = "data/chats/" + actualChat.getContentURL();
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            String jsonContent = new String(bytes);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Message> messageList = objectMapper.readValue(jsonContent, new TypeReference<List<Message>>() {
            });
            if (messageList.size() == 0) {
                messageList = new ArrayList<>();
            }
            model.addAttribute("messages", messageList);
            model.addAttribute("usersession", sessionUser);
            model.addAttribute("chat", actualChat);
            if (actualChat.getChatType() == 2) {
                model.addAttribute("chatName", actualChat.getName());
            }else{
                List<User> userList = new ArrayList<>(chatUserList);
                model.addAttribute("interestsList", getCommonInterests(sessionUser, userList.get(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "chat";
    }

    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public Message send(MessageRequest message) throws Exception {
        Chat chat = service.readChatId(message.getId());
        saveMessage(message.getUser(), message.getMessage(), chat);
        return new Message(message.getUser(), message.getMessage());
    }

    public void saveMessage(String user, String message, Chat chat) {
        uploadService.saveChatMessage(user, message, chat);
    }
}
