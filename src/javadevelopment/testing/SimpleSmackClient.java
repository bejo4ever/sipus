/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

import java.io.File;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

/**
 *
 * @author agustian
 */
/**
 *
 * @author ekakoll
 */
public class SimpleSmackClient {

    public XMPPConnection connection;
    private FileTransferManager manager;
    private static final Logger log = Logger.getLogger(SimpleSmackClient.class);

    //login to server
    public void login(String username, String password) throws XMPPException {
        XMPPConnection.DEBUG_ENABLED = true;
        ConnectionConfiguration config = new ConnectionConfiguration("host", 5222, "host");
        connection = new XMPPConnection(config);
        connection.connect();
        connection.login(username, password);
        manager = new FileTransferManager(connection);
    }

    public void sendMessage(String message, String destination) throws XMPPException {
        Chat chat = connection.getChatManager().createChat(destination,
                new MessageListener() {

                    public void processMessage(Chat c, Message message) {
                        System.out.println(c.getParticipant() + " says: " + message.getBody());
                    }
                });
        chat.sendMessage(message);
    }

    public void logout() {
        if (connection != null) {
            connection.disconnect();
        }
    }

    public void fileTransfer(String fileName, String destination) throws XMPPException {
        // Create the file transfer manager
        //FileTransferManager manager = new FileTransferManager(connection);
        FileTransferNegotiator.setServiceEnabled(connection, true);
        // Create the outgoing file transfer
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(destination);

        // Send the file
        transfer.sendFile(new File(fileName), "You won't believe this!");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
        }
        System.out.println("Status :: " + transfer.getStatus() + " Error :: " + transfer.getError() + " Exception :: " + transfer.getException());
        System.out.println("Is it done? " + transfer.isDone());
    }

    public void fileReceiver(final boolean accept, final String fileName) {
        // Create the file transfer manager
        //final FileTransferManager manager = new FileTransferManager(connection);
        // Create the listener
        manager.addFileTransferListener(new FileTransferListener() {

            public void fileTransferRequest(FileTransferRequest request) {
                // broadcast something here. Wheather users want to accept file
                // Check to see if the request should be accepted
                if (accept) {
                    // Accept it
                    IncomingFileTransfer transfer = request.accept();
                    try {
                        transfer.recieveFile(new File(fileName));
                        System.out.println("File " + fileName + "Received Successfully");
                    //InputStream input = transfer.recieveFile();
                    } catch (XMPPException ex) {
                        log.debug("Error: " + ex.getMessage());
                    }
                } else {
                    // Reject it
                    request.reject();
                }
            }
        });
    }
}
