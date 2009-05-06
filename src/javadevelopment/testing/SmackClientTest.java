/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author agustian
 */
public class SmackClientTest {

    public SmackClientTest() {
        try {
            XMPPConnection connection = new XMPPConnection("localhost");
            connection.connect();
            connection.login("user1", "user1");
            Message message = new Message();
            message.setTo("user2");
            message.setSubject("Server down");
            message.setBody("The 'jupiter' server has just gone down");
            message.setType(Message.Type.headline);
            connection.sendPacket(message);
            connection.disconnect();
        } catch (XMPPException ex) {
            Logger.getLogger(SmackClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        SmackClientTest sct = new SmackClientTest();
        SmackReceiverTest srt = new SmackReceiverTest();
    }
}
