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
public enum DataType {
    WATT(true,"ВаттМетр"),TEMP(false,"Температура");
    
    private final boolean counter;
    private final String name;
    
    DataType(boolean counter,String name){
        this.counter=counter;
        this.name=name;
    }
}
