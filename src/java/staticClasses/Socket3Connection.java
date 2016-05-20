/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticClasses;

import entitys.main.facility.SensorsData;
import facades.SensorsDataFacade;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Panker
 */
@Named(value = "socket3Connection")
@RequestScoped
public class Socket3Connection {

    @EJB
    static SensorsDataFacade dataFacade;
    public Socket3Connection() {

    }

    public static String getTemperature(String address, int port) {
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            socket = new Socket(address, port);
            byte b[] = new byte[]{0x44};
            os = socket.getOutputStream();
            os.write(b);
            os.flush();
            Thread.sleep(100);
            is = socket.getInputStream();
            byte c[] = new byte[is.available()];
            while (is.available() > 0) {
                is.read(c);
                SensorsData sd=new SensorsData();
                sd.setDt(new Date());
                sd.setIsAction(false);
                sd.setPinNum((short)0);
                sd.setSensorId("Socket3"+address+port);
                sd.setValue(String.valueOf(c[1]));
                dataFacade.create(sd);
                return ("+" + String.valueOf(c[1]));
            }

        } catch (IOException | IndexOutOfBoundsException | InterruptedException ex) {
            Logger.getLogger(Socket3Connection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            try {
                if (socket != null)socket.close();                
                if(os!=null)os.close();
                if(is!=null)is.close();
            } catch (IOException exception) {
                
            }
        }
        return "Нет данных. Ошибка подключения";
    }
}
