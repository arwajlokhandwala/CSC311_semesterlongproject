package service;

import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;

public class UserSession {

    private static UserSession instance;

    private String userName;

    private String password;
    private String privileges;

    private static final Object lock = new Object();

    private UserSession(String userName, String password, String privileges) {
        this.userName = userName;
        this.password = password;
        this.privileges = privileges;
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("USERNAME", userName);
        userPreferences.put("PASSWORD", password);
        userPreferences.put("PRIVILEGES", privileges);
    }


    public static UserSession getInstace(String userName, String password, String privileges) {
        synchronized (lock) {
            if (instance == null) {
                instance = new UserSession(userName, password, privileges);
            }
            return instance;
        }
    }

    public static UserSession getInstace(String userName, String password) {
        synchronized (lock) {
            if (instance == null) {
                instance = new UserSession(userName, password, "NONE");
            }
            return instance;
        }
    }

    public String getUserName() {
        synchronized (lock) {
            return this.userName;
        }
    }

    public String getPassword() {
        synchronized (lock) {
            return this.password;
        }
    }

    public String getPrivileges() {
        synchronized (lock) {
            return this.privileges;
        }
    }

    public void cleanUserSession() {
        synchronized (lock) {
            this.userName = "";// or null
            this.password = "";
            this.privileges = "";// or null
        }
    }

    @Override
    public String toString() {
        synchronized (lock) {
            return "UserSession{" +
                    "userName='" + this.userName + '\'' +
                    ", privileges=" + this.privileges +
                    '}';
        }
    }
}
