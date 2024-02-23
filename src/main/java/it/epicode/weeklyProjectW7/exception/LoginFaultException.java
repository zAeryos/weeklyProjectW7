package it.epicode.weeklyProjectW7.exception;

public class LoginFaultException extends RuntimeException{

    public LoginFaultException(String message){
        super(message);
    }
}
