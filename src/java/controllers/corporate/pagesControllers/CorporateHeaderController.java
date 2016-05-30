/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.corporate.pagesControllers;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "corporateHeaderController")
@RequestScoped
public class CorporateHeaderController {

    private long viewId;
    
    public CorporateHeaderController() {
    }

    public long getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("corpViewFacilityId", viewId);
        
    }
    
    
}
