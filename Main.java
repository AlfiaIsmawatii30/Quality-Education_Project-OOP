import javax.swing.SwingUtilities;

/**
 * Kelas utama (Entry Point) untuk menjalankan demonstrasi sistem pembelajaran digital.
 * Kelas ini mendemonstrasikan implementasi pilar-pilar Object-Oriented Programming (OOP)
 * seperti Pewarisan (Inheritance), Enkapsulasi (Encapsulation), Polimorfisme (Polymorphism),
 * serta Abstraksi menggunakan kelas dan interface. Di akhir demo, kelas ini memicu 
 * peluncuran antarmuka GUI utama.
 * * @author Kelompok 4
 * @version 1.0
 */
public class Main {
    
    /**
     * Method utama yang dieksekusi pertama kali saat program dijalankan.
     * Berisi skenario pembuatan objek pengguna (Admin, Instruktur, Siswa), simulasi login,
     * validasi materi, penanganan polimorfisme notifikasi, serta inisialisasi
     * aplikasi berbasis antarmuka grafis (GUI) MateriApp.
     * * @param args argumen baris perintah yang dilewatkan saat eksekusi program (opsional).
     */
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   QUALITY EDUCATION — OOP DEMO");
        System.out.println("========================================\n");
        
        // Inisialisasi Data Objek Awal
        Instruktur ins1   = new Instruktur("pak_bayu", "pass123", "INS-001");
        Siswa      siswa1 = new Siswa("budi_s", "budi99", "SIS-001", "menengah");
        Siswa      siswa2 = new Siswa("citra_r", "citra22", "SIS-002", "pemula");

        // Buat objek (POLYMORPHISM: semua adalah User)
        Admin      admin      = new Admin("admin01", "pass123", "ADM-001");
        Instruktur instruktur = new Instruktur("alfia", "instr123", "INS-001");
        Siswa      siswa      = new Siswa("siswa01", "siswa123", "STD-001", "10A");

        // --- LOGIN (method dari User abstract) ---
        System.out.println("--- Login ---");
        admin.login();
        instruktur.login();
        siswa.login();
        System.out.println();

        // --- TAMPILKAN PROFIL ---
        System.out.println("--- Profil ---");
        admin.tampilkanProfil();
        instruktur.tampilkanProfil();
        siswa.tampilkanProfil();

        // --- BUAT MATERI ---
        System.out.println("--- Buat Materi ---");
        Materi materi = new Materi(1, "Pengenalan OOP", "10A",
                "OOP adalah paradigma pemrograman berbasis objek.", instruktur);
        materi.tampilkanInfo();

        // --- VALIDASI MATERI oleh Admin ---
        System.out.println("--- Validasi Materi ---");
        admin.validasiMateri(materi);
        materi.tampilkanInfo();

        // --- NOTIFIKASI (POLYMORPHISM via interface Notifiable) ---
        System.out.println("--- Notifikasi ---");
        Notifiable[] penerima = { admin, instruktur, siswa };
        for (Notifiable n : penerima) {
            n.kirimNotifikasi("Materi Baru", "Materi OOP sudah tersedia.");
        }
        System.out.println();

        // --- CEK ROLE (POLYMORPHISM: getRole() override di tiap subclass) ---
        System.out.println("--- Role Masing-masing User ---");
        User[] users = { admin, instruktur, siswa };
        for (User u : users) {
            System.out.println(u.getUsername() + " -> " + u.getRole());
        }
        System.out.println();

        // --- VERIFIKASI PASSWORD ---
        System.out.println("--- Verifikasi Password ---");
        System.out.println("Admin verifikasi 'pass123'  : " + admin.verifyPassword("pass123"));
        System.out.println("Admin verifikasi 'salah'    : " + admin.verifyPassword("salah"));
        System.out.println("Siswa verifikasi 'siswa123' : " + siswa.verifyPassword("siswa123"));
        System.out.println();
        
        // --- NOTIFIKASI LANGSUNG VIA OBJECT ---
        System.out.println("--- Notifikasi Langsung ---");
        ins1.kirimNotifikasi("Materi Disetujui", "Materi 'OOP Analysis' telah disetujui.");
        siswa1.kirimNotifikasi("Quiz Baru Tersedia", "Quiz 'OOP Lanjutan' dibuka, selesaikan sebelum 30 April.");
        siswa2.kirimNotifikasi("Selamat Datang", "Akun Anda aktif. Mulai belajar sekarang!");

        System.out.println("\n Semua demo selesai!");
        System.out.println("----------------------------------------------");
        System.out.println("MEMBUKA ANTARMUKA GUI (MATERI APP)...");
        System.out.println("----------------------------------------------");

        // Membuka Aplikasi Utama GUI MateriApp
        SwingUtilities.invokeLater(() -> {
            new MateriApp();
        });
    }
}