package org.example.util;

import org.example.model.Session;

public class SessionManager {

    private static Session currentSession;

    private SessionManager() {
    }

    public static void startSession(Session session) {
        currentSession = session;
    }

    public static Session getCurrentSession() {
        return currentSession;
    }

    public static boolean isLoggedIn() {
        return currentSession != null;
    }

    public static void logout() {
        currentSession = null;
    }

    public static boolean isEmployee() {
        return isLoggedIn() &&
                currentSession.getRole().equalsIgnoreCase("EMPLOYEE");
    }

    public static boolean isManager() {
        return isLoggedIn() &&
                currentSession.getRole().equalsIgnoreCase("MANAGER");
    }

    public static boolean isAdmin() {
        return isLoggedIn() &&
                currentSession.getRole().equalsIgnoreCase("ADMIN");
    }
}