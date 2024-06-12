package com.pfg.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfg.interfaceService.IEventService;
import com.pfg.interfaceService.IInterestService;
import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaceService.IUserService;
import com.pfg.models.User;
import com.pfg.models.Event;
import com.pfg.models.SessionUtils;
import com.pfg.models.Chat;
import com.pfg.models.Interest;
import com.pfg.models.UserData;
import com.pfg.service.UploadFileService;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {

    //SERVICIO DE USUARIO
    @Autowired
    private IUserService service;

    //SERVICIO DE INTERESES
    @Autowired
    private IInterestService intService;

    //SERVICIO DE DATOS DE USUARIO
    @Autowired
    private IUserDataService userDataService;

    //CLASE SISTEMA DE GUARDADO DE ARCHIVOS
    @Autowired
    private UploadFileService uploadService;

    //SERVICIO DE EVENTOS
    @Autowired
    private IEventService eventService;

    //CLASE SISTEMA DE SESIONES
    private SessionUtils SU = new SessionUtils();

    //ENCRIPTADOR PARA CONTRASEÑAS
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //PAGINA DE SOPORTE
    @GetMapping({ "/support" })
    public String supportPage(Model model) {
        return "support";
    }

    //OBTENER SESION ACTUAL
    public HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(false);
    }

    //OBTENER USUARIO DE LA SESION
    public User getSessionUser() {
        HttpSession session = getSession();
        User sessionUser = (User) session.getAttribute("user");
        return sessionUser;
    }

    //INTERESES EN COMUN ENTRE DOS USUARIOS
    private String getCommonInterestsFraction(User user1, User user2) {
        List<Interest> user1Interests = intService.listByIndexes(userDataService.getInterestList(user1));
        List<Interest> user2Interests = intService.listByIndexes(userDataService.getInterestList(user2));

        long commonInterestsCount = user1Interests.stream()
                .filter(user2Interests::contains)
                .count();
        return commonInterestsCount + "/" + user1Interests.size() + " intereses coincidentes";
    }

    //PAGINA DE DESCUBRIMIENTO DE PERSONAS
    @GetMapping({ "/meet/{id}" })
    public String meetPage(Model model, @PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //LISTA DE INTERESES Y USUARIOS QUE COINCIDEN CON ESE INTERES
        User user = service.readUserId(id);
        List<Interest> currentUserInterests = intService.listByIndexes(userDataService.getInterestList(user));
        List<User> matchedUsers = service.findUsersByInterests(currentUserInterests);

        //RETIRADA DE USUARIO DE LA SESION DE LA LISTA
        matchedUsers.remove(user);

        //NUMERO DE USUARIOS RECOMENDADOS
        List<User> topMatchedUsers = matchedUsers.subList(0, Math.min(3, matchedUsers.size()));
        List<String> interestNumberMatch = new ArrayList<>();

        //LISTA DE INTERESES EN COMUN ENTRE USUARIOS
        for (User userExist : topMatchedUsers) {
            interestNumberMatch.add(getCommonInterestsFraction(userExist, user));
        }

        //AÑADIDO DE DATOS A MODELO
        model.addAttribute("matchedUsers", topMatchedUsers);
        model.addAttribute("interestNumber", interestNumberMatch);
        model.addAttribute("interestList", intService.listAllInterest());
        model.addAttribute("usersession", getSessionUser());

        return "meet_page";
    }

    @GetMapping({ "/meet" })
    public String meetPage(Model model) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //AÑADIDO DE DATOS A MODELO
        model.addAttribute("matchedUsers", service.listAllUsers());
        model.addAttribute("interestList", intService.listAllInterest());
        model.addAttribute("usersession", getSessionUser());

        return "meet_page";
    }

    // OBTENER AMIGOS
    public Set<User> getFriends(User currentUser, Set<Chat> chats) {
        Set<User> friends = new HashSet<>();

        //lISTADO DE CHATS CON OTROS USUARIOS
        for (Chat chat : chats) {
            Set<User> chatUsers = chat.getUsers();
            for (User user : chatUsers) {
                if (!user.equals(currentUser)) { //CONDICION SI EL USUARIO ES DIFERENTE DEL USUARIO DE LA SESION
                    friends.add(user);
                }
            }
        }
        return friends;
    }

    //PAGINA DE INICIO DE SESION
    @GetMapping({ "/login" })
    public String redirectLogIn(Model model) {
        return "try_session";
    }

    //CIERRE DE SESION
    @GetMapping({ "/logout" })
    public String logout(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        //CIERRE DE LA SESION
        session.invalidate();
        return "redirect:/";
    }

    //FILTRADO DE USUARIOS
    @PostMapping("/filter")
    public ResponseEntity<String> viewUsersByFilter(@Valid @RequestBody Map<String, String> payload) {
        //RECOGIDA DEL INTERES SELECCIONADO
        String interestValue = payload.get("value");
        Long id = Long.parseLong(interestValue);
        Interest interest = intService.getInterestById(id);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        //OBTENCION DE LOS USUARIOS DEL INTERES SELECCIONADO
        Set<UserData> users = interest.getUserDatas();
        List<User> listUsers = new ArrayList<>();
        for (UserData user : users) {
            if (!user.getUser().getRol().isAdministrator() && sessionUser.getId() != user.getUser().getId()) { //CONDICION SI EL USUARIO NO ES ADMINISTRADOR Y ES DIFERENTE DEL USUARIO DE LA SESION
                listUsers.add(user.getUser());
            }
        }
        //VACIADO DE DATOS INNECESARIOS
        for (User user : listUsers) {
            user.setChats(null);
            user.setEvents(null);
            user.getUserData().setUser(null);
            user.setRol(null);
        }
        //MAPEO DE LISTADO A JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonEvents = objectMapper.writeValueAsString(listUsers);
            return ResponseEntity.ok().body(jsonEvents);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //PAGINA DE USUARIO POR INTERES
    @GetMapping("/view/{id}")
    public String viewUser(Model model, @PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //AÑADIDO DE DATOS A MODELO
        User user = service.readUserId(id);
        model.addAttribute("uSession", getSessionUser());
        model.addAttribute("user", user);
        model.addAttribute("birth", user.getFormattedBirthdate());
        model.addAttribute("interestList", intService.listByIndexes(userDataService.getInterestList(user)));
        return "view_user";
    }

    //REPORTAR USUARIO
    @GetMapping("/report/{id}")
    public String reportUser(@PathVariable Long id) {
        //USUARIO QUE REPORTA Y USUARIO REPORTADO
        User user = service.readUserId(id);
        UserData userD = user.getUserData();
        //SUBIDA DE NUMERO DE REPORTES A USUARIO REPORTADO
        userD.setReport_number(userD.getReport_number() + 1);
        //GUARDADO DE DATOS
        userDataService.saveUserPreferences(userD);
        return "redirect:/home/meet/" + getSessionUser().getId();
    }

    //PAGINA DE USUARIO
    @GetMapping({ "/userpage/{id}" })
    public String userPage(Model model, @PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }

        User userC = new User();
        try {
            userC = service.readUserId(id);
        } catch (Exception e) {
            return "/error";
        }
        //AÑADIDO DE DATOS A MODELO
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("user", userC);
        model.addAttribute("user", userC);
        model.addAttribute("birth", userC.getFormattedBirthdate());
        model.addAttribute("interestList", intService.listByIndexes(userDataService.getInterestList(userC)));
        model.addAttribute("interestList2", intService.listAllInterest());
        model.addAttribute("chatList", userC.getChats());
        model.addAttribute("friends", getFriends(userC, userC.getChats()));
        if (userC.getRol().isAdministrator()) { //CONDICION SI ES ADMINISTRADOR
            model.addAttribute("userList", service.listAllUsers());
            model.addAttribute("eventList", eventService.listAllEvents());
        } else {
            model.addAttribute("eventList", userC.getEvents());
        }

        return "user_page";
    }

    //CREACION DE NUEVOS USUARIOS
    @GetMapping("/new")
    public String newUser(Model model) {
        //AÑADIDO DE DATOS A MODELO
        User user = new User();
        int[] avatarList = { 1, 2, 3, 4, 5, 6 };
        model.addAttribute("user", user);
        model.addAttribute("interestList", intService.listAllInterest());
        model.addAttribute("avatarList", avatarList);
        return "create_user";
    }


    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request,
            @RequestParam("profileImage") MultipartFile file) {
        User userCreated = service.readUserName(user.getUsername());
        if (userCreated == null) { //CONDICION SI EL USUARIO CREADO NO EXISTE
            userCreated = service.readEmail(user.getEmail());
        }
        if (userCreated != null) { //CONDICION SI EL USUARIO SIGUE SIENDO NULO CON EL EMAIL
            return "redirect:/home/new";
        } else {
            //SUBIDA DE IMAGEN A ARCHIVOS LOCALES
            uploadService.saveUserImage(file);
            //ASIGNACION DE DATOS AL USUARIO
            user.setImage_url(file.getOriginalFilename());
            //HASHEO DE CONTRASEÑA DEL USUARIO
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //GUARDADO DEL USUARIO
            service.createUser(user);
            //AÑADIDO DE INTERESES A USUARIO
            String[] interestIds = request.getParameterValues("interests");
            if (interestIds != null) { //CONDICION SI LOS INTERESES NO SON NULOS
                UserData UD = new UserData();
                UD.setUser(user);
                Set<Interest> interests = new HashSet<>();
                for (String interestId : interestIds) {
                    Interest interest = intService.findById(Long.valueOf(interestId));
                    if (interest != null) {
                        interests.add(interest);
                    }
                }
                //ACTUALIZACION DE DATOS DEL USUARIO
                UD.setInterest(interests);
                UD.setReport_number(0L);
                userDataService.saveUserPreferences(UD);

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(false);

                if (session == null) { // CASO SI ES UN USUARIO NUEVO DESDE EL INDEX
                    session = attr.getRequest().getSession(true);
                    session.setMaxInactiveInterval(-1);
                    session.setAttribute("user", user);
                }
            }
            return "redirect:/home/userpage/" + user.getId();
        }
    }

    //BORRADO DE USUARIOS
    @GetMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        User user = service.readUserId(id);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        //ELMINACION DE EVENTOS DONDE SE ES PROPIETARIO
        for(Event event : user.getEvents()){
            if(event.getIdCreator().equals(user.getId())){ //CONDICION SI EL USUARIO ES EL CREADOR
                for (User userA : event.getUsers()) {
                    //RETIRADA DE EVENTO DEL USUARIO
                    userA.getEvents().removeIf(eventA -> eventA.getId().equals(event.getId()));
                    service.createUser(userA);
                }
                //BORRADO DE EVENTO
                eventService.deleteEvent(event.getId());
            }
        }
        if (sessionUser.getRol().isAdministrator() && sessionUser.getId() != user.getId()) { //CONDICION SI EL USUARIO ES ADMINISTRADOR Y ES DIFERENTE DEL USUARIO BORRADO
            //BORRADO DE USUARIO
            service.deleteUser(id);
            return "redirect:/home/userpage/" + sessionUser.getId();
        } else {
            //BORRADOD DE USUARIO
            service.deleteUser(id);
            return "redirect:/";
        }
    }

    //ACTUALIZACION DE USUARIOS
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //AÑADIDO DE DATOS AL MODELO
        User user = service.readUserId(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        //COMPROBACION DE SESION
        boolean sessionN = SU.checkSession(getSession());
        if (!sessionN) {
            return "redirect:/";
        }
        //ACTUALIZACION DE DATOS DEL USUARIO EXISTENTE CON EL USUARIO ENVIADO
        User existingUser = service.readUserId(user.getId());
        existingUser.setUsername(user.getUsername());
        if(user.getPassword() != ""){
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());
        existingUser.setGender(user.getGender());
        existingUser.setBirthdate(user.getBirthdate());
        //ACTUALIZACION DEL USUARIO
        service.createUser(existingUser);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        return "redirect:/home/userpage/" + sessionUser.getId();

    }

    @PostMapping("/interestUpdate")
    public String updateInterest(@RequestParam(name = "interests", required = false) List<Long> interests, Model model) {
        //DATOS DEL USUARIO
        UserData data = getSessionUser().getUserData();
        //VACIADO DE LOS INTERESES DEL USUARIO
        data.getInterests().clear();
        //ASIGNACION DE NUEVOS INTERESES
        for(Long id : interests){
            Interest interest = intService.findById(Long.valueOf(id));
            if (interest != null) { //CONDICION SI EL INTERES EXISTE
                data.getInterests().add(interest);
            }
        }
        userDataService.saveUserPreferences(data);
        return "redirect:/home/userpage/" + getSessionUser().getId();
    }
    

    //CHECKEO DE SESION DE USUARIO
    @PostMapping("/checkuser")
    public String checkUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        User userC = service.readUserName(user.getUsername());
        if (bindingResult.hasErrors()) { //CONDICION SI LOS DATOS SON ERRONEOS
            return "redirect:/try_session";
        }
        if (userC != null && passwordEncoder.matches(user.getPassword(), userC.getPassword())) { //CONDICION SI EL USUARIO NO ES NULO Y COINCIDE LA CONTRASEÑA INTRODUCIDA CON LA DEL USUARIO DE LA BBDD
            //CREACION DE LA SESION
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setMaxInactiveInterval(-1);
            session.setAttribute("user", userC);
            return "redirect:/home/userpage/" + userC.getId();

        } else {
            model.addAttribute("error", "Los datos son incorrectos");
            return "try_session";
        }
    }
}
