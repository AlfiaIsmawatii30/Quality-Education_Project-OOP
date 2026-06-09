public class Kaprodi extends User {
    private String kodeProdi;

    public Kaprodi(String username, String password, String kodeProdi) {
        super(username, password);
        this.kodeProdi = kodeProdi; 
    }

    public String getKodeProdi() {
        return kodeProdi;
    }

    public void setKodeProdi(String kodeProdi) {
        this.kodeProdi = kodeProdi;
    }

    @Override
    public String getRole() {
        return "Kaprodi";
    }

    public String tampilkanDetail() {
        return "Aktor: " + getRole() + " | Username: " + getUsername() + " | Prodi: " + this.kodeProdi;
    }
}