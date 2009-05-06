/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.jabber;

/**
 *
 * @author agustian
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.ou.kmi.buddyspace.core.BSCore;
import edu.ou.kmi.buddyspace.core.BSMessengerBean;
import edu.ou.kmi.buddyspace.xml.XEventBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jabber.jabberbeans.MessageBuilder;
import org.jabber.jabberbeans.util.JID;
import edu.ou.kmi.buddyspace.core.BSConnectionBean;
import edu.ou.kmi.buddyspace.core.BSLoginListener;
import org.jabber.jabberbeans.ConnectionEvent;
import org.jabber.jabberbeans.ConnectionListener;
import org.jabber.jabberbeans.InfoQuery;
import edu.ou.kmi.buddyspace.core.BSMessageListener;
import edu.ou.kmi.buddyspace.core.BSAuthEvent;
import edu.ou.kmi.buddyspace.core.BSAuthorizationBean;
import edu.ou.kmi.buddyspace.core.BSPresenceBean;
import edu.ou.kmi.buddyspace.core.BSPresenceInfo;
import edu.ou.kmi.buddyspace.core.BSPresenceListener;
import org.jabber.jabberbeans.Message;

/**
 *
 * @author aprayitn
 */
public class JabberTest implements ConnectionListener, BSLoginListener,
        BSMessageListener,
        BSPresenceListener {

    private BSMessengerBean messengerBean;
    private BSAuthorizationBean authorizationBean;
    private BSPresenceBean presenceBean;
    private JID jid;
    private BSCore core;

    public JabberTest() {

        core = new BSCore();
        core.addLoginListener(this);
        BSConnectionBean connectionBean = core.getConnectionBean();
        if (connectionBean != null) {
            connectionBean.addConnectionListener(this);
        }
        messengerBean = core.getMessengerBean();
        if (messengerBean != null) {
            messengerBean.addMessageListener(this);
        }
//            core.connect("trac", "tr4c1234", "TRAC", "localhost", 5222, 1, false);
        core.connect("theo", "theo", "TRAC", "192.168.1.136", 5222,
                1, false);

        presenceBean = core.getPresenceBean();
        presenceBean.addPresenceListener(this);

    }

    public void chatMessageReceived(Message msg) {
        jid = msg.getFromAddress();
        System.out.println((new StringBuilder("JIDStucture:")).append(jid.toString()).toString());
        String msgBody = msg.getBody();
        System.out.println((new StringBuilder("message Body:")).append(msgBody).toString());
    }

    public void groupchatMessageReceived(org.jabber.jabberbeans.Message message) {
    }

    public void headlineMessageReceived(org.jabber.jabberbeans.Message message) {
    }

    public void messageError(org.jabber.jabberbeans.util.JID jid,
            java.lang.String s, java.lang.String s1) {
    }

    public void messageError(org.jabber.jabberbeans.Message message) {
    }

    public void messageRead(org.jabber.jabberbeans.Message message) {
    }

    public void plainMessageReceived(org.jabber.jabberbeans.Message message) {
    }

    public void loginAuthorized() {
        try {
//            jid = new JID("aprayitn", "aprayitn-laptop", "TRAC");
            jid = new JID("cesc", "centos", "TRAC");

            edu.ou.kmi.buddyspace.xml.XEventBuilder eb = new XEventBuilder();
            eb.setIsComposing(true);
            MessageBuilder mb = new MessageBuilder();
            mb.setToAddress(jid);
            mb.setBody("Test");
//            mb.setThread(thread);

            mb.setFromAddress(org.jabber.jabberbeans.util.JID.fromString("CARREQUEST"));
            mb.setType("chat");
            mb.addExtension(eb.build());
            Message temp = (Message) mb.build();
            messengerBean.sendMessage(temp);
            Date timestamp = new Date();
            timestamp.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringBuilder Timestamp = new StringBuilder(sdf.format(timestamp));
            System.out.println("--->" + jid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginError(InfoQuery infoquery) {
    }

    public void loginError(String error) {
        if (error.compareToIgnoreCase("Not authenticated") != 0) {
            System.out.println((new StringBuilder("Error Login:")).append(error).toString());
        }
    }

    public void loginRegistered() {
    }

    public void connectionChanged(ConnectionEvent arg0) {
        if (ConnectionEvent.STATE_DISCONNECTED.getValue() == 0) {
            System.out.println((new StringBuilder("state Connection:")).append(ConnectionEvent.STATE_DISCONNECTED.getValue()).toString());

        }
    }

    public void authError(BSAuthEvent event) {
    }

    public void presencesCleared() {
    }

    public void subscriptionApproved(JID jid) {
        System.out.println("subscriptionApproved");
    }

    public void subscriptionRequested(JID jid) {
        System.out.println("subscriptionRequested: " + jid);
        presenceBean.sendSubscriptionApproved(jid);
    }

    public void presenceChanged(BSPresenceInfo ifo) {
    }

    public static void main(String[] args) {
        JabberTest j = new JabberTest();
    }
}




