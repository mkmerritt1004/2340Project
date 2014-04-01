package com.example.m3;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;

/**
 * Database interface class.
 * 
 * @author Tripp
 * @version 1.0
 */
public class DatabaseInterface {
    
    /**
     * String url.
     */
    final String url = "http://intense-garden-9893.herokuapp.com/api/";
    
    /**
     * Registers user.
     * @return the http response 
     * @param name name
     * @param email email
     * @param password password
     * @param passwordConfirmation password confirmation
     * @throws InterruptedException interruption exception
     * @throws ExecutionException execution exception
     */
    public HttpResponse registerUser(String name, String email, String password, String passwordConfirmation) throws InterruptedException, ExecutionException {
        return new RegisterTask().execute(url, name, email, password, passwordConfirmation).get();
    }

    /**
     * Gets accounts for user.
     * 
     * @return the http response 
     * @param authToken authentication token
     * @throws InterruptedException interruption exception
     * @throws ExecutionException execution exception
     */
    public HttpResponse getAccounts(String authToken) throws InterruptedException, ExecutionException {
        return new GetAccountsTask().execute(url, authToken).get();
    }
    
    /**
     * Makes a deposit.
     * 
     * @return the http response
     * @param source source
     * @param effectiveDate effective date
     * @param amount amount
     * @param accountID account identification
     * @param authToken authentication token
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception
     */
    
    public HttpResponse deposit(String source, String effectiveDate, String amount, String accountID, String authToken) throws InterruptedException, ExecutionException {
        return new DepositTask().execute(url, source, effectiveDate, amount, accountID, authToken).get();
    }
    
    /**
     * Login user.
     * 
     * @return the http response
     * @param user user
     * @param pass pass
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse login(String user, String pass) throws InterruptedException, ExecutionException {
        return new LoginTask().execute(url, user, pass).get();
    }
    
    /**
     * Creates an account for a user.
     * 
     * @return the http response
     * @param name name
     * @param accountName account name
     * @param balance balance
     * @param interestRate interest rate
     * @param authToken authentication token
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse createAccount(String name, String accountName, String balance, String interestRate, String authToken) throws InterruptedException, ExecutionException {
        return new CreateAccountTask().execute(url, name, accountName, balance, interestRate, authToken).get();
    }
    
    /**
     * Get category sums for a user.
     * 
     * @return the http response
     * @param startDate start date
     * @param endDate end date
     * @param authToken authentication token
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse getCategorySums(String startDate, String endDate, String authToken) throws InterruptedException, ExecutionException {
        return new GetCategorySumsTask().execute(url, startDate, endDate, authToken).get();
    }
    
    /**
     * Get account info.
     * 
     * @return the http response
     * @param authToken authentication token
     * @param accountID account identification
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse getAccountInfo(String authToken, String accountID) throws InterruptedException, ExecutionException {
        return new GetAccountInfoTask().execute(url, accountID, authToken).get();
    }
    
    /**
     * Get account info.
     * 
     * @return the http response
     * @param reason reason
     * @param category category
     * @param date 
     */
    public HttpResponse withdraw(String reason, String category, String date, String amount, String accountID, String authToken) throws InterruptedException, ExecutionException {
        return new WithdrawTask().execute(url, reason, category, date, amount, accountID, authToken).get();
    }
}
