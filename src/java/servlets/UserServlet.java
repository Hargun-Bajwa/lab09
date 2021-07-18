
package servlets;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.UserService;


public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();

        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);         
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            try {
                String email = (String) request.getParameter("email");
                User user = us.get(email);
                request.setAttribute("selectedUser", user);
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (action != null && action.equals("delete")) {
            try {
                 String email = (String) request.getParameter("email");
                us.delete(email);
                List<User> users = us.getAll();
                request.setAttribute("users", users);
                
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            return;
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

    }
    
 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        UserService us = new UserService();
        
        String email = request.getParameter("email");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String password = request.getParameter("password");
        boolean active =Boolean.parseBoolean(request.getParameter("active") );
        int role = Integer.parseInt(request.getParameter("role"));
        
        try {
            switch (action) {
                case "add":
                    if(email==null|| firstName==null || lastName==null|| password==null ||email.equals("")|| firstName.equals("") || lastName.equals("")|| password.equals("")){
                    request.setAttribute("errorMssg",true);}
                    else{
                    us.insert(email, active, firstName, lastName, password, role);
                    break;}
                    
                case "update":
                    if(email==null|| firstName==null || lastName==null|| password==null ||email.equals("")|| firstName.equals("") || lastName.equals("")|| password.equals("")){
                    request.setAttribute("errorMssg",true);}
                    else{
                    us.update(email, active, firstName, lastName, password, role);
                    break;   }   

            }          
        }
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       if (action != null && action.equals("delete")) {
            try {
                 email = (String) request.getParameter("email");
                us.delete(email);
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

    }

    

}
