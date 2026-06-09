import java.util.ArrayList;
import java.util.List;

public class MateriController {

    /**
     * Struktur data dinamis berbentuk List untuk menampung seluruh objek Materi di memori.
     */
    private List<Materi> materiList = new ArrayList<>();

    /**
     * Generator ID otomatis bertipe integer untuk memberikan nomor urut unik pada setiap materi baru.
     */
    private int nextId = 1;

    /**
     * Membuat dan menambahkan objek materi baru ke dalam sistem, sekaligus mengirimkan 
     * notifikasi pengajuan secara otomatis kepada seluruh pengguna dengan hak akses Admin.
     * * @param judul judul utama konten materi.
     * @param kategori klasifikasi target tingkatan kelas.
     * @param isi narasi atau penjabaran materi.
     * @param pembuat objek {@link Instruktur} selaku penyusun materi.
     * @param notifController kontroler perantara pengiriman pesan notifikasi.
     * @param authController kontroler pencari daftar pengguna aktif dalam sistem.
     */
    public void addMateri(String judul, String kategori, String isi, Instruktur pembuat, NotifikasiController notifController, AuthController authController) {
        Materi materi = new Materi(nextId++, judul, kategori, isi, pembuat);
        materiList.add(materi);
        System.out.println("Materi berhasil ditambahkan!");

        // Otomatis kirim notifikasi ke semua Admin di dalam sistem
        String judulAdmin = "Persetujuan Materi Baru";
        String pesanAdmin = "Instruktur " + pembuat.getUsername() + " mengajukan materi baru '" + judul + "'. Mohon segera divalidasi!";
        
        for (User user : authController.getUserList()) {
            if (user instanceof Admin) {
                notifController.kirimNotifikasi((Admin) user, judulAdmin, pesanAdmin);
            }
        }
    }

    /**
     * Mengambil seluruh kumpulan data materi yang terdaftar di dalam sistem.
     * * @return kumpulan data materi dalam bentuk objek {@link List}.
     */
    public List<Materi> getMateriList() { return materiList; }

    /**
     * Melakukan pencarian objek materi berdasarkan kecocokan ID unik pengenal.
     * * @param id nomor unik ID materi yang dicari.
     * @return objek {@link Materi} jika ditemukan, atau {@code null} jika ID tidak cocok.
     */
    public Materi getMateriById(int id) {
        for (Materi m : materiList) {
            if (m.getIdMateri() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Memperbarui informasi konten (judul dan teks isi) pada materi tertentu berdasarkan pencarian ID.
     * * @param id kode pengenal materi yang akan diubah.
     * @param judulBaru nama judul baru pengganti data lama.
     * @param isiBaru teks isi materi baru pengganti data lama.
     */
    public void updateMateri(int id, String judulBaru, String isiBaru) {
        for (Materi m : materiList) {
            if (m.getIdMateri() == id) {
                m.setJudulMateri(judulBaru);
                m.setIsiMateri(isiBaru);
                System.out.println("Materi berhasil diupdate!");
                return;
            }
        }
        System.out.println("Materi tidak ditemukan.");
    }

    /**
     * Menghapus objek materi secara permanen dari daftar repositori sistem berdasarkan kecocokan ID.
     * * @param id kode pengenal materi yang ingin dihapus.
     */
    public void deleteMateri(int id) {
        Materi materiDihapus = null;
        for (Materi m : materiList) {
            if (m.getIdMateri() == id) {
                materiDihapus = m;
                break;
            }
        }
        if (materiDihapus != null) {
            materiList.remove(materiDihapus);
            System.out.println("Materi berhasil dihapus.");
        } else {
            System.out.println("Materi tidak ditemukan.");
        }
    }

    /**
     * Memproses persetujuan (validasi) penerbitan materi oleh Admin, memperbarui status 
     * internal materi, serta mendistribusikan notifikasi otomatis ke Instruktur pembuat 
     * beserta seluruh akun Siswa yang terdaftar di sistem.
     * * @param admin objek {@link Admin} yang mengeksekusi validasi materi.
     * @param idMateri kode unik identifikasi materi yang akan disetujui.
     * @param notifController kontroler media distribusi pengiriman pesan.
     * @param authController kontroler penyedia data seluruh pengguna untuk filter peran.
     */
    public void validasiMateri(Admin admin, int idMateri, NotifikasiController notifController, AuthController authController) {
        Materi materi = getMateriById(idMateri);
        
        if (materi == null) {
            System.out.println("Materi tidak ditemukan.");
            return;
        }

        admin.validasiMateri(materi);
        
        // Kirim notifikasi ke Instruktur pembuat materi
        if (materi.getPembuat() != null) {
            notifController.kirimNotifikasi(materi.getPembuat(), 
                "Materi Disetujui", 
                "Materi '" + materi.getJudulMateri() + "' Anda telah divalidasi dan diterbitkan oleh Admin.");
        }
        
        // Kirim notifikasi otomatis ke seluruh akun Siswa di dalam sistem
        String judulSiswa = "Materi Kuliah Baru Tersedia!";
        String pesanSiswa = "Materi '" + materi.getJudulMateri() + "' (" + materi.getCategoryKls() + ") telah terbit. Silahkan dipelajari!";
        
        for (User user : authController.getUserList()) {
            if (user instanceof Siswa) {
                notifController.kirimNotifikasi((Siswa) user, judulSiswa, pesanSiswa);
            }
        }
    }

    /**
     * Membuat dan menambahkan objek MateriPremium baru ke dalam sistem.
     * Selain menyimpan ke daftar materi yang sama dengan materi biasa (Collections),
     * method ini juga mengirimkan notifikasi khusus bertanda [PREMIUM] ke seluruh Admin.
     *
     * @param judul            judul utama konten materi premium.
     * @param kategori         klasifikasi target tingkatan kelas.
     * @param isi              narasi atau penjabaran materi.
     * @param pembuat          objek {@link Instruktur} selaku penyusun materi.
     * @param tingkatKesulitan tingkat kesulitan materi (misal: "Sulit").
     * @param jumlahSks        jumlah SKS yang diberikan (harus > 0).
     * @param batasAkses       batas tanggal akses eksklusif (format: "yyyy-MM-dd HH:mm").
     * @param notifController  kontroler perantara pengiriman pesan notifikasi.
     * @param authController   kontroler pencari daftar pengguna aktif dalam sistem.
     * @throws InputTidakValidException jika jumlahSks <= 0 atau field penting kosong.
     */
    public void addMateriPremium(String judul, String kategori, String isi,
                                  Instruktur pembuat, String tingkatKesulitan,
                                  int jumlahSks, String batasAkses,
                                  NotifikasiController notifController,
                                  AuthController authController)
            throws InputTidakValidException {

        // Validasi input krusial menggunakan Custom Exception
        if (judul == null || judul.trim().isEmpty()) {
            throw new InputTidakValidException("Judul materi premium tidak boleh kosong!");
        }
        if (jumlahSks <= 0) {
            throw new InputTidakValidException(
                    "Jumlah SKS tidak valid! Nilai harus lebih dari 0, namun diterima: " + jumlahSks);
        }
        if (batasAkses == null || batasAkses.trim().isEmpty()) {
            throw new InputTidakValidException("Batas akses materi premium tidak boleh kosong!");
        }

        MateriPremium materiPremium = new MateriPremium(
                nextId++, judul, kategori, isi, pembuat,
                tingkatKesulitan, jumlahSks, batasAkses);

        // Disimpan ke Collections (List<Materi>) yang SAMA dengan materi biasa
        materiList.add(materiPremium);
        System.out.println("Materi Premium berhasil ditambahkan: ★ " + judul);

        // Notifikasi khusus premium ke semua Admin
        String judulAdmin = "[PREMIUM] Persetujuan Materi Prioritas Baru";
        String pesanAdmin = "Instruktur " + pembuat.getUsername()
                + " mengajukan materi PREMIUM '" + judul
                + "' (SKS: " + jumlahSks + "). Mohon segera divalidasi!";

        for (User user : authController.getUserList()) {
            if (user instanceof Admin) {
                notifController.kirimNotifikasi((Admin) user, judulAdmin, pesanAdmin);
            }
        }
    }

    /**
     * Memfilter dan mengembalikan hanya daftar materi bertipe Premium dari Collections.
     * Memanfaatkan operator {@code instanceof} untuk menyeleksi objek {@link MateriPremium}
     * dari dalam List utama yang juga menyimpan materi biasa.
     *
     * @return {@link List} berisi hanya objek-objek {@link MateriPremium} yang terdaftar.
     */
    public List<Materi> filterMateriPremium() {
        List<Materi> hasilFilter = new ArrayList<>();
        for (Materi m : materiList) {
            if (m instanceof MateriPremium) {
                hasilFilter.add(m);
            }
        }
        return hasilFilter;
    }

    /**
     * Mengambil jumlah total materi yang saat ini tersimpan di dalam memori list sistem.
     * @return nilai integer representasi ukuran jumlah total data materi.
     */
    public int getSize() { return materiList.size(); }
}