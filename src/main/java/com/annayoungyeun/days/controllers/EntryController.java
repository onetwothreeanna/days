package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Entry;
//import com.annayoungyeun.days.models.data.ArchiveDao;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.EntryDao;
//import com.annayoungyeun.days.models.data.UserDao;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("days")
public class EntryController {

    @Autowired
    private EntryDao entryDao;

    @Autowired
    private UserDao userDao;

    //main page - add an item, view this year's journal
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addEntry(Model model, HttpServletRequest request){
        //current user
        int currentUserId = userDao.findByUsername(
        request.getSession().getAttribute("currentUser").toString()).getId();

        //find today's date and format it
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = now.format(formatter);

        //find if there are entries for today's date
        List<Entry> todayEntry = entryDao.findByDateAndUserId(date, currentUserId);

        //pass to view
        model.addAttribute("todayEntry", todayEntry);
        model.addAttribute("title", "days");
        model.addAttribute("entries", entryDao.findByUserIdOrderByIdDesc(currentUserId));
        model.addAttribute(new Entry());
        return "entry/index";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addEntry(@ModelAttribute @Valid Entry newEntry, Errors errors, Model model, HttpServletRequest request)
            throws ServletException, IOException{
        User user = userDao.findByUsername(request.getSession().getAttribute("currentUser").toString());
        if(errors.hasErrors()){
            model.addAttribute("title", "days");
            model.addAttribute("entries", entryDao.findByUserId(user.getId()));
            model.addAttribute(newEntry);
            return "entry/index";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = now.format(formatter);
        newEntry.setDate(date);
        newEntry.setUser(user);
        entryDao.save(newEntry);
        return "redirect:/days";
    }



}
