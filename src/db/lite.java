/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import assets.classes.AlertDialogs;
import static assets.classes.statics.DATABASE_LOCATION;
import static assets.classes.statics.DATABASE_LOCATION_DEFAULT;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.BNBForAromaticOils;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

/**
 *
 * @author ahmed
 */
public class lite {

    Connection connection;
    private static String url = "";
    private static Connection con;

    public lite() {

    }

    private static void setURL() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(BNBForAromaticOils.class);
        String get = prefs.get(DATABASE_LOCATION, DATABASE_LOCATION_DEFAULT);
        File a = new File(get);
        boolean file = a.isFile();
        if (file) {
            url = "jdbc:sqlite:" + prefs.get(DATABASE_LOCATION, DATABASE_LOCATION_DEFAULT);

        } else {
            throw new Exception("error");
        }
    }

    public static boolean setConnection() {

        try {
            setURL();
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            if (con == null) {
                return false;
            }
            System.out.println("db.lite.setConnection()");
            try {

                ResultSet executeQuery = con.createStatement().executeQuery("select * from users");
                while (executeQuery.next()) {
                    // executeQuery.getString(1);
                }
            } catch (SQLException e) {  return false;
                
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;

        }

    }

    public static Connection getCon() {
        try {
            if (con == null) {
                setConnection();
            } else {
                return con;
            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        return con;
    }

    public static boolean addToData(String sql) {
        try {

            Statement st = con.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            AlertDialogs.showErrors(ex);
            return false;
        }

    }

    public static boolean excuteNon(String sql) {
        try {
            Statement st = con.createStatement();
            boolean execute = st.execute(sql);System.out.println(execute);System.out.println("ok");
            return true;
        } catch (SQLException ex) {
            AlertDialogs.showErrors(ex);
            return false;
        }

    }
    static String tables = " "
            + " "
            + "--"
            + "-- File generated with SQLiteStudio v3.2.1 on Sat Apr 24 02:02:53 2021"
            + "--"
            + "-- Text encoding used: System"
            + "--"
            + "PRAGMA foreign_keys = off;"
            + "BEGIN TRANSACTION;"
            + ""
            + "-- Table: att_attendance"
            + "DROP TABLE IF EXISTS att_attendance;"
            + ""
            + "CREATE TABLE att_attendance ("
            + "    id          INTEGER  PRIMARY KEY AUTOINCREMENT,"
            + "    employee_id INT (11) NOT NULL,"
            + "    date        DATE     NOT NULL,"
            + "    time        TIME     NOT NULL,"
            + "    statue      STRING   DEFAULT from_device"
            + ");"
            + ""
            + ""
            + "-- Table: att_department"
            + "DROP TABLE IF EXISTS att_department;"
            + ""
            + "CREATE TABLE att_department ("
            + "    id              INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name            VARCHAR (700) NOT NULL,"
            + "    organization_id INT (11)      NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_device_users"
            + "DROP TABLE IF EXISTS att_device_users;"
            + ""
            + "CREATE TABLE att_device_users ("
            + "    uid        INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name       VARCHAR (200) NOT NULL,"
            + "    privileges VARCHAR (200) NOT NULL,"
            + "    password   VARCHAR (200) NOT NULL,"
            + "    groupId    VARCHAR (200) NOT NULL,"
            + "    userId     VARCHAR (200) NOT NULL,"
            + "    card       VARCHAR (200) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_employee"
            + "DROP TABLE IF EXISTS att_employee;"
            + ""
            + "CREATE TABLE att_employee ("
            + "    id         INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name       VARCHAR (700) NOT NULL,"
            + "    tele       VARCHAR (700) DEFAULT NULL,"
            + "    adress     VARCHAR (700) DEFAULT NULL,"
            + "    age        VARCHAR (700) DEFAULT NULL,"
            + "    salary     VARCHAR (700) DEFAULT NULL,"
            + "    section_id INT (11)      DEFAULT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_employee_shifts"
            + "DROP TABLE IF EXISTS att_employee_shifts;"
            + ""
            + "CREATE TABLE att_employee_shifts ("
            + "    employee_id INT (11) NOT NULL,"
            + "    shift_id    INT (11) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_employee_solfa"
            + "DROP TABLE IF EXISTS att_employee_solfa;"
            + ""
            + "CREATE TABLE att_employee_solfa ("
            + "    id     INTEGER PRIMARY KEY,"
            + "    emp_id INTEGER,"
            + "    acc_id,"
            + "    amount STRING,"
            + "    date   DATE"
            + ");"
            + ""
            + ""
            + "-- Table: att_holiday"
            + "DROP TABLE IF EXISTS att_holiday;"
            + ""
            + "CREATE TABLE att_holiday ("
            + "    id   INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name VARCHAR (700) NOT NULL,"
            + "    date DATE          NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_late_rule"
            + "DROP TABLE IF EXISTS att_late_rule;"
            + ""
            + "CREATE TABLE att_late_rule ("
            + "    id       INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    shift_id INT (11)      NOT NULL,"
            + "    rule     VARCHAR (700) NOT NULL,"
            + "    [action] VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_leave_master"
            + "DROP TABLE IF EXISTS att_leave_master;"
            + ""
            + "CREATE TABLE att_leave_master ("
            + "    id         INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name       VARCHAR (700) NOT NULL,"
            + "    max_num    VARCHAR (700) NOT NULL,"
            + "    paied_type VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_machines"
            + "DROP TABLE IF EXISTS att_machines;"
            + ""
            + "CREATE TABLE att_machines ("
            + "    id                    INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name                  VARCHAR (700) NOT NULL,"
            + "    ip                    VARCHAR (700) NOT NULL,"
            + "    port                  VARCHAR (700) NOT NULL,"
            + "    commincation_password VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_organization"
            + "DROP TABLE IF EXISTS att_organization;"
            + ""
            + "CREATE TABLE att_organization ("
            + "    id       INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name     VARCHAR (700) NOT NULL,"
            + "    location VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_overtime"
            + "DROP TABLE IF EXISTS att_overtime;"
            + ""
            + "CREATE TABLE att_overtime ("
            + "    id          INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name        VARCHAR (700) NOT NULL,"
            + "    start_after VARCHAR (700) NOT NULL,"
            + "    calc_type   VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_report"
            + "DROP TABLE IF EXISTS att_report;"
            + ""
            + "CREATE TABLE att_report ("
            + "    id          INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    emp_id      INT (11)      NOT NULL,"
            + "    emp_name    VARCHAR (700) NOT NULL,"
            + "    date        DATE          NOT NULL,"
            + "    shift_name  VARCHAR (700) DEFAULT NULL,"
            + "    shift_start TIME          DEFAULT NULL,"
            + "    shift_end   TIME          DEFAULT NULL,"
            + "    emp_att     TIME          DEFAULT NULL,"
            + "    emp_leave   TIME          DEFAULT NULL,"
            + "    overtime    VARCHAR (700) DEFAULT NULL,"
            + "    late        VARCHAR (700) DEFAULT NULL,"
            + "    earlyLeave  VARCHAR (700) DEFAULT NULL,"
            + "    salary_calc VARCHAR (700) DEFAULT NULL,"
            + "    statue      VARCHAR (700) DEFAULT NULL,"
            + "    notes       VARCHAR (700) DEFAULT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_section"
            + "DROP TABLE IF EXISTS att_section;"
            + ""
            + "CREATE TABLE att_section ("
            + "    id            INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name          VARCHAR (700) NOT NULL,"
            + "    department_id INT (11)      NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_shifts"
            + "DROP TABLE IF EXISTS att_shifts;"
            + ""
            + "CREATE TABLE att_shifts ("
            + "    id              INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name            VARCHAR (700) NOT NULL,"
            + "    overtime_id     INT (11)      NOT NULL,"
            + "    working_days_id INT (11)      NOT NULL,"
            + "    start_time      VARCHAR (700) NOT NULL,"
            + "    end_time        VARCHAR (700) NOT NULL,"
            + "    in_same_day     VARCHAR (700) NOT NULL,"
            + "    late_time       VARCHAR (700) NOT NULL,"
            + "    early_time      VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: att_target_devices"
            + "DROP TABLE IF EXISTS att_target_devices;"
            + ""
            + "CREATE TABLE att_target_devices ("
            + "    device_id INTEGER"
            + ");"
            + ""
            + ""
            + "-- Table: att_working_days"
            + "DROP TABLE IF EXISTS att_working_days;"
            + ""
            + "CREATE TABLE att_working_days ("
            + "    id       INTEGER       PRIMARY KEY AUTOINCREMENT,"
            + "    name     VARCHAR (700) NOT NULL,"
            + "    holidays VARCHAR (700) NOT NULL"
            + ");"
            + ""
            + ""
            + "-- Table: priviliges_name"
            + "DROP TABLE IF EXISTS priviliges_name;"
            + ""
            + "CREATE TABLE priviliges_name ("
            + "    name STRING"
            + ");"
            + ""
            + ""
            + "-- Table: static_values"
            + "DROP TABLE IF EXISTS static_values;"
            + ""
            + "CREATE TABLE static_values ("
            + "    attribute STRING,"
            + "    value     STRING"
            + ");"
            + ""
            + ""
            + "-- Table: users"
            + "DROP TABLE IF EXISTS users;"
            + ""
            + "CREATE TABLE users ("
            + "    id       INTEGER       PRIMARY KEY,"
            + "    name     VARCHAR (500),"
            + "    password STRING,"
            + "    role     STRING"
            + ");"
            + ""
            + "INSERT INTO users ("
            + "                      id,"
            + "                      name,"
            + "                      password,"
            + "                      role"
            + "                  )"
            + "                  VALUES ("
            + "                      1,"
            + "                      'admin',"
            + "                      'admin',"
            + "                      'super_admin'"
            + "                  );"
            + ""
            + ""
            + "-- Table: users_permissions"
            + "DROP TABLE IF EXISTS users_permissions;"
            + ""
            + "CREATE TABLE users_permissions ("
            + "    user_id    INT,"
            + "    privileges STRING,"
            + "    value      STRING"
            + ");"
            + ""
            + ""
            + "COMMIT TRANSACTION;"
            + "PRAGMA foreign_keys = on;";
}
