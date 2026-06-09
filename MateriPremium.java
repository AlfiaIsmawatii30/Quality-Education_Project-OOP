pubic class MateriPremium extends Materi {
    /// encapsulation
    private String tingkatKesulitan;
    private double hargaAkses;
    /// constructor
    public MateriPremium(int idMateri, Stirng judulMateri, String categoryKls, String isiMateri, Instruktur pembuat, String tingkatKesulitan, double hargaAkses) {

        super(idMateri, judulMateri, categoryKls, isiMateri, pembuat);
        this.tingkatKesulitan = tingkatKesulitan;
        this.hargaAkses = hargaAkses
    }
    /// getter dan setter
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
    /// Override tamilkanInfo
    @Override
    public void tampilkanInfo() {
        system.out.println("=== Detail Materi PREMIUM ===");
        system.out.println("MATERI PREMIUM");
        system.out.println("ID Materi : " + getIdMateri());
        system.out.println("Judul :" + getJudulMateri());
        system.out.println("Kategori : " + getCategoryKls());
        system.out.println("Isi : " + getIsiMateri());
        system.out.println("Tingkat Kesulitan : " + tingkatKesulitan);
        system.out.println("Harga Akses : Rp %.2f%n", hargaAkses);
        system.out.println("Status Validasi : " + getStatusValidasi());
        system.out.println("Waktu Validasi : " + getWaktuValidasi());
        if (getPembuat() != null) {
            system.out.println("Dibuat Oleh : " + getPembuat().getUsername()
                    + " (" + getPembuat().getIdInstruktur() + ")");
        }
    }

    public boolean isSulit() {
        return "Sulit".equalsignoreCase(tingkatKesulitan);
    }
}