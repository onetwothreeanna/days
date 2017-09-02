package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Login;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("user")
@SpringBootApplication
public class UserController {

    @Autowired
    private UserDao userDao;


    //---------------------------user sign up ------------------------------

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new User());
        model.addAttribute("title", "days");
        return "user/add";
    }

    @RequestMapping(value = "add", method = POST)
    public String add(@ModelAttribute @Valid User user, Errors errors, Model model, String verify) {

        User userAlreadyExists = userDao.findByUsername(user.getUsername());
        if(userAlreadyExists != null){
            model.addAttribute("title", "days Register");
            model.addAttribute("usernameError", "Username not available.");
            model.addAttribute(user);
            return "user/add";
        }

        if (errors.hasErrors()){
            model.addAttribute("title", "days Register");
            model.addAttribute(user);
            return "user/add";

        }

        model.addAttribute("user", user);
        boolean passwordsMatch = true;
        if (user.getPassword() == null || verify == null
                || !user.getPassword().equals(verify)) {
            passwordsMatch = false;
            model.addAttribute("verifyError", "Passwords must match");
        }

        if (passwordsMatch) {
            userDao.save(user);
            return "user/index";
        }
        return "user/add";

    }


    //---------------------------login handling ------------------------------

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute(new Login());
        model.addAttribute("title", "days Login");
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid Login loginAttempt, Errors errors, Model model,
                        HttpServletResponse response) {
        if (errors.hasErrors()){
            model.addAttribute("title", "days Login");
            model.addAttribute(loginAttempt);
            return "user/login";
        }

        model.addAttribute("login", loginAttempt);
        //look for username in database
        User user = userDao.findByUsername(loginAttempt.getUsername());

        //if there, get the user object.
        if (user != null) {
            if (userDao.findById(user.getId()) != null) { //exists doesn't work here for some reason??
                model.addAttribute("user", user);

                //check password
                boolean passwordsMatch = user.getPassword().equals(loginAttempt.getPassword());
                if (!passwordsMatch) {
                    model.addAttribute("verifyError", "Username or password is incorrect.");
                }

                if (passwordsMatch) {
                    Cookie userCookie = new Cookie("user", user.getUsername());
                    userCookie.setPath("/");
                    response.addCookie(userCookie);
                    return "/user/index";
                }
            }
        }

        //if not there, show login form again with error.
        model.addAttribute("title", "days Login");
        model.addAttribute("verifyError", "Username or password is incorrect.");
        return "user/login";

    }

    //---------------------------logout handling-------------------------------


    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(Model model) {
        model.addAttribute(new Login());
        model.addAttribute("title", "days Logout");
        return "user/logout";
    }
    @RequestMapping(value="logout", method = RequestMethod.POST)
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response)  {
        Cookie userCookie = null;
        Cookie [] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("user")){
                    userCookie = cookie;  //find cookie associated with this user
                }
            }
        }

        if(userCookie != null){
            request.getSession().invalidate();
            userCookie.setMaxAge(0);
            userCookie.setPath("/");
            response.addCookie(userCookie);
            model.addAttribute("logoutMessage", "You have been logged out.");
            return "user/logout";

        }
        model.addAttribute("title", "days Login");
        return "redirect:/user/login";
    }



}

