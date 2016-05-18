/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.facility.ESP;
import entitys.main.facility.Facility;
import entitys.main.facility.RSPB;
import facades.UserFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Panker
 */
@Named(value = "facilityWizardPageController")
@SessionScoped
public class FacilityWizardPageController implements Serializable {

    @EJB
    UserFacade uf;

    Facility created = new Facility();

    private String lat, lng;
    private MapModel mp = new DefaultMapModel();

    
    public FacilityWizardPageController() {
    }

    public User getCurrent() {
        return hc().getCurrent();
    }

    public Facility getCreated() {
        return created;
    }

    public void setCreated(Facility created) {
        this.created = created;
    }

    public String createFacility() {
        hc().getCurrent().addFacility(created);
        uf.edit(hc().getCurrent());
        created = new Facility();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Объект успешно добавлен"));
        return "index.xhtml?faces-redirect=true";
    }

    public String getLatLng() {
        String a = "г." + created.getSity() + "," + "ул." + created.getStreet()
                + " " + "д." + created.getHome() + (created.getAppartament().length() == 0 ? "" : "кв." + created.getAppartament());

        try {
            String urlS = "http://maps.google.com/maps/api/geocode/json?" + "sensor=false&address=" + URLEncoder.encode(a, "UTF-8");
            URL url = new URL(urlS);
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();

            JSONObject obj = new JSONObject(str);
            if (!obj.getString("status").equals("OK")) {
                lat = "59.5611525";
                lng = "150.8301413";
            } else {
                JSONObject res = obj.getJSONArray("results").getJSONObject(0);
                System.out.println(res.getString("formatted_address"));
                JSONObject loc
                        = res.getJSONObject("geometry").getJSONObject("location");
                lat = String.valueOf(loc.getDouble("lat"));
                lng = String.valueOf(loc.getDouble("lng"));
            }

        } catch (UnsupportedEncodingException | MalformedURLException ex) {
            lat = "59.5611525";
            lng = "150.8301413";
        } catch (IOException ex) {
            lat = "59.5611525";
            lng = "150.8301413";
        }

        mp.addOverlay(new Marker(new LatLng(Double.valueOf(lat), Double.valueOf(lng)), created.getTitle()));
        return (lat + "," + lng);
    }

    public void addRSPB() {
        created.getRspbs().add(new RSPB());
    }

    public void addESP() {
        created.getEsps().add(new ESP());
    }

    public MapModel getModel() {
        return mp;
    }
    
    private HeaderController hc(){
        ELContext context=FacesContext.getCurrentInstance().getELContext();
        HeaderController hc=(HeaderController)context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }
}
