package swen90006.xilften;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

public class PartitioningTests
{
    protected Xilften xilften;

    //Any method annotated with "@Before" will be executed before each test,
    //allowing the tester to set up some shared resources.
    @Before public void setUp()
    {
       xilften = new Xilften();
    }

    //Any method annotated with "@After" will be executed after each test,
    //allowing the tester to release any shared resources used in the setup.
    @After public void tearDown()
    {
    }


    // Test Cases for Register API
    // Registration of Duplicate User throws exception(EC1)
    @Test(expected = swen90006.xilften.DuplicateUserException.class)
    public void duplicateUser_1()
    throws DuplicateUserException, InvalidUsernameException
    {
      xilften.register("userName2","password");
      xilften.register("userName2","password");
    }

    // Size of UserName should be greater than 3 (EC2)
    @Test(expected = swen90006.xilften.InvalidUsernameException.class)
    public void userNameSize_2()
    throws DuplicateUserException, InvalidUsernameException
    {
      xilften.register("us!","password");
    }

    // UserName with only characters is valid (EC3)
    @Test public void userNameChar_3()
    throws DuplicateUserException, InvalidUsernameException
    {
      xilften.register("user","password");
      assertTrue(xilften.isUser("user"));
    }

    // UserName with only digits is valid (EC4)
    @Test public void userNameNum_4()
    throws DuplicateUserException, InvalidUsernameException
    {
      xilften.register("0123456789","password");
      assertTrue(xilften.isUser("0123456789"));
    }

    // UserName could be combination of characters and digits (EC5)
    @Test public void userNameCharNum_5()
    throws DuplicateUserException, InvalidUsernameException
    {
      xilften.register("username123abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ","password");
      assertTrue(xilften.isUser("username123abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
    }

    // Special Characters are not allowed in UserName (EC6)
    @Test(expected = swen90006.xilften.InvalidUsernameException.class)
    public void invalidUser_6()
    throws DuplicateUserException, InvalidUsernameException
    {
      xilften.register("!;]{","password");
    }


    // Test Cases for Rent API
    // Throws user not found exception if user is not registered (EC1)
    @Test(expected = swen90006.xilften.NoSuchUserException.class)
    public void userNotFound_7()
    throws NoSuchUserException, IncorrectPasswordException
    {
      String newUser = "userName2";
      String password = "password";
      Date currentDate = new Date(31,8,2020);
      Date returnDate = new Date(4,9,2020);
      xilften.rent(newUser,password,currentDate,returnDate,Xilften.StreamType.HD);
    }

    // Password is incorrect exception (EC2)
    @Test(expected = swen90006.xilften.IncorrectPasswordException.class)
    public void incorrectPassword_8()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      String newPassword = "password1";
      Date currentDate = new Date(31,8,2020);
      Date returnDate = new Date(4,9,2020);
      xilften.register(userName,password);
      xilften.rent(userName,newPassword,currentDate,returnDate,Xilften.StreamType.HD);
    }

    // Rent movie in february month during leap year (EC3-EC10)
    // Rent of HD movie in leap year while weekdays<=5 (EC3)
    @Test public void rentCount_9()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,2020);
      Date returnDate = new Date(5,3,2020);
      double expectedResult = 5.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during leap year in Feb (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in leap year while weekdays>5 and <=20 (EC4)
    @Test public void rentCount_10()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(24,2,2016);
      Date returnDate = new Date(3,3,2016);
      double expectedResult = 5.1;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during leap year in Feb (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in leap year while weekdays>20 (EC5)
    @Test public void rentCount_11()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(1,2,2000);
      Date returnDate = new Date(1,3,2000);
      double expectedResult = 6.5;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during leap year in Feb (weekend scenario and weekdays>20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in leap year while weekdays<=5 (EC6)
    @Test public void rentCount_12()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(22,2,2000);
      Date returnDate = new Date(29,2,2000);
      double expectedResult = 4.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during leap year in Feb (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in leap year while weekdays>5 and <=20 (EC7)
    @Test public void rentCount_13()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,2008);
      Date returnDate = new Date(6,3,2008);
      double expectedResult = 4.1;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during leap year in Feb (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in leap year while weekdays>20 (EC8)
    @Test public void rentCount_14()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(2,2,2004);
      Date returnDate = new Date(2,3,2004);
      double expectedResult = 5.5;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during leap year in Feb (weekend scenario and weekdays>20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in leap year while weekdays<6 (EC9)
    @Test public void rentCount_15()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,2012);
      Date returnDate = new Date(3,3,2012);
      double expectedResult = 5.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during leap year in Feb (no weekend scenario and weekdays<6)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in leap year while weekdays<6 (EC10)
    @Test public void rentCount_16()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,2012);
      Date returnDate = new Date(4,3,2012);
      double expectedResult = 4.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during leap year in Feb (no weekend scenario and weekdays<6)", expectedResult, actualResult, 0.0f);
    }


    // Rent movie in february month during non-leap year (EC11-EC18)
    // Rent of HD movie in non-leap year while weekdays<=5 (EC11)
    @Test public void rentCount_17()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,2019);
      Date returnDate = new Date(6,3,2019);
      double expectedResult = 5.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during non-leap year in Feb (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in non-leap year while weekdays>5 and <=20 (EC12)
    @Test public void rentCount_18()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(26,2,2018);
      Date returnDate = new Date(6,3,2018);
      double expectedResult = 5.1;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during non-leap year in Feb (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in non-leap year while weekdays>20 (EC13)
    @Test public void rentCount_19()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(1,2,2017);
      Date returnDate = new Date(2,3,2017);
      double expectedResult = 6.5;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during non-leap year in Feb (weekend scenario and weekdays>20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in non-leap year while weekdays<=5 (EC14)
    @Test public void rentCount_20()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,1900);
      Date returnDate = new Date(6,3,1900);
      double expectedResult = 4.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during non-leap year in Feb (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in non-leap year while weekdays>5 and <=20 (EC15)
    @Test public void rentCount_21()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(27,2,1900);
      Date returnDate = new Date(7,3,1900);
      double expectedResult = 4.1;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during non-leap year in Feb (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in non-leap year while weekdays>20 (EC16)
    @Test public void rentCount_22()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(2,2,2015);
      Date returnDate = new Date(3,3,2015);
      double expectedResult = 5.5;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during non-leap year in Feb (weekend scenario and weekdays>20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in non-leap year while weekdays<6 (EC17)
    @Test public void rentCount_23()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(25,2,2019);
      Date returnDate = new Date(2,3,2019);
      double expectedResult = 5.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during non-leap year in Feb (no weekend scenario and weekdays<6)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in non-leap year while weekdays<6 (EC18)
    @Test public void rentCount_24()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(25,2,2019);
      Date returnDate = new Date(4,3,2019);
      double expectedResult = 4.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during non-leap year in Feb (no weekend scenario and weekdays<6)", expectedResult, actualResult, 0.0f);
    }


    // Rent movie in other months(EC19-EC26)
    // Rent of HD movie in other months while weekdays<=5 (EC19)
    @Test public void rentCount_25()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(24,1,2019);
      Date returnDate = new Date(31,1,2019);
      double expectedResult = 5.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during other months (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);

      currentDate = new Date(24,4,2020);
      returnDate = new Date(1,5,2020);
      actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during other months (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in other months while weekdays>5 and <=20 (EC20)
    @Test public void rentCount_26()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(30,3,2020);
      Date returnDate = new Date(7,4,2020);
      double expectedResult = 5.1;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during other months (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);

      currentDate = new Date(24,8,2020);
      returnDate = new Date(1,9,2020);
      actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during other months (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);

    }

    // Rent of HD movie in other months while weekdays>20 (EC21)
    @Test public void rentCount_27()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(3,4,2020);
      Date returnDate = new Date(2,5,2020);
      double expectedResult = 6.5;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during other months (weekend scenario and weekdays>20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in other months while weekdays<=5 (EC22)
    @Test public void rentCount_28()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(30,6,2020);
      Date returnDate = new Date(7,7,2020);
      double expectedResult = 4.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during other months (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);

      currentDate = new Date(24,9,2019);
      returnDate = new Date(1,10,2019);
      actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during other months (weekend scenario and weekdays<=5)", expectedResult, actualResult, 0.0f);

    }

    // Rent of SD movie in other months while weekdays>5 and <=20 (EC23)
    @Test public void rentCount_29()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(29,7,2020);
      Date returnDate = new Date(6,8,2020);
      double expectedResult = 4.1;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during other months (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);

      currentDate = new Date(23,11,2016);
      returnDate = new Date(1,12,2016);
      actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during other months (weekend scenario and weekdays>5 and <=20)", expectedResult, actualResult, 0.0f);

    }

    // Rent of SD movie in other months while weekdays>20 (EC24)
    @Test public void rentCount_30()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(2,8,2018);
      Date returnDate = new Date(31,8,2018);
      double expectedResult = 5.5;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during other months (weekend scenario and weekdays>20)", expectedResult, actualResult, 0.0f);
    }

    // Rent of HD movie in other months while weekdays<6 (EC25)
    @Test public void rentCount_31()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(28,5,2018);
      Date returnDate = new Date(4,6,2018);
      double expectedResult = 5.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
      assertEquals("Error: HD rate calculation during other months (no weekend scenario and weekdays<6)", expectedResult, actualResult, 0.0f);
    }

    // Rent of SD movie in other months while weekdays<6 (EC26)
    @Test public void rentCount_32()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
      String userName = "userName3";
      String password = "password";
      xilften.register(userName,password);
      Date currentDate = new Date(25,5,2020);
      Date returnDate = new Date(31,5,2020);
      double expectedResult = 4.0;
      double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
      assertEquals("Error: SD rate calculation during other months (no weekend scenario and weekdays<6)", expectedResult, actualResult, 0.0f);
    }

}
