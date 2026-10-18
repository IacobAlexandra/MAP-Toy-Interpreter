package interpreter.exceptions;

public class ADTException extends Exception{
    public ADTException(String message){
        super(message); // call Exception constructor, which calls Throwable constructor,
        // which provides the getMessage() method
    }
}
