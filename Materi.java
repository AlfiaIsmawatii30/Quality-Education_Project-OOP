/**
 * Representasi dari data Materi Pembelajaran di dalam sistem pembelajaran digital.
 * Kelas ini menyimpan informasi detail mengenai konten materi, kategori kelas,
 * status validasi oleh Admin, serta merepresentasikan hubungan (asosiasi) 
 * dengan objek {@link Instruktur} sebagai pembuat materi.
 * * @author Kelompok 4
 * @version 1.0
 */
public class Materi {

    /**
     * ID unik sebagai pengenal dari sebuah materi.
     */
    private int    idMateri;

    /**
     * Judul atau topik utama dari materi pembelajaran.
     */
    private String judulMateri;

    /**
     * Kategori tingkatan kelas target untuk materi ini (misal: "10A", "pemula").
     */
    private String categoryKls;

    /**
     * Konten isi atau penjabaran teks dari materi pembelajaran.
     */
    private String isiMateri;

    /**
     * Status verifikasi materi oleh administrator. 
     * Nilai default awal diset menjadi "Belum divalidasi".
     */
    private String statusValidasi = "Belum divalidasi";

    /**
     * Catatan waktu kapan materi ini berhasil divalidasi oleh Admin.
     * Nilai default berupa tanda strip "-" sebelum proses validasi dilakukan.
     */
    private String waktuValidasi  = "-";

    /**
     * Objek pengajar yang memproduksi atau membuat materi ini.
     * Membentuk hubungan asosiasi antar objek.
     */
    private Instruktur pembuat;

    /**
     * Konstruktor untuk membuat objek Materi baru dengan data konten awal lengkap.
     * * @param idMateri kode unik identifikasi materi.
     * @param judulMateri judul materi pembelajaran.
     * @param categoryKls kategori tingkatan kelas.
     * @param isiMateri teks isi atau materi lengkap.
     * @param pembuat objek {@link Instruktur} yang menyusun materi ini.
     */
    public Materi(int idMateri, String judulMateri, String categoryKls,
                  String isiMateri, Instruktur pembuat) {
        this.idMateri    = idMateri;
        this.judulMateri = judulMateri;
        this.categoryKls = categoryKls;
        this.isiMateri   = isiMateri;
        this.pembuat     = pembuat;
    }

    /**
     * Mengambil ID unik materi.
     * @return nilai integer dari ID Materi.
     */
    public int getIdMateri()           { return idMateri; }

    /**
     * Mengambil judul materi pembelajaran.
     * @return teks judul materi.
     */
    public String getJudulMateri()     { return judulMateri; }

    /**
     * Mengambil kategori tingkatan kelas dari materi.
     * @return teks tingkatan kelas.
     */
    public String getCategoryKls()     { return categoryKls; }

    /**
     * Mengambil isi konten atau teks utama materi.
     * @return teks isi materi.
     */
    public String getIsiMateri()       { return isiMateri; }

    /**
     * Mengambil status validasi terkini dari materi.
     * @return status berupa string (misal: "Telah divalidasi oleh admin").
     */
    public String getStatusValidasi()  { return statusValidasi; }

    /**
     * Mengambil catatan waktu kapan materi disetujui.
     * @return teks representasi tanggal dan waktu validasi.
     */
    public String getWaktuValidasi()   { return waktuValidasi; }

    /**
     * Mengambil data objek Instruktur yang membuat materi ini.
     * @return objek {@link Instruktur} terkait.
     */
    public Instruktur getPembuat()     { return pembuat; }

    /**
     * Mengubah atau mengatur ulang ID materi.
     * @param idMateri ID materi baru yang akan diterapkan.
     */
    public void setIdMateri(int idMateri)           { this.idMateri = idMateri; }

    /**
     * Mengubah atau memperbarui judul materi pembelajaran.
     * @param judulMateri judul materi yang baru.
     */
    public void setJudulMateri(String judulMateri)  { this.judulMateri = judulMateri; }

    /**
     * Mengubah atau menetapkan kategori kelas materi yang baru.
     * @param categoryKls kategori kelas target yang baru.
     */
    public void setCategoryKls(String categoryKls)  { this.categoryKls = categoryKls; }

    /**
     * Mengubah isi teks materi pembelajaran.
     * @param isiMateri konten materi baru yang akan dimasukkan.
     */
    public void setIsiMateri(String isiMateri)      { this.isiMateri = isiMateri; }

    /**
     * Memperbarui informasi status validasi admin (biasanya dipanggil oleh method di kelas Admin).
     * @param validasi teks status pembaruan validasi baru.
     */
    public void setValidasi(String validasi)        { this.statusValidasi = validasi; }

    /**
     * Mengatur catatan waktu eksekusi validasi materi.
     * @param waktu format string waktu validasi terbaru.
     */
    public void setWaktuValidasi(String waktu)      { this.waktuValidasi = waktu; }

    /**
     * Mengubah atau menetapkan ulang hak kepemilikan pembuat materi ke instruktur lain.
     * @param pembuat objek {@link Instruktur} penanggung jawab baru.
     */
    public void setPembuat(Instruktur pembuat)      { this.pembuat = pembuat; }

    /**
     * Menampilkan informasi detail spesifikasi materi secara terstruktur ke layar konsol,
     * termasuk melacak nama serta ID pengajar yang memproduksi materi ini.
     */
    public void tampilkanInfo() {
        System.out.println("=== Detail Materi ===");
        System.out.println("ID Materi      : " + idMateri);
        System.out.println("Judul          : " + judulMateri);
        System.out.println("Kategori       : " + categoryKls);
        System.out.println("Isi            : " + isiMateri);
        System.out.println("Status Validasi: " + statusValidasi);
        System.out.println("Waktu Validasi : " + waktuValidasi);
        if (pembuat != null) {
            System.out.println("Dibuat Oleh    : " + pembuat.getUsername()
                    + " (" + pembuat.getIdInstruktur() + ")");
        }
        System.out.println("=====================");
    }
}