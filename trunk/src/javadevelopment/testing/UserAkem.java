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
public class UserAkem extends User {

    public void init() throws Exception {
        setUserName("akem");
        setPassword("akem");
        ssc = new SimpleSmackClient();
        ssc.login(this.getUserName(), this.getPassword());
    }

    public void sendMessage(String message, String destination) throws XMPPException {
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
        UserAkem uak = new UserAkem();
        uak.init();



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message = null;
        while (!(message = br.readLine()).equalsIgnoreCase("bye")) {
            ssc.sendMessage(message, "akolly@host");
        }

        // Send file to user akolly

        uak.fileTransfer("D:\\Documents and Settings\\ekakoll\\Desktop\\Book Review.txt", "akolly@host/Smack");



    //uak.logout();
    }
}
