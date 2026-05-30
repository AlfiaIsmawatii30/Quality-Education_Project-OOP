import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MateriApp extends JFrame {

    private JTextField txtIdMateri, txtJudul, txtKategori, txtIsi;
    private JButton btnAdd, btnUpdate, btnDelete, btnValidasi, btnBroadcast, btnClear;
    private JButton btnLogout; 
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JPanel formPanel;
    private JPanel topPanel; 
    private JLabel lblDashboard; 
    
    private String currentUserRole = "";
    private String currentUsername = "";
    private String statusMateriTerakhir = "Belum ada aktivitas materi terbaru pada sesi ini.";

    private AuthController authController;
    private NotifikasiController notifikasiController;
    private MateriController materiController; 
    private User loggedInUserObject = null;   
    public MateriApp() {
        this.authController = new AuthController();
        this.notifikasiController = new NotifikasiController();
        this.materiController = new MateriController();
        
        mulaiSesiAplikasi();
    }

    private void mulaiSesiAplikasi() {
        showLoginDialog();
        
        if (currentUserRole.isEmpty()) {
            System.exit(0);
        }

        setTitle("SISTEM PEMBELAJARAN DIGITAL");
        setSize(780, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        getContentPane().removeAll();
        
        setupTampilan();
        setupLogikaRobustness();
        aturHakAksesKomponen();
        
        // Load data awal jika ada materi default di controller
        refreshTableData();
        
        revalidate();
        repaint();
        setVisible(true); 
    }

    private void showLoginDialog() {
        JTextField txtLoginUser = new JTextField(15);
        JPasswordField txtLoginPass = new JPasswordField(15);

        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(txtLoginUser);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(txtLoginPass);

        int result = JOptionPane.showConfirmDialog(null, loginPanel, 
                "SILAHKAN LOGIN", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String user = txtLoginUser.getText().trim();
            String pass = new String(txtLoginPass.getPassword()).trim();
            
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username dan Password tidak boleh kosong!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                showLoginDialog(); 
            } else {
                loggedInUserObject = authController.login(user, pass);
                
                if (loggedInUserObject != null) {
                    currentUsername = loggedInUserObject.getUsername();
                    
                    if (loggedInUserObject instanceof Admin) {
                        currentUserRole = "Admin";
                    } else if (loggedInUserObject instanceof Instruktur) {
                        currentUserRole = "Instruktur";
                    } else if (loggedInUserObject instanceof Siswa) {
                        currentUserRole = "Siswa";
                    }
                    
                    JOptionPane.showMessageDialog(null, "[PROSES LOGIN]\n" + currentUsername + " berhasil login sebagai " + currentUserRole + ".", "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                    showLoginDialog(); 
                }
            }
        } else {
            currentUserRole = "";
        }
    }

    private void aturHakAksesKomponen() {
        if (currentUserRole.equals("Instruktur")) {
            btnValidasi.setVisible(false);
        } 
        else if (currentUserRole.equals("Admin")) {
            formPanel.setVisible(false); 
            btnAdd.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnClear.setVisible(false);
        } 
        else if (currentUserRole.equals("Siswa")) {
            formPanel.setVisible(false);
            btnAdd.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnValidasi.setVisible(false);
            btnClear.setVisible(false);
        }
    }

    private void setupTampilan() {
        topPanel = new JPanel(new BorderLayout()); 
        topPanel.setBackground(new Color(102, 51, 153)); 
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        lblDashboard = new JLabel("DASHBOARD UTAMA SYSTEM | Login Sebagai: " + currentUsername.toUpperCase() + " (" + currentUserRole.toUpperCase() + ")");
        lblDashboard.setForeground(Color.WHITE); 
        lblDashboard.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 53, 69)); 
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnLogout.setFocusPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setOpaque(true);
        
        topPanel.add(lblDashboard, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Input Data Materi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Materi (Otomatis):"), gbc);
        gbc.gridx = 1; txtIdMateri = new JTextField(10);
        txtIdMateri.setEditable(false); // ID diatur otomatis oleh MateriController (nextId++)
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

        String[] columns = {"ID Materi", "Judul Materi", "Kategori Kelas", "Isi Materi", "Status Validasi", "Waktu Validasi"};
        tableModel = new DefaultTableModel(columns, 0); 
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(720, 250)); 
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Materi Kuliah"));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void refreshTableData() {
        tableModel.setRowCount(0); 
        
        for (Materi m : materiController.getMateriList()) {
            String status = "Tersedia"; 
            String waktu = "-";
            
            tableModel.addRow(new Object[]{
                m.getIdMateri(), 
                m.getJudulMateri(), 
                m.getCategoryKls(), 
                m.getIsiMateri(), 
                status, 
                waktu
            });
        }
    }

    private void setupLogikaRobustness() {
        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda yakin ingin keluar (Logout) dari sistem?", 
                    "Konfirmasi Keluar Sesi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (konfirmasi == JOptionPane.YES_OPTION) {
                authController.logout();
                JOptionPane.showMessageDialog(this, "Sesi " + currentUsername + " berakhir. Kembali ke halaman login.", "Logout Berhasil", JOptionPane.INFORMATION_MESSAGE);
                currentUserRole = "";
                currentUsername = "";
                loggedInUserObject = null;
                setVisible(false);
                mulaiSesiAplikasi();
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                String judul = txtJudul.getText().trim();
                String kategori = txtKategori.getText().trim();
                String isi = txtIsi.getText().trim();

                if (judul.isEmpty() || kategori.isEmpty() || isi.isEmpty()) {
                    throw new IllegalArgumentException("Semua kolom form materi wajib diisi!");
                }

                materiController.addMateri(judul, kategori, isi, (Instruktur) loggedInUserObject, notifikasiController, authController);
                
                statusMateriTerakhir = "menambahkan materi baru '" + judul + "'. Menunggu Validasi.";
            
                refreshTableData();
                
                JOptionPane.showMessageDialog(this, "Materi Berhasil Ditambahkan!\nNotifikasi otomatis dikirim ke Admin.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1 && currentUserRole.equals("Instruktur")) {
                txtIdMateri.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtJudul.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtKategori.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtIsi.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    throw new IllegalStateException("Pilih salah satu baris materi pada tabel terlebih dahulu!");
                }
                
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String judul = txtJudul.getText().trim();
                String isi = txtIsi.getText().trim();

                if (judul.isEmpty() || isi.isEmpty()) {
                    throw new IllegalArgumentException("Kolom judul dan isi tidak boleh kosong saat update!");
                }
                materiController.updateMateri(id, judul, isi);
                statusMateriTerakhir = "mengubah detail isi pada materi '" + judul + "'.";
                
                refreshTableData();
                JOptionPane.showMessageDialog(this, "Data materi berhasil diperbarui di sistem!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Peringatan Update", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    throw new IllegalStateException("Pilih data pada tabel yang ingin dihapus!");
                }

                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String judulMateriDihapus = tableModel.getValueAt(selectedRow, 1).toString();

                int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus materi ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    materiController.deleteMateri(id);
                    statusMateriTerakhir = "menghapus materi '" + judulMateriDihapus + "' dari perkuliahan.";
                    
                    refreshTableData();
                    JOptionPane.showMessageDialog(this, "Materi berhasil dihapus dari sistem!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                }
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Peringatan Delete", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnValidasi.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    throw new IllegalStateException("Pilih materi di tabel yang ingin divalidasi!");
                }

                int idMateri = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String judul = tableModel.getValueAt(selectedRow, 1).toString();
                
                materiController.validasiMateri((Admin) loggedInUserObject, idMateri, notifikasiController, authController);

                statusMateriTerakhir = "memvalidasi penerbitan materi '" + judul + "'.";

                refreshTableData();
                
                StringBuilder riwayatLog = new StringBuilder("Sistem: Validasi Sukses!\nNotifikasi otomatis dikirim ke pembuat & seluruh siswa:\n");
                for (String log : notifikasiController.getRiwayatNotif()) {
                    riwayatLog.append(log).append("\n");
                }
                
                JOptionPane.showMessageDialog(this, riwayatLog.toString(), "Validasi & Notifikasi Sukses", JOptionPane.INFORMATION_MESSAGE);
                table.clearSelection();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Peringatan Validasi", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBroadcast.addActionListener(e -> {
            String pengirimInisial = "";
            if (currentUserRole.equals("Instruktur")) pengirimInisial = "[INS]";
            else if (currentUserRole.equals("Admin")) pengirimInisial = "[ADM]";
            else if (currentUserRole.equals("Siswa")) pengirimInisial = "[SWA]";

            String judulNotif = "Broadcast Manual Aktivitas";
            String pesanNotif = pengirimInisial + " " + currentUsername + " " + statusMateriTerakhir;

            List<Notifiable> daftarPenerima = new ArrayList<>();
            for (User u : authController.getUserList()) {
                if (u instanceof Notifiable) {
                    daftarPenerima.add((Notifiable) u);
                }
            }

            notifikasiController.broadcast(daftarPenerima, judulNotif, pesanNotif);

            StringBuilder riwayatLog = new StringBuilder(">> Mengirim Broadcast Manual via NotifikasiController:\n");
            for (String log : notifikasiController.getRiwayatNotif()) {
                riwayatLog.append(log).append("\n");
            }
            
            JOptionPane.showMessageDialog(this, riwayatLog.toString(), "Notifikasi Broadcast Sukses", JOptionPane.INFORMATION_MESSAGE);
        });

        btnClear.addActionListener(e -> clearFields());
    }

    private void clearFields() {
        txtIdMateri.setText("");
        txtJudul.setText("");
        txtKategori.setText("");
        txtIsi.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
               
            }
            new MateriApp();
        });
    }
}