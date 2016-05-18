/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.support.PageContent;
import facades.PageContentFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "rControlController")
@RequestScoped
public class RControlController {
    
    @EJB
    private PageContentFacade pcf;
    private PageContent content;
    
    public RControlController() {
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
            content.setAdvertName("Удаленное управление");
            content.setAdvMainText("Рекламный текст о удаленном управлении");
            content.setAdvSubtitleText("Рекламный текст о удаленном управлении может быть изменен администратором");
            content.setPageName("rControl");
        } else {
            boolean found = false;
            for (PageContent page : pages) {
                if (page.getPageName().equals("rControl")) {
                    content = page;
                    found = true;
                }

            }
            if (!found) {
                content = new PageContent();
                content.setAdvertName("Удаленное управление");
                content.setAdvMainText("Рекламный текст о удаленном управлении");
                content.setAdvSubtitleText("Рекламный текст о удаленном управлении может быть изменен администратором");
                content.setPageName("rControl");
                pcf.create(content);
            }

        }

    }
    
    public void onSwitch(){
        Runtime runtime=Runtime.getRuntime();
        String cmd[]={"mosquitto_pub","-u","micro","-P","microcity","-t","sensors/ESP_5c:cf:7f:12:00:e3/0/action","-m","1"};
        try {
            Process process=runtime.exec(cmd);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Команда успешно отправлена"));
        } catch (IOException ex) {
            System.out.println("Команда включения не прошла");;
        }
    }
    
    public void offSwitch(){
        Runtime runtime=Runtime.getRuntime();
        String cmd[]={"mosquitto_pub","-u","micro","-P","microcity","-t","sensors/ESP_5c:cf:7f:12:00:e3/0/action","-m","0"};
        try {
            Process process=runtime.exec(cmd);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Команда успешно отправлена"));
        } catch (IOException ex) {
            System.out.println("Команда выключения не прошла");;
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
