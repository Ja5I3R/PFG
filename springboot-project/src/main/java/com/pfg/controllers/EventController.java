package com.pfg.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pfg.interfaceService.IEventDataService;
import com.pfg.interfaceService.IEventService;
import com.pfg.interfaceService.IInterestService;
import com.pfg.interfaceService.IUserService;
import com.pfg.models.Event;
import com.pfg.models.EventData;
import com.pfg.models.User;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class EventController {
    @Autowired
    private IEventService service;

    @Autowired
    private IEventDataService eventDService;

    @Autowired
    private IInterestService intService;

    @Autowired
    private IUserService userService;

    //CREAR EVENTO
    @GetMapping("/events/new")
    public String gotoeventCreation(Model model) {
        Event EV = new Event();
        model.addAttribute("event", EV);
        model.addAttribute("interestList", intService.listAllInterest());
        return "create_event";
    }

    @PostMapping("/events/create")
    public String postMethodName(@ModelAttribute("event") Event event, BindingResult bindingResult, HttpServletRequest request, @RequestParam("eventImage") MultipartFile file) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");
        Path path = Paths.get("src//main//resources//static//img/events");
        String absolutePath = path.toFile().getAbsolutePath();

        try {
                byte[] bytes = file.getBytes();
                Path completePath = Paths.get(absolutePath + "//" + file.getOriginalFilename());
                Files.write(completePath, bytes);
                event.setImage_url(file.getOriginalFilename());
                event.setIdCreator(actualUser.getId());
                service.createEvent(event);
                
                EventData ED = new EventData();
                ED.setIdUser(actualUser.getId());
                ED.setIdEvent(event.getId());
                eventDService.saveEventData(ED);
        
                return "index";
        } catch (IOException e){
            e.printStackTrace();
            return "error";
        }
    }

    //VER EVENTO INDIVIDUAL
    @GetMapping("/events/view/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        Event actualEvent = service.readEventId(id);
        List<User> userList = userService.getUserList(eventDService.getUsersToEvent(id));
        User author = userService.readUserId(actualEvent.getIdCreator());
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");
        model.addAttribute("userS", actualUser);
        model.addAttribute("event", actualEvent);
        model.addAttribute("author", author);
        model.addAttribute("userList", userList);
        return "event_page";
    }

    //IR A BUSQUEDA DE EVENTOS
    @GetMapping("/events/search")
    public String goToEventSearch(Model model) {
        List<Event> eventList = service.listAllEvents(); 
        model.addAttribute("eventList", eventList);
        return "events";
    }
    
    
}
