package back.infrustructure.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import back.DTO.UserDTO;

public class PostgreDB implements IDataBase {
    private DataSource ds;

    private Connection getConnectionPool() throws Exception {
        try {
            InitialContext initialContext = new InitialContext();
            ds = (DataSource) initialContext.lookup("local_pg_pool");
            Connection conn = ds.getConnection();
            return conn;
        } catch (Exception e) {
            throw new Exception("Exception in getConn()" + e.getMessage());
        }
    }

    @Override
    public String checkUser(UserDTO user) {
        try {
            Connection conn = getConnectionPool();
            try {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
                st.setString(1, user.getLogin());
                st.setString(2, user.getPassword());
                ResultSet rs = st.executeQuery();

                boolean isRegistredUser = rs.next();

                rs.close();
                st.close();
                if (isRegistredUser)
                    return "OK";
                else {
                    st = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
                    st.setString(1, user.getLogin());
                    rs = st.executeQuery();
                    rs.next();
                    String password = rs.getString("password");
                    rs.close();
                    st.close();
                    return "maybe your password is *" + password + "*?";
                }

            } finally {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error while JDBC operating: " + e.getMessage());
        }
        return "BAD";
    }

    @Override
    public String addUser(UserDTO user) {
        try {
            Connection conn = getConnectionPool();
            try {
                PreparedStatement st = conn.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
                st.setString(1, user.getLogin());
                st.setString(2, user.getPassword());
                st.executeUpdate();
                st.close();
                return "OK";
            } finally {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error while JDBC operating: " + e.getMessage());
        }
        return "BAD";
    }
}