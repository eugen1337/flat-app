package back.infrastructure.out.storage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import back.DTO.FlatDTO;
import back.DTO.RoomDTO;
import back.DTO.UserDTO;
import back.domain.calculator.Room;
import back.infrastructure.out.storage.entities.EFlat;
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

            double length = Arrays.stream(room.getWallsLength())
                    .min()
                    .getAsDouble();

            double width = Arrays.stream(room.getWallsLength())
                    .max()
                    .getAsDouble();

            ERoom roomEntity = new ERoom();
            roomEntity.setType(room.getType());
            roomEntity.setLevel(room.getLevel());
            // roomEntity.setWallsLength(room.getWallsLength());
            // roomEntity.setUserId(userId);

            roomEntity.setLength((int) length * 100);
            roomEntity.setWidth((int) width * 100);

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

    @Override
    public String addFlat(FlatDTO flat, String login) {
        try {
            UserTransaction userTransaction = (UserTransaction) new InitialContext()
                    .lookup("java:comp/UserTransaction");
            userTransaction.begin();
            entityManager.joinTransaction();
            Query query = entityManager.createQuery("SELECT u FROM EUser u WHERE u.login = :username")
                    .setParameter("username", login);
            List<EUser> persons = query.getResultList();
            if (persons == null || persons.isEmpty()) {
                System.out.println("DB User doesnt exist");
                return "User doesnt exist";
            }
            System.out.println(persons.size());
            System.out.println(persons.get(0).getLogin());
            System.out.println(persons.get(0).getPassword());
            int userId;
            userId = persons.get(0).getId();

            System.out.println("DB userId sucseeds");

            int flatId = new BigDecimal(new Date().getTime() / 100 % 1000000000).intValueExact();
            EFlat flatEntity = new EFlat();
            flatEntity.setId(flatId);
            flatEntity.setUserId(userId);
            flatEntity.setTotalArea(flat.getArea());
            flatEntity.setTotalPerimeter(flat.getPerimeter());

            entityManager.persist(flatEntity);
            System.out.println("DB persist(flatEntity) sucseeds");

            for (RoomDTO room : flat.getRooms()) {
                double length = Arrays.stream(room.getWallsLength())
                        .min()
                        .getAsDouble();

                double width = Arrays.stream(room.getWallsLength())
                        .max()
                        .getAsDouble();

                ERoom roomEntity = new ERoom();
                roomEntity.setType(room.getType());
                roomEntity.setLevel(room.getLevel());
                // roomEntity.setWallsLength(room.getWallsLength());
                roomEntity.setFlatId(flatId);

                roomEntity.setLength((int) length * 100);
                roomEntity.setWidth((int) width * 100);
                entityManager.persist(roomEntity);
            }
            System.out.println("DB persist(roomEntity)sucseeds");
            entityManager.getTransaction().commit();
            userTransaction.commit();
            return "OK";

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
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
