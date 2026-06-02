import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MateriControllerTest {

    private MateriController controller;
    private NotifikasiController notifController;
    private AuthController authController;
    private Instruktur instruktur;
    private Admin admin;

    @BeforeEach
    void setUp() {
        controller     = new MateriController();
        notifController = new NotifikasiController();
        authController  = new AuthController();
        instruktur     = new Instruktur("instruktur", "pass123", "INS-001");
        admin          = new Admin("admin1", "admin123", "ADM-01");
    }

    @Test
    void testAddMateri() {
        controller.addMateri("OOP Analysis", "Software Design", "Isi materi", instruktur, notifController, authController);
        assertEquals(1, controller.getSize());
    }

    @Test
    void testViewAllMateriTidakKosong() {
        controller.addMateri("Java Collections", "Pemrograman", "Isi", instruktur, notifController, authController);
        assertFalse(controller.getMateriList().isEmpty());
    }

    @Test
    void testUpdateMateri() {
        controller.addMateri("Judul Lama", "Kategori", "Isi Lama", instruktur, notifController, authController);
        controller.updateMateri(1, "Judul Baru", "Isi Baru");
        assertEquals("Judul Baru", controller.getMateriById(1).getJudulMateri());
    }

    @Test
    void testDeleteMateri() {
        controller.addMateri("OOP Analysis", "Software Design", "Isi", instruktur, notifController, authController);
        controller.deleteMateri(1);
        assertEquals(0, controller.getSize());
    }

    @Test
    void testValidasiMateri() {
        controller.addMateri("OOP Analysis", "Software Design", "Isi materi", instruktur, notifController, authController);
        Materi m = controller.getMateriById(1);

        assertEquals("Belum divalidasi", m.getStatusValidasi());

        controller.validasiMateri(admin, 1, notifController, authController);

        assertNotEquals("Belum divalidasi", m.getStatusValidasi());
        assertTrue(notifController.getSize() >= 1);
    }
}