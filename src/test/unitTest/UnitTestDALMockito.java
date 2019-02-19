package unitTest;

import dao.KweetDAO;
import dao.UserDAO;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.mock;

public class UnitTestDALMockito {
    @Mock
    private KweetDAO kweetDAO;

    @Mock
    private UserDAO userDAO;

    @BeforeMethod
    public void beforeMethod(){
        kweetDAO = mock(KweetDAO.class);
        userDAO = mock(UserDAO.class);
    }




}
