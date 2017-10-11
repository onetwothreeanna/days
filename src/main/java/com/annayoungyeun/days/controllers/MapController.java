package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Bundle;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.LocationDao;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("days")
public class MapController {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="/map", method = RequestMethod.GET)
    public String viewArchive(Model model, HttpServletRequest request) {
        User user = userDao.findByUsername(request.getSession().getAttribute("currentUser").toString());

        return "map/map";

    }

}
