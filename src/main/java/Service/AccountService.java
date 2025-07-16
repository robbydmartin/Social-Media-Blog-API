package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    /**
     * No-arg account constructor.
     * @param account
     * @return
     */
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /**
     * Function for account login.
     * @param account an account object.
     * @return the account upon successful login, null if otherwise.
     */
    public Account accountLogin(Account account){
        Account validAccount = accountDAO.accountLogin(account);
        if (validAccount == null) {
            return null;
        } else {
            return validAccount;
        }
    }

    /**
     * Function for adding new account. Checks if input is valid: username must not be blank,
     * password must be more than 4 characters long, and the account must not already exist.
     * @param account an account object.
     * @return the new account, or null if the account does not pass the above criteria.
     */
    public Account addAccount(Account account) {
        Account validAccount = accountDAO.accountLogin(account);
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());
        if (!account.getUsername().isBlank() && account.getPassword().length() > 4 && validAccount == null) {
            Account addedAccount = accountDAO.addAccount(account);
            return addedAccount;
        } else {
            return null;
        }
    }
}
