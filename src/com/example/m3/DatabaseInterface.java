package com.example.m3;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;


public class DatabaseInterface {
	final String url = "http://intense-garden-9893.herokuapp.com/api/";
	
	/**
	 * Registers user
	 * @returns the http response 
	 */
    public HttpResponse registerUser(String name, String email, String password, String passwordConfirmation) throws InterruptedException, ExecutionException {
        return new RegisterTask().execute(url, name, email, password, passwordConfirmation).get();
	}

	/**
	 * Gets accounts for user
	 * @returns the http response 
	 */
	
	public HttpResponse getAccounts(String auth_token) throws InterruptedException, ExecutionException {
		return new GetAccountsTask().execute(url, auth_token).get();
	}
	
	/**
	 * Makes a deposit
	 * @returns the http response 
	 */
	
	public HttpResponse deposit(String source, String effectiveDate, String amount, String account_id, String auth_token) throws InterruptedException, ExecutionException {
		return new DepositTask().execute(url, source, effectiveDate, amount, account_id, auth_token).get();
	}
	
	/**
	 * Login user
	 * @returns the http response 
	 */
	public HttpResponse login(String user, String pass) throws InterruptedException, ExecutionException {
		return new LoginTask().execute(url, user, pass).get();
	}
	
	/**
	 * Creates an account for a user
	 * @returns the http response 
	 */
	public HttpResponse createAccount(String name, String accountName, String balance, String interestRate, String auth_token) throws InterruptedException, ExecutionException {
		return new CreateAccountTask().execute(url, name, accountName, balance, interestRate, auth_token).get();
	}
	
	/**
	 * Get category sums for a user
	 * @returns the http response 
	 */
	public HttpResponse getCategorySums(String startDate, String endDate, String auth_token) throws InterruptedException, ExecutionException {
		return new GetCategorySumsTask().execute(url, startDate, endDate, auth_token).get();
	}
	
	/**
	 * Get account info
	 * @returns the http response 
	 */
	public HttpResponse getAccountInfo(String auth_token, String account_id) throws InterruptedException, ExecutionException {
		return new GetAccountInfoTask().execute(url, account_id, auth_token).get();
	}
	
	/**
	 * Get account info
	 * @returns the http response 
	 */
	public HttpResponse withdraw(String reason, String category, String date, String amount, String account_id, String auth_token) throws InterruptedException, ExecutionException {
		return new WithdrawTask().execute(url, reason, category, date, amount, account_id, auth_token).get();
	}
}
