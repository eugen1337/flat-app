package back.infrustructure.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import back.DTO.UserDTO;
import back.infrustructure.storage.entities.UserEntity;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.transaction.UserTransaction;

public class PostgreDB implements IDataBase {
    private DataSource ds;

    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    public PostgreDB() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("flat-pu");
            entityManager = emf.createEntityManager();

            InitialContext context = new InitialContext();
            userTransaction = (UserTransaction) context.lookup("java:comp/UserTransaction");
        } catch (Exception e) {
            System.out.println("Error while Persistence creating: " + e.getMessage());
        }

    }

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

                if (rs.next()) {
                    st.close();
                    return "OK";
                } else {
                    st = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
                    st.setString(1, user.getLogin());
                    rs = st.executeQuery();
                    rs.next();
                    String password = rs.getString("password");
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
            userTransaction.begin();
            entityManager.joinTransaction();

            Query query = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.login = :username")
                    .setParameter("username", user.getLogin());

            List<UserEntity> persons = query.getResultList();

            if (persons == null || persons.isEmpty()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setLogin(user.getLogin());
                userEntity.setPassword(user.getPassword());

                entityManager.persist(userEntity);
                userTransaction.commit();
                return "OK";
            } else
                return "login already used";

        } catch (Exception e) {
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return "BAD";
    }

}

// private int retrieveRowsCountByJPA() throws Exception {
// try {
// userTransaction.begin();
// entityManager.joinTransaction();

// List<EPerson> persons = entityManager.createQuery("SELECT p FROM EPerson p",
// EPerson.class).getResultList();

// /*
// * EPerson personFind = entityManager.find(EPerson.class,new Integer(2));
// * personFind.setPersonName("Person_Find");
// * entityManager.merge(personFind);
// *
// * EPerson personPersist = new EPerson();
// * //personPersist.setPersonID(new Integer(3));
// * personPersist.setPersonName("Person_Persist");
// * entityManager.persist(personPersist);
// */

// userTransaction.commit();

// return persons.size();
// } catch (Exception e) {
// throw new Exception("Error while JPA operating: " + e.getMessage());
// }
// }