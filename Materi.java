public class Materi {
    private int    idMateri;
    private String judulMateri;
    private String categoryKls;
    private String isiMateri;
    private String statusValidasi = "Belum divalidasi";
    private String waktuValidasi  = "-";
    private Instruktur pembuat;

    public Materi(int idMateri, String judulMateri, String categoryKls,
                  String isiMateri, Instruktur pembuat) {
        this.idMateri    = idMateri;
        this.judulMateri = judulMateri;
        this.categoryKls = categoryKls;
        this.isiMateri   = isiMateri;
        this.pembuat     = pembuat;
    }

    public String getJudulMateri()                  { return judulMateri; }
    public void setValidasi(String validasi)        { this.statusValidasi = validasi; }
    public void setWaktuValidasi(String waktu)      { this.waktuValidasi = waktu; }
    public int getIdMateri()                        { return idMateri; }
    public String getCategoryKls()                  { return categoryKls; }
    public String getIsiMateri()                    { return isiMateri; }
    public String getStatusValidasi()               { return statusValidasi; }
    public String getWaktuValidasi()                { return waktuValidasi; }
    public Instruktur getPembuat()                  { return pembuat; }
    public void setJudulMateri(String judulMateri)  { this.judulMateri = judulMateri; }
    public void setIsiMateri(String isiMateri)      { this.isiMateri = isiMateri; }
    public void setCategoryKls(String categoryKls)  { this.categoryKls = categoryKls; }

    public void tampilkanInfo() {
        System.out.println("Detail Materi");
        System.out.println("ID Materi      : " + idMateri);
        System.out.println("Judul          : " + judulMateri);
        System.out.println("Kategori       : " + categoryKls);
        System.out.println("Isi            : " + isiMateri);
        System.out.println("Status Validasi: " + statusValidasi);
        System.out.println("Waktu Validasi : " + waktuValidasi);
        if (pembuat != null) {
            System.out.println("Dibuat Oleh    : " + pembuat.getUsername());
        }
        System.out.println("---------------------");
    }
}