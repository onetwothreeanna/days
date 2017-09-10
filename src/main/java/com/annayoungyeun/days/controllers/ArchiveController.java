package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Bundle;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.BundleDao;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("days")
public class ArchiveController {

    @Autowired
    BundleDao bundleDao;

    @Autowired
    UserDao userDao;


    // view handler
    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public String viewArchive(Model model, HttpServletRequest request) {
        User user = userDao.findByUsername(request.getSession().getAttribute("currentUser").toString());

        List<Bundle> bundles = bundleDao.findByUserIdOrderByIdDesc(user.getId());
        model.addAttribute("bundles", bundles);

        return "archive/archive";
    }
}
