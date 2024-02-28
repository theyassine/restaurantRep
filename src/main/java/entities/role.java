package entities;

public enum role {
    GERANT,
    LIVREUR;
    public static role setFromString(String role) {
        switch (role.toUpperCase()) {
            case "GERANT":
                return GERANT;
            case "LIVREUR":
                return LIVREUR;
            default:
                throw new IllegalArgumentException("role invalide : " +role );
        }
    }
}



