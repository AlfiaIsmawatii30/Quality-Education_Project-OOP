/**
 * Kelas abstrak yang berfungsi sebagai cetakan dasar (Superclass) bagi seluruh 
 * entitas pengguna di dalam sistem pembelajaran digital.
 * Kelas ini menerapkan konsep enkapsulasi data kredensial, menyediakan metode 
 * otentikasi dasar, serta mewajibkan setiap kelas turunan (Subclass) untuk 
 * mendefinisikan peran spesifiknya melalui metode abstrak.
 * * @author Kelompok 4
 * @version 1.0
 */
public abstract class User {

    /**
     * Nama unik pengguna yang digunakan sebagai identitas saat masuk ke sistem.
     */
    private String username;

    /**
     * Kata sandi rahasia yang digunakan untuk memverifikasi keamanan akun pengguna.
     */
    private String password;

    /**
     * Konstruktor untuk menginisialisasi data kredensial dasar dari pengguna baru.
     * * @param username nama unik identitas pengguna.
     * @param password kata sandi keamanan akun.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Mengambil nama unik pengguna (Username).
     * * @return string berisi nama pengguna.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Mengubah atau memperbarui nama unik pengguna (Username).
     * * @param username nama pengguna baru yang akan diterapkan.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Mengambil data kata sandi rahasia pengguna.
     * * @return string berisi kata sandi.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Mengubah atau memperbarui kata sandi keamanan akun.
     * * @param password kata sandi baru yang akan diterapkan.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Memverifikasi kecocokan kata sandi yang diinput oleh sistem dengan kata sandi asli.
     * Akses diatur sebagai {@code protected} agar hanya dapat divalidasi oleh internal 
     * sistem atau kelas turunannya sendiri (Encapsulation).
     * * @param input teks kata sandi mentah hasil inputan pengguna pada form login.
     * @return {@code true} jika input kata sandi cocok dengan memori, atau {@code false} jika salah.
     */
    protected boolean verifyPassword(String input) {
        return this.password.equals(input);
    }

    /**
     * Menampilkan pesan konfirmasi keberhasilan proses otentikasi masuk ke layar konsol terminal (CLI),
     * dengan melacak peran dinamis secara polimorfik dari objek yang sedang aktif.
     */
    public void login() {
        System.out.println(username + " berhasil login sebagai " + getRole() + ".");
    }

    /**
     * Mengambil informasi peran khusus (Role) dari jenis pengguna yang aktif.
     * Merupakan method abstrak yang wajib di-override oleh seluruh kelas turunan konkret 
     * (seperti Admin, Instruktur, Siswa) untuk memenuhi pilar Polimorfisme.
     * * @return string deskripsi peran spesifik pengguna.
     */
    public abstract String getRole();
}