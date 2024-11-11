package com.utec.pdtasur.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashContraseña {
    public static boolean comparePassword(String password, String hash) {
        BCrypt Bcrypt = null;
        return Bcrypt.checkpw(password, hash);
    }
    public static String hashPassword(String password) {
        BCrypt Bcrypt = null;
        return Bcrypt.hashpw(password, Bcrypt.gensalt());
    }

}
