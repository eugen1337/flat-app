package back.infrastructure.out.storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
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

    // @Override
    // public String checkUser(UserDTO user) {
    // try {
    // InitialContext initialContext = new InitialContext();
    // DataSource ds = (DataSource) initialContext.lookup("local_pg_pool");
    // Connection conn = ds.getConnection();
    // try {

    // PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE login
    // = ? AND password = ?");
    // st.setString(1, user.getLogin());
    // st.setString(2, user.getPassword());
    // ResultSet rs = st.executeQuery();

    // if (rs.next()) {
    // st.close();
    // return "OK";
    // } else {
    // st = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
    // st.setString(1, user.getLogin());
    // rs = st.executeQuery();
    // rs.next();
    // String password = rs.getString("password");
    // st.close();
    // return "maybe your password is *" + password + "*?";
    // }

    // } finally {
    // conn.close();
    // }
    // } catch (Exception e) {
    // System.out.println("Error while JDBC operating: " + e.getMessage());
    // }
    // return "BAD";
    // }

    @Override
    public String checkUser(UserDTO user) {
        try {
            Query query = entityManager
                    .createQuery("SELECT u FROM EUser u WHERE u.login = :username and u.password= :password")
                    .setParameter("username", user.getLogin()).setParameter("password",
                            user.getPassword());

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

            ERoom roomEntity = new ERoom();
            roomEntity.setType(room.getType());
            roomEntity.setLevel(room.getLevel());
            // roomEntity.setWallsLength(room.getWallsLength());
            // roomEntity.setUserId(userId);

            roomEntity.setLength((int) room.getLength());
            roomEntity.setWidth((int) room.getWidth());

            entityManager.persist(roomEntity);
            userTransaction.commit();
            return "OK";

        } catch (Exception e) {
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return "BAD";
    }

    @Override
    public Room getRoom(Room room, String login) {
        throw new UnsupportedOperationException("Unimplemented method 'getRoom'");
    }

    private void saveRoom(RoomDTO room, int flatId)
            throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException,
            RollbackException, HeuristicMixedException, HeuristicRollbackException {
        UserTransaction userTransaction = (UserTransaction) new InitialContext()
                .lookup("java:comp/UserTransaction");
        userTransaction.begin();
        entityManager.joinTransaction();

        ERoom roomEntity = new ERoom();
        roomEntity.setX_coord(room.getCoords()[0]);
        roomEntity.setY_coord(room.getCoords()[1]);

        roomEntity.setType(room.getType());
        roomEntity.setLevel(room.getLevel());
        roomEntity.setArea(room.getArea());
        roomEntity.setPerimeter(room.getPerimeter());
        roomEntity.setFlatId(flatId);

        double length = room.getLength();
        double width = room.getWidth();
        roomEntity.setLength((int) length);
        roomEntity.setWidth((int) width);

        entityManager.persist(roomEntity);
        System.out.println("DB persist(roomEntity)sucseeds");
        userTransaction.commit();

    }

    @Override
    public String addFlat(FlatDTO flat, String login) {
        try {
            UserTransaction userTransaction = (UserTransaction) new InitialContext()
                    .lookup("java:comp/UserTransaction");
            userTransaction.begin();
            entityManager.joinTransaction();
            Query query = entityManager.createQuery("SELECT u FROM EUser u WHERE u.login = :username", EUser.class)
                    .setParameter("username", login);
            List<EUser> persons = query.getResultList();
            if (persons == null || persons.isEmpty()) {
                System.out.println("DB User doesnt exist");
                return "User doesnt exist";
            }
            System.out.println("DB userId");
            EUser person = persons.get(0);
            int userId = person.getId();

            System.out.println("DB userId sucseeds");

            // int flatId = new BigDecimal(new Date().getTime() / 100 %
            // 1000000000).intValueExact();

            UUID idOne = UUID.randomUUID();
            String str = "" + idOne;
            int uid = str.hashCode();
            String filterStr = "" + uid;
            str = filterStr.replaceAll("-", "");
            Integer flatId = Integer.parseInt(str);

            EFlat flatEntity = new EFlat();
            flatEntity.setId(flatId);
            flatEntity.setUserId(userId);
            flatEntity.setTotalArea(flat.getArea());
            flatEntity.setTotalPerimeter(flat.getPerimeter());

            entityManager.persist(flatEntity);
            userTransaction.commit();
            System.out.println("DB persist(flatEntity) sucseeds");

            for (RoomDTO room : flat.getRooms()) {
                saveRoom(room, flatId);
                // ERoom roomEntity = new ERoom();
                // roomEntity.setX_coord(room.getCoords()[0]);
                // roomEntity.setY_coord(room.getCoords()[1]);

                // roomEntity.setType(room.getType());
                // roomEntity.setLevel(room.getLevel());
                // roomEntity.setArea(room.getArea());
                // roomEntity.setPerimeter(room.getPerimeter());
                // roomEntity.setFlatId(flatId);

                // double length = room.getLength();
                // double width = room.getWidth();
                // roomEntity.setLength((int) length);
                // roomEntity.setWidth((int) width);

                // entityManager.persist(roomEntity);
                // System.out.println("DB persist(roomEntity)sucseeds");
            }

            return Integer.toString(flatId);

        } catch (Exception e) {
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return "BAD";
    }

    @Override
    public FlatDTO getFlat(String login, int flatId) {
        try {
            UserTransaction userTransaction = (UserTransaction) new InitialContext()
                    .lookup("java:comp/UserTransaction");
            userTransaction.begin();
            entityManager.joinTransaction();

            Query query = entityManager.createQuery("SELECT u FROM EFlat u WHERE u.id = :flat_id", EFlat.class)
                    .setParameter("flat_id", flatId);

            List<EFlat> flats = query.getResultList();
            if (flats == null || flats.isEmpty()) {
                System.out.println("DB Flat doesnt exist");
                return null;
            }
            EFlat flat = flats.get(0);
            System.out.println("flats.get succeds");
            FlatDTO flatDTO = new FlatDTO();
            flatDTO.setArea(flat.getTotalArea());
            flatDTO.setPerimeter(flat.getTotalPerimeter());

            query = entityManager.createQuery("SELECT u FROM ERoom u WHERE u.flat_id = :flat_id", ERoom.class)
                    .setParameter("flat_id", flatId);

            List<ERoom> rooms = query.getResultList();
            if (rooms == null || rooms.isEmpty()) {
                System.out.println("DB Rooms doesnt exist");
                return null;
            }

            RoomDTO[] roomDTOs = new RoomDTO[rooms.size()];
            System.out.println("DB before flatDTO.setRooms(roomDTOs)");
            int count = 0;
            for (ERoom room : rooms) {
                RoomDTO roomDTO = new RoomDTO();

                roomDTO.setCoords(new int[] { room.getX_coord(), room.getY_coord() });

                roomDTO.setArea(room.getArea());
                roomDTO.setLevel(room.getLevel());
                roomDTO.setPerimeter(room.getPerimeter());
                roomDTO.setLength(room.getLength());
                roomDTO.setWidth(room.getWidth());
                roomDTO.setType(room.getType());
                roomDTOs[count] = roomDTO;
                count += 1;
            }
            System.out.println(count);
            flatDTO.setRooms(roomDTOs);
            System.out.println("DB  flatDTO.setRooms(roomDTOs); sucseeds");
            return flatDTO;

        } catch (Exception e) {
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Integer> getFlatList(String login) {
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            UserTransaction userTransaction = (UserTransaction) new InitialContext()
                    .lookup("java:comp/UserTransaction");
            userTransaction.begin();
            entityManager.joinTransaction();
            Query query = entityManager.createQuery("SELECT u FROM EUser u WHERE u.login = :username", EUser.class)
                    .setParameter("username", login);
            @SuppressWarnings("unchecked")
            List<EUser> persons = query.getResultList();
            if (persons == null || persons.isEmpty()) {
                System.out.println("DB User doesnt exist");
                return null;
            }
            EUser person = persons.get(0);
            int userId = person.getId();

            query = entityManager.createQuery("SELECT flat FROM EFlat flat WHERE flat.userId = :id", EFlat.class)
                    .setParameter("id", userId);

            @SuppressWarnings("unchecked")
            List<EFlat> flats = query.getResultList();
            if (flats == null || flats.isEmpty()) {
                System.out.println("DB Flat doesnt exist or empty");
                return ids;
            }

            for (EFlat flat : flats) {
                ids.add(flat.getId());
            }

        } catch (Exception e) {
            System.out.println("Error while JPA operating: " + e.getMessage());
        }
        return ids;
    }

}