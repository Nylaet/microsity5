/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.support.PageContent;
import facades.PageContentFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker
 */
@Named(value = "gpsController")
@RequestScoped
public class GpsController {
    
    @EJB
    private PageContentFacade pcf;
    private PageContent content;
    
    
    public GpsController() {
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
            content.setAdvertName("GPS мониторинг");
            content.setAdvMainText("Рекламный текст о GPS мониторинге");
            content.setAdvSubtitleText("Рекламный текст о GPS мониторинге может быть изменен администратором");
            content.setPageName("gps");
        } else {
            boolean found = false;
            for (PageContent page : pages) {
                if (page.getPageName().equals("gps")) {
                    content = page;
                    found = true;
                }

            }
            if (!found) {
                content = new PageContent();
                content.setAdvertName("GPS мониторинг");
                content.setAdvMainText("Рекламный текст о GPS мониторинге");
                content.setAdvSubtitleText("Рекламный текст о GPS мониторинге может быть изменен администратором");
                content.setPageName("gps");
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
    
    public void saveContent() {
        pcf.edit(content);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Содержимое сохранено"));
    }
}
