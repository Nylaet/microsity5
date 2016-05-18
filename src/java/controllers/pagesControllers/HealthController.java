/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.support.PageContent;
import facades.PageContentFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "healthController")
@RequestScoped
public class HealthController {

    @EJB
    private PageContentFacade pcf;
    private PageContent content;

    private String pulse;
    private String timer;
    private String last_change;

    /**
     * Creates a new instance of HealthController
     */
    public HealthController() {
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
            content.setAdvertName("Он-лайн Биометрия");
            content.setAdvMainText("Рекламный текст о биометрии");
            content.setAdvSubtitleText("Рекламный текст о биометрии может быть изменен администратором");
            content.setPageName("health");
        } else {
            boolean found = false;
            for (PageContent page : pages) {
                if (page.getPageName().equals("health")) {
                    content = page;
                    found = true;
                }

            }
            if (!found) {
                content = new PageContent();
                content.setAdvertName("Он-лайн Биометрия");
                content.setAdvMainText("Рекламный текст о биометрии");
                content.setAdvSubtitleText("Рекламный текст о биометрии может быть изменен администратором");
                content.setPageName("health");
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
    
    public String getPulse() {
        double p=0;
        while (true) {
            p = Math.random() * 1000;
            if(p>60&&p<80)break;
        }
        p = Math.floor(p);
        return Double.toString(p);
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getTimer() {
        return "6:27";
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getLast_change() {
        Date date = new Date();
        if (date.getHours() >= 2) {
            date.setHours(date.getHours() - 1);
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        last_change=df.format(date);
        return last_change;
    }

    public void setLast_change(String last_change) {
        this.last_change = last_change;
    }

}
