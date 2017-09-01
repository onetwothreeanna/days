package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("user")
@SpringBootApplication
public class UserController {
    @Autowired
    private UserDao userDao;

    //User SignUp
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add (Model model){
        model.addAttribute(new User());
        model.addAttribute("title", "days");
        return "user/add";
    }

    @RequestMapping(value = "add", method = POST)
    public String add(@ModelAttribute @Valid User user, Errors errors, Model model, String verify){
        User userAlreadyExists = userDao.findByUsername(user.getUsername());
        if (userAlreadyExists != null){
            model.addAttribute("title", "days register");
            model.addAttribute("usernameError", "Username not available.");
            model.addAttribute(user);
            return "user/add";
        }

        if(errors.hasErrors()){
            model.addAttribute("title", "days register");
            model.addAttribute(user);
            return "user/add";
        }

        model.addAttribute("user", user);
        boolean passwordsMatch = true;
        if (user.getPassword() == null || verify == null
            ||!user.getPassword().equals(verify)){
            passwordsMatch = false;
            model.addAttribute("verifyError", "Passwords must match.");
        }

        if (passwordsMatch) {
            userDao.save(user);
            return "user/index";
        }
        return "user/add";
    }
}
