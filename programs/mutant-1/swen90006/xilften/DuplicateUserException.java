package swen90006.xilften;

public class DuplicateUserException extends Exception 
{
    public DuplicateUserException(String username)
    {
        super("Username already exists: " + username);
    }
}
