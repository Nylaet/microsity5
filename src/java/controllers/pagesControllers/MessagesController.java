/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.support.Message;
import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "messagesController")
@RequestScoped
public class MessagesController {

    List <Message> messages=new ArrayList<>();
    @EJB
    UserFacade uf;
    
    @PostConstruct
    public void init(){
        
        messages=hc().getCurrent().getMessages();
        
    }
    
    public MessagesController() {
    }
    
    public List<Message> getMessages() {
        List<Message> mess1=messages;
        List<Message> mess=new ArrayList<>();
        for(int i=(mess1.size()-1);i!=-1;){
            mess.add(mess1.get(i));
            i--;
        }
        setAsRead();
        return mess;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
        
    public String getDate(Date date){
        SimpleDateFormat df=new SimpleDateFormat("dd-MM-YYYY");
        return df.format(date);
    }
    
    private void setAsRead(){
        for (Message message : messages) {
            if(!message.isReadInSite())message.setReadInSite(true);
        }
        hc().getCurrent().setMessages(messages);
        uf.edit(hc().getCurrent());
        
    }
    
    private HeaderController hc(){
        ELContext context=FacesContext.getCurrentInstance().getELContext();
        HeaderController hc=(HeaderController)context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }
    
}
