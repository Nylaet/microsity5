/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker
 */
@Named(value = "actionWizardController")
@SessionScoped
public class ActionWizardController implements Serializable{

    private String device="№1";
    private String requisition="";
    private String value="";
    private String action="";
    private boolean deviceBool;
    
    public ActionWizardController() {
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRequisition() {
        return requisition;
    }

    public void setRequisition(String requisition) {
        this.requisition = requisition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    public void update(String id){
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(id);
    }

    public boolean isDeviceBool() {
        if(device.contains("№1"))return false;
        return true;
    }

    public void setDeviceBool(boolean deviceBool) {
        this.deviceBool = deviceBool;
    }
    
    public String next(){
        if(device.contains("№1"))return("actionWizardCount.xhtml");
        else return("actionWizardBool.xhtml");
    }
    
    
}
