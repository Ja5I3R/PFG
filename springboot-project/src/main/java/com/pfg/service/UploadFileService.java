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

    private String upload_folder = "//src//main//resources//static//img//";
    private String upload_Folder2 = "src/main/resources/static/img/eventsImg";

    public void saveUserImage(MultipartFile file) throws IOException{
        if(!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Path completePath = Paths.get(upload_folder + file.getOriginalFilename());
            Files.write(completePath, bytes);
        }
    }

    public void saveEventImage(MultipartFile file){
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
}
