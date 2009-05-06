/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.jabber;

import edu.ou.kmi.buddyspace.core.BSConnectionBean;
import edu.ou.kmi.buddyspace.core.BSCore;
import edu.ou.kmi.buddyspace.core.BSLoginListener;
import edu.ou.kmi.buddyspace.core.BSMessageListener;
import edu.ou.kmi.buddyspace.core.BSMessengerBean;
import edu.ou.kmi.buddyspace.core.BSPresenceBean;
import edu.ou.kmi.buddyspace.core.BSPresenceInfo;
import edu.ou.kmi.buddyspace.core.BSPresenceListener;
import org.apache.log4j.Logger;
import org.jabber.jabberbeans.ConnectionEvent;
import org.jabber.jabberbeans.ConnectionListener;
import org.jabber.jabberbeans.InfoQuery;
import org.jabber.jabberbeans.Message;
import org.jabber.jabberbeans.util.JID;

/**
 *
 * @author agustian
 */
public class JabberPeer implements ConnectionListener, BSLoginListener, BSMessageListener, BSPresenceListener {

    private static final Logger log = Logger.getLogger(JabberPeer.class);
    private BSMessengerBean messengerBean;
    private BSPresenceBean presenceBean;
    private BSCore core;
    private String username;
    private String password;
    private String buddy;
    private String serverXmpp;
    private static final int portXmpp = 5222;

    public JabberPeer(String username, String password, String buddy, String server) {
        this.username = username;
        this.password = password;
        this.buddy = buddy;
        this.serverXmpp = server;
    }

    public void connectToServer() {
        core.addLoginListener(this);
        BSConnectionBean connectionBean = core.getConnectionBean();
        if (connectionBean != null) {
            connectionBean.addConnectionListener(this);
        }
        messengerBean = core.getMessengerBean();
        if (messengerBean != null) {
            messengerBean.addMessageListener(this);
        }
        //"192.168.1.136"
        core.connect(username, password, buddy, serverXmpp, portXmpp, 1, false);

        presenceBean = core.getPresenceBean();
        presenceBean.addPresenceListener(this);
    }

    public void connectionChanged(ConnectionEvent arg0) {
        if (ConnectionEvent.STATE_DISCONNECTED.getValue() == 0) {
            log.debug((new StringBuilder("state Connection:")).append(ConnectionEvent.STATE_DISCONNECTED.getValue()).toString());
        }
    }

    public void loginError(InfoQuery arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loginError(String error) {
        if (error.compareToIgnoreCase("Not authenticated") != 0) {
            log.debug((new StringBuilder("Error Login:")).append(error).toString());
        }
    }

    public void loginRegistered() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loginAuthorized() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void plainMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void chatMessageReceived(Message msg) {
        JID jid = msg.getFromAddress();
        log.debug((new StringBuilder("JIDStucture:")).append(jid.toString()).toString());
        String msgBody = msg.getBody();
        log.debug((new StringBuilder("message Body:")).append(msgBody).toString());
        //validateMessage()
    }

    public void groupchatMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void headlineMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageRead(Message msg) {
        log.debug("Message Read: " + msg.getSubject());
    }

    public void messageError(JID arg0, String arg1, String arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageError(Message msg) {
        log.debug("MessageError: " + msg.getErrorText());
    }

    public void presenceChanged(BSPresenceInfo psi) {
        log.debug("PresenceChanged: " + psi.getStatus());
    }

    public void subscriptionRequested(JID jid) {
        log.debug("SubscriptionRequested: " + jid);
        presenceBean.sendSubscriptionApproved(jid);
    }

    public void subscriptionApproved(JID arg0) {
        log.debug("SubscriptionApproved");
    }

    public void presencesCleared() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
