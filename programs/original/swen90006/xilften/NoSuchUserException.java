package swen90006.xilften;

public class NoSuchUserException extends Exception
{
    public NoSuchUserException (String username)
    {
        super("Username does not exist: " + username);
    }
}
