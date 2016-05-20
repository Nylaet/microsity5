/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.facility.Facility;
import entitys.main.facility.Sensor;
import entitys.main.support.Log;
import facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "sensorWizardController")
@SessionScoped
public class SensorWizardController implements Serializable {

    private Facility facility = new Facility();
    private Sensor created = new Sensor();
    private User current;

    @EJB
    private UserFacade uf;

    @PostConstruct
    public void init() {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        current = (User) hs.getAttribute("current");
    }

    public SensorWizardController() {
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {

        this.facility = facility;

    }

    public Sensor getCreated() {
        return created;
    }

    public void setCreated(Sensor created) {
        this.created = created;
    }

    public String create() {
        for (Facility facility1 : current.getFacilitys()) {
            if (facility1.getId().equals(facility.getId())) {
                facility1.getSensors().add(created);
                current.addLog(new Log("new sensor added"));
                current.addMessage("Добавлен новый датчик " + created.getName());
                uf.edit(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Датчик добавлен"));
            }
        }
        created = new Sensor();
        facility = new Facility();
        return "sensors.xhtml?faces-redirect=true";
    }

}
