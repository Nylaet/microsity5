/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.facility;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Panker
 */
@Entity
public class Camera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String vlcLink;
    private String JPGLink;
    @Column(name="j_size")
    private String size;
    private boolean showInTheMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVlcLink() {
        return vlcLink;
    }

    public void setVlcLink(String vlcLink) {
        this.vlcLink = vlcLink;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isShowInTheMap() {
        return showInTheMap;
    }

    public void setShowInTheMap(boolean showInTheMap) {
        this.showInTheMap = showInTheMap;
    }

    public String getJPGLink() {
        return JPGLink;
    }

    public void setJPGLink(String JPGLink) {
        this.JPGLink = JPGLink;
    }
    
    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Camera)) {
            return false;
        }
        Camera other = (Camera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.facility.Camera[ id=" + id + " ]";
    }
    
}