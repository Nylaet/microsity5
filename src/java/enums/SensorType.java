/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author Panker
 */
public enum SensorType {
    ESP("ESPModule"),SOCKET3("Socket3Module");
    private final String name;

    private SensorType(String name) {
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
