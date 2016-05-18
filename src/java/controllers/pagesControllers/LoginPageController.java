/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.User;
import facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import staticClasses.Mail;

/**
 *
 * @author Panker
 */
@Named(value = "loginPageController")
@SessionScoped
public class LoginPageController implements Serializable {

    private String returnedMessage = "";

    @EJB
    UserFacade uf;

    public LoginPageController() {
    }

    public void sendPassword(String email) {
        if (!email.contains("@")) {
            returnedMessage = "Не верный формат Эл. адрес :" + email;
        } else {
            List<User> users = new ArrayList<>();
            users = uf.findAll();
            if (!users.isEmpty()) {
                for (User user : users) {
                    if (user.getMain_email().equalsIgnoreCase(email)) {
                        if (new Mail().sendPassword(user.getMain_email(),user.getPassword())) {
                            returnedMessage = "Данные отправлены на " + email;
                            return;
                        } else {
                            returnedMessage = "Ошибка отправления. Свяжитесь с администрацией.";
                            return;
                        }
                    } else {
                        returnedMessage = "Ошибка отправления.Не найдено совпадений с таким ящиком:"+email;
                    }

                }
            }
            returnedMessage = "Ошибка отправления.Не найдено совпадений с таким ящиком:"+email+". Зарегистрируйтесь";
        }
    }

    public String getReturnedMessage() {
        return returnedMessage;
    }

    public void setReturnedMessage(String returnedMessage) {
        this.returnedMessage = returnedMessage;
    }

}
