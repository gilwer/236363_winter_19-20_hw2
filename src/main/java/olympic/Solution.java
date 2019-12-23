package olympic;

import javafx.util.Pair;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static olympic.business.ReturnValue.*;
import static olympic.data.DBConnector.getSchema;

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
            statements.add("PRIMARY KEY (id_1, id_2)");
            statements.add("CONSTRAINT pos_id1 CHECK (id_1 > 0)");
            statements.add("CONSTRAINT pos_id2 CHECK (id_2 > 0)");
            statements.add("CONSTRAINT not_equal CHECK (id_1 <> id_2)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();
            statements.clear();
            statements.add("CREATE TABLE participators_observers");
            statements.add("athlete_id integer NOT NULL");
            statements.add("sport_id integer NOT NULL");
            statements.add("place integer");
            statements.add("payment integer");
            statements.add("CONSTRAINT pos_id1 CHECK (place > 0)");
            statements.add("CONSTRAINT pos_id2 CHECK (place < 4)");
            statements.add("CONSTRAINT pos_payment CHECK (payment >= 0)");
            statements.add("FOREIGN KEY (athlete_id) REFERENCES athletes(athlete_id) on delete cascade on update cascade");
            statements.add("FOREIGN KEY (sport_id) REFERENCES sports(sport_id) on delete cascade on update cascade");
            statements.add("PRIMARY KEY (athlete_id, sport_id)");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.execute();

            createViews(pstmt, connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void createViews(PreparedStatement pstmt, Connection connection) throws SQLException {
        pstmt = connection.prepareStatement("CREATE VIEW first_place AS\n" +
                "SELECT athlete_id, COUNT(place)\n" +
                "FROM participators_observers\n" +
                "WHERE place=3\n" +
                "GROUP BY athlete_id");
        pstmt.execute();

        pstmt = connection.prepareStatement("CREATE VIEW second_place AS\n" +
                "SELECT athlete_id, COUNT(place)\n" +
                "FROM participators_observers\n" +
                "WHERE place=2\n" +
                "GROUP BY athlete_id");
        pstmt.execute();

        pstmt = connection.prepareStatement("CREATE VIEW third_place AS\n" +
                "SELECT athlete_id, COUNT(place)\n" +
                "FROM participators_observers\n" +
                "WHERE place=1\n" +
                "GROUP BY athlete_id");
        pstmt.execute();

        pstmt = connection.prepareStatement("CREATE VIEW sum_place AS\n" +
                "SELECT athlete_id, SUM(place)\n" +
                "FROM participators_observers\n" +
                "WHERE payment=0\n" +
                "GROUP BY athlete_id");
        pstmt.execute();
    }


    public static void clearTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("DELETE FROM athletes");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE FROM sports");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE FROM friends");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DELETE FROM participators_observers");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static void dropTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            List<String> statements = new ArrayList<>();
            statements.add("DROP VIEW sum_place");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP VIEW first_place");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP VIEW second_place");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP VIEW third_place");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE participators_observers");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE friends");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE athletes");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
            statements.clear();
            statements.add("DROP TABLE sports");
            pstmt = connection.prepareStatement(prepareCreateStatement(statements));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue addAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            List<Object> values = new ArrayList<>();
            values.add(athlete.getId());
            values.add(athlete.getName());
            values.add(athlete.getCountry());
            values.add(athlete.getIsActive());
            pstmt = connection.prepareStatement(prepareInsert("athletes", "athlete_id, athlete_name, country, active", values));
            result = pstmt.executeUpdate() > 0 ? OK : ERROR;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static Athlete getAthleteProfile(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Athlete result = null;
        try {
            pstmt = connection.prepareStatement(prepareSelect("athletes", "*", "WHERE athlete_id=" + athleteId));
            ResultSet resultSet = pstmt.executeQuery();
            result = getAthlete(resultSet);
        } catch (SQLException e) {
            result = Athlete.badAthlete();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static ReturnValue deleteAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            pstmt = connection.prepareStatement(prepareDelete("athletes", "athlete_id=" + String.valueOf(athlete.getId())));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
            pstmt = connection.prepareStatement(prepareDelete("friends", "id_1=" + athlete.getId() + " OR id_2=" +  athlete.getId()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue addSport(Sport sport) {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            List<Object> values = new ArrayList<>();
            values.add(sport.getId());
            values.add(sport.getName());
            values.add(sport.getCity());
            values.add(0);
            pstmt = connection.prepareStatement(prepareInsert("sports", "sport_id, sport_name, city, athletes_counter", values));
            result = pstmt.executeUpdate() > 0 ? OK : ERROR;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static Sport getSport(Integer sportId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Sport result = null;
        try {
            pstmt = connection.prepareStatement(prepareSelect("sports", "*", "WHERE sport_id=" + sportId));
            ResultSet resultSet = pstmt.executeQuery();
            result = getSportResult(resultSet);
        } catch (SQLException e) {
            result = Sport.badSport();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static ReturnValue deleteSport(Sport sport) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result;
        try {
            pstmt = connection.prepareStatement(prepareDelete("sports", "sport_id=" + sport.getId()));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue athleteJoinSport(Integer sportId, Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            List<Object> values = new ArrayList<>();
            values.add(athleteId);
            values.add(sportId);
            pstmt = connection.prepareStatement(prepareSelect("athletes", "active", "WHERE athlete_id=" + athleteId));
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                pstmt.close();
                connection.close();
                return NOT_EXISTS;
            }
            if (resultSet.getBoolean("active")) {
                values.add(0);
            } else {
                values.add(100);
            }
            pstmt = connection.prepareStatement(prepareInsert("participators_observers", "athlete_id,sport_id,payment", values));
            pstmt.executeUpdate();
            if (resultSet.getBoolean("active")) {
                pstmt = connection.prepareStatement(prepareSelect("sports","athletes_counter","WHERE sport_id=" + sportId));
                resultSet = pstmt.executeQuery();
                resultSet.next();
                int athletes_counter = resultSet.getInt("athletes_counter");
                athletes_counter++;
                pstmt = connection.prepareStatement(prepareUpdate("sports","athletes_counter=" + athletes_counter, "sport_id=" + sportId));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            return parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue athleteLeftSport(Integer sportId, Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            pstmt = connection.prepareStatement(prepareUpdate("sports", "athletes_counter=athletes_counter-1","sport_id IN (" + prepareSelect("participators_observers","sport_id","WHERE athlete_id="+athleteId + " AND payment=0 AND sport_id=" + sportId)+")"));
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement(prepareDelete("participators_observers", "sport_id=" + sportId + " AND athlete_id=" + athleteId));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue confirmStanding(Integer sportId, Integer athleteId, Integer place) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            pstmt = connection.prepareStatement(prepareUpdate("participators_observers", "place=" + (4 - place), "sport_id=" + sportId + " AND athlete_id=" + athleteId + " AND payment = 0"));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue athleteDisqualified(Integer sportId, Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            pstmt = connection.prepareStatement(prepareUpdate("participators_observers", "place=" + "NULL", "sport_id=" + sportId + " AND athlete_id=" + athleteId));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue makeFriends(Integer athleteId1, Integer athleteId2) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        if (athleteId1 == athleteId2)
            return BAD_PARAMS;
        if (getAthleteProfile(athleteId1).getId() == -1 || getAthleteProfile(athleteId2).getId() == -1) {
            return NOT_EXISTS;
        }
        ReturnValue result = OK;
        int temp = athleteId1;
        athleteId1 = Math.min(athleteId1, athleteId2);
        athleteId2 = Math.max(temp, athleteId2);
        try {
            List<Object> values = new ArrayList<>();
            values.add(athleteId1);
            values.add(athleteId2);
            pstmt = connection.prepareStatement(prepareInsert("friends", "id_1,id_2", values));
            result = pstmt.executeUpdate() > 0 ? OK : ERROR;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue removeFriendship(Integer athleteId1, Integer athleteId2) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        int temp = athleteId1;
        athleteId1 = Math.min(athleteId1, athleteId2);
        athleteId2 = Math.max(temp, athleteId2);
        try {
            pstmt = connection.prepareStatement(prepareDelete("friends", "id_1="
                    + athleteId1 + " AND id_2=" + athleteId2));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static ReturnValue changePayment(Integer athleteId, Integer sportId, Integer payment) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue result = OK;
        try {
            pstmt = connection.prepareStatement(prepareUpdate("participators_observers", "payment=" + payment + ", sport_id=" + sportId + ", athlete_id=" + athleteId,
                    "sport_id=" + sportId + " AND athlete_id=" + athleteId + " AND payment <> 0"));
            result = pstmt.executeUpdate() > 0 ? OK : NOT_EXISTS;
        } catch (SQLException e) {
            result = parseError(Integer.valueOf(e.getSQLState()));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                result = ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                result = ERROR;
            }
        }
        return result;
    }

    public static Boolean isAthletePopular(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            Athlete athlete = getAthleteProfile(athleteId);
            if(athlete.getId() != -1){
                pstmt = connection.prepareStatement("SELECT COUNT(a.sport_id) AS count FROM" +
                        "(SELECT sport_id FROM participators_observers LEFT JOIN friends ON " +
                        "friends.id_1 = participators_observers.athlete_id OR friends.id_2 = participators_observers.athlete_id " +
                        "WHERE friends.id_1 =" + athleteId  +" OR friends.id_2=" + athleteId + "\n" +
                        "AND participators_observers.athlete_id !=" + athleteId + " ) a " +
                        "WHERE a.sport_id NOT IN (SELECT sport_id FROM participators_observers\n" +
                        "WHERE athlete_id=" + athleteId +" )");

                ResultSet res = pstmt.executeQuery();
                res.next();
                int countResult = res.getInt("count");
                result = countResult == 0;
            }
        } catch (SQLException e) {
            result = false;
        } finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static Integer getTotalNumberOfMedalsFromCountry(String country) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = connection.prepareStatement(prepareSelect("participators_observers JOIN athletes", "COUNT(place)",
                    "ON(participators_observers.athlete_id=athletes.athlete_id AND country=" + convertParam(country) + ")"));
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            result = resultSet.getInt("COUNT");
        } catch (SQLException e) {
            result = 0;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static Integer getIncomeFromSport(Integer sportId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = connection.prepareStatement(prepareSelect("participators_observers", "SUM(payment)",
                    "WHERE sport_id=" + sportId));
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            result = resultSet.getInt("SUM");
        } catch (SQLException e) {
            result = 0;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static String getBestCountry() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        String result = "";
        try {
            pstmt = connection.prepareStatement("SELECT a.country FROM" +
                    "(SELECT country, COUNT (participators_observers.place) AS medals\n" +
                    "FROM participators_observers LEFT JOIN athletes ON " +
                    "participators_observers.athlete_id=athletes.athlete_id " +
                    "GROUP BY country\n" +
                    "ORDER BY medals DESC, country ASC ) a \n" +
                    "WHERE medals!= 0\n" +
                    "LIMIT 1");
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            result = resultSet.getString("country");
        } catch (SQLException e) {
            result = "";
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static String getMostPopularCity() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        String result = "";
        try {
            pstmt = connection.prepareStatement("SELECT city, CAST(AthletsSUM AS DECIMAL)" +
                    "/CAST(NumberOfSport AS DECIMAL) AS Average FROM " +
                    "(SELECT city,COUNT(*)As NumberOfSport,SUM(athletes_counter)AS AthletsSUM " +
                    "FROM sports " +
                    "GROUP BY city) a " +
                    "ORDER by Average DESC, city DESC " +
                    "LIMIT 1");

            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            result = resultSet.getString("city");
        } catch (SQLException e) {
            result = "";
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }


    public static ArrayList<Integer> getAthleteMedals(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> result = null;
        try {
            pstmt = connection.prepareStatement(prepareSelect("first_place FULL OUTER JOIN second_place ON(first_place.athlete_id=second_place.athlete_id) FULL OUTER JOIN third_place ON(third_place.athlete_id=second_place.athlete_id)", "first_place.count, second_place.count, third_place.count",
                    "WHERE first_place.athlete_id=" + convertParam(athleteId)));
            ResultSet resultSet = pstmt.executeQuery();
            result = getResults(resultSet);
            if (result.size() == 0) {
                result = new ArrayList<>(Arrays.asList(0, 0, 0));
            }

        } catch (SQLException e) {
            result = new ArrayList<>(Arrays.asList(0, 0, 0));
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static ArrayList<Integer> getMostRatedAthletes() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> result = null;
        try {
            pstmt = connection.prepareStatement(prepareSelect("sum_place FULL JOIN athletes", "athletes.athlete_id", "ON sum_place.athlete_id=athletes.athlete_id ORDER BY sum_place.sum DESC NULLS LAST, athletes.athlete_id ASC"));
            ResultSet resultSet = pstmt.executeQuery();
            result = getResults(resultSet, 10);

        } catch (SQLException e) {
            result = new ArrayList<>();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return result;
    }

    public static ArrayList<Integer> getCloseAthletes(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt;
        ArrayList<Integer> result = new ArrayList<>();
        try {
            Athlete athlete = getAthleteProfile(athleteId);
            if (athlete.getId() != -1) {
                pstmt = connection.prepareStatement("SELECT COUNT(sport_id) AS  NumberOfSports " +
                        "FROM participators_observers WHERE athlete_id=" + convertParam(athleteId));
                ResultSet res = pstmt.executeQuery();
                if (res.next()) {
                    if (res.getInt("NumberOfSports") == 0) {
                        pstmt = connection.prepareStatement("SELECT athlete_id FROM athletes " +
                                "WHERE athlete_id !=" + convertParam(athleteId) + "\n" +
                                "ORDER BY athlete_id ASC");
                    } else {
                        pstmt = connection.prepareStatement("SELECT athlete_id " +
                                "FROM participators_observers " +
                                "WHERE sport_id IN " +
                                "(SELECT sport_id FROM participators_observers WHERE athlete_id=" + convertParam(athleteId) + ") " +
                                "AND athlete_id !=" + convertParam(athleteId) + " " +
                                "GROUP BY athlete_id " +
                                "HAVING COUNT(sport_id) * 2 >= (SELECT COUNT (sport_id) FROM participators_observers " +
                                "WHERE athlete_id=" + convertParam(athleteId) + ") "  +
                                "ORDER by athlete_id ASC");
                    }
                }
                ResultSet res2 = pstmt.executeQuery();
                result = getResults(res2, 10);
                res2.close();
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    return new ArrayList<>();
                }
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return new ArrayList<>();
            }
        }
        return result;
    }

    public static ArrayList<Integer> getSportsRecommendation(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt;
        ArrayList<Integer> result = new ArrayList<>();
        try {
            Athlete athlete = getAthleteProfile(athleteId);
            if (athlete.getId() != -1) {
                pstmt = connection.prepareStatement("SELECT COUNT(sport_id) AS  NumberOfSports " +
                        "FROM participators_observers WHERE athlete_id=" + convertParam(athleteId));
                ResultSet res = pstmt.executeQuery();
                if (res.next()) {
                    if (res.getInt("NumberOfSports") == 0) {
                        pstmt = connection.prepareStatement("SELECT sport_id " +
                                "FROM participators_observers\n" +
                                "GROUP BY sport_id\n" +
                                "ORDER BY COUNT(athlete_id) DESC, sport_id ASC\n" +
                                "LIMIT 3");
                    } else {
                        pstmt = connection.prepareStatement("SELECT sport_id  FROM " +
                                "(SELECT athlete_id, count(sport_id) " +
                                "FROM participators_observers " +
                                "WHERE sport_id IN " +
                                "(SELECT sport_id FROM participators_observers WHERE athlete_id=" + convertParam(athleteId) + ") " +
                                "AND athlete_id !=" + convertParam(athleteId) + " " +
                                "GROUP BY athlete_id " +
                                "HAVING COUNT(sport_id) * 2 >= (SELECT COUNT (sport_id) FROM participators_observers " +
                                "WHERE athlete_id=" + convertParam(athleteId) + " ) " +
                                "ORDER by athlete_id ASC) AS CloseResults " +
                                "JOIN participators_observers ON participators_observers.athlete_id = CloseResults.athlete_id " +
                                "WHERE sport_id NOT IN (SELECT sport_id FROM participators_observers WHERE athlete_id= " +
                                convertParam(athleteId) + ") " +
                                "GROUP BY sport_id " +
                                "ORDER BY COUNT(participators_observers.athlete_id) DESC, sport_id ASC");
                    }
                }
                pstmt.executeQuery();
                ResultSet res2 = pstmt.executeQuery();
                result = getResults(res2, 10);
                res2.close();
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    return new ArrayList<>();
                }
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return new ArrayList<>();
            }
        }
        return result;
    }


    private static String prepareCreateStatement(List<String> statements) {
        if (statements.size() == 0) {
            return "";
        }
        if (statements.size() == 1) {
            return statements.get(0);
        }
        if (statements.size() == 2) {
            return statements.get(0) + "\n(\n" + statements.get(1) + "\n)";
        }
        return statements.get(0) + "\n(\n" +
                String.join(",\n", statements.subList(1, statements.size())) + "\n)";
    }

    private static String prepareSelect(String table, String params, String additional) {
        return "SELECT " + params + "\nFROM " + table + (additional != null ? "\n" + additional : "");
    }

    private static String prepareUpdate(String table, String params, String where) {
        return "UPDATE " + table + "\nSET " + params + "\nWHERE " + where;
    }

    private static String prepareInsert(String table, String params, List<Object> values) {
        return "INSERT INTO " + table + "(\n " + params + ")\nVALUES " +
                values.stream().map(Solution::convertParam).collect(Collectors.joining(",", "(", ")"));
    }

    private static String prepareDelete(String table, String where) {
        return "DELETE FROM " + table + "\nWHERE " + where;
    }


    private static String queryString(PreparedStatement pstmt) throws SQLException {
        return getSchema(pstmt.executeQuery()).get(0).getValue();
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

    private static Sport getSportResult(ResultSet resultSet) throws SQLException {
        resultSet.next();
        Sport a = new Sport();
        a.setId(resultSet.getInt("sport_id"));
        a.setName(resultSet.getString("sport_name"));
        a.setCity(resultSet.getString("city"));
        a.setAthletesCount(resultSet.getInt("athletes_counter"));
        return a;
    }

    private static String convertParam(Object param) {
        if (param instanceof String) {
            return "'" + param + "'";
        } else return String.valueOf(param);
    }

    private static ReturnValue parseError(int c) {
        if (PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue() == c) return ALREADY_EXISTS;
        if (PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue() == c) return BAD_PARAMS;
        if (PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue() == c) return NOT_EXISTS;
        if (PostgreSQLErrorCodes.CHECK_VIOLATION.getValue() == c) return BAD_PARAMS;
        return ERROR;
    }

    private static ArrayList<Integer> getResults(ResultSet result, Integer limit) throws SQLException {
        ArrayList<Integer> resultLst = new ArrayList<>();
        ArrayList<Pair<String, String>> schema = getSchema(result);
        while (result.next()) {
            for (int i = 1; i <= schema.size(); i++) {
                resultLst.add(result.getInt(i));
                if((limit != null) && (resultLst.size() == limit)) {
                    return resultLst;
                }
            }
        }
        return resultLst;
    }

    private static ArrayList<Integer> getResults(ResultSet result) throws SQLException {
        return getResults(result, null);
    }


}

