/**
 * Interface (Kontrak) untuk objek yang dapat menerima atau dikirimkan notifikasi.
 * Setiap kelas yang mengimplementasikan interface ini wajib menyediakan logika
 * pengiriman notifikasi khusus serta mampu mengidentifikasi peran (role) dirinya.
 * * @author Kelompok 4
 * @version 1.0
 */
public interface Notifiable {
    
    /**
     * Mengirimkan atau menampilkan pesan notifikasi ke objek penerima.
     * * @param judul topik utama atau judul dari notifikasi yang dikirim.
     * @param pesan isi informasi atau detail pesan notifikasi.
     */
    void kirimNotifikasi(String judul, String pesan);
    
    /**
     * Mengambil peran (role) dari objek yang mengimplementasikan interface ini.
     * Digunakan untuk identifikasi tipe pengguna saat proses distribusi pesan.
     * * @return string yang menyatakan peran pengguna (misal: "Admin", "Instruktur", "Siswa").
     */
    String getRole();
}