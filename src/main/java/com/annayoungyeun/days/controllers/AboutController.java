package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("days")
public class AboutController {

    @Autowired
    UserDao userDao;

    // view handler
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String viewAbout(Model model,  HttpServletRequest request) {

        User user = userDao.findByUsername(request.getSession().getAttribute("currentUser").toString());
        String userPref = user.getPrefs();

        model.addAttribute("userPref", userPref);

        return "about/about";

    }
}



