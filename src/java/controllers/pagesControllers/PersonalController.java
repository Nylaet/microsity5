/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "personalController")
@SessionScoped
public class PersonalController implements Serializable {

    private User current;
    
    @PostConstruct
    public void init() {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        current = (User) hs.getAttribute("current");
    }

    public String getLastVisit() {
        String lVisit = "Впервые";
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM YYYY");
        if (current.getLastVisit() != null) {
            lVisit = df.format(current.getLastVisit());
        }
        return lVisit;
    }

    public String getEndSubscription() {
        String eS = "Не известно";
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM YYYY");
        try {
            if (current.getSubscription().getEndSubscription() != null) {
                eS = df.format(current.getSubscription().getEndSubscription());
            }
        } catch (NullPointerException ex) {
        }
        return eS;
    }

    public String ViewFacility(String id) {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        hs.setAttribute("viewFacility", id);
        return "viewFacility.xhtml?faces-redirect=true";
    }

    public String EditFacility(String id) {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        hs.setAttribute("editFacility", id);
        return "editFacility.xhtml?faces-redirect=true";
    }

    public User getCurrent() {
        if(current==null)init();
        return current;
    }
    
    public void setCurrent(User current){
        this.current=current;
    }

    private HeaderController hc() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        HeaderController hc = (HeaderController) context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }

}
