package org.example.entities;

public enum role {
    GERANT,
    LIVREUR,
    CLIENT;
    public static role setFromString(String role) {
        switch (role.toUpperCase()) {
            case "GERANT":
                return GERANT;
            case "LIVREUR":
                return LIVREUR;
            case "CLIENT":
                return CLIENT;
            default:
                throw new IllegalArgumentException("role invalide : " +role );
        }
    }
}
