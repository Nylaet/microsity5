/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import facades.UserFacade;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "profileController")
@SessionScoped
public class ProfileController implements Serializable {

    private User current;
    private String newEmail;
    private String newPhone;

    @EJB
    UserFacade uf;

    @PostConstruct
    public void init() {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        current = (User) hs.getAttribute("current");
    }

    public ProfileController() {
    }

    public void addEmail() {
        if (newEmail != null) {
            current.addEmail(newEmail);
        }

    }

    public void addPhone() {
        if (newPhone != null) {
            current.addPhone(newPhone);
        }

    }

    public User getCurrent() {
        if (current == null) {
            init();
        }
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public void editUser() {
        uf.edit(current);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Данные успешно сохранены"));
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    //Наверное не понадобится
    private HeaderController hc() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        HeaderController hc = (HeaderController) context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }
}
