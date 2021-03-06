/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.facility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.primefaces.model.map.LatLng;

/**
 *
 * @author Panker
 */
@Entity
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String about;
    private String country;
    private String sity;
    private String street;
    private String home;
    private String appartament;
    private String icon;
        
    private LatLng coord;
    
    private boolean showInTheMap;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Camera> cameras;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Counter> counters;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Sensor> sensors;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ESP> esps;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RSPB> rspbs;

    public String getCoord(){
        String text="";
        text=(String.valueOf(coord.getLat()))+","+(String.valueOf(coord.getLng()));
        return text;
    }
    
    public LatLng getLatLng(){
        return coord;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAppartament() {
        return appartament;
    }

    public void setAppartament(String appartament) {
        this.appartament = appartament;
    }

    

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public boolean isShowInTheMap() {
        return showInTheMap;
    }

    public void setShowInTheMap(boolean showInTheMap) {
        this.showInTheMap = showInTheMap;
    }

    public List<Camera> getCameras() {
        if(cameras==null)cameras=new ArrayList<>();
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }

    public List<Counter> getCounters() {
        if(counters==null)counters=new ArrayList<>();
        return counters;
    }

    public void setCounters(List<Counter> counters) {
        this.counters = counters;
    }

    public List<Sensor> getSensors() {
        if(sensors==null)sensors=new ArrayList<>();
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<ESP> getEsps() {
        if(esps==null)esps=new ArrayList<>();
        return esps;
    }

    public void setEsps(List<ESP> esps) {
        this.esps = esps;
    }

    public List<RSPB> getRspbs() {
        if(rspbs==null)rspbs=new ArrayList<>();
        return rspbs;
    }

    public void setRspbs(List<RSPB> rspbs) {
        this.rspbs = rspbs;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
              
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facility)) {
            return false;
        }
        Facility other = (Facility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }
    
}
