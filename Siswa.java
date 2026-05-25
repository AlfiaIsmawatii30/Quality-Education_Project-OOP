public class Siswa extends User implements Notifiable {
    private String idSiswa;
    private String tingkat; 

    public Siswa(String username, String password, String idSiswa, String tingkat) {
        super(username, password);
        this.idSiswa = idSiswa;
        this.tingkat = tingkat;
    }

    @Override
    public void kirimNotifikasi(String judul, String pesan) {
        System.out.println("[NOTIF → SISWA] " + getUsername()
            + " (Tingkat: " + tingkat + ") | " + judul + ": " + pesan);
    }

    @Override
    public String getRole() {
        return "Siswa (Tingkat: " + tingkat + ")";
    }

    public void tampilkanProfil() {
        System.out.println("Profil Siswa");
        System.out.println("ID Siswa  : " + idSiswa);
        System.out.println("Username  : " + getUsername());
        System.out.println("Tingkat   : " + tingkat);
        System.out.println("--------------------");
    }

    public String getIdSiswa() { return idSiswa; }
    public String getTingkat() { return tingkat; }
    public void setTingkat(String tingkat) { this.tingkat = tingkat; }
}