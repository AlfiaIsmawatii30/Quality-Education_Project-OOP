import javax.swing.SwingUtilities;

public class Main {
    public static void broadcastPesan(Notifiable penerima, String judul, String pesan) {
        System.out.println(">> Mengirim ke: " + penerima.getRole());
        penerima.kirimNotifikasi(judul, pesan);
    }

    public static void main(String[] args) {
        Instruktur ins1   = new Instruktur("pak_bayu", "pass123", "INS-001");
        Admin      adm1   = new Admin("admin1", "admin123", "ADM-01");
        Siswa      siswa1 = new Siswa("budi_s", "budi99", "SIS-001", "menengah");
        Siswa      siswa2 = new Siswa("citra_r", "citra22", "SIS-002", "pemula");

        Materi m1 = new Materi(
            101,
            "Object-Oriented Analysis",
            "Software Design",
            "Mempelajari Transformasi ERD ke Class Diagram",
            ins1
        );

        System.out.println("[ PROSES LOGIN ]");
        adm1.login();

        System.out.println("\n[ STATUS AWAL ]");
        m1.tampilkanInfo();

        System.out.println("[ PROSES VALIDASI OLEH ADMIN ]");
        adm1.validasiMateri(m1);

        System.out.println("\n[ STATUS AKHIR ]");
        m1.tampilkanInfo();

        System.out.println("[ DATA PROFIL ]");
        ins1.tampilkanProfil();
        adm1.tampilkanProfil();

        broadcastPesan(adm1,
            "Laporan Harian",
            "Ada 3 materi baru menunggu validasi.");

        broadcastPesan(ins1,
            "Materi Disetujui",
            "Materi 'OOP Analysis' telah disetujui.");

        broadcastPesan(siswa1,
            "Quiz Baru Tersedia",
            "Quiz 'OOP Lanjutan' dibuka, selesaikan sebelum 30 April.");

        broadcastPesan(siswa2,
            "Selamat Datang",
            "Akun Anda aktif. Mulai belajar sekarang!");

        System.out.println("\n Semua demo selesai!");
        System.out.println("----------------------------------------------");
        System.out.println("MEMBUKA ANTARMUKA GUI (MATERI APP)...");
        System.out.println("----------------------------------------------");

        SwingUtilities.invokeLater(() -> {
            new MateriApp();
        });
    }
}