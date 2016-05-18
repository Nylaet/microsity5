/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys.main;

import entitys.main.facility.Camera;
import entitys.main.facility.Facility;
import entitys.main.satelite.SateliteH;
import entitys.main.satelite.SateliteM;
import entitys.main.support.Log;
import entitys.main.support.Message;
import entitys.main.support.Subscription;
import enums.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Panker
 */
@Entity(name = "m_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password="";
    private boolean rememberMe=false;
    private String main_email="";
    private List<String> emails;
    private List<String> phones;
    @Column(name = "j_role")
    private Role role=Role.USER;
    private double balance;
    @OneToOne(cascade = CascadeType.ALL)
    private Subscription subscription;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastVisit;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Facility> facilitys;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SateliteH> satelits_h;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SateliteM> satelits_m;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Log> logs;
    
    private String lastSession="";

    public void addEmail(String email) {
        if (emails == null) {
            emails = new ArrayList<>();
        }
        emails.add(email);
    }
    
    public void addPhone(String phone){
        if(phones==null){
            phones=new ArrayList<>();
        }
        phones.add(phone);
    }
    
    public void addFacility(Facility facility){
        if(facilitys==null){
            facilitys=new ArrayList<>();
        }
        facilitys.add(facility);
    }
    
    public void addSateliteH(SateliteH sateliteH){
        if(satelits_h==null){
            satelits_h=new ArrayList<>();
        }
        satelits_h.add(sateliteH);
    }
    
    public void addSateliteM(SateliteM sateliteM){
        if(satelits_m==null){
            satelits_m=new ArrayList<>();
        }
        satelits_m.add(sateliteM);
    }
    
    public void addMessage(Message message){
        if(messages==null){
            messages=new ArrayList<>();
        }
        messages.add(message);
    }
    
    public void addMessage(String message){
        if(messages==null){
            messages=new ArrayList<>();
        }
        messages.add(new Message("Сообщение от администрации", message));
    }
    
    public void addLog(Log log){
        if(logs==null){
            logs=new ArrayList<>();
        }
        logs.add(log);
    }

    // Getters/Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
    
    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public List<Facility> getFacilitys() {
        if(facilitys==null)facilitys=new ArrayList<>();
        return facilitys;
    }

    public void setFacilitys(List<Facility> facilitys) {
        this.facilitys = facilitys;
    }

    public List<SateliteH> getSatelits_h() {
        return satelits_h;
    }

    public void setSatelits_h(List<SateliteH> satelits_h) {
        this.satelits_h = satelits_h;
    }

    public List<SateliteM> getSatelits_m() {
        return satelits_m;
    }

    public void setSatelits_m(List<SateliteM> satelits_m) {
        this.satelits_m = satelits_m;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public String getMain_email() {
        return main_email;
    }

    public void setMain_email(String main_email) {
        this.main_email = main_email;
    }

    public String getLastSession() {
        return lastSession;
    }

    public void setLastSession(String lastSession) {
        this.lastSession = lastSession;
    }
    
    public boolean getIsAdmin(){
        if(role.equals(Role.ADMIN))return true;
        return false;
    }
    
    public void setIsAdmin(boolean value){
        if(value)role=Role.ADMIN;
        else role=Role.USER;
    }
    
    
    
    //Overides
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.User[ id=" + id + " ]";
    }
    
}
