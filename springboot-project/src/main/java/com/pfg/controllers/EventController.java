package com.pfg.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfg.interfaceService.IEventService;
import com.pfg.interfaceService.IInterestService;
import com.pfg.interfaceService.IUserService;
import com.pfg.models.Event;
import com.pfg.models.Interest;
import com.pfg.models.SessionUtils;
import com.pfg.models.User;
import com.pfg.service.UploadFileService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/events")
public class EventController {

    //SERVICIO DE EVENTOS
    @Autowired
    private IEventService service;

    //SERVICIO DE INTERESES
    @Autowired
    private IInterestService intService;

    //SERVICIO DE DATOS DE USUARIO
    @Autowired
    private IUserService userService;

    //SERVICIO DE SUBIDA DE ARCHIVOS
    @Autowired
    private UploadFileService uploadService;

    //CLASE SISTEMA DE SESIONES
    private SessionUtils SU = new SessionUtils();

    //OBTENER SESION ACTUAL
    public HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(false);
    }

    //OBTENER USUARIO DE SESION
    public User getSessionUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        return sessionUser;
    }

    //CREAR EVENTO
    @GetMapping("/new")
    public String gotoEventCreation(Model model) {
        //CREACION DE NUEVO EVENTO
        Event EV = new Event();
        //AÑADIDO DE DATOS AL MODELO
        model.addAttribute("event", EV);
        model.addAttribute("interestList", intService.listAllInterest());
        model.addAttribute("user", getSessionUser());
        return "create_event";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute("event") Event event, BindingResult bindingResult, HttpServletRequest request, @RequestParam("eventImage") MultipartFile file,
    @RequestParam("interest") Long interest) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //USUARIO DE LA SESION
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder .currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");

        //GUARDADO DE LA FOTO DEL USUARIO EN LOCAL
        uploadService.saveEventImage(file);
        //ASIGNACION DE DATOS AL EVENTO
        event.setImage_url(file.getOriginalFilename());
        event.setIdCreator(actualUser.getId());
        Interest it = intService.getInterestById(interest);
        event.setInterest(it);

        //ACTUALIZACION DE EVENTOS DEL USUARIO
        Set<Event> events = actualUser.getEvents();
        events.add(event);
        actualUser.setEvents(events);
        //GUARDADO DE EVENTO
        service.createEvent(event);
        
        userService.createUser(actualUser);

        return "redirect:/home/userpage/" + actualUser.getId();
    }

    //VER EVENTO INDIVIDUAL
    @GetMapping("/view/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //OBTENCION DE DATOS PARA LA WEB
        Event actualEvent = service.readEventId(id);
        Set<User> userList = actualEvent.getUsers();
        User author = userService.readUserId(actualEvent.getIdCreator());
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");
        //AÑADIDO DE ELEMENTOS A MODELO
        model.addAttribute("userS", actualUser);
        model.addAttribute("event", actualEvent);
        model.addAttribute("author", author);
        model.addAttribute("userList", userList);
        model.addAttribute("startDate", LocalDate.parse(actualEvent.getInitialDate()));
        model.addAttribute("endDate", LocalDate.parse(actualEvent.getEndDate()));
        return "event_page";
    }

    //EDITAR EVENTO
    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //AÑADIDO DE DATOS AL MODELO
        Event actualEvent = service.readEventId(id);
        model.addAttribute("event", actualEvent);
        model.addAttribute("userS", getSessionUser());
        model.addAttribute("interestList", intService.listAllInterest());
        return "edit_event";
    }

    @PostMapping("/update")
    public String updateEvent(@ModelAttribute("event") Event event, BindingResult bindingResult) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //ACTUALIZACION DE EVENTO EXISTENTE CON EVENTO ENVIADO
        Event actualEvent = service.readEventId(event.getId());
        actualEvent.setName(event.getName());
        actualEvent.setInitialDate(event.getInitialDate());
        actualEvent.setEndDate(event.getEndDate());
        actualEvent.setLocation(event.getLocation());
        actualEvent.setDescription(event.getDescription());
        actualEvent.setInterest(event.getInterest());
        //GUARDADO DEL EVENTO
        service.createEvent(actualEvent);
        return "redirect:/events/view/" + actualEvent.getId();
    }
    
    //IR A BUSQUEDA DE EVENTOS
    @GetMapping("/search")
    public String goToEventSearch(Model model) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //AÑADIDO DE DATOS A MODELO
        List<Event> eventList = service.listEventsByList(getSessionUser().getUserData().getInterests());
        model.addAttribute("interestList", intService.listAllInterest());
        model.addAttribute("eventList", eventList);
        model.addAttribute("usersession", getSessionUser());
        return "events";
    }

    //EVENTOS POR INTERES
    @PostMapping("/interest")
    public ResponseEntity<String> handleInterest(@Valid @RequestBody Map<String, String> payload) {
        String interestValue = payload.get("value");
        Long id = Long.parseLong(interestValue); //ID RECOGIDO
        //INTERES EN BASE AL ID
        Interest interest = intService.getInterestById(id);
        //LISTA DE EVENTOS EN BASE A INTERES
        Set<Event> list = interest.getOnlyEvent(); 
        List<Event> list2 = new ArrayList<>(list);
        ObjectMapper objectMapper = new ObjectMapper();
        //MAPEADO DE LISTA DE EVENTOS A JSON
        try {
            String jsonEvents = objectMapper.writeValueAsString(list2);
            return ResponseEntity.ok().body(jsonEvents);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //AÑADIR A EVENTO
    @GetMapping("/join/{id}")
    public String eventUserAdd(Model model, @PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //AÑADIDO DE DATOS A MODELO
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");
        Event actualEvent = service.readEventId(id);
        actualEvent.getUsers().add(actualUser);
        service.createEvent(actualEvent);
        actualUser.getEvents().add(actualEvent);
        userService.createUser(actualUser);
        
        return "redirect:/home/userpage/" + actualUser.getId();
    }

    //ELIMINAR DE EVENTO
    @GetMapping("/leave/{id}")
    public String eventUserLeave(Model model, @PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //DATOS PARA EL BORRADO
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");
        Event actualEvent = service.readEventId(id);

        //BORRADO DE USUARIO DE EVENTO Y BORRADO DE EVENTO DE USUARIO
        actualUser.getEvents().removeIf(event -> event.getId().equals(actualEvent.getId()));
        userService.createUser(actualUser);
        actualEvent.getUsers().removeIf(user -> user.getId().equals(userService.readUserId(actualUser.getId()).getId()));
        service.createEvent(actualEvent);
        return "redirect:/home/userpage/" + actualUser.getId();
    }

    //ELIMINAR EVENTO
    @GetMapping("/delete/{id}")
    public String deleteEvent(Model model, @PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //DATOS PARA RETIRADA
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");
        Event actualEvent = service.readEventId(id);
        //ACTUALIZACION DE USUARIOS QUITANDO EVENTO
        for (User user : actualEvent.getUsers()) {
            user.getEvents().removeIf(event -> event.getId().equals(actualEvent.getId()));
            userService.createUser(user);
        }
        //ELIMINACION DE EVENTO
        service.deleteEvent(id);
        return "redirect:/home/userpage/" + actualUser.getId();
    }
    
}
