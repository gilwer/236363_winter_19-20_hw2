
package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;
import org.junit.Ignore;
import org.junit.Test;

import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;


public class BasicAPITests extends AbstractTest {
    @Test
    public void addAthleteToSportTest() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.athleteJoinSport(1, -19);
        assertEquals(NOT_EXISTS, res);

        res = Solution.athleteJoinSport(71, 2);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void addAthleteToSport2Test() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(ALREADY_EXISTS, res);
    }

    @Test
    public void athleteLeftSportTest() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);
        res = Solution.athleteLeftSport(1, 2);
        assertEquals(OK, res);
    }

    @Test
    public void athleteLeftSport2Test() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        res = Solution.athleteLeftSport(1, 2);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void confirmStandingTest1() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.confirmStanding(1,2,1);
        assertEquals(OK,res);
        res = Solution.confirmStanding(1,2,2);
        assertEquals(OK,res);
        res = Solution.confirmStanding(1,2,3);
        assertEquals(OK,res);
    }


    @Test
    public void confirmStandingTest2() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.confirmStanding(1,2,4);
        assertEquals(BAD_PARAMS,res);

        res = Solution.confirmStanding(1,2,0);
        assertEquals(BAD_PARAMS,res);

        res = Solution.confirmStanding(1,2,-10);
        assertEquals(BAD_PARAMS,res);
    }

    @Test
    public void confirmStandingTest4() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.confirmStanding(1, 2, 1);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void confirmStandingTest3() {

        ReturnValue res = Solution.confirmStanding(-1,2,1);
        assertEquals(NOT_EXISTS,res);

        res = Solution.confirmStanding(1,-2,2);
        assertEquals(NOT_EXISTS,res);

    }

    @Test
    public void athleteDisqualified1() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.confirmStanding(1,2,1);
        assertEquals(OK,res);

        res = Solution.athleteDisqualified(1,2);
        assertEquals(OK,res);
    }

    @Test
    public void athleteDisqualified2() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteDisqualified(1,2);
        assertEquals(NOT_EXISTS,res);

        res = Solution.athleteDisqualified(-1,2);
        assertEquals(NOT_EXISTS,res);

        res = Solution.athleteDisqualified(1,-2);
        assertEquals(NOT_EXISTS,res);
    }

    @Test
    public void changePaymentTest1() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.changePayment(2, 1, 1);
        assertEquals(OK, res);
    }

    @Test
    public void changePaymentTest2() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.changePayment(-1, 2, 1);
        assertEquals(NOT_EXISTS, res);
        res = Solution.changePayment(1, -2, 1);
        assertEquals(NOT_EXISTS, res);
        res = Solution.changePayment(2, 1, -9);
        assertEquals(BAD_PARAMS, res);
    }

    @Test
    public void changePaymentTest3() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.changePayment(1, 2, 1);
        assertEquals(NOT_EXISTS, res);

    }



    @Ignore
    @Test
    public void friendsTest() {

        ReturnValue res;
        Athlete a = new Athlete();
        a.setId(4);
        a.setName("Neymar");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);


        a.setId(5);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.makeFriends(4,5);
        assertEquals(OK, res);

        res = Solution.removeFriendship(2,5);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void totalNumberOfMedalsTest1() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.confirmStanding(1,2,1);
        assertEquals(OK,res);
        int result = Solution.getTotalNumberOfMedalsFromCountry("Brazil");
        assertEquals(1,result);
    }

    @Test
    public void totalNumberOfMedalsTest2() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.confirmStanding(1,2,1);
        assertEquals(OK,res);
        int result = Solution.getTotalNumberOfMedalsFromCountry("Isreal");
        assertEquals(0,result);
    }

    @Test
    public void totalNumberOfMedalsTest3() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);
        res = Solution.confirmStanding(1,2,1);
        assertEquals(OK,res);
        s = new Sport();
        s.setId(3);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);
        res = Solution.addSport(s);
        assertEquals(OK, ret);
        res = Solution.athleteJoinSport(3, 2);
        assertEquals(OK, res);
        res = Solution.confirmStanding(3,2,2);
        assertEquals(OK,res);
        a = new Athlete();
        a.setId(4);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        res = Solution.athleteJoinSport(3, 4);
        assertEquals(OK, res);
        res = Solution.confirmStanding(3,4,2);
        assertEquals(OK,res);
        a = new Athlete();
        a.setId(5);
        a.setName("Artur");
        a.setCountry("foo");
        a.setIsActive(true);
        ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        res = Solution.athleteJoinSport(3, 5);
        assertEquals(OK, res);
        res = Solution.confirmStanding(3,5,2);
        assertEquals(OK,res);
        int result = Solution.getTotalNumberOfMedalsFromCountry("Brazil");
        assertEquals(3,result);
    }

    @Test
    public void totalNumberOfMedalsTest4() {

        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);
        res = Solution.confirmStanding(1,2,1);
        assertEquals(OK,res);
        s = new Sport();
        s.setId(3);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);
        res = Solution.addSport(s);
        assertEquals(OK, ret);
        res = Solution.athleteJoinSport(3, 2);
        assertEquals(OK, res);
        res = Solution.confirmStanding(3,2,2);
        assertEquals(OK,res);
        int result = Solution.getTotalNumberOfMedalsFromCountry("Brazil");
        assertEquals(2,result);
        res = Solution.athleteDisqualified(3,2);
        result = Solution.getTotalNumberOfMedalsFromCountry("Brazil");
        assertEquals(1,result);
    }

    @Test
    public void totalIncomeTest1() {
        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        int result = Solution.getIncomeFromSport(1);
        assertEquals(100, result);
    }

    @Test
    public void totalIncomeTest2() {
        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        int result = Solution.getIncomeFromSport(1);
        assertEquals(0, result);
    }

    @Test
    public void totalIncomeTest3() {
        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        int result = Solution.getIncomeFromSport(1);
        assertEquals(0, result);

        result = Solution.getIncomeFromSport(7);
        assertEquals(0, result);
    }

    @Test
    public void totalIncomeTest4() {
        ReturnValue res;
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);

        res = Solution.addSport(s);
        assertEquals(OK, res);

        Athlete a = new Athlete();
        a.setId(2);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.athleteJoinSport(1, 2);
        assertEquals(OK, res);

        res = Solution.changePayment(2, 1,50);
        assertEquals(OK, res);

        int result = Solution.getIncomeFromSport(1);
        assertEquals(50, result);

        a = new Athlete();
        a.setId(3);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ret = Solution.addAthlete(a);
        res = Solution.athleteJoinSport(1, 3);
        assertEquals(OK, res);
        result = Solution.getIncomeFromSport(1);
        assertEquals(150, result);
    }

}


