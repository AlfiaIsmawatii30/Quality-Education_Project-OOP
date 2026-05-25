import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MateriApp extends JFrame {

    private JComboBox<String> cbAktor; 
    private JTextField txtIdMateri, txtJudul, txtKategori, txtIsi;
    private JButton btnAdd, btnUpdate, btnDelete, btnValidasi, btnBroadcast, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;


    public String getRole() { return "System"; }

    public MateriApp() {
        setTitle("SISTEM PEMBELAJARAN DIGITAL");
        setSize(750, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        setupTampilan();
        
        setVisible(true); 
    }

    private void setupTampilan() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(102, 51, 153)); 
        
        JLabel lblAktor = new JLabel("Pilih Peran Anda (Login Sebagai): ");
        lblAktor.setForeground(Color.WHITE); 
        lblAktor.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        String[] listAktor = {"Instruktur", "Admin", "Siswa (SWA01/SWA02)"};
        cbAktor = new JComboBox<>(listAktor);
        
        topPanel.add(lblAktor);
        topPanel.add(cbAktor);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Input Data Materi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Materi (Angka):"), gbc);
        gbc.gridx = 1; txtIdMateri = new JTextField(10);
        formPanel.add(txtIdMateri, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Judul Materi:"), gbc);
        gbc.gridx = 1; txtJudul = new JTextField(25);
        formPanel.add(txtJudul, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Kategori Kelas:"), gbc);
        gbc.gridx = 1; txtKategori = new JTextField(25);
        formPanel.add(txtKategori, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Isi Materi:"), gbc);
        gbc.gridx = 1; txtIsi = new JTextField(25);
        formPanel.add(txtIsi, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd       = new JButton("Add");
        btnUpdate    = new JButton("Update");
        btnDelete    = new JButton("Delete");
        btnValidasi  = new JButton("Validasi Admin");
        btnBroadcast = new JButton("Broadcast Notif");
        btnClear     = new JButton("Clear Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnValidasi);
        buttonPanel.add(btnBroadcast);
        buttonPanel.add(btnClear);

        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MateriApp());
    }
}