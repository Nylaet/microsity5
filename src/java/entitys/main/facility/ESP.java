/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.facility;

import enums.DataType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Panker
 */
@Entity
public class ESP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String microsity_id;
    private String sensorId;
    
    private List<Pin> pins;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicrosity_id() {
        return microsity_id;
    }

    public void setMicrosity_id(String microsity_id) {
        this.microsity_id = microsity_id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public void addData(SensorsData sensorsData) {
        boolean find=false;
        for (Pin pin : pins) {
            if(sensorsData.getPinNum()==pin.number){
                find=true;
                pin.addValue(sensorsData.getDt(),Double.parseDouble(sensorsData.getValue()));
            }
        }        
    }

    private class Pin{
        int number;
        DataType dataType;
        
        List<Value> values;
        
        public void addValue(Date date, double value){
            values.add(new Value(date, value));
        }
        
        private class Value{
            Date date;
            double value;

            public Value(Date date, double value) {
                this.date = date;
                this.value = value;
            }
            
            
        }
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
        if (!(object instanceof ESP)) {
            return false;
        }
        ESP other = (ESP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.main.facility.ESP[ id=" + id + " ]";
    }
    
}
