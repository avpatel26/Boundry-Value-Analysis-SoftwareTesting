package swen90006.xilften;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

//By extending PartitioningTests, we inherit tests from the script
public class BoundaryTests
    extends PartitioningTests
{

  // Rent movie in february month during leap year
  // Rent of HD movie in leap year while weekdays=20 (Onpoint of EC4)
  @Test public void rentCountBoundary_1()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(6,2,2020);
    Date returnDate = new Date(5,3,2020);
    double expectedResult = 6.5;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
    assertEquals("Error: HD rate calculation during leap year in Feb (weekend scenario and weekdays=20(Onpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of HD movie in leap year while weekdays=6 (Offpoint of EC11)
  @Test public void rentCountBoundary_2()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(3,2,2020);
    Date returnDate = new Date(11,2,2020);
    double expectedResult = 5.1;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
    assertEquals("Error: HD rate calculation during leap year in Feb (no weekend scenario and weekdays=6(Offpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of SD movie in leap year while weekdays=20 (Onpoint of EC7)
  @Test public void rentCountBoundary_3()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(6,2,2020);
    Date returnDate = new Date(5,3,2020);
    double expectedResult = 5.5;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
    assertEquals("Error: SD rate calculation during leap year in Feb (weekend scenario and weekdays=20(Onpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of SD movie in leap year while weekdays=6 (Offpoint of EC10)
  @Test public void rentCountBoundary_4()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(3,2,2020);
    Date returnDate = new Date(11,2,2020);
    double expectedResult = 4.1;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
    assertEquals("Error: SD rate calculation during leap year in Feb (no weekend scenario and weekdays=6(Offpoint))", expectedResult, actualResult, 0.0f);
  }


  // Rent movie in february month during non-leap year
  // Rent of HD movie in non-leap year while weekdays=20 (Onpoint of EC12)
  @Test public void rentCountBoundary_5()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(1,2,2019);
    Date returnDate = new Date(1,3,2019);
    double expectedResult = 6.5;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
    assertEquals("Error: HD rate calculation during non-leap year in Feb (weekend scenario and weekdays=20(Onpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of HD movie in non-leap year while weekdays=6 (Offpoint of EC17)
  @Test public void rentCountBoundary_6()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(4,2,2019);
    Date returnDate = new Date(12,2,2019);
    double expectedResult = 5.1;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
    assertEquals("Error: HD rate calculation during non-leap year in Feb (no weekend scenario and weekdays=6(Offpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of SD movie in non-leap year while weekdays=20 (Onpoint of EC15)
  @Test public void rentCountBoundary_7()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(1,2,2019);
    Date returnDate = new Date(1,3,2019);
    double expectedResult = 5.5;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
    assertEquals("Error: SD rate calculation during non-leap year in Feb (weekend scenario and weekdays=20(Onpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of SD movie in non-leap year while weekdays=6 (Offpoint of EC18)
  @Test public void rentCountBoundary_8()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(4,2,2019);
    Date returnDate = new Date(12,2,2019);
    double expectedResult = 4.1;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
    assertEquals("Error: SD rate calculation during non-leap year in Feb (no weekend scenario and weekdays=6(Offpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent movie in other months
  // Rent of HD movie in other months while weekdays=20 (Onpoint of EC20)
  @Test public void rentCountBoundary_9()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(5,9,2019);
    Date returnDate = new Date(3,10,2019);
    double expectedResult = 6.5;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
    assertEquals("Error: HD rate calculation during other months (weekend scenario and weekdays=20(Onpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of HD movie in other months while weekdays=6 (Offpoint of EC25)
  @Test public void rentCountBoundary_10()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(28,10,2019);
    Date returnDate = new Date(5,11,2019);
    double expectedResult = 5.1;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.HD);
    assertEquals("Error: HD rate calculation during other months (no weekend scenario and weekdays=6(Offpoint))", expectedResult, actualResult, 0.0f);
  }

  // Rent of SD movie in other months while weekdays=20 (Onpoint of EC23)
  @Test public void rentCountBoundary_11()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(7,11,2017);
    Date returnDate = new Date(5,12,2017);
    double expectedResult = 5.5;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
    assertEquals("Error: SD rate calculation during other months (weekend scenario and weekdays=20(Onpoint))", expectedResult, actualResult, 0.0f);
  }

  //Rent of SD movie in other months while weekdays=6 (Offpoint of EC26)
  @Test public void rentCountBoundary_12()
  throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
  {
    String userName = "userName3";
    String password = "password";
    xilften.register(userName,password);
    Date currentDate = new Date(24,12,2018);
    Date returnDate = new Date(1,1,2019);
    double expectedResult = 4.1;
    double actualResult = xilften.rent(userName,password,currentDate,returnDate,Xilften.StreamType.SD);
    assertEquals("Error: SD rate calculation during other months (no weekend scenario and weekdays=6(Offpoint))", expectedResult, actualResult, 0.0f);
  }
}
