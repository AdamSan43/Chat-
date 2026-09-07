package chattingapp.database;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginHelper {

    char[] symbols = "!@#$%^&*()-_=+[]{} ;:'\",.<>/?\\|`~".toCharArray();


    public boolean userNameRules(String username) {
        boolean length;
        boolean symbol = false;
        //rules for username length/ allowed symbols
        // returns true if username is appropriate length and no symbols are found

        if (!username.isEmpty() && username.length() < 26) {
            length = true;
        }
        else {
            length = false;
        }

        for (int i = 0; i < username.length(); i++) {
            for (int k = 0; k < symbols.length; k++) {
                if (username.charAt(i) == symbols[k]) {
                    symbol = true;
                }
            }

        }

        if ((length) && (!symbol)) {
            return true;
        } else {
            return false;
        }

    }



    public boolean passwordRules(String password) {
        boolean length;
        boolean symbol = false;
        //password has to have a symbol and be atleast 8 charcters long
        if(!password.isEmpty() && password.length() >= 8) {
            length = true;
        } else {
            length = false;
        }


        for(int i = 0; i < password.length();i++) {
            for(int k = 0; k < symbols.length;k++) {
                if(password.charAt(i) == symbols[k]) {
                    symbol = true;
                }
            }
        }

        if ((length) && (symbol)) {
            return true;
        } else {
            return false;
        }


    }

    public static String passwordHasher(String plainPass) {
        return BCrypt.hashpw(plainPass, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPass, String hash) {
            return BCrypt.checkpw(plainPass,hash);
    }


}