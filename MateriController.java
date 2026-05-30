import java.util.ArrayList;
import java.util.List;

public class MateriController {

    private List<Materi> materiList = new ArrayList<>();
    private int nextId = 1;

    // CREATE MATERI & NOTIFIKASI OTOMATIS KE ADMIN
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

    // READ ALL
    public List<Materi> getMateriList() { return materiList; }

    // READ by ID
    public Materi getMateriById(int id) {
        for (Materi m : materiList) {
            if (m.getIdMateri() == id) {
                return m;
            }
        }
        return null;
    }

    // UPDATE
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

    // DELETE
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

    // 2. VALIDASI MATERI & NOTIFIKASI KE INSTRUKTUR + SEMUA SISWA
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

    public int getSize() { return materiList.size(); }
}