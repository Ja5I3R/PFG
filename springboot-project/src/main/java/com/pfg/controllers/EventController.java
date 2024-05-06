package com.pfg.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

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
import com.pfg.models.Event;
import com.pfg.models.EventData;
import com.pfg.models.User;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class EventController {
    @Autowired
    private IEventService service;

    @Autowired
    private IEventDataService eventDService;

    @Autowired
    private IInterestService intService;

    @GetMapping("/events/new")
    public String gotoeventCreation(Model model) {
        Event EV = new Event();
        model.addAttribute("event", EV);
        model.addAttribute("interestList", intService.listAllInterest());
        return "create_event";
    }

    @PostMapping("/events/create")
    public String postMethodName(@ModelAttribute("event") Event event, BindingResult bindingResult, HttpServletRequest request) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User actualUser = (User) session.getAttribute("user");

        /*String initialDateString = request.getParameter("initialDate");
        LocalDate initialDate = LocalDate.parse(initialDateString, DateTimeFormatter.ISO_DATE);

        String endDateString = request.getParameter("endDate");
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ISO_DATE);
        
        event.setInitialDate(initialDate);
        event.setEndDate(endDate);*/
        event.setIdCreator(actualUser.getId());
        service.createEvent(event);
        
        EventData ED = new EventData();
        ED.setIdUser(actualUser.getId());
        ED.setIdEvent(event.getId());
        eventDService.saveEventData(ED);
        
        return "index";
    }
}
