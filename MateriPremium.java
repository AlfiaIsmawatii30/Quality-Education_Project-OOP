
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MateriPremium extends Materi {

 
    private String tingkatKesulitan;

 
    private int jumlahSks;

 
    private String batasAkses;

    public MateriPremium(int idMateri, String judulMateri, String categoryKls,
                         String isiMateri, Instruktur pembuat,
                         String tingkatKesulitan, int jumlahSks, String batasAkses) {
        super(idMateri, judulMateri, categoryKls, isiMateri, pembuat);
        this.tingkatKesulitan = tingkatKesulitan;
        this.jumlahSks        = jumlahSks;
        this.batasAkses       = batasAkses;
    }


    public String getTingkatKesulitan() { return tingkatKesulitan; }

 
    public void setTingkatKesulitan(String tingkatKesulitan) {
        this.tingkatKesulitan = tingkatKesulitan;
    }

 
    public int getJumlahSks() { return jumlahSks; }


    public void setJumlahSks(int jumlahSks) { this.jumlahSks = jumlahSks; }


    public String getBatasAkses() { return batasAkses; }


    public void setBatasAkses(String batasAkses) { this.batasAkses = batasAkses; }

   
    @Override
    public void tampilkanInfo() {
        System.out.println("=== [PREMIUM] Detail Materi Premium ===");
        System.out.println("ID Materi      : " + getIdMateri());
        System.out.println("Judul          : ★ " + getJudulMateri() + " [PREMIUM]");
        System.out.println("Kategori       : " + getCategoryKls());
        System.out.println("Isi            : " + getIsiMateri());
        System.out.println("Tingkat Sulit  : " + tingkatKesulitan);
        System.out.println("Jumlah SKS     : " + jumlahSks + " SKS");
        System.out.println("Batas Akses    : " + batasAkses);
        System.out.println("Status Validasi: " + getStatusValidasi());
        System.out.println("Waktu Validasi : " + getWaktuValidasi());
        if (getPembuat() != null) {
            System.out.println("Dibuat Oleh    : " + getPembuat().getUsername()
                    + " (" + getPembuat().getIdInstruktur() + ")");
        }
        System.out.println("=======================================");
    }
}
