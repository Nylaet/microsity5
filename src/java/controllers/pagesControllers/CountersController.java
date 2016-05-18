/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import entitys.main.facility.ESP;
import entitys.main.facility.SensorsData;
import entitys.main.support.PageContent;
import facades.PageContentFacade;
import facades.SensorsDataFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Panker
 */
@Named(value = "countersController")
@SessionScoped
public class CountersController implements Serializable {

    @EJB
    private PageContentFacade pcf;
    private PageContent content;

    @EJB
    private SensorsDataFacade sdf;

    private DemoValue dValue;

    public CountersController() {
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
            content.setAdvertName("Он-лайн счетчики");
            content.setAdvMainText("Рекламный текст о счетчиках");
            content.setAdvSubtitleText("Рекламный текст о счетчиках может быть изменен администратором");
            content.setPageName("counters");
        } else {
            boolean found = false;
            for (PageContent page : pages) {
                if (page.getPageName().equals("counters")) {
                    content = page;
                    found = true;
                }
                
            }
            if (!found) {
                content = new PageContent();
                content.setAdvertName("Он-лайн счетчики");
                content.setAdvMainText("Рекламный текст о счетчиках");
                content.setAdvSubtitleText("Рекламный текст о счетчиках может быть изменен администратором");
                content.setPageName("counters");
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

    public void setCounterToChange(ESP toChange) {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        hs.setAttribute("counterToChange", toChange);
    }

    private HeaderController hc() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        HeaderController hc = (HeaderController) context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }

    public DemoValue getDValue() {
        dValue = new DemoValue();
        return dValue;
    }

    public class DemoValue {

        private double start;
        private double now;
        private double difference;
        private String lastUpdate;
        List<SensorsData> demoSensor = new ArrayList<>();
        private LineChartModel model = new LineChartModel();

        public DemoValue() {
            update();
        }

        public double getStart() {
            update();
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public double getNow() {
            return now;
        }

        public void setNow(int now) {
            this.now = now;
        }

        public double getDifference() {
            return difference;
        }

        public void setDifference(int difference) {
            this.difference = difference;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public LineChartModel getModel() {
            LineChartSeries series = new LineChartSeries();
            series.setLabel("График потребления электроэнергии");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateDay = "";
            double value=start;
            for (SensorsData sensorsData : demoSensor) {
                if (!dateDay.equals(sdf.format(sensorsData.getDt()))) {
                    dateDay=sdf.format(sensorsData.getDt());
                    series.set(dateDay, value+=Double.parseDouble(sensorsData.getValue()));
                }
            }
            model.addSeries(series);
            model.getAxis(AxisType.Y).setLabel("kWatt");
            DateAxis axis= new DateAxis("Дата");
            axis.setTickAngle(-45);
            axis.setTickFormat("%b %#d, %y");
            model.getAxes().put(AxisType.X, axis);
            return model;
        }

        private void update() {
            List<SensorsData> all = sdf.findAll();
            for (SensorsData sensorsData : all) {
                if (sensorsData.getSensorId().contains(":a0:e4")) {
                    demoSensor.add(sensorsData);
                }
            }
            start = 54449;
            for (SensorsData sensorsData : demoSensor) {
                now += Double.parseDouble(sensorsData.getValue())*0.1;
            }
            now += start;
            difference = now - start;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            int index = demoSensor.size();
            if (index > 0) {
                Date last = demoSensor.get(index - 1).getDt();

                lastUpdate = df.format(last);
            }
        }

    }

}
