import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MateriApp extends JFrame {

    // Tempat mendaftarkan komponen aplikasi
    private JComboBox<String> cbAktor; // Kotak pilihan Instruktur/Admin/Siswa
    private JTextField txtIdMateri, txtJudul, txtKategori, txtIsi;
    private JButton btnAdd, btnUpdate, btnDelete, btnValidasi, btnBroadcast, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    // Constructor: Fondasi utama rumah aplikasi
    public MateriApp() {
        setTitle("SISTEM PEMBELAJARAN DIGITAL");
        setSize(750, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        // Memanggil fungsi penataan tampilan
        setupTampilan();
        
        setVisible(true); // Memunculkan jendela ke layar
    }

    // Fungsi untuk menata tampilan komponen (Ini isi yang tadi belum ada!)
    private void setupTampilan() {
        // 1. Membuat Panel Bagian Atas (NORTH) untuk pilihan Aktor
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(102, 51, 153)); // Ungu khas dosenmu
        
        JLabel lblAktor = new JLabel("Pilih Peran Anda (Login Sebagai): ");
        lblAktor.setForeground(Color.WHITE); // Biar tulisan warna putih
        lblAktor.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Membuat JComboBox / Dropdown Pilihan
        String[] listAktor = {"Instruktur", "Admin", "Siswa (SWA01/SWA02)"};
        cbAktor = new JComboBox<>(listAktor);
        
        // Masukkan label dan dropdown ke panel atas
        topPanel.add(lblAktor);
        topPanel.add(cbAktor);
        
        // Pasang panel atas ini ke posisi UTARA (BorderLayout.NORTH)
        add(topPanel, BorderLayout.NORTH);
    }

    // Jalur sakelar utama untuk me-run aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MateriApp());
    }
}