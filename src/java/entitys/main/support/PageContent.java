/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Panker
 */
@Entity
public class PageContent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pageName;
    private String advertImage;
    private String advertName;
    private String advMainText;
    private String advSubtitleText;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getAdvertImage() {
        if (advertImage==null) {
            return "noImage.jpg";
        }
        return advertImage;
    }

    public void setAdvertImage(FileUploadEvent event) {
        String relative="/resources/images/content_picture/";
        ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute=context.getRealPath(relative);
        
        UploadedFile uploadedFile=(UploadedFile)event.getFile();
        Path path=Paths.get(absolute);
        InputStream is=null;
        try {
            is=uploadedFile.getInputstream();
        } catch (IOException ex) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File file=new File(path.toString()+"/"+uploadedFile.getFileName());
        
        try {
            FileUtils.copyInputStreamToFile(is, file);
            advertImage=file.getName();
        } catch (IOException ex) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvMainText() {
        return advMainText;
    }

    public void setAdvMainText(String advMainText) {
        this.advMainText = advMainText;
    }

    public String getAdvSubtitleText() {
        return advSubtitleText;
    }

    public void setAdvSubtitleText(String advSubtitleText) {
        this.advSubtitleText = advSubtitleText;
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
        if (!(object instanceof PageContent)) {
            return false;
        }
        PageContent other = (PageContent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.main.support.PageContent[ id=" + id + " ]";
    }
    
}
