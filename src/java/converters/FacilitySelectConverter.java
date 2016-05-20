/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import controllers.pagesControllers.HeaderController;
import entitys.main.User;
import entitys.main.facility.Facility;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Panker
 */

@FacesConverter("facilitySelectConverter")
public class FacilitySelectConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value!=null&&value.trim().length()>0){
            try{
                HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                User current = (User) hs.getAttribute("current");
                Facility facility=null;
                for (Facility facility1 : current.getFacilitys()) {
                    if(facility1.getTitle().equals(value))
                        facility=facility1;
                }
                return facility;
                
            }catch(NumberFormatException ex){
                System.out.println("Blyad");
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value !=null){
            return ((Facility)value).getTitle();
        }
        return"";
    }
    
}
