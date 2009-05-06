/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.jivesoftware.smack.XMPPException;

/**
 *
 * @author agustian
 */
public class UserAkolly extends User {

    public void init() throws Exception {
        setUserName("akolly");
        setPassword("akolly");
        ssc = new SimpleSmackClient();
        ssc.login(this.getUserName(), this.getPassword());
    }

    public void sendMessage(String message, String destination) throws XMPPException, XMPPException {
        ssc.sendMessage(message, destination);
    }

    public void logout() {
        ssc.logout();
    }

    public void fileTransfer(String fileName, String destination) throws XMPPException {
        ssc.fileTransfer(fileName, destination);
    }

    public void fileReceiver(final boolean accept, final String fileName) {
        ssc.fileReceiver(accept, fileName);
    }

    public static void main(String[] args) throws Exception {
        UserAkolly uako = new UserAkolly();
        uako.init();



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message = null;
        while (!(message = br.readLine()).equalsIgnoreCase("bye")) {
            ssc.sendMessage(message, "akem@host");

            // Receive new file. You can give it a new name
            uako.fileReceiver(true, "D:\\Documents and Settings\\ekakoll\\Desktop\\Received Book Review.txt");
        }



    //uako.logout();
    }
}
