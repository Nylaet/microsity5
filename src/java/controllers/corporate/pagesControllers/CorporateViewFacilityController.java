/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.corporate.pagesControllers;

import entitys.main.User;
import entitys.main.facility.Camera;
import entitys.main.facility.Facility;
import facades.UserFacade;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "corporateViewFacilityController")
@SessionScoped
public class CorporateViewFacilityController implements Serializable {

    private User current;
    private Facility facility;
    private Camera addedCamera;
    private double randomTempSausage;
    private boolean mainDoor;
    private boolean backDoor;
    private int mainWatt;
    private int rentWatt;
    private int visitors;
    private int visitorsTotal;
    private double temperature;
    private int workers;

    @EJB
    UserFacade uf;

    @PostConstruct
    public void init() {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        current = (User) hs.getAttribute("current");
        facility = (Facility) hs.getAttribute("corpViewFacilityId");

        while (true) {
            double a = Math.random() * 100;
            double b = Math.floor(a);
            a = b / 10;
            if (a < 10) {
                randomTempSausage = a;
                break;
            }
        }
        mainWatt = 0;
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.DATE);
        for (int i = 1; i != m; i++) {
            double a;
            while (true) {
                a = Math.random() * 100;
                if (a > 60 && a < 100) {
                    break;
                }
            }
            mainWatt += (int) Math.floor(a);
        }

        rentWatt = (int) Math.floor(mainWatt * 0.3);

        while (true) {
            double a = Math.random() * 100;
            if (a > 21 && a < 69) {
                visitorsTotal = (int) Math.floor(a);
                break;
            }
        }
        
        while (true) {
            double a = Math.random() * 100;
            if (a > 18 && a < 35) {
                temperature = (int) Math.floor(a);
                break;
            }
        }
    }

    public CorporateViewFacilityController() {
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Camera getAddedCamera() {
        return addedCamera;
    }

    public void setAddedCamera(Camera addedCamera) {
        this.addedCamera = addedCamera;
    }

    public String getRandomTempSausage() {
        if (Math.random() > 0.5) {
            randomTempSausage += 0.3;
        } else {
            randomTempSausage -= 0.3;
        }
        return String.valueOf(Math.floor(randomTempSausage*10)/10);
    }

    public void setRandomTempSausage(double randomTempSausage) {
        this.randomTempSausage = randomTempSausage;
    }

    public boolean isMainDoor() {
        return (Math.random() < 0.7);
    }

    public void setMainDoor(boolean mainDoor) {
        this.mainDoor = mainDoor;
    }

    public boolean isBackDoor() {

        return (Math.random() < 0.3);
    }

    public void setBackDoor(boolean backDoor) {
        this.backDoor = backDoor;
    }

    public int getMainWatt() {
        return mainWatt;
    }

    public void setMainWatt(int mainWatt) {
        this.mainWatt = mainWatt;
    }

    public int getRentWatt() {
        return rentWatt;
    }

    public void setRentWatt(int rentWatt) {
        this.rentWatt = rentWatt;
    }

    public int getVisitors() {
        if (Math.random() > 0.5) {
            visitors++;
            visitorsTotal++;
        } else if (visitors!=0) {
            visitors--;
        }

        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public int getVisitorsTotal() {
        return visitorsTotal;
    }

    public void setVisitorsTotal(int visitorsTotal) {
        this.visitorsTotal = visitorsTotal;
    }

    public double getTemperature() {
        if(Math.random()>0.5)temperature+=0.1;
        else temperature-=0.1;        
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getWorkers() {
        double a=Math.random()*10;
        if(a<3)return 2;
        else if(a<5) return 5;
        return 7;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public void saveCamera() {
        if (!addedCamera.getName().isEmpty() && !addedCamera.getIp().isEmpty()) {
            int i = current.getFacilitys().indexOf(facility);
            current.getFacilitys().get(i).getCameras().add(addedCamera);
            uf.edit(current);
        }
    }

}
