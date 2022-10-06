package controller;

import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="UserServlet",urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID=1L;
    private UserDAO userDAO;
    public void init(){
        userDAO=new UserDAO();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        String action1=request.getParameter("abc");
        System.out.println(action1);
        if(action==null){
            action="";
        }
//        try{
            switch (action){
                case "create":

                    break;
                case "edit":

            }
//        }catch (SQLException ex){
//            throw new ServletException(ex);
//        }
    }
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        String action=request.getParameter("action");
        String action1=request.getParameter("abc");
        if(action==null){
            action="";
        }
        try{
            switch (action){
                case "create":
                    break;
                case "edit":
                    showEditFrom(request,response);
                    break;
                case "delete":
                    break;
                default:
                    break;
            }
        }catch (SQLException ex){
            throw new ServletException(ex);
        }
    }
    private void showEditFrom(HttpServletRequest request,HttpServletResponse response) throws SQLException ,ServletException,IOException{
        int id=Integer.parseInt(request.getParameter("id"));
//        User existingUser=userDAO.selectUser(id);
        User existingUser =userDAO.geUserById(id);
        RequestDispatcher dispatcher=request.getRequestDispatcher("edit.jsp");
//        request.setAttribute("user",existingUser);
        dispatcher.forward(request,response);
//        User newUser;
//        userDAO.insertUserStore(newUser);
    }
}

