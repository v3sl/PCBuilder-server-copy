package serviceTest;

import by.fpmibsu.PCBuilder.dao.DaoException;
import by.fpmibsu.PCBuilder.entity.User;
import by.fpmibsu.PCBuilder.service.UserServiceImpl;
import by.fpmibsu.PCBuilder.service.utils.GoogleException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserServiceTest {
    @Test(description = "Check isExist with correct input")
    public void testIsExistWithCorrectInput() throws DaoException {
        UserServiceImpl userService = new UserServiceImpl();
        boolean isExist = userService.isExist("fpm.videvich@bsu.by");
        boolean expectedResult = true;
        Assert.assertEquals(isExist, expectedResult);
    }

    @Test(description = "Check isExist with incorrect input")
    public void testIsExistWithIncorrectInput() throws DaoException {
        UserServiceImpl userService = new UserServiceImpl();
        boolean isExist = userService.isExist("");
        boolean expectedResult = false;
        Assert.assertEquals(isExist, expectedResult);
    }

    @Test(description = "Check IsCorrectUser with correct input")
    public void testIsCorrectUserWithCorrectInput() throws DaoException, GoogleException {
        UserServiceImpl userService = new UserServiceImpl();
        boolean isCorrectUser = userService.isCorrectUser("java1@mail.com", "qwerty1");
        boolean expectedResult = true;
        Assert.assertEquals(isCorrectUser, expectedResult);
    }

    @Test(description = "Check IsCorrectUser with incorrect input")
    public void testIsCorrectUserWithIncorrectInput() throws DaoException, GoogleException {
        UserServiceImpl userService = new UserServiceImpl();
        boolean isCorrectUser = userService.isCorrectUser("java1@mail.com", "qwссс");
        boolean expectedResult = false;
        Assert.assertEquals(isCorrectUser, expectedResult);
    }

    @Test(description = "Check IsCorrectUser with correct input")
    public void testGetUserWithCorrectInput() throws DaoException, GoogleException {
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.getUser("java1@mail.com");
        User expectedUser = new User(7, "java1@mail.com", "$argon2id$v=19$m=15360,t=2,p=1$JTMw4W9XbUi1e30Za8Lgv1nu247CkhvO7O8IR7Neld8$StUNMNfSM5Rxk/NF1OLdnyK51v5c6H/d1HJ33Yfp9x8", false, "java1@mail.com", false);
        Assert.assertEquals(user, expectedUser);
    }

    @Test(description = "Check getUser with incorrect input")
    public void testGetUserWithIncorrectInput() throws DaoException, GoogleException {
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.getUser("");
        User expectedUser = new User();
        Assert.assertEquals(user, expectedUser);
    }
}
