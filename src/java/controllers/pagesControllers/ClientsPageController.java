/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.facility.Facility;
import entitys.main.support.Log;
import enums.Role;
import facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "clientsPageController")
@SessionScoped
public class ClientsPageController implements Serializable {
    @EJB
    private UserFacade uf;
    private User selected;
    private String newMessage;
    private String icon;
    private boolean admin;
        
    List<User> users=new ArrayList<>();
    
    public ClientsPageController() {
    }
    
    @PostConstruct
    public void init(){
        users=new ArrayList<>();
        try{
        users=uf.findAll();
        }catch(NullPointerException ex){}
        if(users.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Проблема с доступом в базу."));
        }        
        
    }

    public User getSelected() {
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public void updateUser(User user){
        uf.edit(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Данные сохранены"));
        hc().getCurrent().addLog(new Log("Client "+user.getMain_email()+"is succesfull updated"));
    }
    
    public String getCountCameras(User user){
        String count="";
        int i=0;
        if(!user.getFacilitys().isEmpty()){
            for (Facility facility : user.getFacilitys()) {
                i+=facility.getCameras().size();
            }
        }
        if(i>0)return (count=Integer.toString(i));
        else return "нет";
    }
    
    public String getCountCounters(User user){
        String count="";
        int i=0;
        if(!user.getFacilitys().isEmpty()){
            for (Facility facility : user.getFacilitys()) {
                i+=facility.getCounters().size();
            }
        }
        if(i>0)return (count=Integer.toString(i));
        else return "нет";
    }
    
    public String getCountSensors(User user){
        String count="";
        int i=0;
        if(!user.getFacilitys().isEmpty()){
            for (Facility facility : user.getFacilitys()) {
                i+=facility.getSensors().size();
            }
        }
        if(i>0)return (count=Integer.toString(i));
        else return "нет";
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }
    
    public String getMessage(){
        String mess=newMessage;
        newMessage="";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Сообщение отправлено"));
        return mess;
    }

    public void sendMessage(User user){
        user.addMessage(newMessage);
        newMessage="";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Сообщение отправлено"));
    }
    
    public List<String> getRoles(){
        List<String> roles=new ArrayList<>();
        for (Role string : Role.values()) {
            roles.add(string.getName());
        }
        return roles;
    }

    public String getIcon(User user) {
        if(user.getRole()==Role.ADMIN){
            return "admin_icon.png";
        }
        return "user_icon.png";
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    private HeaderController hc(){
        ELContext context=FacesContext.getCurrentInstance().getELContext();
        HeaderController hc=(HeaderController)context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }
}
