import java.util.ArrayList;
import java.util.List;

public class MateriController {

    private List<Materi> materiList = new ArrayList<>();
    private int nextId = 1;

    // CREATE
    public void addMateri(String judul, String kategori, String isi, Instruktur pembuat) {
        Materi materi = new Materi(nextId++, judul, kategori, isi, pembuat);
        materiList.add(materi);
        System.out.println("Materi berhasil ditambahkan!");
    }

    // READ
    public void viewAllMateri() {
        if (materiList.isEmpty()) {
            System.out.println("Belum ada materi.");
            return;
        }
        for (Materi m : materiList) {
            m.tampilkanInfo();
        }
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
        materiList.removeIf(m -> m.getIdMateri() == id);
        System.out.println("Materi berhasil dihapus!");
    }

    // Dipakai JUnit Test
    public List<Materi> getMateriList() { return materiList; }
    public int getSize()                { return materiList.size(); }
}