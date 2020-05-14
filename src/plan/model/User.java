package plan.model;

public class User {
    private int id;
    private String username;
    private String nameSurname;
    private String password;
    private String email;
    private String teamName;
    private boolean admin;

    public User(String username, String nameSurname, String password, String email, String teamName, boolean admin) {
        this.username = username;
        this.nameSurname = nameSurname;
        this.password = password;
        this.email = email;
        this.teamName = teamName;
        this.admin = admin;
    }

    public User(int id, String username, String nameSurname, String password, String email, String teamName, boolean admin) {
        this.id = id;
        this.username = username;
        this.nameSurname = nameSurname;
        this.password = password;
        this.email = email;
        this.teamName = teamName;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nameSurname='" + nameSurname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", teamName='" + teamName + '\'' +
                ", admin=" + admin +
                '}';
    }
}
