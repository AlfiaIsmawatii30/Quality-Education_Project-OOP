import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MateriApp extends JFrame {

    /** Komponen input teks untuk data materi pembelajaran. */
    private JTextField txtIdMateri, txtJudul, txtKategori, txtIsi;
    
    /** Tombol aksi manipulasi data materi dan kontrol notifikasi sistem. */
    private JButton btnAdd, btnUpdate, btnDelete, btnValidasi, btnBroadcast, btnClear;
    
    /** Tombol fitur baru versi 2.0: tambah materi premium dan filter tampilan. */
    private JButton btnAddPremium, btnFilterPremium;
    
    /** Tombol untuk mengakhiri sesi masuk pengguna saat ini. */
    private JButton btnLogout; 
    
    /** Tabel visual untuk menampilkan daftar seluruh data materi perkuliahan. */
    private JTable table;
    
    /** Model data tabel yang memfasilitasi penambahan dan penghapusan baris data. */
    private DefaultTableModel tableModel;
    
    /** Panel kontainer untuk menampung elemen-elemen form input data. */
    private JPanel formPanel;
    
    /** Panel bagian atas (header) untuk navigasi informasi user. */
    private JPanel topPanel; 
    
    /** Label teks penanda informasi nama pengguna dan hak akses yang sedang aktif. */
    private JLabel lblDashboard; 
    
    /** String penyimpan peran (role) dari pengguna yang berhasil login. */
    private String currentUserRole = "";
    
    /** String penyimpan nama unik (username) pengguna aktif. */
    private String currentUsername = "";
    
    /** Catatan teks aktivitas materi terakhir untuk kebutuhan konten pesan broadcast. */
    private String statusMateriTerakhir = "Belum ada aktivitas materi terbaru pada sesi ini.";

    /** Kontroler untuk memanajemen sesi otentikasi login/logout. */
    private AuthController authController;
    
    /** Kontroler untuk mengelola riwayat pencatatan dan distribusi notifikasi. */
    private NotifikasiController notifikasiController;
    
    /** Kontroler inti penanganan operasi bisnis data materi pembelajaran. */
    private MateriController materiController; 
    
    /** Objek pengguna aktif hasil konfirmasi otentikasi sistem. */
    private User loggedInUserObject = null;   

    /**
     * Konstruktor utama kelas MateriApp.
     * Menginisialisasi objek-objek kontroler bisnis (Auth, Notifikasi, Materi) 
     * serta langsung memicu dimulainya sesi aplikasi.
     */
    public MateriApp() {
        this.authController = new AuthController();
        this.notifikasiController = new NotifikasiController();
        this.materiController = new MateriController();
        
        mulaiSesiAplikasi();
    }

    /**
     * Mengatur alur siklus hidup inisialisasi aplikasi.
     * Menampilkan dialog login terlebih dahulu, melakukan validasi penutupan aplikasi 
     * jika login dibatalkan, serta mengonfigurasi layout komponen visual jika login sukses.
     */
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
        
        refreshTableData();
        
        revalidate();
        repaint();
        setVisible(true); 
    }

    /**
     * Membuka jendela dialog interaktif (JOptionPane) khusus untuk proses otentikasi.
     * Menangani validasi string kosong, pemanggilan fungsi masuk kontroler, 
     * hingga penentuan tipe instansiasi objek (Admin, Instruktur, atau Siswa).
     */
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

    /**
     * Menerapkan aturan Role-Based Access Control (RBAC) pada elemen antarmuka.
     * Menyembunyikan form input serta tombol manipulasi jika peran pengguna adalah Admin/Siswa, 
     * atau menonaktifkan fungsi tombol validasi jika pengguna adalah Instruktur.
     */
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
            btnAddPremium.setVisible(false);
        } 
        else if (currentUserRole.equals("Siswa")) {
            formPanel.setVisible(false);
            btnAdd.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnValidasi.setVisible(false);
            btnClear.setVisible(false);
            btnAddPremium.setVisible(false);
        }
    }

    /**
     * Membangun, menyusun, dan mewarnai seluruh struktur tata letak (layout) visual 
     * komponen Swing seperti header panel, form GridBagLayout, tombol-tombol aksi, 
     * hingga tabel penampung data di bagian bawah frame.
     */
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
        txtIdMateri.setEditable(false); 
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
        btnAdd          = new JButton("Add");
        btnUpdate       = new JButton("Update");
        btnDelete       = new JButton("Delete");
        btnValidasi     = new JButton("Validasi Admin");
        btnBroadcast    = new JButton("Broadcast Notif");
        btnClear        = new JButton("Clear Form");
        btnAddPremium   = new JButton("★ Add Premium");
        btnFilterPremium = new JButton("Filter Premium");

        btnAddPremium.setBackground(new Color(255, 215, 0));
        btnAddPremium.setForeground(new Color(80, 50, 0));
        btnAddPremium.setFont(new Font("Segoe UI", Font.BOLD, 11));

        btnFilterPremium.setBackground(new Color(70, 130, 180));
        btnFilterPremium.setForeground(Color.WHITE);
        btnFilterPremium.setFont(new Font("Segoe UI", Font.BOLD, 11));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnValidasi);
        buttonPanel.add(btnBroadcast);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnAddPremium);
        buttonPanel.add(btnFilterPremium);

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

    /**
     * Memperbarui item baris data pada komponen tabel GUI.
     * Membersihkan seluruh data lama dan menarik ulang kumpulan objek data 
     * dari `materiController` untuk dimasukkan ke dalam model tabel.
     */
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
            // Perbaikan logika strings statis agar selaras dengan versi robust
            });
        }
    }

    /**
     * Menghubungkan seluruh komponen tombol dengan Event Listener (ActionListener).
     * Berisi logika pertahanan sistem (Robustness) menggunakan blok try-catch untuk 
     * menangkap runtime exception, pengondisian seleksi tabel, hingga konfirmasi dialog keluar sesi.
     */
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

        // ===== FITUR BARU VERSI 2.0: ADD MATERI PREMIUM =====
        btnAddPremium.addActionListener(e -> {
            // Dialog form khusus untuk input data materi premium
            JTextField txtJudulP   = new JTextField(20);
            JTextField txtKategoriP = new JTextField(20);
            JTextField txtIsiP     = new JTextField(20);
            JComboBox<String> cmbKesulitan = new JComboBox<>(
                    new String[]{"Mudah", "Menengah", "Sulit", "Sangat Sulit"});
            JTextField txtSks      = new JTextField(5);
            JTextField txtBatas    = new JTextField(20);
            txtBatas.setText("2026-12-31 23:59");

            JPanel panelPremium = new JPanel(new GridLayout(6, 2, 8, 8));
            panelPremium.add(new JLabel("Judul Materi:")); panelPremium.add(txtJudulP);
            panelPremium.add(new JLabel("Kategori Kelas:")); panelPremium.add(txtKategoriP);
            panelPremium.add(new JLabel("Isi Materi:")); panelPremium.add(txtIsiP);
            panelPremium.add(new JLabel("Tingkat Kesulitan:")); panelPremium.add(cmbKesulitan);
            panelPremium.add(new JLabel("Jumlah SKS (> 0):")); panelPremium.add(txtSks);
            panelPremium.add(new JLabel("Batas Akses (yyyy-MM-dd HH:mm):")); panelPremium.add(txtBatas);

            int result = JOptionPane.showConfirmDialog(this, panelPremium,
                    "★ Tambah Materi Premium", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String judul    = txtJudulP.getText().trim();
                    String kategori = txtKategoriP.getText().trim();
                    String isi      = txtIsiP.getText().trim();
                    String kesulitan = (String) cmbKesulitan.getSelectedItem();
                    String batas    = txtBatas.getText().trim();

                    // Validasi SKS menggunakan Custom Exception
                    if (txtSks.getText().trim().isEmpty()) {
                        throw new InputTidakValidException("Jumlah SKS tidak boleh kosong!");
                    }
                    int sks;
                    try {
                        sks = Integer.parseInt(txtSks.getText().trim());
                    } catch (NumberFormatException nfe) {
                        throw new InputTidakValidException(
                                "Format SKS tidak valid! Masukkan angka bulat positif, bukan: '"
                                + txtSks.getText().trim() + "'");
                    }

                    materiController.addMateriPremium(judul, kategori, isi,
                            (Instruktur) loggedInUserObject,
                            kesulitan, sks, batas,
                            notifikasiController, authController);

                    statusMateriTerakhir = "menambahkan materi PREMIUM '★ " + judul + "'. Menunggu Validasi.";
                    refreshTableData();
                    JOptionPane.showMessageDialog(this,
                            "★ Materi Premium Berhasil Ditambahkan!\nNotifikasi [PREMIUM] dikirim ke Admin.",
                            "Sukses Tambah Premium", JOptionPane.INFORMATION_MESSAGE);

                } catch (InputTidakValidException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Input Tidak Valid:\n" + ex.getMessage(),
                            "Error Validasi Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ===== FITUR BARU VERSI 2.0: FILTER MATERI PREMIUM =====
        btnFilterPremium.addActionListener(e -> {
            java.util.List<Materi> hasilFilter = materiController.filterMateriPremium();

            if (hasilFilter.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tidak ada Materi Premium yang ditemukan dalam sistem.",
                        "Filter Premium", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Tampilkan hasil filter di tabel (mode sementara)
            tableModel.setRowCount(0);
            for (Materi m : hasilFilter) {
                MateriPremium mp = (MateriPremium) m;
                tableModel.addRow(new Object[]{
                    mp.getIdMateri(),
                    "★ " + mp.getJudulMateri(),
                    mp.getCategoryKls() + " | SKS: " + mp.getJumlahSks(),
                    "[" + mp.getTingkatKesulitan() + "] " + mp.getIsiMateri(),
                    mp.getStatusValidasi(),
                    "Batas: " + mp.getBatasAkses()
                });
            }
            JOptionPane.showMessageDialog(this,
                    "Menampilkan " + hasilFilter.size() + " Materi Premium.\nTekan 'Refresh' (Clear Form) untuk kembali ke tampilan semua materi.",
                    "Filter Premium Aktif", JOptionPane.INFORMATION_MESSAGE);
        });
    }

   
    private void clearFields() {
        txtIdMateri.setText("");
        txtJudul.setText("");
        txtKategori.setText("");
        txtIsi.setText("");
        table.clearSelection();
        refreshTableData(); // Mengembalikan tampilan tabel penuh setelah filter
    }

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
               // Mengabaikan error Look and Feel agar sistem tetap meluncur default
            }
            new MateriApp();
        });
    }
}