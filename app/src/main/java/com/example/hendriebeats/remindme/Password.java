package com.example.hendriebeats.remindme;

public class Password {
    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static int workload = 12;

    public static String getNextSalt() {

        return String.valueOf(System.nanoTime()%1000000)+String.valueOf(System.nanoTime()%1000000)+String.valueOf(System.nanoTime()%1000000)
                +String.valueOf(System.nanoTime()%1000000)+String.valueOf(System.nanoTime()%1000000);
    }
    
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }

    public static void main(String[] args) {}

}
