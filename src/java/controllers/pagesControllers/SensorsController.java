/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import controllers.pagesControllers.SensorsController.PwrMax.PinLocal;
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
import javax.enterprise.context.RequestScoped;
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
@Named(value = "sensorsController")
@SessionScoped
public class SensorsController implements Serializable {

    @EJB
    private PageContentFacade pcf;
    private PageContent content;

    @EJB
    private SensorsDataFacade sdf;

    private DemoValue dValue;

    private PwrMax pwrMax;

    public SensorsController() {
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
            content.setAdvertName("Он-лайн датчики");
            content.setAdvMainText("Рекламный текст о датчиках");
            content.setAdvSubtitleText("Рекламный текст о датчиках может быть изменен администратором");
            content.setPageName("sensors");
        } else {
            boolean found = false;
            for (PageContent page : pages) {
                if (page.getPageName().equals("sensors")) {
                    content = page;
                    found = true;
                }

            }
            if (!found) {
                content = new PageContent();
                content.setAdvertName("Он-лайн датчики");
                content.setAdvMainText("Рекламный текст о датчиках");
                content.setAdvSubtitleText("Рекламный текст о датчиках может быть изменен администратором");
                content.setPageName("sensors");
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

    public void setSensorToChange(ESP toChange) {
        HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        hs.setAttribute("sensorToChange", toChange);
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

    public PwrMax getPwrMax() {
        pwrMax=new PwrMax();
        return pwrMax;
    }
    
    public void disarmPin(PinLocal pin){
        SensorsData pinDisarm=new SensorsData();
        pinDisarm.setDt(new Date());
        pinDisarm.setIsAction(true);
        pinDisarm.setPinNum((short)pin.getPin_num());
        pinDisarm.setValue("0");
        pinDisarm.setSensorId("ESP_5c:cf:7f:8b:cc:e0");
        sdf.create(pinDisarm);
    }
    

    public class DemoValue {

        private double now;
        private String lastUpdate;
        List<SensorsData> demoSensor = new ArrayList<>();
        private LineChartModel model = new LineChartModel();

        public DemoValue() {
            update();
        }

        public double getNow() {
            return now;
        }

        public void setNow(int now) {
            this.now = now;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public LineChartModel getModel() {
            LineChartSeries series = new LineChartSeries();
            series.setLabel("График изменения температуры");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateDay = "";
            double midValue = 0;
            boolean newDay = false;
            for (SensorsData sensorsData : demoSensor) {
                if (!dateDay.equals(sdf.format(sensorsData.getDt()))) {
                    dateDay = sdf.format(sensorsData.getDt());
                    midValue = Double.parseDouble(sensorsData.getValue());
                    if (newDay) {
                        newDay = false;
                        series.set(dateDay, midValue);
                    }
                } else {
                    midValue = (midValue + Double.parseDouble(sensorsData.getValue())) / 2;
                    newDay = true;
                }
            }
            model.addSeries(series);
            model.getAxis(AxisType.Y).setLabel("С");
            DateAxis axis = new DateAxis("Дата");
            axis.setTickAngle(-45);
            axis.setTickFormat("%b %#d, %y");
            model.getAxes().put(AxisType.X, axis);
            return model;
        }

        private void update() {
            List<SensorsData> all = sdf.findAll();
            if (!all.isEmpty()) {
                for (SensorsData sensorsData : all) {
                    if (sensorsData.getSensorId().contains(":00:e3")) {
                        demoSensor.add(sensorsData);
                    }
                }

                for (SensorsData sensorsData : demoSensor) {
                    now = Double.parseDouble(sensorsData.getValue());
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                int index = demoSensor.size();
                if (index > 0) {
                    Date last = demoSensor.get(index - 1).getDt();

                    lastUpdate = df.format(last);
                }
            }
        }

    }
    
    public class PwrMax {

        private PinLocal pin4 = new PinLocal(4, "Тревожная кнопка №1");
        private PinLocal pin5 = new PinLocal(5, "Тревожная кнопка №2");
        private PinLocal pin12 = new PinLocal(12, "Датчик открытия двери");
        private PinLocal pin14 = new PinLocal(14, "Датчик затопления");
        private PinLocal pin2 = new PinLocal(2, "Сигнализатор разряда батареи");
        private PinLocal pin0 = new PinLocal(0, "Сигнализатор помех");
        private PinLocal pin13 = new PinLocal(13, "Сигнализатор вскрытия устройства");
        private PinLocal pin15 = new PinLocal(13, "Сигнализатор отключения устройства");
        List<SensorsData> demoSensor = new ArrayList<>();

        public PwrMax() {
            update();
        }

        
        
        public void update() {
            List<SensorsData> all = sdf.findAll();
            if (!all.isEmpty()) {
                for (SensorsData sensorsData : all) {
                    if (sensorsData.getSensorId().contains("8b:cc:e0")) {
                        demoSensor.add(sensorsData);
                    }
                }

                for (SensorsData sensorsData : demoSensor) {
                    switch (sensorsData.getPinNum()) {
                        case 4: {
                            if (sensorsData.getValue().equals("1")) {
                                pin4.setLast_state(true,sensorsData.getDt());
                            }else{
                                pin4.setLast_state(false,sensorsData.getDt());
                            }
                        }
                        ;
                        break;
                        case 5: {
                            if (sensorsData.getValue().equals("1")) {
                                pin5.setLast_state(true,sensorsData.getDt());
                            }else{
                                pin5.setLast_state(false,sensorsData.getDt());
                            }
                        }
                        ;
                        break;
                        case 12: {
                            if (sensorsData.getValue().equals("1")) {
                                pin12.setLast_state(true,sensorsData.getDt());
                            } else{
                                pin12.setLast_state(false,sensorsData.getDt());
                            }
                        }
                        ;
                        break;
                        case 14: {
                            if (sensorsData.getValue().equals("1")) {
                                pin14.setLast_state(true,sensorsData.getDt());
                            } else{
                                pin14.setLast_state(false,sensorsData.getDt());
                            }
                        };
                        break;
                        case 2:{
                            if (sensorsData.getValue().equals("1")) {
                                pin2.setLast_state(true,sensorsData.getDt());
                            } else{
                                pin2.setLast_state(false,sensorsData.getDt());
                            }
                        };
                        break;
                        case 0:{
                            if (sensorsData.getValue().equals("1")) {
                                pin0.setLast_state(true,sensorsData.getDt());
                            } else{
                                pin0.setLast_state(false,sensorsData.getDt());
                            }
                        };
                        break;
                        case 13:{
                            if (sensorsData.getValue().equals("1")) {
                                pin13.setLast_state(true,sensorsData.getDt());
                            } else{
                                pin13.setLast_state(false,sensorsData.getDt());
                            }
                        };
                        break;
                        case 15:{
                            if (sensorsData.getValue().equals("1")) {
                                pin15.setLast_state(true,sensorsData.getDt());
                            } else{
                                pin15.setLast_state(false,sensorsData.getDt());
                            }
                        }
                        
                    }
                    
                }
            }
        }

        public PinLocal getPin4() {
            return pin4;
        }

        public void setPin4(PinLocal pin4) {
            this.pin4 = pin4;
        }

        public PinLocal getPin5() {
            return pin5;
        }

        public void setPin5(PinLocal pin5) {
            this.pin5 = pin5;
        }

        public PinLocal getPin12() {
            return pin12;
        }

        public void setPin12(PinLocal pin12) {
            this.pin12 = pin12;
        }

        public PinLocal getPin14() {
            return pin14;
        }

        public void setPin14(PinLocal pin14) {
            this.pin14 = pin14;
        }

        public PinLocal getPin2() {
            return pin2;
        }

        public void setPin2(PinLocal pin2) {
            this.pin2 = pin2;
        }

        public PinLocal getPin0() {
            return pin0;
        }

        public void setPin0(PinLocal pin0) {
            this.pin0 = pin0;
        }

        public PinLocal getPin13() {
            return pin13;
        }

        public void setPin13(PinLocal pin13) {
            this.pin13 = pin13;
        }

        public PinLocal getPin15() {
            return pin15;
        }

        public void setPin15(PinLocal pin15) {
            this.pin15 = pin15;
        }

        public class PinLocal {

            private final int pin_num;
            private boolean last_state=false;
            private final String pin_name;
            private Date last_change;

            public PinLocal(int pin_num, String pin_name) {
                this.pin_num = pin_num;
                this.pin_name = pin_name;
            }

            public String getPin_name() {
                return pin_name;
            }

            public int getPin_num() {
                return pin_num;
            }

            public boolean isLast_state() {
                return last_state;
            }

            public void setLast_state(boolean last_state, Date change) {
                this.last_state = last_state;
                if(last_state)setLast_change(change);
            }
            
            public void setLast_state(boolean last_state) {
                this.last_state = last_state;
                if(last_state)setLast_change(new Date());
            }
            

            public String getLast_change() {
                String l_change="Не известно";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if(last_change!=null) l_change = df.format(last_change);
                return l_change;
            }

            private void setLast_change(Date last_change) {
                this.last_change = last_change;
            }

        }
    }
}
