package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Entry;
//import com.annayoungyeun.days.models.data.ArchiveDao;
import com.annayoungyeun.days.models.Location;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.EntryDao;
//import com.annayoungyeun.days.models.data.UserDao;
import com.annayoungyeun.days.models.data.LocationDao;
import com.annayoungyeun.days.models.data.UserDao;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
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
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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

    @Autowired
    private LocationDao locationDao;

    private DatabaseReader dbReader;

    public EntryController() throws IOException {
        File database = new File("/Users/AnnaYoungyeun/code/days/src/main/resources/GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();
    }

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
            throws ServletException, IOException, GeoIp2Exception{

        //error handling
        User user = userDao.findByUsername(request.getSession().getAttribute("currentUser").toString());
        if(errors.hasErrors()){
            model.addAttribute("title", "days");
            model.addAttribute("entries", entryDao.findByUserIdOrderByIdDesc(user.getId()));
            model.addAttribute(newEntry);
            return "entry/index";
        }


        //get user IP address
        String ip = //request.getRemoteAddr(); hardcode in various IP addresses to test.  Localhost will not show actual IP addresses
                "206.196.115.38";
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();

        Location location = new Location();
        location.setCity(cityName);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setUser(user);
        locationDao.save(location);


        //find current date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = now.format(formatter);
        newEntry.setDate(date);
        newEntry.setUser(user);
        entryDao.save(newEntry);
        return "redirect:/days";
    }



}
