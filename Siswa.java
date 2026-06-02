/**
 * Representasi dari entitas Siswa di dalam sistem pembelajaran digital.
 * Kelas ini merupakan turunan dari kelas abstrak {@link User} (Inheritance)
 * dan mengimplementasikan kontrak interface {@link Notifiable} agar dapat menerima
 * berbagai notifikasi aktivitas materi atau kuis dari sistem.
 * * @author Kelompok 4
 * @version 1.0
 */
public class Siswa extends User implements Notifiable {

    /**
     * ID unik sebagai pengenal identitas siswa di dalam sistem.
     */
    private String idSiswa;

    /**
     * Jenjang atau tingkatan kelas aktif dari siswa (misal: "pemula", "menengah", "10A").
     */
    private String tingkat;

    /**
     * Konstruktor untuk menginisialisasi objek Siswa baru dengan data kredensial 
     * dan informasi tingkatan kelas awal.
     * * @param username nama unik pengguna untuk keperluan autentikasi login.
     * @param password kata sandi rahasia untuk keamanan akun siswa.
     * @param idSiswa  kode unik identifikasi siswa.
     * @param tingkat  tingkatan kelas atau jenjang kompetensi siswa.
     */
    public Siswa(String username, String password, String idSiswa, String tingkat) {
        super(username, password);
        this.idSiswa = idSiswa;
        this.tingkat = tingkat;
    }

    /**
     * Mengambil kode unik identifikasi siswa.
     * * @return string berisi ID Siswa.
     */
    public String getIdSiswa() {
        return idSiswa;
    }

    /**
     * Memperbarui atau menetapkan ID unik siswa yang baru.
     * * @param idSiswa string berisi ID Siswa yang baru.
     */
    public void setIdSiswa(String idSiswa) {
        this.idSiswa = idSiswa;
    }

    /**
     * Mengambil informasi jenjang atau tingkatan kelas siswa.
     * * @return string berupa nama tingkatan kelas.
     */
    public String getTingkat() {
        return tingkat;
    }

    /**
     * Mengubah status jenjang atau tingkatan kelas aktif siswa.
     * * @param tingkat string nama tingkatan kelas yang baru.
     */
    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    /**
     * Mengambil informasi peran spesifik dari objek ini.
     * Method ini merupakan bentuk Polimorfisme yang melakukan override terhadap 
     * method abstrak di kelas induk {@link User}.
     * * @return string yang mendeskripsikan peran siswa beserta informasi tingkatannya.
     */
    @Override
    public String getRole() {
        return "Siswa (Tingkat: " + tingkat + ")";
    }

    /**
     * Memverifikasi keabsahan kata sandi yang diinput oleh pengguna.
     * Memanggil implementasi logika verifikasi pengekapsulan dari kelas induk.
     * * @param input teks kata sandi mentah yang dimasukkan saat login.
     * @return {@code true} jika kata sandi cocok, atau {@code false} jika salah.
     */
    @Override
    protected boolean verifyPassword(String input) {
        return super.verifyPassword(input);
    }

    /**
     * Menangani aksi penerimaan notifikasi sistem yang dikirimkan secara personal 
     * maupun massal. Merupakan implementasi dari kontrak interface {@link Notifiable}.
     * * @param judul subjek utama atau topik pemberitahuan.
     * @param pesan detail teks informasi notifikasi yang diterima.
     */
    @Override
    public void kirimNotifikasi(String judul, String pesan) {
        System.out.println("[NOTIF -> SISWA] " + getUsername()
                + " (Tingkat: " + tingkat + ") | " + judul + ": " + pesan);
    }

    /**
     * Menampilkan rangkuman profil data siswa secara terstruktur ke konsol terminal,
     * meliputi informasi ID, nama pengguna, jenjang tingkat, dan peran sistemnya.
     */
    public void tampilkanProfil() {
        System.out.println("=== Profil Siswa ===");
        System.out.println("ID Siswa  : " + idSiswa);
        System.out.println("Username  : " + getUsername());
        System.out.println("Tingkat   : " + tingkat);
        System.out.println("Role      : " + getRole());
        System.out.println("====================");
    }
}