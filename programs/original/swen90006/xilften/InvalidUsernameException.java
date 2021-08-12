package swen90006.xilften;

public class InvalidUsernameException extends Exception
{
    public InvalidUsernameException(String username)
    {
        super("Username " + username + " does not comply with the Xilften rules\n" +
	      "\t- must contains at least " +
	      Xilften.MINIMUM_USERNAME_LENGTH + " characters\n" +
	      "\t- must contain only letters and numbers");
    }
}
