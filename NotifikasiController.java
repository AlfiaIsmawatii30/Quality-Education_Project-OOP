import java.util.ArrayList;
import java.util.List;

public class NotifikasiController {

    private List<String> riwayatNotif = new ArrayList<>();

    // Kirim notifikasi ke satu penerima
    public void kirimNotifikasi(Notifiable penerima, String judul, String pesan) {
        penerima.kirimNotifikasi(judul, pesan);
        riwayatNotif.add("[" + penerima.getRole() + "] " + judul + ": " + pesan);
    }

    // Broadcast ke semua penerima
    public void broadcast(List<Notifiable> penerima, String judul, String pesan) {
        for (Notifiable n : penerima) {
            kirimNotifikasi(n, judul, pesan);
        }
    }

    // Lihat riwayat notifikasi
    public void viewRiwayat() {
        if (riwayatNotif.isEmpty()) {
            System.out.println("Belum ada notifikasi.");
            return;
        }
        for (String notif : riwayatNotif) {
            System.out.println(notif);
        }
    }

    // Dipakai JUnit Test
    public List<String> getRiwayatNotif() { return riwayatNotif; }
    public int getSize()                  { return riwayatNotif.size(); }
}
