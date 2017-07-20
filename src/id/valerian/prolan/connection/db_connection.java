/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.valerian.prolan.connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Valerian
 */
public class db_connection {

    private static Connection mysqlconfig;
    public static Properties mypanel, myLanguage;
    private static String strNamePanel;
    private static String url, pass;
    private static String urlDatabaseConfig = "src/id/valerian/prolan/connection/database.ini";
    // "./database.ini" untuk yang debug
    static File file = new File(urlDatabaseConfig);

    public static String settingPanel(String nmPanel) {
        try {
            mypanel = new Properties();
            mypanel.load(new FileInputStream(urlDatabaseConfig));
            strNamePanel = mypanel.getProperty(nmPanel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Koneksi ke File Konfigurasi Database Gagal", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return strNamePanel;
    }

    public static Connection configDB() throws SQLException {
        try {
            url = settingPanel("DBDatabase");
            String user = settingPanel("DBUsername"); //user admin database
            pass = settingPanel("DBPassword"); //pass admin db
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = (Connection) DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi ke Database Gagal", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return mysqlconfig;
    }

    public static void checkFile() {
        if (!file.exists()) {
            setDataConfig("3306", "sekolah", "root", "");//default database
        }
    }

    public static void setDataConfig(String port, String db, String username, String password) {
        try {
            file.createNewFile();
            PrintWriter writern = new PrintWriter(urlDatabaseConfig);
            writern.println("#‎setting‬ JDBC\r\n"
                    + "‪#‎getDriver‬ JDBC\r\n"
                    + "DBDriver=com.mysql.jdbc.Driver\r\n"
                    + "‪#‎getDatabase‬\r\n"
                    + "DBDatabase=jdbc:mysql://localhost:" + port + "/" + db + "\r\n"
                    + "‪#getPort\r\n"
                    + "DBPort="+port+ "\r\n"
                    + "‪#getNameDatabase\r\n"
                    + "DBName="+db+ "\r\n"
                    + "#setting username\r\n"
                    + "DBUsername=" + username + "\r\n"
                    + "#setting password\r\n"
                    + "DBPassword=" + password);
            writern.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Koneksi ke File Konfigurasi Database Gagal", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
