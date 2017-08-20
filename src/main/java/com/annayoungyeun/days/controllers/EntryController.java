package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Entry;
//import com.annayoungyeun.days.models.data.ChronicleDao;
import com.annayoungyeun.days.models.data.EntryDao;
//import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("days")
public class EntryController {

    @Autowired
    private EntryDao entryDao;

//    @Autowired
//    private ChronicleDao chronicleDao;
//
//    @Autowired
//    private UserDao userDao;

    //main page - add an item, view this year's journal
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addEntry(Model model){
        model.addAttribute("title", "days");
        model.addAttribute("entries", entryDao.findAll());
        model.addAttribute(new Entry());
        return "entry/index";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addEntry(@ModelAttribute @Valid Entry newEntry, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "days");
            model.addAttribute("entries", entryDao.findAll());
            model.addAttribute(newEntry);
            return "entry/index";
        }
        entryDao.save(newEntry);
        return "redirect:/days";
    }

}
