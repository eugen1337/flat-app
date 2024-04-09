package back.infrustructure.storage.entities;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "\"users\"")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"login\"")
    private String login;
    @Column(name = "\"password\"")
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}