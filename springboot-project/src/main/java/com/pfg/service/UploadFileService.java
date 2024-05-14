package com.pfg.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.Multipart;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

    public void saveUserImage(MultipartFile file){
        Path directory = Paths.get("src//main//resources//static/img");
        String absolutePath = directory.toFile().getAbsolutePath();

        try{
            byte[] bytesImg = file.getBytes();
            Path completePath = Paths.get(absolutePath + "//" + file.getOriginalFilename());
            Files.write(completePath, bytesImg);

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveEventImage(MultipartFile file){
        Path directory = Paths.get("src//main//resources//static//img/eventsImg");
        String absolutePath = directory.toFile().getAbsolutePath();

        try{
            byte[] bytesImg = file.getBytes();
            Path completePath = Paths.get(absolutePath + "//" + file.getOriginalFilename());
            Files.write(completePath, bytesImg);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
