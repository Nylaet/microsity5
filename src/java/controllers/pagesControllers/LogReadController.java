/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.support.Log;
import facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Panker
 */
@Named(value = "logReadController")
@SessionScoped
public class LogReadController implements Serializable {

    @EJB
    UserFacade uf;
        
    public LogReadController() {
    }
    
    public List<Logs> getLogs () {
        List<Logs> logs=new ArrayList<>();
            try{
                List<User> users=uf.findAll();
                for (User user : users) {
                    for (Log log : user.getLogs()) {
                        logs.add(new Logs (user.getMain_email(),log.toString()));
                    }
                }
            }catch (NullPointerException ex){}
        return logs;
    }
    
    public class Logs{
        private String userMail;
        private String log;
        
        public Logs (String user, String log){
            this.userMail=user;
            this.log=log;
        }
        
        public String getUserMail() {
            return userMail;
        }

        public void setUserMail(String user) {
            this.userMail = user;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }
}
