/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.facility;


import entitys.main.support.Event;
import enums.SensorType;
import facades.SensorsDataFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import staticClasses.Socket3Connection;

/**
 *
 * @author Panker
 */
@Entity
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @EJB
    private SensorsDataFacade sdf;
    
    private String name;
    @Column(name="j_value")
    private String value;
    private boolean showInTheMap;
    private SensorType type;
    private String address;
    private int port;
    
    private List<String> alarmMails;
    private List<String> alarmPhones;
    
    private String iconName;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events;

    public Long getId() {
        return id;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }
        
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if(type==SensorType.SOCKET3){
            SensorsData newValue=new SensorsData();
            newValue.setDt(new Date());
            newValue.setIsAction(false);
            newValue.setPinNum(Short.valueOf("1"));
            newValue.setSensorId("Socket3"+address+":"+port);
            newValue.setValue(Socket3Connection.getTemperature(address, port));
            newValue.setTiming(0);
            sdf.create(newValue);
        }
        this.value = value;
    }

    public boolean isShowInTheMap() {
        return showInTheMap;
    }

    public void setShowInTheMap(boolean showInTheMap) {
        this.showInTheMap = showInTheMap;
    }

    public List<String> getAlarmMails() {
        return alarmMails;
    }

    public void setAlarmMails(List<String> alarmMails) {
        this.alarmMails = alarmMails;
    }

    public List<String> getAlarmPhones() {
        return alarmPhones;
    }

    public void setAlarmPhones(List<String> alarmPhones) {
        this.alarmPhones = alarmPhones;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
        if (!(object instanceof Sensor)) {
            return false;
        }
        Sensor other = (Sensor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.facility.sensors.Sensor[ id=" + id + " ]";
    }
    
}
