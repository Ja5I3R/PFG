package com.pfg.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.mail.Multipart;

import org.apache.catalina.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfg.models.Chat;
import com.pfg.models.Message;

@Service
public class UploadFileService {

    public void saveUserImage(MultipartFile file){
        try{
            byte[] bytesImg = file.getBytes();
            Path completePath = Paths.get("src/main/resources/static/img/usersImg" + "/" + file.getOriginalFilename());
            Files.write(completePath, bytesImg);

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveEventImage(MultipartFile file){
        try{
            byte[] bytesImg = file.getBytes();
            Path completePath = Paths.get("src/main/resources/static/img/eventsImg" + "/" + file.getOriginalFilename());
            Files.write(completePath, bytesImg);

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveChatMessage(String user, String message, Chat actualChat){
        try{
            ClassPathResource resource = new ClassPathResource("data/chats/" + actualChat.getContentURL());
            byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
            String jsonContent = new String(bytes);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Message> messageList = objectMapper.readValue(jsonContent, new TypeReference<List<Message>>() {});
            
            Message newMessage = new Message(user, message);
            messageList.add(newMessage);
            String jsonString = objectMapper.writeValueAsString(messageList); //CONVERTIR A JSON STRING PARA ARCHIVO
            //FALTARIA GUARDAR EN JSON
            String outputPath = resource.getFile().getPath();
            Files.write(Paths.get(outputPath), jsonString.getBytes());


        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
