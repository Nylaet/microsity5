/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.support.Log;
import entitys.main.support.Message;
import enums.Role;
import facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import staticClasses.Mail;

/**
 *
 * @author Panker
 */
@Named(value = "headerController")
@SessionScoped
public class HeaderController implements Serializable {

    private boolean entered = false;
    private boolean admin = false;
    private boolean remMe = false;
    private String enterLogMail = "";
    private String enterPass = "";
    private User current = new User();
    private User createdUser = new User();
    private boolean haveNewMessage = false;
    private String password2;

    @EJB
    UserFacade uf;

    public HeaderController() {
    }

    @PostConstruct
    public void init() {
        Cookie[] cs = ((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest())).getCookies();
        if (cs != null && cs.length > 0) {
            for (Cookie c : cs) {
                if (c.getName().equals("MicroSityLastSession")) {
                    List<User> users = uf.findAll();
                    for (User user : users) {
                        if (user.isRememberMe() || user.getLastSession().equals(c.getValue())) {
                            current = user;
                            entered = true;
                            user.setLastVisit(new Date());
                            user.addLog(new Log("User entered"));

                            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                            session.setAttribute("current", current);

                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Добро пожаловать, " + current.getMain_email()));

                            if (remMe || user.isRememberMe()) {
                                String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
                                Cookie lastSession = new Cookie("MicroSityLastSession", sessionId);
                                user.setLastSession(sessionId);
                                lastSession.setMaxAge(155520000);
                                ((HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse())).addCookie(lastSession);
                            }

                            uf.edit(user);
                        }
                    }
                }
            }
        }
    }

    public String login() {
        List<User> users = uf.findAll();
        if (users.isEmpty()) {
            User newUser = new User();
            newUser.setPassword("156456851");
            newUser.setMain_email("panker@mksat.net");
            newUser.addPhone("+380664119956");
            newUser.setRole(Role.ADMIN);
            uf.create(newUser);
            users.add(newUser);
        }
        for (User user : users) {
            if (user.getMain_email().equalsIgnoreCase(enterLogMail)) {
                if (user.getPassword().equals(enterPass)) {
                    current = user;
                    entered = true;
                    if (current.getRole().equals(Role.ADMIN)) {
                        admin = true;
                    }
                    user.setLastVisit(new Date());
                    user.addLog(new Log("User entered"));

                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setAttribute("current", current);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Добро пожаловать, " + current.getMain_email()));

                    if (remMe || user.isRememberMe()) {
                        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
                        Cookie lastSession = new Cookie("MicroSityLastSession", sessionId);
                        user.setLastSession(sessionId);
                        lastSession.setMaxAge(155520000);
                        ((HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse())).addCookie(lastSession);
                    }

                    uf.edit(user);
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("@all");
                    if(user.getRole().equals(Role.CORP_USER))return "corporate/index.xhtml?faces-redirect=true";
                    return "index.xhtml?faces-redirect=true";
                }
            }
        }
        return "loginError";
    }

    public String logout() {
        current.addLog(new Log("User logout"));
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        Cookie lastSession = new Cookie("MicroSityLastSession", sessionId);
        lastSession.setMaxAge(0);
        ((HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse())).addCookie(lastSession);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        entered = false;
        admin=false;
        return ("index");
    }

    //getters/setters
    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public String getEnterLogMail() {
        return enterLogMail;
    }

    public void setEnterLogMail(String enterLogMail) {
        this.enterLogMail = enterLogMail;
    }

    public String getEnterPass() {
        return enterPass;
    }

    public void setEnterPass(String enterPass) {
        this.enterPass = enterPass;
    }

    public boolean isRemMe() {
        return remMe;
    }

    public void setRemMe(boolean remMe) {
        this.remMe = remMe;
    }

    public boolean isHaveNewMessage() {
        User temp = uf.find(current.getId());
        List<Message> messages = temp.getMessages();
        for (Message message : messages) {
            if (!message.isReadInSite()) {
                return true;
            }
        }

        return false;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }
    
    public String createNewUser(){
        if(createdUser.getMain_email().contains("@")){
            List <User> users=uf.findAll();
            boolean existing=false;
            for (User user : users) {
                if(user.getMain_email().equals(createdUser.getMain_email())){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Такой логин/Эл. адрес уже зарегистрирован"));
                    existing=true;
                }
            }
            if(!existing){
                if(createdUser.getPassword().trim().length()<8&&createdUser.getPassword().equals(password2)){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароль должен быть не менее 8 символов"));
                }else{
                    createdUser.setRole(Role.USER);
                    createdUser.addLog(new Log("created user "+createdUser.getMain_email()));
                    createdUser.addMessage(new Message("Регистрация", "Добро пожаловать! Вы успешно зарегистрированны в системе MicroSity"));
                    createdUser.addMessage(new Message("Регистрация", "Перейдите, пожалуйста, в Ваш профиль, и заполните недостающие данные"));
                    uf.create(createdUser);
                    enterLogMail=createdUser.getMain_email();
                    enterPass=createdUser.getPassword();
                    createdUser=new User();
                    new Mail().confirmMail(enterLogMail,enterPass);
                    return(login());
                }   
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не верный формат логина/Эл. адрес"));
        }
        return "";
    }
    
    public void sendTest(){
        new Mail().test();
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    
}
