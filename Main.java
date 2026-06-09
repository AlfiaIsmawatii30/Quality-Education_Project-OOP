import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   QUALITY EDUCATION — OOP DEMO");
        System.out.println("========================================\n");

        Admin      admin      = new Admin("ADM1", "ADM1", "ADM-002");
        Instruktur instruktur = new Instruktur("INS1", "INS1", "INS-002");
        Siswa      siswa      = new Siswa("SWA1", "SWA1", "STD-002", "10A");

        System.out.println("--- Login ---");
        admin.login();
        instruktur.login();
        siswa.login();
        System.out.println();

        System.out.println("--- Profil ---");
        admin.tampilkanProfil();
        instruktur.tampilkanProfil();
        siswa.tampilkanProfil(); 
        System.out.println();

        System.out.println("--- Buat Materi ---");
        Materi materi = new Materi(1, "Pengenalan OOP", "10A",
                "OOP adalah paradigma pemrograman berbasis objek.", instruktur);
        materi.tampilkanInfo();

        System.out.println("--- Validasi Materi ---");
        admin.validasiMateri(materi);
        materi.tampilkanInfo();

        System.out.println("--- Notifikasi ---");
        Notifiable[] penerima = { admin, instruktur, siswa };
        for (Notifiable n : penerima) {
            n.kirimNotifikasi("Materi Baru", "Materi OOP sudah tersedia.");
        }
        System.out.println();

        System.out.println("--- Role Masing-masing User ---");
        User[] users = { admin, instruktur, siswa };
        for (User u : users) {
            System.out.println(u.getUsername() + " -> " + u.getRole());
        }
        System.out.println();

        System.out.println("--- Verifikasi Password ---");
        System.out.println("Admin verifikasi 'ADM1'     : " + admin.verifyPassword("ADM1"));
        System.out.println("Admin verifikasi 'salah'    : " + admin.verifyPassword("salah"));
        System.out.println("Siswa verifikasi 'SWA1'     : " + siswa.verifyPassword("SWA1"));
        System.out.println();

        // --- MENU INTERAKTIF UAS V2.0 ---
        AuthController auth = new AuthController();
        Scanner scanner = new Scanner(System.in);
        boolean runningMenu = true;

        while (runningMenu) {
            System.out.println("=== MENU INTERAKTIF UAS V2.0 ===");
            System.out.println("1. Tambah Kaprodi");
            System.out.println("2. Filter Kaprodi");
            System.out.println("3. Tampilkan Semua User");
            System.out.println("4. Buka GUI Aplikasi");
            System.out.print("Pilih Opsi (1-4): ");
            
            String pilihan = scanner.nextLine();
            
            switch (pilihan) {
                case "1":
                    try {
                        System.out.print("Username: ");
                        String userKaprodi = scanner.nextLine();
                        System.out.print("Password: ");
                        String passKaprodi = scanner.nextLine();
                        System.out.print("Kode Prodi: ");
                        String kodeProdi = scanner.nextLine();

                        if (userKaprodi.trim().isEmpty() || passKaprodi.trim().isEmpty() || kodeProdi.trim().isEmpty()) {
                            throw new InputTidakValidException("Input tidak boleh kosong!");
                        }

                        auth.getUserList().add(new Kaprodi(userKaprodi.trim(), passKaprodi, kodeProdi.trim().toUpperCase()));
                        System.out.println("👍 Sukses menyimpan data Kaprodi.");
                    } catch (InputTidakValidException e) {
                        System.err.println("\n[ERROR]: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("\n--- Hasil Filter Kaprodi ---");
                    List<Kaprodi> listTerfilter = auth.filterKhususKaprodi();
                    if (listTerfilter.isEmpty()) {
                        System.out.println("Belum ada data Kaprodi.");
                    } else {
                        for (Kaprodi kp : listTerfilter) {
                            System.out.println(kp.tampilkanDetail());
                        }
                    }
                    break;
                case "3":
                    System.out.println("\n--- Semua User di ArrayList ---");
                    for (User u : auth.getUserList()) {
                        System.out.println("- " + u.getUsername() + " [" + u.getRole() + "]");
                    }
                    break;
                case "4":
                    runningMenu = false;
                    break;
                default:
                    System.out.println("Pilihan salah!");
            }
            System.out.println();
        }
        
        System.out.println("--- Membuka Jendela GUI Aplikasi... ---");
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            new MateriApp();
        });
    }
}