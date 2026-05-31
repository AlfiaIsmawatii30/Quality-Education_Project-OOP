import java.util.ArrayList;
import java.util.List;

public class AuthController {

    private List<User> userList = new ArrayList<>();
    private List<String> passwordList = new ArrayList<>();
    private User userLogin = null;

    public AuthController() {
        userList.add(new Admin("admin", "ADM01", "ADM-01"));
        passwordList.add("ADM01");

        userList.add(new Instruktur("instruktur", "INS01", "INS-001"));
        passwordList.add("INS01");

        userList.add(new Siswa("siswa1", "SWA1", "SIS-001", "menengah"));
        passwordList.add("SWA1");

        userList.add(new Siswa("siswa2", "SWA2", "SIS-002", "pemula"));
        passwordList.add("SWA2");
    }

    public User login(String username, String password) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)
                    && passwordList.get(i).equals(password)) {
                userLogin = userList.get(i);
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