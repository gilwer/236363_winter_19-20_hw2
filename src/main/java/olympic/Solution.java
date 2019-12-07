package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;
import olympic.data.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static olympic.business.ReturnValue.OK;

public class Solution {
    public static void createTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("CREATE TABLE Athletes");
            statements.add(" athlete_id integer NOT NULL");
            statements.add("athlete_name text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("country text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("active boolean NOT NULL");
            statements.add(" CONSTRAINT \"Athletes_pkey\" PRIMARY KEY (athlete_id)");
            statements.add(" CONSTRAINT pos_id CHECK (athlete_id > 0)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE Sports");
            statements.add("sport_id integer NOT NULL");
            statements.add("sport_name text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("city text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("athletes_counter integer DEFAULT 0");
            statements.add("CONSTRAINT \"Sports_pkey\" PRIMARY KEY (sport_id)");
            statements.add("CONSTRAINT pos_id CHECK (sport_id > 0)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE Friends");
            statements.add("id_1 integer NOT NULL");
            statements.add("id_2 integer NOT NULL");
            statements.add("CONSTRAINT pos_id1 CHECK (id_1 > 0) ");
            statements.add("CONSTRAINT pos_id2 CHECK (id_2 > 0)");
            statements.add("CONSTRAINT not_equal CHECK (id_1 <> id_2)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE Payments");
            statements.add("athlete_id integer NOT NULL");
            statements.add("sport_id integer NOT NULL");
            statements.add("payment integer NOT NULL");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE Participators");
            statements.add("athlete_id integer NOT NULL");
            statements.add("sport_id integer NOT NULL");
            statements.add("medal integer ");
            statements.add("CONSTRAINT pos_id1 CHECK (place > 0)");
            statements.add("CONSTRAINT pos_id2 CHECK (place < 4)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }}

    public static void clearTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("DELETE * FROM Athletes");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM Sports");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM Friends");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM Payments");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM Participators");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
    }

    public static void dropTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("DROP TABLE Athletes");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE Sports");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE Friends");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE Payments");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE Participators");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
    }

    public static ReturnValue addAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> values = new ArrayList<>();
            values.add(String.valueOf(athlete.getId()));
            values.add(athlete.getName());
            values.add(athlete.getCountry());
            values.add(String.valueOf(athlete.getIsActive()));
            pstmt = connection.prepareStatement(prepareInsert("athlete_id, athlete_name, country, active","Athletes",values));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        return OK;
    }

    public static Athlete getAthleteProfile(Integer athleteId) {
        return Athlete.badAthlete();
    }

    public static ReturnValue deleteAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(prepareDelete("Athletes","athlete_id="+String.valueOf(athlete.getId())));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        return OK;
    }

    public static ReturnValue addSport(Sport sport) {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> values = new ArrayList<>();
            values.add(String.valueOf(sport.getId()));
            values.add(sport.getName());
            values.add(sport.getCity());
            values.add(String.valueOf(0));
            pstmt = connection.prepareStatement(prepareInsert("sport_id, sport_name, city, athletes_counter","Sports",values));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        return OK;
    }

    public static Sport getSport(Integer sportId) { return Sport.badSport(); }

    public static ReturnValue deleteSport(Sport sport) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(prepareDelete("Sports","sport_id="+String.valueOf(sport.getId())));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        return OK;
    }

    public static ReturnValue athleteJoinSport(Integer sportId, Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            List<String> values = new ArrayList<>();
            values.add(String.valueOf(athleteId));
            values.add(String.valueOf(sportId));
            pstmt = connection.prepareStatement(prepareSelect("active","Athletes","athlete_id="+String.valueOf(athleteId)));
            if(queryBoolean(pstmt)){
                values.add(String.valueOf(0));
                pstmt = connection.prepareStatement(prepareInsert("athlete_id,sport_id,place","Participators",values));
            } else {
                values.add(String.valueOf(100));
                pstmt = connection.prepareStatement(prepareInsert("athlete_id,sport_id,payment","Payments",values));
            }
            pstmt.executeUpdate();
        }catch (SQLException e){

        }

        return OK;
    }

    public static ReturnValue athleteLeftSport(Integer sportId, Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement(prepareSelect("active","Athletes","athlete_id="+ athleteId));
            String table=queryBoolean(pstmt)?"Participators":"Payments";
            pstmt = connection.prepareStatement(prepareDelete(table,"sport_id="+sportId+ " AND athlete_id=" + athleteId ));
            pstmt.executeUpdate();
        }catch (SQLException e){

        }

        return OK;
    }

    public static ReturnValue confirmStanding(Integer sportId, Integer athleteId, Integer place) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement(prepareUpdate("place="+place,"Participators","sport_id="+sportId+ " AND athlete_id=" + athleteId ));
            pstmt.executeUpdate();
        }catch (SQLException e){

        }

        return OK;
    }

    public static ReturnValue athleteDisqualified(Integer sportId, Integer athleteId) {
        return OK;
    }

    public static ReturnValue makeFriends(Integer athleteId1, Integer athleteId2) {
        return OK;
    }

    public static ReturnValue removeFriendship(Integer athleteId1, Integer athleteId2) {
        return OK;
    }

    public static ReturnValue changePayment(Integer athleteId, Integer sportId, Integer payment) {
        return OK;
    }

    public static Boolean isAthletePopular(Integer athleteId) {
        return true;
    }

    public static Integer getTotalNumberOfMedalsFromCountry(String country) {
        return 0;
    }

    public static Integer getIncomeFromSport(Integer sportId) {
        return 0;
    }

    public static String getBestCountry() {
        return "";
    }

    public static String getMostPopularCity() {
        return "";
    }

    public static ArrayList<Integer> getAthleteMedals(Integer athleteId) {
        return new ArrayList<>();
    }

    public static ArrayList<Integer> getMostRatedAthletes() {
        return new ArrayList<>();
    }

    public static ArrayList<Integer> getCloseAthletes(Integer athleteId) {
        return new ArrayList<>();
    }

    public static ArrayList<Integer> getSportsRecommendation(Integer athleteId) {
        return new ArrayList<>();
    }

    private static String prepareCreateStatement(List<String> statements){
        if(statements.size()==0){
            return "";
        }
        if(statements.size()==1){
            return statements.get(0);
        }
        if(statements.size()==2){
            return statements.get(0)+"\n(\n" +statements.get(1) + "\n)";
        }
        return statements.get(0) + "\n(\n" +
                String.join(",\n",statements.subList(1,statements.size()-2)) +
                statements.get(statements.size()-1) + "\n)";
    }
    private static String prepareSelect(String params,String table,String additional){
        return "SELECT "+ params +"\nFROM " + table + (additional!=null?"\n"+additional:"");
    }

    private static String prepareUpdate(String params,String table,String where){
        return "UPDATE "+ table +"\nSET " + params + "\nWHERE " + where;
    }

    private static String prepareInsert(String params,String table,List<String> values){
        return "INSERT INTO "+ table +"(\n " + params + ")\nVALUES " +
                values.stream().collect(Collectors.joining(",","(",")"));
    }

    private static String prepareDelete(String table,String where){
        return "DELETE "+ table +"\nWHERE " + where;
    }

    private static Boolean queryBoolean(PreparedStatement pstmt) throws SQLException {
        return Boolean.valueOf(DBConnector.getSchema(pstmt.executeQuery()).get(0).getValue());
    }

    private static String queryString(PreparedStatement pstmt) throws SQLException {
        return DBConnector.getSchema(pstmt.executeQuery()).get(0).getValue();
    }

    private static Integer queryInteger(PreparedStatement pstmt) throws SQLException {
        return Integer.valueOf(DBConnector.getSchema(pstmt.executeQuery()).get(0).getValue());
    }




}

