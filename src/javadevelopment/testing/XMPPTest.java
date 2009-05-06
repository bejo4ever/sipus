/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

/**
 *
 * @author agustian
 */
public class XMPPTest extends Applet implements ActionListener {

    ConnectionConfiguration cc = null;
    XMPPConnection conn = null;
    Button connect, disconnect, send;
    TextArea taRecv;
    TextField taSend;
    Button sf;

    public void init() {
        connect = new Button("Connect");
        disconnect = new Button("Disconnect");
        send = new Button("Send");
        taSend = new TextField(20);
        taRecv = new TextArea("", 5, 30);

        sf = new Button("SendFile");

        add(connect);
        add(disconnect);
        add(send);
        add(taSend);
        add(taRecv);
        add(sf);

        connect.addActionListener(this);
        disconnect.addActionListener(this);
        send.addActionListener(this);
        sf.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        String str = ae.getActionCommand();
        if (str.equals("Connect")) {
            setupXMPPAndLogin();
        }
        if (str.equals("Disconnect")) {
            closeConn();
        }
        if (str.equals("Send")) {
            sendMessage(taSend.getText());
            taRecv.append("me: " + taSend.getText() + "\n");
            taSend.setText("");
        }
        if (str.equals("SendFile")) {
            sendFile();
        }
    }

    void setupXMPPAndLogin() {
        try {

            cc = new ConnectionConfiguration("jabber.org", 5222);
            conn = new XMPPConnection(cc);

            showStatus("Attempting to connect");
            conn.connect();
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            conn.login("username", "password");
            showStatus("Logged In!");

            setListener();

            showStatus("Listeners set");

        } catch (XMPPException xe) {
            System.out.println(xe.getMessage());
        }

    }

    void setListener() {
        PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
        conn.addPacketListener(new PacketListener() {

            public void processPacket(Packet pkt) {
                Message m = (Message) pkt;
                if (m.getBody() != null) {
                    taRecv.append("recv: " + m.getBody() + "\n");
                }
            }
        }, filter);

    }

    void sendMessage(String data) {
        Message msg = new Message();
        msg.setType(Message.Type.chat);
        msg.setTo("recv.xmpp@jabber.org");
        msg.setBody(data);
        conn.sendPacket(msg);
        showStatus("Packet Dispatched");
    }

    void closeConn() {
        showStatus("Logging Off!");
        conn.disconnect();
    }

    void sendFile() {
        FileTransferManager ftm = new FileTransferManager(conn);
        OutgoingFileTransfer otf = ftm.createOutgoingFileTransfer("recv.xmpp@jabber.o rg");


        try {

            otf.sendFile(new File("test.txt"), "This is a test");
            showStatus("File Transfer started");

        } catch (XMPPException xe) {
            System.out.println(xe.getLocalizedMessage());
        }
    }
}
