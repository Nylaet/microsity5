/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.ESPRegisteredBase;
import entitys.main.facility.SensorsData;
import facades.ESPRegisteredBaseFacade;
import facades.SensorsDataFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Panker
 */
@Named(value = "espViewController")
@SessionScoped
public class EspViewController implements Serializable {

    private List<SensorsData> sds = new ArrayList<>();
    List<ESPRegisteredBase> registeredBases = new ArrayList<>();

    @EJB
    private SensorsDataFacade sdf;
    @EJB
    private ESPRegisteredBaseFacade esprbf;

    public EspViewController() {
    }

    public List<SensorsData> getUnRegisteredESP() {
        sds = sdf.findAll();
        registeredBases = esprbf.findAll();
        List<SensorsData> unregistered = new ArrayList<>();
        for (SensorsData sd : sds) {
            boolean needAdded = true;
            for (SensorsData sensorsData : unregistered) {
                if (sensorsData.getSensorId().equals(sd.getSensorId())) {
                    needAdded = false;
                    break;
                }
            }
            if (needAdded) {
                for (ESPRegisteredBase registered : registeredBases) {
                    if (registered.getSensorID().equals(sd.getSensorId())) {
                        needAdded = false;
                        break;
                    }
                }
            }
            if (needAdded) {
                unregistered.add(sd);
            }
        }
        return unregistered;
    }
    
    public List<ESPRegisteredBase> getRegisteredESP(){
        registeredBases = esprbf.findAll();
        return registeredBases;
    }

    public void addESP(SensorsData added) {
        registeredBases = esprbf.findAll();
        boolean find = false;
        for (ESPRegisteredBase registered : registeredBases) {
            if (registered.getSensorID().equals(added.getSensorId())) {
                find = true;
                break;
            }
        }
        if (!find) {
            ESPRegisteredBase addedESP = new ESPRegisteredBase();
            addedESP.setSensorID(added.getSensorId());
            esprbf.create(addedESP);
        }
    }

    private HeaderController hc() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        HeaderController hc = (HeaderController) context.getELResolver().getValue(context, null, "headerController");
        return hc;
    }
}
