import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   QUALITY EDUCATION — OOP DEMO");
        System.out.println("========================================\n");

        // Buat objek (POLYMORPHISM: semua adalah User)
        Admin      admin      = new Admin("ADM1", "ADM1", "ADM-002");
        Instruktur instruktur = new Instruktur("INS1", "INS1", "INS-002");
        Siswa      siswa      = new Siswa("SWA1", "SWA1", "STD-002", "10A");

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
        siswa.tampilkanProfil(); // DIBAIKI DISINI: diubah dari siswaProfil() menjadi tampilkanProfil()
        System.out.println();

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
        System.out.println("Admin verifikasi 'ADM1'     : " + admin.verifyPassword("ADM1"));
        System.out.println("Admin verifikasi 'salah'    : " + admin.verifyPassword("salah"));
        System.out.println("Siswa verifikasi 'SWA1'     : " + siswa.verifyPassword("SWA1"));
        
        // ====================================================================
        // TAMBAHAN: Memicu Peluncuran Antarmuka GUI (MateriApp) Setelah Demo CLI
        // ====================================================================
        System.out.println("\n--- Membuka Jendela GUI Aplikasi... ---");
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Menyamakan tema tampilan visual dengan sistem operasi laptop (Windows/Mac/Linux)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Mengabaikan error Look and Feel agar sistem tetap meluncur default
            }
            // Memanggil antarmuka utama Sistem Manajemen Distribusi Materi Digital
            new MateriApp();
        });
    }
}