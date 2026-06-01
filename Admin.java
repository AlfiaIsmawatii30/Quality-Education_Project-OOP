import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representasi dari pengguna dengan hak akses sebagai Administrator sistem.
 * Kelas ini mengelola data spesifik admin, memvalidasi materi pembelajaran, 
 * serta mengirimkan notifikasi khusus admin.
 * * @author Kelompok 4
 * @version 1.0
 */
public class Admin extends User implements Notifiable {

    /**
     * Kode unik identifikasi untuk setiap akun Admin.
     */
    private String idAdmin;

    /**
     * Konstruktor untuk membuat objek Admin baru dengan kredensial data tertentu.
     * * @param username nama unik pengguna untuk login admin.
     * @param password kata sandi akun admin.
     * @param idAdmin kode unik identitas admin.
     */
    public Admin(String username, String password, String idAdmin) {
        super(username, password);
        this.idAdmin = idAdmin;
    }

    /**
     * Mengambil data ID unik milik Admin.
     * * @return representasi string dari ID Admin.
     */
    public String getIdAdmin() {
        return idAdmin;
    }

    /**
     * Mengubah atau mengatur ulang nilai ID Admin.
     * * @param idAdmin ID Admin baru yang akan diterapkan.
     */
    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    /**
     * Mengambil peran (role) dari pengguna ini di dalam sistem.
     * Method ini merupakan bentuk Polimorfisme dari kelas abstrak {@link User}.
     * * @return string yang menyatakan peran sebagai "Admin Sistem".
     */
    @Override
    public String getRole() {
        return "Admin Sistem";
    }

    /**
     * Melakukan verifikasi apakah kata sandi yang diinputkan sesuai dengan data admin.
     * * @param input teks kata sandi yang dimasukkan oleh pengguna.
     * @return {@code true} jika kata sandi cocok, atau {@code false} jika salah.
     */
    @Override
    protected boolean verifyPassword(String input) {
        return super.verifyPassword(input);
    }

    /**
     * Mengirimkan pesan notifikasi khusus ke layar konsol Admin.
     * Method ini diimplementasikan dari interface {@link Notifiable}.
     * * @param judul topik utama atau judul dari notifikasi.
     * @param pesan isi informasi atau detail pesan notifikasi.
     */
    @Override
    public void kirimNotifikasi(String judul, String pesan) {
        System.out.println("[NOTIF -> ADMIN] " + getUsername()
                + " | " + judul + ": " + pesan);
    }

    /**
     * Melakukan validasi terhadap modul materi yang diajukan oleh instruktur.
     * Method ini akan memperbarui status validasi dan mencatat waktu eksekusinya secara otomatis.
     * * @param materi objek {@link Materi} yang akan diperiksa dan divalidasi oleh admin.
     */
    public void validasiMateri(Materi materi) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        materi.setValidasi("Telah divalidasi oleh " + getUsername());
        materi.setWaktuValidasi(LocalDateTime.now().format(dtf));
        System.out.println("Sistem: Materi '" + materi.getJudulMateri()
                + "' berhasil divalidasi.");
    }

    /**
     * Menampilkan informasi profil lengkap Admin ke layar konsol, 
     * meliputi ID Admin, Username, Peran, dan Status akun.
     */
    public void tampilkanProfil() {
        System.out.println("=== Profil Admin ===");
        System.out.println("ID Admin  : " + idAdmin);
        System.out.println("Username  : " + getUsername());
        System.out.println("Role      : " + getRole());
        System.out.println("Status    : Administrator");
        System.out.println("====================");
    }
}