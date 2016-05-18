/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticClasses;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.apache.commons.net.telnet.TelnetClient;

/**
 *
 * @author Panker
 */
@Named(value = "socket3Connection")
@RequestScoped
public class Socket3Connection {
    
    /**
     * Creates a new instance of Socket3Connection
     */
    public Socket3Connection() {
        
    }
    
    public static String getTemperature(String address,int port){
        try {
            Socket socket=new Socket(address, port);
            byte b[]=new byte[]{0x44};
            OutputStream os=socket.getOutputStream();
            os.write(b);
            os.flush();
            Thread.sleep(100);
            InputStream is=socket.getInputStream();
            byte c[]=new byte[is.available()];
            while(is.available()>0){
                is.read(c);
                return("+" + String.valueOf(c[1]));
            }
            
        } catch (IOException |IndexOutOfBoundsException| InterruptedException ex) {
            Logger.getLogger(Socket3Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Нет данных. Ошибка подключения";
    }
}
