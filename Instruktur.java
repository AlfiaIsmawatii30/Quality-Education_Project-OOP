/**
 * Representasi dari pengguna dengan hak akses sebagai Instruktur (Pengajar).
 * Kelas ini mengelola data spesifik instruktur, mengimplementasikan pengiriman 
 * notifikasi pengajar, serta menampilkan profil terkait.
 * * @author Kelompok 4
 * @version 1.0
 */
public class Instruktur extends User implements Notifiable {

    /**
     * Kode unik identifikasi untuk setiap akun Instruktur.
     */
    private String idInstruktur;

    /**
     * Konstruktor untuk membuat objek Instruktur baru dengan kredensial data tertentu.
     * * @param username nama unik pengguna untuk login instruktur.
     * @param password kata sandi akun instruktur.
     * @param idInstruktur kode unik identitas pengajar.
     */
    public Instruktur(String username, String password, String idInstruktur) {
        super(username, password);
        this.idInstruktur = idInstruktur;
    }

    /**
     * Mengambil data ID unik milik Instruktur.
     * * @return representasi string dari ID Instruktur.
     */
    public String getIdInstruktur() {
        return idInstruktur;
    }

    /**
     * Mengubah atau mengatur ulang nilai ID Instruktur.
     * * @param idInstruktur ID Instruktur baru yang akan diterapkan.
     */
    public void setIdInstruktur(String idInstruktur) {
        this.idInstruktur = idInstruktur;
    }

    /**
     * Mengambil peran (role) dari pengguna ini di dalam sistem.
     * Method ini merupakan bentuk Polimorfisme dari kelas abstrak {@link User}.
     * * @return string yang menyatakan peran sebagai "Instruktur".
     */
    @Override
    public String getRole() {
        return "Instruktur";
    }

    /**
     * Melakukan verifikasi apakah kata sandi yang diinputkan sesuai dengan data instruktur.
     * * @param input teks kata sandi yang dimasukkan oleh pengguna.
     * @return {@code true} jika kata sandi cocok, atau {@code false} jika salah.
     */
    @Override
    protected boolean verifyPassword(String input) {
        return super.verifyPassword(input);
    }

    /**
     * Mengirimkan pesan notifikasi khusus ke layar konsol Instruktur.
     * Method ini diimplementasikan dari interface {@link Notifiable}.
     * * @param judul topik utama atau judul dari notifikasi.
     * @param pesan isi informasi atau detail pesan notifikasi.
     */
    @Override
    public void kirimNotifikasi(String judul, String pesan) {
        System.out.println("[NOTIF -> INSTRUKTUR] " + getUsername()
                + " | " + judul + ": " + pesan);
    }

    /**
     * Menampilkan informasi profil lengkap Instruktur ke layar konsol, 
     * meliputi ID Instruktur, Username, Peran, dan Status keaktifan akun.
     */
    public void tampilkanProfil() {
        System.out.println("=== Profil Instruktur ===");
        System.out.println("ID Instruktur : " + idInstruktur);
        System.out.println("Username      : " + getUsername());
        System.out.println("Role          : " + getRole());
        System.out.println("Status        : Aktif sebagai Instruktur");
        System.out.println("=========================");
    }
}