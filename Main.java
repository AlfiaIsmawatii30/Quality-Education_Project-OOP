public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   QUALITY EDUCATION — OOP DEMO");
        System.out.println("========================================\n");

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
    }
}