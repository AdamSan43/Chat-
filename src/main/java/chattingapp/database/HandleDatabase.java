package chattingapp.database;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;

import static chattingapp.database.LoginHelper.checkPassword;
import static chattingapp.database.LoginHelper.passwordHasher;


public class HandleDatabase {
    public static boolean accountResult = false;
    private static Connection connection;


        //coonects to mySQL database on startup
    private static void initalizeConnection() throws SQLException  {
        try {
            String dbHost = System.getenv("DB_HOST");
            String dbPort = System.getenv("DB_PORT");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            String dbName = System.getenv("DB_NAME");
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("CONNECTED TO DATABASE");

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
    public static void publicInitalizeConnection() throws SQLException {
        initalizeConnection();
    }

    private boolean userExists(String username) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultset = statement.executeQuery();

            if (resultset.next()) {
                System.out.println("user already exists!");
                return true;

            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean publicuserExists (String username) throws SQLException {

            return userExists(username);
    }


    private static void createUser(String username, String password) throws SQLException {
                String pass = passwordHasher(password);;


        try {

                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES  (?,?)" );
                insertStatement.setString(1,username);
                insertStatement.setString(2,pass);
                insertStatement.executeUpdate();
                System.out.println("USER CREATED");
                accountResult = true;
                System.out.println(accountResult);



        } catch(SQLException e) {
            e.printStackTrace();
        }



    }
    public static void publicCreateUser (String username, String password) throws SQLException {
            createUser(username, password);
        }

        private static boolean authentication(String username, String password) throws SQLException {

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
                statement.setString(1, username);
                ResultSet resultset = statement.executeQuery();

                if (resultset.next()) {
                    String hash = resultset.getString("password");
                    if(checkPassword(password, hash)) {
                        return true;
                    }

                } else {
                    System.out.println("USER NOT FOUND");
                    return false;
                                    }
            } catch(SQLException e) {
                e.printStackTrace();
            }

        return false;


        }

        public boolean login(String username, String password) throws SQLException {
                return authentication(username,password);
        }


        //logs messages into database
        public static void logMessage(String username, String message, String time) throws SQLException {

            try {
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO messages (username, content, time) VALUES  (?,?,?)");
                insertStatement.setString(1,username);
                insertStatement.setString(2,message);
                insertStatement.setString(3,time);
                insertStatement.executeUpdate();
                System.out.println("message logged");
            }catch(SQLException e) {
                e.printStackTrace();

            }




        }


        }




