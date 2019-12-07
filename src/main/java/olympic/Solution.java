package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;
import olympic.data.DBConnector;
import olympic.data.PostgreSQLErrorCodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static olympic.business.ReturnValue.*;

public class Solution {
    public static void createTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("CREATE TABLE athletes");
            statements.add(" athlete_id integer NOT NULL");
            statements.add("athlete_name text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("country text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("active boolean NOT NULL");
            statements.add(" CONSTRAINT \"Athletes_pkey\" PRIMARY KEY (athlete_id)");
            statements.add(" CONSTRAINT pos_id CHECK (athlete_id > 0)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE sports");
            statements.add("sport_id integer NOT NULL");
            statements.add("sport_name text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("city text COLLATE pg_catalog.\"default\" NOT NULL");
            statements.add("athletes_counter integer DEFAULT 0");
            statements.add("CONSTRAINT \"Sports_pkey\" PRIMARY KEY (sport_id)");
            statements.add("CONSTRAINT pos_id CHECK (sport_id > 0)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE friends");
            statements.add("id_1 integer NOT NULL");
            statements.add("id_2 integer NOT NULL");
            statements.add("CONSTRAINT pos_id1 CHECK (id_1 > 0)");
            statements.add("CONSTRAINT pos_id2 CHECK (id_2 > 0)");
            statements.add("CONSTRAINT not_equal CHECK (id_1 <> id_2)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE payments");
            statements.add("athlete_id integer NOT NULL");
            statements.add("sport_id integer NOT NULL");
            statements.add("payment integer NOT NULL");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE participators");
            statements.add("athlete_id integer NOT NULL");
            statements.add("sport_id integer NOT NULL");
            statements.add("place integer NOT NULL");
            statements.add("CONSTRAINT pos_id1 CHECK (place >= 0)");
            statements.add("CONSTRAINT pos_id2 CHECK (place < 4)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }}

    public static void clearTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("DELETE * FROM athletes");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM sports");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM friends");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM payments");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE * FROM participators");
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
            statements.add("DROP TABLE athletes");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE sports");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE friends");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE payments");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE participators");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
    }

    public static ReturnValue addAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            List<Object> values = new ArrayList<>();
            values.add(athlete.getId());
            values.add(athlete.getName());
            values.add(athlete.getCountry());
            values.add(athlete.getIsActive());
            pstmt = connection.prepareStatement(prepareInsert("athletes", "athlete_id, athlete_name, country, active", values));
            result=pstmt.executeUpdate();
        } catch (SQLException e) {
            return parseError(Integer.valueOf(e.getSQLState()));
        }
        return result>0?OK:ERROR;
    }

    public static Athlete getAthleteProfile(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Athlete result = null;
        try{
            pstmt = connection.prepareStatement(prepareSelect("athletes", "*", "WHERE athlete_id="+ athleteId));
            ResultSet resultSet = pstmt.executeQuery();
            result =getAthlete(resultSet);
        }catch (SQLException e){
            result = Athlete.badAthlete();
        }
        return result;
    }

    public static ReturnValue deleteAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = connection.prepareStatement(prepareDelete("athletes","athlete_id="+String.valueOf(athlete.getId())));
            result=pstmt.executeUpdate();
        } catch (SQLException e) {
            return parseError(Integer.valueOf(e.getErrorCode()));
        }
        return result>0?OK:NOT_EXISTS;
    }

    public static ReturnValue addSport(Sport sport) {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<Object> values = new ArrayList<>();
            values.add(sport.getId());
            values.add(sport.getName());
            values.add(sport.getCity());
            values.add(0);
            pstmt = connection.prepareStatement(prepareInsert("sports", "sport_id, sport_name, city, athletes_counter", values));
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
            pstmt = connection.prepareStatement(prepareDelete("sports","sport_id="+String.valueOf(sport.getId())));
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
            List<Object> values = new ArrayList<>();
            values.add(athleteId);
            values.add(sportId);
            pstmt = connection.prepareStatement(prepareSelect("athletes", "active", "athlete_id="+String.valueOf(athleteId)));
            if(queryBoolean(pstmt)){
                values.add(0);
                pstmt = connection.prepareStatement(prepareInsert("participators", "athlete_id,sport_id,place", values));
            } else {
                values.add(100);
                pstmt = connection.prepareStatement(prepareInsert("payments", "athlete_id,sport_id,payment", values));
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
            pstmt = connection.prepareStatement(prepareSelect("athletes", "active", "athlete_id="+ athleteId));
            String table=queryBoolean(pstmt)?"participators":"payments";
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
            pstmt = connection.prepareStatement(prepareUpdate("participators", "place="+place, "sport_id="+sportId+ " AND athlete_id=" + athleteId ));
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
                String.join(",\n",statements.subList(1,statements.size())) + "\n)";
    }
    private static String prepareSelect(String table, String params, String additional){
        return "SELECT "+ params +"\nFROM " + table + (additional!=null?"\n"+additional:"");
    }

    private static String prepareUpdate(String table, String params, String where){
        return "UPDATE "+ table +"\nSET " + params + "\nWHERE " + where;
    }

    private static String prepareInsert(String table, String params, List<Object> values){
        return "INSERT INTO "+ table +"(\n " + params + ")\nVALUES " +
                values.stream().map(Solution::convertParam).collect(Collectors.joining(",","(",")"));
    }

    private static String prepareDelete(String table,String where){
        return "DELETE FROM "+ table +"\nWHERE " + where;
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

    private static Athlete getAthlete(ResultSet result) throws SQLException {
        result.next();
        Athlete a = new Athlete();
        a.setId(result.getInt("athlete_id"));
        a.setName(result.getString("athlete_name"));
        a.setCountry(result.getString("country"));
        a.setIsActive(result.getBoolean("active"));
        return a;
    }

    private static String convertParam(Object param){
        if(param instanceof String){
            return "'" + param + "'";
        }
        else return String.valueOf(param);
    }

    private static ReturnValue parseError(int c){
        if(PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue()==c) return ALREADY_EXISTS;
        if(PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue()==c) return BAD_PARAMS;
        if(PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()==c) return BAD_PARAMS;
        if(PostgreSQLErrorCodes.CHECK_VIOLATION.getValue()==c) return BAD_PARAMS;
        return ERROR;
    }



}

