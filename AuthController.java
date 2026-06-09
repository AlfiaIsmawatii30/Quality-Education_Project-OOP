import java.util.ArrayList;
import java.util.List;

public class AuthController {

    private List<User> userList = new ArrayList<>();
    private User userLogin = null;

    public AuthController() {
        userList.add(new Admin("ADM1", "ADM1", "ADM-002"));
        userList.add(new Instruktur("INS1", "INS1", "INS-002"));
        userList.add(new Siswa("SWA1", "SWA1", "STD-002", "10A"));

        userList.add(new Admin("admin1", "admin123", "ADM-01"));
        userList.add(new Instruktur("instruktur", "pass123", "INS-001"));
        userList.add(new Siswa("siswa1", "siswa123", "SIS-001", "menengah"));
        userList.add(new Siswa("siswa2", "siswa123", "SIS-002", "pemula"));
    }

    public List<Kaprodi> filterKhususKaprodi() {
        List<Kaprodi> hasilFilter = new ArrayList<>();
        if (this.userList != null) {
            for (User u : this.userList) {
                if (u instanceof Kaprodi) {
                    hasilFilter.add((Kaprodi) u);
                }
            }
        }
        return hasilFilter;
    }

    public User login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                userLogin = user;
                userLogin.login();
                return userLogin;
            }
        }
        System.out.println("Login gagal! Username atau password salah.");
        return null;
    }

    public void logout() {
        if (userLogin != null) {
            System.out.println(userLogin.getUsername() + " berhasil logout.");
            userLogin = null;
        } else {
            System.out.println("Tidak ada user yang sedang login.");
        }
    }

    public User getUserLogin()      { return userLogin; }
    public boolean isLoggedIn()     { return userLogin != null; }
    public List<User> getUserList() { return userList; }
}