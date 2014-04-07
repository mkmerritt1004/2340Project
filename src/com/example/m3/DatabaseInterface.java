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
     * @param inputs name, email, password, passwordConfirmation
     * @throws InterruptedException interruption exception
     * @throws ExecutionException execution exception
     */
    public HttpResponse registerUser(String... inputs) throws InterruptedException, ExecutionException {
        return new RegisterTask().execute(url, inputs[0], inputs[1], inputs[2], inputs[3]).get();
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
     * @param inputs source, effectiveDate, amount, accountId, authToken
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception
     */
    
    public HttpResponse deposit(String...inputs) throws InterruptedException, ExecutionException {
        return new DepositTask().execute(url, inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]).get();
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
     * @param inputs name, accountName, balance, interestRate, authToken
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse createAccount(String...inputs) throws InterruptedException, ExecutionException {
        return new CreateAccountTask().execute(url, inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]).get();
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
     * @param accountId account identification
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse getAccountInfo(String authToken, String accountId) throws InterruptedException, ExecutionException {
        return new GetAccountInfoTask().execute(url, accountId, authToken).get();
    }
    
    /**
     * Get account info.
     * 
     * @return the http response
     * @param inputs reason, category, date, amount, accountId, authToken
     * @throws InterruptedException interrupted exception
     * @throws ExecutionException execution exception 
     */
    public HttpResponse withdraw(String...inputs) throws InterruptedException, ExecutionException {
        return new WithdrawTask().execute(url, inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]).get();
    }
}
