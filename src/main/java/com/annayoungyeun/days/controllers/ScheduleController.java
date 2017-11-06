package com.annayoungyeun.days.controllers;

import com.annayoungyeun.days.models.Bundle;
import com.annayoungyeun.days.models.Entry;
import com.annayoungyeun.days.models.User;
import com.annayoungyeun.days.models.data.BundleDao;
import com.annayoungyeun.days.models.data.EntryDao;
import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ScheduleController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EntryDao entryDao;

    @Autowired
    private BundleDao bundleDao;


//    @Scheduled(fixedDelay = 30000)
    @Scheduled(cron = "0 59 23 * * ?", zone = "CST")
    public void addBlank(){
        //List of Users
        List<User> users = (List<User>) userDao.findAll();

        //find today's date and format it
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = now.format(formatter);

        //loop through all users
        for(User user : users){
//            find if there are entries for today's date
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
            //if entries reaches 100, bundle into one string, store in DB.
            if(allEntries.size() == 60){
                Bundle freshBundle = new Bundle();
                String BundleString = "";
                for(Entry entry : allEntries){
                    BundleString += entry.getDate() + " " + entry.getEntryText() + "<br />\n";
                    entryDao.delete(entry);
                }
                freshBundle.setUser(user);
                freshBundle.setBundleText(BundleString);
                bundleDao.save(freshBundle);
            }

        }

    }

    @Scheduled(cron = "0 0 19 * * ?", zone = "CST")
    public void notifications() throws MessagingException {
        //List of Users
        List<User> users = (List<User>) userDao.findAll();

        //set up email
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //find today's date and format it
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = now.format(formatter);

        //loop through all users
        for(User user : users){
            //find if there is no todayEntry && if user notifications are On
            List<Entry> todayEntry = entryDao.findByDateAndUserId(date, user.getId());
            Boolean notifsOn = user.getPrefs().contains("On");
            //send e-mail if both conditions are true
            if(todayEntry.size() <1 && notifsOn){
                //send notification
                helper.setTo(user.getEmail());
                helper.setText("Greetings, " + user.getUsername() + "! Remember to add an entry to your Days Journal by 11:59 pm. " +
                        "\nIf you can't get around to it or don't feel like it, no worries! A blank line is automatically " +
                        "added to your entries to mark another day passed. " +
                        "\n\n\nE-mail notifications are sent at 7pm on days you have not yet posted.  " +
                        "To stop these notifications, visit your user settings page.");
                helper.setSubject("Days: Remember to post");

                //send message
                sender.send(message);
            }

        }

    }

}
