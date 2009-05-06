/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

/**
 *
 * @author agustian
 */
public class SmackReceiverTest {

    public SmackReceiverTest() {
        try {
            XMPPConnection connection = new XMPPConnection("localhost");
            connection.connect();
            connection.login("user2", "user2");
            PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class), new FromContainsFilter("user1"));

            PacketCollector collector = connection.createPacketCollector(filter);
            Packet packet = collector.nextResult();
            if (packet instanceof Message) {
                Message msg = (Message) packet;
                System.out.println("Message: " + msg.getBody());

            }
            /*
            PacketListener myListener = new PacketListener() {
            
            public void processPacket(Packet packet) {
            if (packet instanceof Message) {
            Message msg = (Message) packet;
            // Process message
            System.out.println("Roger, Roger");
            System.out.println("Message: " + msg.getBody());
            }
            }
            };
             * */
            // Register the listener.
            //connection.addPacketListener(myListener, filter);

        } catch (XMPPException ex) {
            Logger.getLogger(SmackClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
