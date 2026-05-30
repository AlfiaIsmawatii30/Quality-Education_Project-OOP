import java.util.ArrayList;
import java.util.List;

public class NotifikasiController {

    private List<String> riwayatNotif = new ArrayList<>();

    
    public void kirimNotifikasi(Notifiable penerima, String judul, String pesan) {
        penerima.kirimNotifikasi(judul, pesan);
        riwayatNotif.add("[" + penerima.getRole() + "] " + judul + ": " + pesan);
    }

   
    public void broadcast(List<Notifiable> penerima, String judul, String pesan) {
        for (Notifiable n : penerima) {
            kirimNotifikasi(n, judul, pesan);
        }
    }

    
    public void viewRiwayat() {
        if (riwayatNotif.isEmpty()) {
            System.out.println("Belum ada notifikasi.");
            return;
        }
        for (String notif : riwayatNotif) {
            System.out.println(notif);
        }
    }

    
    public List<String> getRiwayatNotif() { return riwayatNotif; }
    public int getSize()                  { return riwayatNotif.size(); }
}
