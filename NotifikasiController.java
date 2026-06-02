import java.util.ArrayList;
import java.util.List;

/**
 * Kelas pengontrol (Controller) yang memanajemen distribusi dan pencatatan notifikasi.
 * Kelas ini memanfaatkan konsep Polimorfisme melalui interface {@link Notifiable} 
 * untuk mengirimkan pesan baik secara individu maupun massal (broadcast) kepada 
 * pengguna, serta mendokumentasikan setiap riwayat pesan yang terkirim.
 * * @author Kelompok 4
 * @version 1.0
 */
public class NotifikasiController {

    /**
     * Struktur data dinamis berbentuk List untuk menampung rekaman teks log 
     * seluruh notifikasi yang telah sukses didistribusikan oleh sistem.
     */
    private List<String> riwayatNotif = new ArrayList<>();

    /**
     * Mengirimkan pesan notifikasi secara personal kepada objek penerima tertentu, 
     * lalu otomatis mencatatkan format pesan tersebut ke dalam daftar riwayat sistem.
     * * @param penerima objek yang mengimplementasikan interface {@link Notifiable}.
     * @param judul topik utama atau subjek notifikasi yang akan dikirimkan.
     * @param pesan isi teks informasi atau detail berita dari notifikasi.
     */
    public void kirimNotifikasi(Notifiable penerima, String judul, String pesan) {
        penerima.kirimNotifikasi(judul, pesan);
        riwayatNotif.add("[" + penerima.getRole() + "] " + judul + ": " + pesan);
    }

    /**
     * Mendistribusikan pesan notifikasi secara serentak (massal) ke seluruh objek 
     * penerima yang terdaftar di dalam list parameter.
     * * @param penerima kumpulan data objek {@link List} yang berhak menerima pesan broadcast.
     * @param judul topik atau subjek utama notifikasi broadcast.
     * @param pesan isi teks informasi massal yang disiarkan.
     */
    public void broadcast(List<Notifiable> penerima, String judul, String pesan) {
        for (Notifiable n : penerima) {
            kirimNotifikasi(n, judul, pesan);
        }
    }

    /**
     * Menampilkan seluruh rekaman log teks riwayat notifikasi yang tersimpan di dalam memori 
     * secara berurutan ke layar konsol terminal (CLI).
     */
    public void viewRiwayat() {
        if (riwayatNotif.isEmpty()) {
            System.out.println("Belum ada notifikasi.");
            return;
        }
        for (String notif : riwayatNotif) {
            System.out.println(notif);
        }
    }

    /**
     * Mengambil kumpulan list rekaman string dari seluruh riwayat notifikasi sistem.
     * * @return kumpulan data riwayat notifikasi dalam bentuk {@link List}.
     */
    public List<String> getRiwayatNotif() { return riwayatNotif; }

    /**
     * Mengambil jumlah total baris pesan notifikasi yang saat ini tersimpan di memori riwayat.
     * * @return nilai integer representasi ukuran jumlah total log notifikasi.
     */
    public int getSize()                  { return riwayatNotif.size(); }
}