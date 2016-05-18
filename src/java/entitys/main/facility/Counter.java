/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.facility;

import entitys.main.facility.support.CounterSensorHistory;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Panker
 */
@Entity
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private double valueNow;
    private double earlyValue;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date recordDate;
    private int dayRecord;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<CounterSensorHistory> counterHistorys;
    

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

    public double getValueNow() {
        return valueNow;
    }

    public void setValueNow(double valueNow) {
        this.valueNow = valueNow;
    }

    public double getEarlyValue() {
        return earlyValue;
    }

    public void setEarlyValue(double earlyValue) {
        this.earlyValue = earlyValue;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getDayRecord() {
        return dayRecord;
    }

    public void setDayRecord(int dayRecord) {
        this.dayRecord = dayRecord;
    }

    public List<CounterSensorHistory> getCounterHistorys() {
        return counterHistorys;
    }

    public void setCounterHistorys(List<CounterSensorHistory> counterHistorys) {
        this.counterHistorys = counterHistorys;
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
        if (!(object instanceof Counter)) {
            return false;
        }
        Counter other = (Counter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.facility.Counter[ id=" + id + " ]";
    }
    
}
