/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.facility.Facility;
import facades.UserFacade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
@Named(value = "facilityViewController")
@RequestScoped
public class FacilityViewController {

    private Facility currentFacility = new Facility();
    private String lat, lng;
    private User current;

    @PostConstruct
    public void init() {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        current = (User) hs.getAttribute("current");
        String needId = (String) hs.getAttribute("viewFacility");
        for (Facility facility : current.getFacilitys()) {
            if (facility.getId().toString().equals(needId)) {
                currentFacility = facility;
                hs.removeAttribute("viewFacility");
            }
        }
    }

    public FacilityViewController() {
    }

    public String getAddress() {
        String a = "г." + currentFacility.getSity() + "," + "ул." + currentFacility.getStreet()
                + " " + "д." + currentFacility.getHome() + (currentFacility.getAppartament().length() == 0 ? "" : "кв." + currentFacility.getAppartament());
        return a;
    }

    public String getGMapsAdress() {
        String address = "";
        address = currentFacility.getCountry() + "," + currentFacility.getSity() + currentFacility.getStreet() + "," + currentFacility.getHome();
        try {
            String urlS = "http://maps.google.com/maps/api/geocode/json?" + "sensor=false&address=" + URLEncoder.encode(address, "UTF-8");
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
        return (lat + "," + lng);
    }

    

    public MapModel getModel() {
        MapModel mp = new DefaultMapModel();
        mp.addOverlay(new Marker(new LatLng(Double.valueOf(lat), Double.valueOf(lng))));
        return mp;
    }
    public static MapModel getModel(Facility facility) {
        FacilityViewController fvc=new FacilityViewController();
        fvc.currentFacility=facility;
        MapModel mp = new DefaultMapModel();
        mp.addOverlay(new Marker(facility.getLatLng()));
        return mp;
    }

    public Facility getCurrentFacility() {
        return currentFacility;
    }

    private HeaderController hc() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        HeaderController hc = (HeaderController) context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }

}
