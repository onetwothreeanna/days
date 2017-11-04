 package com.annayoungyeun.days.filters;

import com.annayoungyeun.days.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

 /**
  * Created by AnnaYoungyeun on 7/17/17.
  */


 @Configuration
 @WebFilter(filterName = "AuthenticationFilter", value = {"/*"})
 public class AuthenticationFilter implements Filter{

     @Autowired
     UserDao userDao;

     private ServletContext context;
     @Override
     public void init(FilterConfig config) throws ServletException {
         this.context = config.getServletContext();
         this.context.log("-- Initializing authentication filter");
     }

     @Override
     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
             throws IOException, ServletException {

         HttpServletRequest req = (HttpServletRequest) request;
         HttpServletResponse res = (HttpServletResponse) response;
         String uri = req.getRequestURI();
         HttpSession session = req.getSession(false);
         Cookie[] cookies = req.getCookies();
         Cookie userCookie = null;

         //If there are cookies, find if a User cookie exists.
         if (cookies != null) {
             for (Cookie cookie : cookies) {
                 if (cookie.getName().equals("user")) {
                     userCookie = cookie;
                 }
             }
         }


         //If there is a User cookie, set session to true.  Else, false.  Set currentUser attribute in session
         if (userCookie != null){
             session = req.getSession(true);
             String userCookieName = userCookie.getValue();
             req.getSession().setAttribute("currentUser", userCookieName );
         } else {
             session = req.getSession(false);
         }

//         if there is no session, and they are not accessing the login page or the add page, stop them and redirect to login
         if(session == null && !uri.endsWith("login") && !uri.endsWith("user/add")){
             this.context.log(" -- Unauthorized request");
             res.sendRedirect("/user/login");
         } else {
             this.context.log("-- Onward.");
             chain.doFilter(req, res);
         }


     }

     @Override
     public void destroy(){
         this.context.log("Destroying Authentication filter");


     }




 }


