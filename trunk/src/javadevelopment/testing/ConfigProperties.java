package javadevelopment.testing;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author agustian
 */
public final class ConfigProperties {

    private static Properties props;
    private static String propertiesFile = "config.properties";
    

    static {
        if (props == null) {
            props = new Properties();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(propertiesFile);
            props.load(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File - " + propertiesFile + " not found. Messages: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("Error handled: " + e.getMessage());
        }
    }

    public static String getProperty(
            String key) {
        String value = props.getProperty(key);
        if (value == null) {
            System.out.println("Warning: attempt to get an non-existant value in property file: " + key);
        }

        return value;
    }
    public static void main(String args[]) {
        System.out.println("---> "+ConfigProperties.getProperty("db_user"));
    }
}
