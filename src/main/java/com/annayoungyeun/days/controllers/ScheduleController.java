package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Entry;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.EntryDao;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ScheduleController {

    @Autowired
    UserDao userDao;

    @Autowired
    EntryDao entryDao;

    @Scheduled(fixedDelay = 30000)
    public void addBlank(){
        //List of Users
        List<User> users = (List<User>) userDao.findAll();

        //find today's date and format it
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = now.format(formatter);

        //loop through all users
        for(User user : users){
            //find if there are entries for today's date
            List<Entry> todayEntry = entryDao.findByDateAndUserId(date, user.getId());

            //if there is no entry for the day, save a blank string
            if(todayEntry.size() < 1){
                Entry blankEntry = new Entry();
                blankEntry.setDate(date);
                blankEntry.setUser(user);
                blankEntry.setEntryText("   ");
                entryDao.save(blankEntry);
            }
        }

    }
}
