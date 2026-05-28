import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MateriControllerTest {

    private MateriController controller;
    private Instruktur instruktur;
    private Admin admin;

    @BeforeEach
    void setUp() {
        controller = new MateriController();
        instruktur = new Instruktur("pak_bayu", "pass123", "INS-001");
        admin      = new Admin("admin1", "admin123", "ADM-01");
    }

    @Test
    void testAddMateri() {
        controller.addMateri("OOP Analysis", "Software Design", "Isi materi", instruktur);
        assertEquals(1, controller.getSize());
    }

    @Test
    void testViewAllMateriTidakKosong() {
        controller.addMateri("Java Collections", "Pemrograman", "Isi", instruktur);
        assertFalse(controller.getMateriList().isEmpty());
    }

    @Test
    void testUpdateMateri() {
        controller.addMateri("Judul Lama", "Kategori", "Isi Lama", instruktur);
        controller.updateMateri(1, "Judul Baru", "Isi Baru");
        assertEquals("Judul Baru", controller.getMateriById(1).getJudulMateri());
    }

    @Test
    void testDeleteMateri() {
        controller.addMateri("OOP Analysis", "Software Design", "Isi", instruktur);
        controller.deleteMateri(1);
        assertEquals(0, controller.getSize());
    }

   @Test
    void testValidasiMateri() {
        controller.addMateri("OOP Analysis", "Software Design", "Isi materi", instruktur);
        Materi m = controller.getMateriById(1); // ganti dari get(0)

        assertEquals("Belum divalidasi", m.getStatusValidasi());
        admin.validasiMateri(m);
        assertNotEquals("Belum divalidasi", m.getStatusValidasi());
    }
}