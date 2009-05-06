package javadevelopment.testing;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class SimpleClient {

    /** Server name */
    public static final String SERVER_NAME = "192.168.1.136";
    /** Server port */
    public static final int SERVER_PORT = 5222; // as you can see in Admin
    // Console properties
    /** Client name - for login */
    private final String clientName;
    /** Client password - for login*/
    private final String clientPassword;
    /** Client color - for writing in color */
    private final String clientColor;
    /** Chat friend */
    private final String friendName;

    /**
     * Constrcuts a new SimpleClient
     * @param clientName0 -
     * @param clientPassword0 -
     * @param clientColor0 -
     * @param friendName0 -
     */
    public SimpleClient() {
        super();
        this.clientName = "";
        this.clientPassword = "";
        this.clientColor = "";
        this.friendName = "";
    }

    public SimpleClient(String clientName0, String clientPassword0, String clientColor0, String friendName0) {
        super();
        this.clientName = clientName0;
        this.clientPassword = clientPassword0;
        this.clientColor = clientColor0;
        this.friendName = friendName0;
    }

    /**
     * Main process.
     */
    public void run() {
        //this.runConn();
        try {
            this.runImpl();
        } catch (XMPPException e) {
            System.err.println("Exception: " + e);
        }

    }

    public static void main(String[] args) {
        SimpleClient sc = new SimpleClient();
        sc.setupXMPPAndLogin();
        sc.sendMessage("Tralalalala YUUUUUUUUUUUHHHHHHHUUUUUUUU");
        sc.closeConn();
    }

    private void runConn() {
        ConnectionConfiguration cc = new ConnectionConfiguration("192.168.1.136", 5222, "centos");
        XMPPConnection connection = new XMPPConnection(cc);
        try {
            connection.connect();
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            // You have to specify your Jabber ID addres WITHOUT @jabber.org at the end
            connection.login("cesc", "cesc", "smackdown");
            // See if you are authenticated
            System.out.println(connection.isAuthenticated());

            final MessageListener messageListner = new SimpleMessageListner();
            final Chat chat = connection.getChatManager().createChat("theo", messageListner);


            final java.util.Date date = new java.util.Date(System.currentTimeMillis());
            final String msgSent = " [sent by " + this.clientName + ", at " + date.toString() + "]";


            final Message newMessage = new Message();
            newMessage.setBody("Howdy (colored)!" + msgSent);
            newMessage.setProperty("favoriteColor", this.clientColor);
            chat.sendMessage(newMessage);
            chat.sendMessage("Howdy!" + msgSent);

            System.out.println("Message has been sent");


        } catch (XMPPException e1) {
            e1.printStackTrace();
        }
    }

    private void runImpl() throws XMPPException {
        XMPPConnection.DEBUG_ENABLED = true;

        /*
         * Configuration
         */
        final ConnectionConfiguration config = new ConnectionConfiguration(
                SimpleClient.SERVER_NAME, SimpleClient.SERVER_PORT, "centos");
        //config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        //config.setCompressionEnabled(false);


        /*
         * Open Connection
         */
        final XMPPConnection connection = new XMPPConnection(config);
        connection.connect();
        SASLAuthentication.supportSASLMechanism("PLAIN", 0);
        connection.login(this.clientName, this.clientPassword);
        System.out.println("isAuthenticated : " + connection.isAuthenticated());

        /*
         * Chat
         */
        System.out.println("5");
        final MessageListener messageListner = new SimpleMessageListner();
        final Chat chat = connection.getChatManager().createChat(this.friendName, messageListner);

        final java.util.Date date = new java.util.Date(System.currentTimeMillis());
        final String msgSent = " [sent by " + this.clientName + ", at " + date.toString() + "]";

//        Message newMessage = new Message("YUUUUUUUUUUUHHHHHHHUUUUUUUU", Message.Type.chat);
//        newMessage.setBody("Howdy (colored)!" + msgSent);
//        //newMessage.setProperty("favoriteColor", this.clientColor);
//        chat.sendMessage(newMessage);
//        chat.sendMessage("Howdy!" + msgSent);

        Message msg = new Message();
        msg.setType(Message.Type.chat);
        msg.setTo("cesc@centos");
        msg.setBody("YUUUUUUUUUUUHHHHHHHUUUUUUUU");
        connection.sendPacket(msg);

        /*
         * Roster
         */
        final Roster roster = connection.getRoster();
        // this is already the default Mode but added just for the example
        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.accept_all);
        roster.createEntry(this.friendName, "", null);

        //this.presenceStatus(roster, "unknown@someone");
        this.presenceStatus(roster, this.friendName);

        final Collection<RosterEntry> entries = roster.getEntries();
        for (RosterEntry entry : entries) {
            System.out.println("RosterEntry " + entry);
        }

        final RosterListener rosterListener = new SimpleRosterListner();
        roster.addRosterListener(rosterListener);

    /*
     * End connection
     */
    //connection.disconnect();
    }

    private void presenceStatus(final Roster roster, String user) {
        final Presence presence = roster.getPresence(user);
        final String response = presence == null ? "Not around" : presence.getStatus();
        System.out.println("Presence of '" + user + "': " + response);
    }

    /**
     *
     * @author some
     *
     */
    private class SimpleMessageListner implements MessageListener {

        public void processMessage(Chat chat, Message message) {
            System.out.println("Received message: " + message.getBody());
        }
    }

    /**
     *
     * @author some
     *
     */
    private class SimpleRosterListner implements RosterListener {
        // Ignored events
        public void entriesAdded(Collection<String> addresses) {
        }

        public void entriesDeleted(Collection<String> addresses) {
        }

        public void entriesUpdated(Collection<String> addresses) {
        }

        public void presenceChanged(Presence presence) {
            System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
        }
    }
    private ConnectionConfiguration cc = null;
    private XMPPConnection conn = null;

    private void setupXMPPAndLogin() {
        try {

            cc = new ConnectionConfiguration("192.168.1.136", 5222, "centos");
            conn = new XMPPConnection(cc);

            System.out.println("Attempting to connect");
            conn.connect();
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            conn.login("cesc", "cesc");
            System.out.println("Logged In!");

            setListener();

            System.out.println("Listeners set");

        } catch (XMPPException xe) {
            System.out.println(xe.getMessage());
        }

    }

    private void setListener() {
        PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
        conn.addPacketListener(new PacketListener() {

            public void processPacket(Packet pkt) {
                Message m = (Message) pkt;
                if (m.getBody() != null) {
                    System.out.println("recv: " + m.getBody() + "\n");
                }
            }
        }, filter);

    }

    private void sendMessage(String data) {
        Message msg = new Message();
        msg.setType(Message.Type.chat);
        msg.setTo("denilson@centos");
        msg.setBody(data);
        //System.out.println("connection: " + conn);
        conn.sendPacket(msg);
        System.out.println("Packet Dispatched");
    }

    private void closeConn() {
        System.out.println("Logging Off!");
        conn.disconnect();
    }
}
