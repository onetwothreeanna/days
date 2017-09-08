package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Bundle;
import com.annayoungyeun.days.models.Entry;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.ArchiveDao;
import com.annayoungyeun.days.models.data.BundleDao;
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

    @Autowired
    BundleDao bundleDao;

    @Autowired
    ArchiveDao archiveDao;

    @Scheduled(fixedDelay = 30000)
//    @Scheduled(cron = "59 23 * * * ?", zone = "CST")  //runs at 11:59pm each day
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

            //find all entries for user by ID desc
            List<Entry> allEntries = entryDao.findByUserIdOrderByIdDesc(user.getId());
            if(allEntries.size() > 10){ //checks this, but doesn't proceed.  Why?
                Bundle freshBundle = new Bundle();
                String BundleString = "";
                for(Entry entry : allEntries){
                    BundleString += entry.getDate() + "  " + entry.getEntryText() + " \n";
                }
                freshBundle.setUser(user);
                freshBundle.setArchive(user.getArchive());
                freshBundle.setBundleText(BundleString);
                bundleDao.save(freshBundle);
            }
        }

    }
}
