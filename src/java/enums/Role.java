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
public enum Role {
    ADMIN("Администратор"), USER("Пользователь"),CORP_USER("Корпоративный клиент");
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}