/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

import java.io.IOException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

/**
 *
 * @author agustian
 */
public class SendTest {
    // Notice the username is NOT smack.test@gmail.com
    private static String username = "cesc";
    private static String password = "cesc";

    public static class MessageParrot implements PacketListener {

        private XMPPConnection xmppConnection;

        public MessageParrot(XMPPConnection conn) {
            xmppConnection = conn;
        }

        public void processPacket(Packet packet) {
            Message message = (Message) packet;
            if (message.getBody() != null) {
                String fromName = StringUtils.parseBareAddress(message.getFrom());
                System.out.println("Message from " + fromName + "\n" + message.getBody() + "\n");
                Message reply = new Message();
                reply.setTo(fromName);
                reply.setBody("I am a Java bot. You said: " + message.getBody());
                xmppConnection.sendPacket(reply);
            }
        }
    };

    public static void main(String[] args) {

        System.out.println("Starting IM client");

        // gtalk requires this or your messages bounce back as errors
        ConnectionConfiguration connConfig = new ConnectionConfiguration("192.168.1.136", 5222, "centos");
        XMPPConnection connection = new XMPPConnection(connConfig);

        try {
            connection.connect();
            System.out.println("Connected to " + connection.getHost());
        } catch (XMPPException ex) {
            //ex.printStackTrace();
            System.out.println("Failed to connect to " + connection.getHost());
            System.exit(1);
        }
        try {
            connection.login(username, password);
            System.out.println("Logged in as " + connection.getUser());

            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);

        } catch (XMPPException ex) {
            //ex.printStackTrace();
            // XMPPConnection only remember the username if login is succesful
            // so we can''t use connection.getUser() unless we log in correctly
            System.out.println("Failed to log in as " + username);
            System.exit(1);
        }

        PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
        connection.addPacketListener(new MessageParrot(connection), filter);

        if (args.length > 0) {
            // google bounces back the default message types, you must use chat
            Message msg = new Message("YUUUUUUUUUUUHHHHHHHUUUUUUUU", Message.Type.chat);
            msg.setBody("Test");
            connection.sendPacket(msg);
        }

        System.out.println("Press enter to disconnect\n");

        try {
            System.in.read();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }

        connection.disconnect();
    }
}

