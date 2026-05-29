import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class NotifikasiControllerTest {

    private NotifikasiController notifController;
    private Admin admin;
    private Instruktur instruktur;
    private Siswa siswa;

    @BeforeEach
    void setUp() {
        notifController = new NotifikasiController();
        admin      = new Admin("admin1", "admin123", "ADM-01");
        instruktur = new Instruktur("pak_bayu", "pass123", "INS-001");
        siswa      = new Siswa("budi_s", "budi99", "SIS-001", "menengah");
    }

    @Test
    void testKirimNotifikasiSatuPenerima() {
        notifController.kirimNotifikasi(admin, "Laporan", "Ada materi baru.");
        assertEquals(1, notifController.getSize());
    }

    @Test
    void testBroadcastKeSemuaPenerima() {
        List<Notifiable> penerima = new ArrayList<>();
        penerima.add(admin);
        penerima.add(instruktur);
        penerima.add(siswa);

        notifController.broadcast(penerima, "Info", "Sistem diperbarui.");
        assertEquals(3, notifController.getSize());
    }

    @Test
    void testRiwayatNotifTidakKosongSetelahKirim() {
        notifController.kirimNotifikasi(instruktur, "Materi Disetujui", "OOP Analysis disetujui.");
        assertFalse(notifController.getRiwayatNotif().isEmpty());
    }

    @Test
    void testRiwayatKosongSebelumKirim() {
        assertTrue(notifController.getRiwayatNotif().isEmpty());
    }
}
