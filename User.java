public class User {
    private String username;
    private String password;
 
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
 
    public String getUsername() {
        return username;
    }
 
    protected boolean verifyPassword(String input) {
        return this.password.equals(input);
    }
 
    public void login() {
        System.out.println(username + " berhasil login ke sistem.");
    }
}