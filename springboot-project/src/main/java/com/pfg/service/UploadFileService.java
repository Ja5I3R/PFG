package com.pfg.service;

import java.io.BufferedWriter;
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

    //GUARDAR IMAGEN DE USUARIO
    public void saveUserImage(MultipartFile file){
        //LECTURA DE IMAGEN Y GUARDADO EN CARPETA DE IMAGEN DE USUARIOS
        try{
            byte[] bytesImg = file.getBytes();
            Path completePath = Paths.get("src/main/resources/static/img/usersImg" + "/" + file.getOriginalFilename());
            Files.write(completePath, bytesImg);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    //GUARDAR IMAGEN DE EVENTO
    public void saveEventImage(MultipartFile file){
        //LECTURA DE IMAGEN Y GUARDADO EN CARPETA DE IMAGEN DE EVENTOS
        try{
            byte[] bytesImg = file.getBytes();
            Path completePath = Paths.get("src/main/resources/static/img/eventsImg" + "/" + file.getOriginalFilename());
            Files.write(completePath, bytesImg);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    //GUARDAR MENSAJE EN CHAT
    public void saveChatMessage(String user, String message, Chat actualChat){
        //LECTURA DE ARCHIVO CON EXTRACCION DEL CONTENIDO Y ACTUALIZACION DEL MISMO
        try{
            //OBTENCION DEL CONTENIDO
            String filePath = "data/chats/" + actualChat.getContentURL();
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            String jsonContent = new String(bytes);
            //MAPEO DEL CONTENIDO A LISTA DE MENSAJES
            ObjectMapper objectMapper = new ObjectMapper();
            List<Message> messageList = objectMapper.readValue(jsonContent, new TypeReference<List<Message>>() {});
            
            //AÑADIDO DE NUEVO MENSAJE
            Message newMessage = new Message(user, message);
            messageList.add(newMessage);
            //GUARDADO DE LISTA ACTUALIZADA
            String jsonString = objectMapper.writeValueAsString(messageList); //CONVERTIR A JSON STRING PARA ARCHIVO
            Path path = Paths.get(filePath);
            BufferedWriter writer = Files.newBufferedWriter(path);
            writer.write(jsonString);
            writer.close();


        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
