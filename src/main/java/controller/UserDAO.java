package controller;

import model.User;
import sevier.IUserDAO;

import java.sql.*;

public class UserDAO implements IUserDAO {
    private String jdbcURl="jdbc:mysql://localhost:3306/thuchanhbai12?useSSL=false";
    private String jdbcUsername="root";
    private String jdbcPassword="Ducminhhuy";
    private static final String INSERT_USERS_SQL="INSERT INTO users(name,email,country) VALUES (?,?,?);";
    private static final String SELECT_USERS_BY_ID="SELECT id,name,email,country from users where id=?";
    private static final String SELECT_ALL_USERS="SELECT * FROM users";
    private static final String DELETE_USERS_SQL="DELETE FROM users WHERE id=?";
    private static final String UPDATE_USERS_SQL="UPDATE users SET name=?,email=?,country=? WHERE id=?";
//    private static final String SQL_INSERT="INSERT INTO "
//    private static final String SQL_UPDATE="UPDATE EMPLOYEE SET"
    public UserDAO() {}
    protected java.sql.Connection getConnection(){
        java.sql.Connection connection=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURl,jdbcUsername,jdbcPassword);
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public User geUserById(int id) {
        User user=null;
        String query="{CALL get_user_by_id(?)}";
        try(Connection connection=getConnection();
            CallableStatement callableStatement=connection.prepareCall(query);
        ) {callableStatement.setInt(1,id);
            ResultSet rs=callableStatement.executeQuery();
            while (rs.next()){
                String name=rs.getString("name");
                String email=rs.getString("email");
                String country=rs.getString("country");
                user=new User(id,name,email,country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    @Override
    public void insertUserStore(User user) throws SQLException {
        String query="{CALL insert_user(?,?,?)";
        try(Connection connection=getConnection();
        CallableStatement callableStatement=connection.prepareCall(query);){
            callableStatement.setString(1,user.getName());
            callableStatement.setString(2,user.getEmail());
            callableStatement.setString(3,user.getCountry());
            System.out.println(callableStatement);
            callableStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    @Override
    public void addUserTransaction(User user, int[] permision) {
        Connection coon=null;
        PreparedStatement pstmt=null;
        PreparedStatement pstmtAssignment=null;
        ResultSet rs=null;
        try{
            coon=getConnection();
            coon.setAutoCommit(false);
            pstmt=coon.prepareStatement(INSERT_USERS_SQL,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,user.getCountry());
            int rowAffected=pstmt.executeUpdate();
            rs =pstmt.getGeneratedKeys();
            int userId=0;
            if(rs.next())
                userId=rs.getInt(1);
            if(rowAffected==1){
                String sqlPivot="INSERT INTO User_permision(user_id,permision_id)"+"VALUE(?,?)";
                pstmtAssignment= coon.prepareStatement(sqlPivot);
                for (int permisionId:permision) {
                    pstmtAssignment.setInt(1,userId);
                    pstmtAssignment.setInt(2,permisionId);
                    pstmtAssignment.executeUpdate();
                }
                coon.commit();
            }else {
                coon.rollback();
            }
        } catch (SQLException ex){
           try{
               if(coon!=null)
               coon.rollback();
           }catch (SQLException e){
               System.out.println(e.getMessage());
           }
            System.out.println(ex.getMessage());
        }
        finally {
            try{
                if(rs!=null)rs.close();
                if(pstmt!=null)pstmt.close();
                if(pstmtAssignment!=null)pstmtAssignment.close();
                if(coon!=null)coon.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

//    @Override
//    public void insertUpdateWithoutTransaction() {
//
//    }

    private void printSQLException(SQLException ex){
        for (Throwable e:ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState:"+((SQLException)e).getSQLState());
                System.err.println("Error Code:"+((SQLException)e).getSQLState());
                System.err.println("Message:"+e.getMessage());
                Throwable t=ex.getCause();
                while (t!=null){
                    System.out.println("Cause:"+t);
                    t=t.getCause();
                }
            }
        }
    }
}
