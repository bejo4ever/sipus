/*
 * BSFrame.java
 *
 * Created on April 27, 2009, 3:59 PM
 */
package javadevelopment.testing.testing2;

import edu.ou.kmi.buddyspace.core.BSConnectionBean;
import edu.ou.kmi.buddyspace.core.BSCore;
import edu.ou.kmi.buddyspace.core.BSLoginListener;
import edu.ou.kmi.buddyspace.core.BSMessageListener;
import edu.ou.kmi.buddyspace.core.BSMessengerBean;
import edu.ou.kmi.buddyspace.core.BSPresenceBean;
import edu.ou.kmi.buddyspace.core.BSPresenceInfo;
import edu.ou.kmi.buddyspace.core.BSPresenceListener;
import edu.ou.kmi.buddyspace.xml.XEventBuilder;
import org.jabber.jabberbeans.ConnectionEvent;
import org.jabber.jabberbeans.ConnectionListener;
import org.jabber.jabberbeans.InfoQuery;
import org.jabber.jabberbeans.Message;
import org.jabber.jabberbeans.MessageBuilder;
import org.jabber.jabberbeans.util.JID;

/**
 *
 * @author  agustian
 */
public class BSFrame extends javax.swing.JFrame implements ConnectionListener, BSLoginListener,
        BSMessageListener,
        BSPresenceListener {

    private BSMessengerBean messengerBean;
    private BSPresenceBean presenceBean;
    private JID jid;
    private BSCore core;

    /** Creates new form BSFrame */
    public BSFrame() {
        initComponents();
        initConnection();
    }

    private void initConnection() {
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAction(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jButton1)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jButton1)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void butAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAction
    sendMessage();
}//GEN-LAST:event_butAction

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BSFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

    public void connectionChanged(ConnectionEvent arg0) {
        if (ConnectionEvent.STATE_DISCONNECTED.getValue() == 0) {
            System.out.println((new StringBuilder("state Connection:")).append(ConnectionEvent.STATE_DISCONNECTED.getValue()).toString());

        }
    }

    public void loginError(InfoQuery arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loginError(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loginRegistered() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loginAuthorized() {
        System.out.println("LoginAuthorized");
    }
    private void sendMessage() {
        jid = new JID("cesc", "centos", "TRAC");
        
        try {
            XEventBuilder eb = new XEventBuilder();
            eb.setIsComposing(true);
            MessageBuilder mb = new MessageBuilder();
            mb.setToAddress(jid); 
            mb.setBody("Kirim pesan ke kamu ni");
            mb.setType("chat");
            mb.addExtension(eb.build());
            Message temp = (Message) mb.build();
            messengerBean.sendMessage(temp);
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void plainMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void chatMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void groupchatMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void headlineMessageReceived(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageRead(Message arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageError(JID arg0, String arg1, String arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void messageError(Message arg0) {
        
    }

    public void presenceChanged(BSPresenceInfo arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void subscriptionRequested(JID arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void subscriptionApproved(JID arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void presencesCleared() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
