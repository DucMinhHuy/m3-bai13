package sevier;


import model.User;


import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
//    void insertUser(User user)throws SQLException;
//    User selectUser(int id);
//    List<User>selectAllUser();
//    boolean deleteUser(int id) throws SQLException;
//    boolean updateUser(User user)throws SQLException;
    User geUserById(int id);
    void insertUserStore(User user)throws SQLException;
//    void insertUpdateWithoutTransaction();
    void addUserTransaction(User user,int[] permision);
}
