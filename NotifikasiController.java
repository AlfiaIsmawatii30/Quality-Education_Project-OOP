import java.util.ArrayList;
import java.util.List;

public class NotifikasiController {

    private List<String> riwayatNotif = new ArrayList<>();

    // Kirim notifikasi ke satu penerima
    public void kirimNotifikasi(Notifiable penerima, String judul, String pesan) {
        penerima.kirimNotifikasi(judul, pesan);
        riwayatNotif.add("[" + penerima.getRole() + "] " + judul + ": " + pesan);
    }

    /**
     * PERBAIKAN: Broadcast Pintar Berdasarkan Peran (Role)
     * Hanya mengirimkan notifikasi jika peran pengguna cocok dengan target yang diinginkan.
     */
    public void broadcastSesuaiRole(List<Notifiable> daftarPenerima, String judul, String pesan, String targetRole) {
        for (Notifiable n : daftarPenerima) {
            if (n.getRole().equalsIgnoreCase(targetRole)) {
                kirimNotifikasi(n, judul, pesan);
            }
        }
    }

    // Broadcast ke semua penerima (Tetap dipertahankan jika dibutuhkan kelas lain)
    public void broadcast(List<Notifiable> penerima, String judul, String pesan) {
        for (Notifiable n : penerima) {
            kirimNotifikasi(n, judul, pesan);
        }
    }

    // Lihat riwayat notifikasi di konsol
    public void viewRiwayat() {
        if (riwayatNotif.isEmpty()) {
            System.out.println("Belum ada notifikasi.");
            return;
        }
        for (String notif : riwayatNotif) {
            System.out.println(notif);
        }
    }

    // Dipakai JUnit Test & GUI Dashboard
    public List<String> getRiwayatNotif() { return riwayatNotif; }
    public int getSize()                  { return riwayatNotif.size(); }
}