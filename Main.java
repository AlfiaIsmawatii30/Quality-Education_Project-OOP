import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   QUALITY EDUCATION — OOP DEMO");
        System.out.println("========================================\n");
        
        // Inisialisasi Data Objek
        Instruktur ins1   = new Instruktur("pak_bayu", "pass123", "INS-001");
        Admin      adm1   = new Admin("admin1", "admin123", "ADM-01");
        Siswa      siswa1 = new Siswa("budi_s", "budi99", "SIS-001", "menengah");
        Siswa      siswa2 = new Siswa("citra_r", "citra22", "SIS-002", "pemula");

        // Buat objek Materi
        Materi m1 = new Materi(101, "Object-Oriented Analysis", "Software Design",
                "Mempelajari Transformasi ERD ke Class Diagram", ins1);

        // --- LOGIN ---
        System.out.println("--- Login ---");
        adm1.login();
        ins1.login();
        siswa1.login();
        System.out.println();

        // --- TAMPILKAN PROFIL ---
        System.out.println("--- Profil ---");
        adm1.tampilkanProfil();
        ins1.tampilkanProfil();
        siswa1.tampilkanProfil();

        // --- STATUS AWAL MATERI ---
        System.out.println("\n[ STATUS AWAL MATERI ]");
        m1.tampilkanInfo();

        // --- VALIDASI OLEH ADMIN ---
        System.out.println("[ PROSES VALIDASI OLEH ADMIN ]");
        adm1.validasiMateri(m1);

        System.out.println("\n[ STATUS AKHIR MATERI ]");
        m1.tampilkanInfo();

        // --- NOTIFIKASI via method broadcast (buat sendiri) ---
        System.out.println("\n[ NOTIFIKASI ]");
        kirimNotifikasi(adm1, "Laporan Harian", "Ada 3 materi baru menunggu validasi.");
        kirimNotifikasi(ins1, "Materi Disetujui", "Materi 'OOP Analysis' telah disetujui.");
        kirimNotifikasi(siswa1, "Quiz Baru Tersedia", "Quiz 'OOP Lanjutan' dibuka, selesaikan sebelum 30 April.");
        kirimNotifikasi(siswa2, "Selamat Datang", "Akun Anda aktif. Mulai belajar sekarang!");

        System.out.println("\n Semua demo selesai!");
        System.out.println("----------------------------------------------");
        System.out.println("MEMBUKA ANTARMUKA GUI (MATERI APP)...");
        System.out.println("----------------------------------------------");

        // Buka GUI
        SwingUtilities.invokeLater(() -> {
            new MateriApp();
        });
    }

    // Method helper untuk mengirim notifikasi (polymorphism via interface)
    private static void kirimNotifikasi(Notifiable penerima, String judul, String pesan) {
        System.out.print(">> Mengirim ke " + penerima.getRole() + " -> ");
        penerima.kirimNotifikasi(judul, pesan);
    }
}