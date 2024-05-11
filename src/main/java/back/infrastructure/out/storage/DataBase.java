package back.infrastructure.out.storage;

import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;

import back.DTO.UserDTO;
import back.domain.Room;
import back.infrastructure.out.storage.entities.ERoom;
import back.infrastructure.out.storage.entities.EUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.transaction.UserTransaction;

public class DataBase implements IDataBase {

    private EntityManager entityManager;

    public DataBase() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("flat-pu");
            entityManager = emf.createEntityManager();
        } catch (Exception e) {
            System.out.println("Error while Persistence creating: " + e.getMessage());
        }

    }

    @Override
    public String checkUser(UserDTO user) {

        try {
            Query query = entityManager
                    .createQuery("SELECT u FROM EUser u WHERE u.login = :username and u.password = :password")
                    .setParameter("username", user.getLogin()).setParameter("password", user.getPassword());

            List<EUser> persons = query.getResultList();

            if (persons == null || persons.isEmpty()) {
                return "user doesnt exist";
            } else
                return "OK";

        } catch (Exception e) {
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return "BAD";
    }

    @Override
    public String addUser(UserDTO user) {

        try {
            UserTransaction userTransaction = (UserTransaction) new InitialContext()
                    .lookup("java:comp/UserTransaction");
            userTransaction.begin();
            entityManager.joinTransaction();

            Query query = entityManager.createQuery("SELECT u FROM EUser u WHERE u.login = :username")
                    .setParameter("username", user.getLogin());

            List<EUser> persons = query.getResultList();

            if (persons == null || persons.isEmpty()) {
                EUser userEntity = new EUser();
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

    @Override
    public String addRoom(Room room, String login) {
        try {
            UserTransaction userTransaction = (UserTransaction) new InitialContext()
                    .lookup("java:comp/UserTransaction");
            userTransaction.begin();
            entityManager.joinTransaction();

            Query query = entityManager.createQuery("SELECT u FROM EUser u WHERE u.login = :username")
                    .setParameter("username", login);

            List<EUser> persons = query.getResultList();

            if (persons == null || persons.isEmpty())
                return "User doesnt exist";

            int userId = persons.get(0).getId();

            int length = Arrays.stream(room.getWallsLength())
                    .min()
                    .getAsInt();

            int width = Arrays.stream(room.getWallsLength())
                    .max()
                    .getAsInt();

            ERoom roomEntity = new ERoom();
            roomEntity.setType(room.getType());
            roomEntity.setLevel(room.getLevel());
            // roomEntity.setWallsLength(room.getWallsLength());
            roomEntity.setUserId(userId);

            roomEntity.setLength(length);
            roomEntity.setWidth(width);

            entityManager.persist(roomEntity);

            entityManager.getTransaction().commit();
            userTransaction.commit();
            return "OK";

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return "BAD";
    }

    @Override
    public Room getRoom(Room room, String login) {
        throw new UnsupportedOperationException("Unimplemented method 'getRoom'");
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
