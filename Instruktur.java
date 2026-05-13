public class Instruktur extends User implements Notifiable {
    private String idInstruktur;
 
    public Instruktur(String username, String password, String idInstruktur) {
        super(username, password);
        this.idInstruktur = idInstruktur;
    }
 
    @Override
    public void kirimNotifikasi(String judul, String pesan) {
        System.out.println("[NOTIF → INSTRUKTUR] " + getUsername()
            + " | " + judul + ": " + pesan);
    }
 
    @Override
    public String getRole() {
        return "Instruktur";
    }
 
    public void tampilkanProfil() {
        System.out.println("Profil Instruktur");
        System.out.println("ID Instruktur : " + idInstruktur);
        System.out.println("Username      : " + getUsername());
        System.out.println("Status        : Aktif sebagai Instruktur");
        System.out.println("--------------------");
    }
}
 