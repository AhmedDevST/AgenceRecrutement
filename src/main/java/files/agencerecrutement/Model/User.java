package files.agencerecrutement.Model;

public class User {
    protected int idUser;
    protected  String userName ;
    protected  String password ;
    protected int roleUser ;
    // 1 : agent
    //2 : entreprise
    // 3: demandeur


    public User(int idUser, String userName, String password, int roleUser) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.roleUser = roleUser;
    }
    public User(int idUser, String userName, String password) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
    }

    public User(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleUser() {
        return roleUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleUser(int roleUser) {
        this.roleUser = roleUser;
    }
}
