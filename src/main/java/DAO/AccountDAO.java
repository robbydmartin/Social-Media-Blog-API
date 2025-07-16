package DAO;

import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    /**
     * Function for account login using the account username and password.
     * @param account an account object.
     * @return an account object including the account_id.
     */
    public Account accountLogin(Account account) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account validAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return validAccount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to create new account using username and password.
     * @param new_account an account object.
     * @return the new account object including the generated account_id.
     */
    public Account addAccount(Account new_account) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Account(username, password) VALUES(?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, new_account.getUsername());
            ps.setString(2, new_account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generated_pkey = rs.getInt(1);
                return new Account(generated_pkey, new_account.getUsername(), new_account.getPassword());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
