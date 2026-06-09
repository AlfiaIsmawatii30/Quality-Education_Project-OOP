public class MateriPremium extends Materi {

    // Encapsulation
    private String tingkatKesulitan;
    private double hargaAkses;

    // Constructor
    public MateriPremium(int idMateri, String judulMateri, String categoryKls, String isiMateri,
                         Instruktur pembuat, String tingkatKesulitan, double hargaAkses) {

        super(idMateri, judulMateri, categoryKls, isiMateri, pembuat);
        this.tingkatKesulitan = tingkatKesulitan;
        this.hargaAkses = hargaAkses;
    }

    // Getter dan Setter
    public String getTingkatKesulitan() {
        return tingkatKesulitan;
    }

    public void setTingkatKesulitan(String tingkatKesulitan) {
        this.tingkatKesulitan = tingkatKesulitan;
    }

    public double getHargaAkses() {
        return hargaAkses;
    }

    public void setHargaAkses(double hargaAkses) {
        this.hargaAkses = hargaAkses;
    }

    // Override tampilkanInfo
    @Override
    public void tampilkanInfo() {
        System.out.println("=== Detail Materi PREMIUM ===");
        System.out.println("MATERI PREMIUM");
        System.out.println("ID Materi         : " + getIdMateri());
        System.out.println("Judul             : " + getJudulMateri());
        System.out.println("Kategori          : " + getCategoryKls());
        System.out.println("Isi               : " + getIsiMateri());
        System.out.println("Tingkat Kesulitan : " + tingkatKesulitan);
        System.out.printf("Harga Akses       : Rp %.2f%n", hargaAkses);
        System.out.println("Status Validasi   : " + getStatusValidasi());
        System.out.println("Waktu Validasi    : " + getWaktuValidasi());
        if (getPembuat() != null) {
            System.out.println("Dibuat Oleh       : " + getPembuat().getUsername()
                    + " (" + getPembuat().getIdInstruktur() + ")");
        }
    }

    public boolean isSulit() {
        return "Sulit".equalsIgnoreCase(tingkatKesulitan);
    }

} // akhir class MateriPremium