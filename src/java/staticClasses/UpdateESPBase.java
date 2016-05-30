/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticClasses;

import entitys.main.ESPRegisteredBase;
import entitys.main.User;
import entitys.main.facility.ESP;
import entitys.main.facility.Facility;
import entitys.main.facility.SensorsData;
import facades.ESPRegisteredBaseFacade;
import facades.SensorsDataFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Panker
 */
@Stateless
public class UpdateESPBase {
    @EJB
    SensorsDataFacade sdf;
    @EJB
    UserFacade uf;
    @EJB
    ESPRegisteredBaseFacade registeredBaseFacade;
    
    private List <UserSensorsDirectory> directory=new ArrayList<>();
    
    @PostConstruct
    public void init(){
        List<User> clients=uf.findAll();        
        for (User client : clients) {
            for (Facility facility : client.getFacilitys()) {
                for (ESP esp : facility.getEsps()) {
                    directory.add(new UserSensorsDirectory(client.getId(),esp.getSensorId()));
                }
            }
        }
    }
    
    @Schedule(minute = "*", hour = "*")
    
    
    public void myTimer() {
        System.out.println("Начинаем сканировать данные с esp");
        List<SensorsData> data=new ArrayList<>();
        try{
            data=sdf.findAll();
        }catch(NullPointerException ex){
            System.out.println("База пуста");
            
        }
        for (SensorsData sensorsData : data) {
            if(!sensorsData.getWasRead()){
                boolean finded=false;
                for (UserSensorsDirectory userSensorsDirectory : directory) {
                    if(sensorsData.getSensorId().equals(userSensorsDirectory.sensorID)){
                        addNewData(userSensorsDirectory,sensorsData);
                        sensorsData.setWasRead(true);
                        finded=true;
                        sdf.edit(sensorsData);
                        break;
                    }
                }
                if(!finded){
                    List <ESPRegisteredBase> regBase=registeredBaseFacade.findAll();
                    boolean registered=false;
                    for (ESPRegisteredBase eSPRegisteredBase : regBase) {
                        if(eSPRegisteredBase.getSensorID().equals(sensorsData.getSensorId())){
                            registered=true;
                            break;
                        }
                    }
                    if(!registered){
                        ESPRegisteredBase newSensor=new ESPRegisteredBase();
                        newSensor.setSensorID(sensorsData.getSensorId());
                        registeredBaseFacade.create(newSensor);
                        System.out.println("Добавлен новый датчик ID: "+newSensor.getMicrosityID());
                    }
                }
            }
        }
        init();
        System.out.println("Сканирование закончено");
        
    }

    private void addNewData(UserSensorsDirectory userSensorsDirectory, SensorsData sensorsData) {
        User client=uf.find(userSensorsDirectory.userID);
        for (Facility facility : client.getFacilitys()) {
            for (ESP esp : facility.getEsps()) {
                if(esp.getSensorId().equals(sensorsData.getSensorId())){
                    esp.addData(sensorsData);
                }
            }
        }
    }
    
    

    private class UserSensorsDirectory{
        Long userID;
        String sensorID;
        
        public UserSensorsDirectory(Long userID,String sensorID){
            this.userID=userID;
            this.sensorID=sensorID;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
