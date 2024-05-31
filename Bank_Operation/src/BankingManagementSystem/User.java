package BankingManagementSystem;

//the resultset values that are extracted frpm database will be in set form we need this 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.print("Full Name: ");
        String full_name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if(user_exist(email)) {
            System.out.println("User Already Exists for this Email Address!!");
            return;
        }
        String register_query = "INSERT INTO User(full_name, email, password) VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int affectedRows = preparedStatement.executeUpdate();/* 
            THe executeupdate method used to insert,delete,update values in database ; we are inserting name
            email,password to database*/
            if (affectedRows > 0) { /* 
            If the value get executeupdated and calumns  may arre added,deleted or ipdated more than 0 */
                System.out.println("Registration Successfull!");
            } else {
                System.out.println("Registration Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String login(){
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean user_exist(String email){
        String query = "SELECT * FROM user WHERE email = ?"; /* ? is the placeholder value that will be shown 
        in prepared statement*/
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            /*preparedstatement is a precompiler for sql  */
            preparedStatement.setString(1, email);
            /*ex : ?,?,?(1,2,3) then abover query variaable as only one ? then 1 is refered to email   */
            ResultSet resultSet = preparedStatement.executeQuery(); /*
            Execute all and results will be in resultset */
            if(resultSet.next()){ /*With that resultset find the email from one index to all values    */
                return true;//if found return true else false
            }
            else{
                return false;
            }
        }catch (SQLException e){ //if any thing happen then display rxception 
            e.printStackTrace();
        }
        return false;
    }
}
