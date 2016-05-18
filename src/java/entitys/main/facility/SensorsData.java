/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.facility;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Panker
 */
@Entity
@Table(name = "SENSORS_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorsData.findAll", query = "SELECT s FROM SensorsData s"),
    @NamedQuery(name = "SensorsData.findById", query = "SELECT s FROM SensorsData s WHERE s.id = :id"),
    @NamedQuery(name = "SensorsData.findBySensorId", query = "SELECT s FROM SensorsData s WHERE s.sensorId = :sensorId"),
    @NamedQuery(name = "SensorsData.findByPinNum", query = "SELECT s FROM SensorsData s WHERE s.pinNum = :pinNum"),
    @NamedQuery(name = "SensorsData.findByValue", query = "SELECT s FROM SensorsData s WHERE s.value = :value"),
    @NamedQuery(name = "SensorsData.findByDt", query = "SELECT s FROM SensorsData s WHERE s.dt = :dt"),
    @NamedQuery(name = "SensorsData.findByWasRead", query = "SELECT s FROM SensorsData s WHERE s.wasRead = :wasRead"),
    @NamedQuery(name = "SensorsData.findByIsAction", query = "SELECT s FROM SensorsData s WHERE s.isAction = :isAction"),
    @NamedQuery(name = "SensorsData.findByTiming", query = "SELECT s FROM SensorsData s WHERE s.timing = :timing")})
public class SensorsData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SENSOR_ID")
    private String sensorId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PIN_NUM")
    private short pinNum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE")
    private String value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WAS_READ")
    private boolean wasRead;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ACTION")
    private boolean isAction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIMING")
    private int timing;

    public SensorsData() {
    }

    public SensorsData(Long id) {
        this.id = id;
    }

    public SensorsData(Long id, String sensorId, short pinNum, String value, Date dt, boolean wasRead, boolean isAction, int timing) {
        this.id = id;
        this.sensorId = sensorId;
        this.pinNum = pinNum;
        this.value = value;
        this.dt = dt;
        this.wasRead = wasRead;
        this.isAction = isAction;
        this.timing = timing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public short getPinNum() {
        return pinNum;
    }

    public void setPinNum(short pinNum) {
        this.pinNum = pinNum;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public boolean getWasRead() {
        return wasRead;
    }

    public void setWasRead(boolean wasRead) {
        this.wasRead = wasRead;
    }

    public boolean getIsAction() {
        return isAction;
    }

    public void setIsAction(boolean isAction) {
        this.isAction = isAction;
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
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
        if (!(object instanceof SensorsData)) {
            return false;
        }
        SensorsData other = (SensorsData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.main.facility.SensorsData[ id=" + id + " ]";
    }
    
}
