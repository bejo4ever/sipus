/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javadevelopment.app;

import javadevelopment.testing.ConfigProperties;

/**
 *
 * @author agustian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Start");
        System.out.println("---> "+ConfigProperties.getProperty("db_user"));
    }

}
