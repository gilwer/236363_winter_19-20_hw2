package olympic;

import olympic.business.ReturnValue;
import olympic.business.Sport;
import org.junit.Test;

import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;

public class BasicSportTest extends AbstractTest {
    @Test
    public void simpleSportTest()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("sp1");
        a.setCity("city1");
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK, ret);
    }

    @Test
    public void simpleSportBadParamTest()
    {
        Sport a = new Sport();
        a.setId(-1);
        a.setName("sp1");
        a.setCity("city1");
        ReturnValue ret = Solution.addSport(a);
        assertEquals(BAD_PARAMS, ret);
    }

    @Test
    public void simpleSportBad2ParamTest()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName(null);
        a.setCity("city1");
        ReturnValue ret = Solution.addSport(a);
        assertEquals(BAD_PARAMS, ret);
    }

    @Test
    public void simpleSportBad3ParamTest()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("adfs");
        a.setCity(null);
        ReturnValue ret = Solution.addSport(a);
        assertEquals(BAD_PARAMS, ret);
    }

    @Test
    public void simpleSportAlreadyExistTest()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("adfs");
        a.setCity("asdfss");
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK, ret);
        Sport b = new Sport();
        b.setId(1);
        b.setName("asd");
        b.setCity("assssss");
        ret = Solution.addSport(a);
        assertEquals(ALREADY_EXISTS, ret);
    }

    @Test
    public void simpleTestGetProfile()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("Artur");
        a.setCity("Brazil");
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK, ret);
        a.setAthletesCount(0);
        Sport b = Solution.getSport(a.getId());
        assertEquals(a,b);
    }

    @Test
    public void simpleSportDeleteTest()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("sp1");
        a.setCity("city1");
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK, ret);
        ret = Solution.deleteSport(a);
        assertEquals(OK, ret);
    }

    @Test
    public void simpleSportDelete2Test()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("sp1");
        a.setCity("city1");
        ReturnValue ret = Solution.deleteSport(a);
        assertEquals(NOT_EXISTS, ret);

    }
}
