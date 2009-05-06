/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javadevelopment.testing;

/**
 *
 * @author agustian
 */
public class ClientSmackThread extends Thread {

    private ConnectionConfiguration cc = null;
    private XMPPConnection conn = null;
    public ClientSmackThread() {
        setupXMPPAndLogin();
    }
    @Override
    public void run() {
        try {
            while (true) {
                
            }
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted: " + e.getMessage());
            //reconnect();
        }
    }
    
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
    
}
