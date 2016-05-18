/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.facility.Camera;
import entitys.main.support.PageContent;
import facades.PageContentFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */
@Named(value = "camerasController")
@SessionScoped
public class CamerasController implements Serializable {
    
    @EJB
    private PageContentFacade pcf;
    private PageContent content;
    
    public CamerasController() {
    }
    
    @PostConstruct
    public void init() {
        List<PageContent> pages = new ArrayList<>();
        try {
            pages = pcf.findAll();
        } catch (NullPointerException ex) {
        }
        if (pages.isEmpty()) {
            content = new PageContent();
            content.setAdvertName("Он-лайн видеонаблюдение");
            content.setAdvMainText("Рекламный текст о видеонаблюдении");
            content.setAdvSubtitleText("Рекламный текст о видеонаблюдении может быть изменен администратором");
            content.setPageName("cameras");
        } else {
            boolean found = false;
            for (PageContent page : pages) {
                if (page.getPageName().equals("cameras")) {
                    content = page;
                    found = true;
                }                
            }
            if (!found) {
                content = new PageContent();
                content.setAdvertName("Он-лайн видеонаблюдение");
                content.setAdvMainText("Рекламный текст о видеонаблюдении");
                content.setAdvSubtitleText("Рекламный текст о видеонаблюдении может быть изменен администратором");
                content.setPageName("cameras");
                pcf.create(content);
            }
        }
        
    }
    
    public PageContent getContent() {
        return content;
    }
    
    public void setContent(PageContent content) {
        this.content = content;
    }
    
    public User getCurrent() {
        return hc().getCurrent();
    }
    
    public void saveContent() {
        pcf.edit(content);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Содержимое сохранено"));
    }
    
    public void setCameraToChange(Camera toChange) {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        hs.setAttribute("cameraToChange", toChange);
    }
    
    private HeaderController hc() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        HeaderController hc = (HeaderController) context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }
    
}
