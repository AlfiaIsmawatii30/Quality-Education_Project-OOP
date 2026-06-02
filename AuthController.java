import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler untuk mengatur sistem otentikasi pengguna (Authentication).
 * Kelas ini menangani proses pembuatan data pengguna awal, verifikasi login,
 * hingga sesi logout pada sistem pembelajaran digital.
 * * @author Kelompok 4
 * @version 1.0
 */
public class AuthController {

    /**
     * Daftar seluruh pengguna yang terdaftar di dalam sistem.
     */
    private List<User> userList = new ArrayList<>();

    /**
     * Daftar kata sandi (password) yang tersinkronisasi berdasarkan indeks pengguna.
     */
    private List<String> passwordList = new ArrayList<>();

    /**
     * Objek pengguna yang saat ini sedang aktif dalam sesi login.
     * Bernilai {@code null} jika tidak ada pengguna yang sedang masuk.
     */
    private User userLogin = null;

    /**
     * Konstruktor default untuk menginisialisasi kontroler otentikasi.
     * Secara otomatis membuat data tiruan (mock data) untuk akun Admin,
     * Instruktur, dan beberapa Siswa ke dalam sistem.
     */
    public AuthController() {
        userList.add(new Admin("admin", "ADM01", "ADM-01"));
        passwordList.add("ADM01");

        userList.add(new Instruktur("instruktur", "INS01", "INS-001"));
        passwordList.add("INS01");

        userList.add(new Siswa("siswa1", "SWA1", "SIS-001", "menengah"));
        passwordList.add("SWA1");

        userList.add(new Siswa("siswa2", "SWA2", "SIS-002", "pemula"));
        passwordList.add("SWA2");
    }

    /**
     * Melakukan proses verifikasi masuk (login) pengguna ke dalam sistem.
     * Method ini akan mencocokkan kombinasi nama pengguna dan kata sandi yang diinputkan
     * dengan data yang tersimpan di dalam daftar pengguna.
     * * @param username nama unik pengguna yang digunakan untuk mengidentifikasi akun.
     * @param password kata sandi yang sesuai dengan akun pengguna tersebut.
     * @return objek {@link User} yang berhasil login jika kredensial valid, 
     * atau {@code null} jika proses login gagal.
     */
    public User login(String username, String password) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)
                    && passwordList.get(i).equals(password)) {
                userLogin = userList.get(i);
                userLogin.login();
                return userLogin;
            }
        }
        System.out.println("Login gagal! Username atau password salah.");
        return null;
    }

    /**
     * Mengakhiri sesi pengguna yang saat ini sedang aktif (logout).
     * Jika sukses, status pengguna aktif akan dikembalikan menjadi {@code null}.
     */
    public void logout() {
        if (userLogin != null) {
            System.out.println(userLogin.getUsername() + " berhasil logout.");
            userLogin = null;
        } else {
            System.out.println("Tidak ada user yang sedang login.");
        }
    }

    /**
     * Mengambil data objek pengguna yang saat ini sedang aktif atau login.
     * * @return objek {@link User} yang sedang aktif, atau {@code null} jika kosong.
     */
    public User getUserLogin()      { return userLogin; }

    /**
     * Memeriksa apakah saat ini ada sesi pengguna yang sedang aktif di dalam sistem.
     * * @return {@code true} jika ada pengguna yang login, atau {@code false} jika tidak ada.
     */
    public boolean isLoggedIn()     { return userLogin != null; }

    /**
     * Mengambil seluruh daftar pengguna yang terdaftar di dalam sistem.
     * * @return kumpulan data pengguna berupa {@link List} dari objek {@link User}.
     */
    public List<User> getUserList() { return userList; }
}