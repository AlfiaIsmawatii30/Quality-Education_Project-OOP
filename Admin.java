import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Admin extends User implements Notifiable {
    private String idAdmin;
 
    public Admin(String username, String password, String idAdmin) {
        super(username, password);
        this.idAdmin = idAdmin;
    }
 
    @Override
    public void kirimNotifikasi(String judul, String pesan) {
        System.out.println("[NOTIF → ADMIN] " + getUsername()
            + " | " + judul + ": " + pesan);
    }
 
    @Override
    public String getRole() {
        return "Admin Sistem";
    }
 
    public void validasiMateri(Materi materi) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        materi.setValidasi("Telah divalidasi oleh " + getUsername());
        materi.setWaktuValidasi(LocalDateTime.now().format(dtf));
        System.out.println("Sistem: Materi '" + materi.getJudulMateri()
            + "' berhasil divalidasi.");
    }
 
    public void tampilkanProfil() {
        System.out.println("Profil Admin");
        System.out.println("ID Admin  : " + idAdmin);
        System.out.println("Username  : " + getUsername());
        System.out.println("Status    : Administrator");
        System.out.println("--------------------");
    }
}