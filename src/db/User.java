/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import assets.classes.AlertDialogs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.BNBForAromaticOils;

/**
 *
 * @author AHMED
 */
public class User {

    int id;
    String name;
    String password;
    String role;

    public User() {
    }

    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkSignIn() {
        try {
            ResultSet rs = db.get.getReportCon().createStatement().executeQuery("select id,role from users where user_name='" + name + "' and password='" + password + "'");
            while (rs.next()) {
                this.id = rs.getInt(1);
                this.role = rs.getString(2);
              
                return true;
            }
            return false;
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        return false;
    }

    public boolean changePassword() {
        try {
            PreparedStatement Prepare = db.get.Prepare("UPDATE `users` SET `password`=? WHERE `id`=?");
            Prepare.setString(1, password);
            Prepare.setInt(2, id);
            Prepare.execute();
            return true;
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
            return false;
        }
    }

    public static boolean canAccess(String priviliages) {
        return true;
    }
 
}
