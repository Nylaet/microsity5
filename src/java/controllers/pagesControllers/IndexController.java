/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.facility.Camera;
import entitys.main.facility.Counter;
import entitys.main.facility.Facility;
import entitys.main.support.News;
import facades.NewsFacade;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Panker
 */
@Named(value = "indexController")
@SessionScoped
public class IndexController implements Serializable {

    private List<News> newses;
    private News addedNews;
    @EJB
    NewsFacade nf;

    public IndexController() {
    }

    @PostConstruct
    public void init() {
        System.out.println(System.getProperty("os.name"));
        try {
            newses = nf.findAll();
        } catch (PersistenceException ex) {
            newses = new ArrayList<>();
        }
        if (newses.isEmpty()) {
            News emptyNews = new News();
            emptyNews.setIconName("news-icon.png");
            emptyNews.setTitle("Новостей нет :(");
            emptyNews.setNewsText("К сожалению, новостей нет, но скоро появятся");
            emptyNews.setMoreText("Здесь размещается более подробная информация о какой либо новости или описание продукта. Изменения вносит администратор");
            newses.add(emptyNews);
        }
    }

    public List<String> getIconsList() {
        List<String> iconsList = new ArrayList<>();
        String relative = "/resources/icons/";
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute = context.getRealPath(relative);

        File file = new File(absolute);
        File[] files = file.listFiles();
        for (File file1 : files) {
            iconsList.add(file1.getName());
        }
        return iconsList;
    }

    public void editNews(News edit) {
        nf.edit(edit);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Новость обновлена"));
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("content");
    }

    public void createNews() {
        nf.create(addedNews);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Новость добавлена"));
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("content");
    }

    public void deleteNews(News delete) {
        nf.remove(delete);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Новость удалена"));
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("content");
    }

    public News getAddedNews() {
        if (addedNews == null) {
            addedNews = new News();
        }
        return addedNews;
    }

    public void setAddedNews(News addedNews) {
        this.addedNews = addedNews;
    }

    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }
    
    public String getLores(){
        String lores=" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec consectetur, lectus quis posuere tincidunt, dui orci malesuada diam, ac eleifend sapien ipsum blandit ante. Aenean blandit, ipsum eget blandit pellentesque, est lorem mattis elit, vel rhoncus tortor augue vitae risus. Nam ac metus orci. Suspendisse ut orci quis justo venenatis mollis vel eu erat. Vestibulum quis lorem sit amet ipsum sagittis dictum. Nulla at scelerisque libero, at molestie mi. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur urna ligula, convallis sit amet lectus sit amet, varius sollicitudin orci. Integer mollis, lacus vel volutpat tristique, ex elit viverra risus, et auctor urna felis faucibus dui. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla mollis purus a consectetur pharetra. Fusce nec dictum lacus. Aliquam lacinia eget ipsum a pharetra. Ut diam turpis, pretium in ante id, cursus tristique tortor. Mauris id ipsum dictum, luctus massa sed, aliquet lacus. Fusce pulvinar neque ac congue luctus.\n" +
"\n" +
"Praesent dapibus tortor et nisl dictum, at porttitor ligula ultricies. Curabitur malesuada nibh in erat viverra tincidunt. Vestibulum eget quam nunc. Mauris tristique eu justo non fermentum. Aliquam consequat est nec dapibus feugiat. Integer lorem elit, luctus ac sapien pulvinar, fringilla fringilla urna. Sed viverra purus quis diam consequat elementum. Aliquam posuere lacus ut libero blandit, at sagittis augue ultricies. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nullam pulvinar vulputate ante nec luctus. Suspendisse eu dolor sodales, porta neque cursus, feugiat augue. In tincidunt hendrerit nulla, ac vestibulum quam molestie ut. In porta ornare erat vitae suscipit. ";
        return lores;
    }
    
    public String getGMapsAdress(Facility facility) {
        String lat = "59.5611525";
        String lng = "150.8301413";
        String address = facility.getCountry() + "," + facility.getSity() + facility.getStreet() + "," + facility.getHome();
        try {
            String urlS = "http://maps.google.com/maps/api/geocode/json?" + "sensor=false&address=" + URLEncoder.encode(address, "UTF-8");
            URL url = new URL(urlS);
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();

            JSONObject obj = new JSONObject(str);
            if (!obj.getString("status").equals("OK")) {
                lat = "59.5611525";
                lng = "150.8301413";
            } else {
                JSONObject res = obj.getJSONArray("results").getJSONObject(0);
                System.out.println(res.getString("formatted_address"));
                JSONObject loc
                        = res.getJSONObject("geometry").getJSONObject("location");
                lat = String.valueOf(loc.getDouble("lat"));
                lng = String.valueOf(loc.getDouble("lng"));
            }

        } catch (UnsupportedEncodingException | MalformedURLException ex) {
            lat = "59.5611525";
            lng = "150.8301413";
        } catch (IOException ex) {
            lat = "59.5611525";
            lng = "150.8301413";
        }
        return (lat + "," + lng);
    }

}
