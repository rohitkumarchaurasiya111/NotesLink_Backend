package in.noteslink.models.enums;

public enum UserRole {
    FREE,
    PREMIUM,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isPremiumOrAbove() {
        return this == PREMIUM || this == ADMIN;
    }

}
