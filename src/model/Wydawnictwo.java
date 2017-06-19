package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Wydawnictwo extends Model {

    private String name;
    private int nip;
    private int regon;
    private int year;


    /**
     * Construct create new Wydawnictwo object with reflection
     *
     * @param result ResultSet
     */
    public Wydawnictwo(ResultSet result) {
        try {
            this.id = result.getInt(1);
            this.name = result.getString(2);
            this.nip = result.getInt(3);
            this.regon = result.getInt(4);
            this.year = result.getInt(5);
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Construct create new Wydawnictwo object
     *
     * @param name  String
     * @param nip   int
     * @param regon int
     * @param year  int
     */
    public Wydawnictwo(int year, int nip, int regon, String name) {
        this.id = connect.getID("Wydawnictwo", name);
        this.year = year;
        this.nip = nip;
        this.regon = regon;
        this.name = name;
    }


    /**
     * Method return object id
     *
     * @return int
     */
    public final int getId() {
        return this.id;
    }

    /**
     * Method getName return object name
     *
     * @return String
     */
    public final String getName() {
        return name;
    }

    /**
     * Method return object year
     *
     * @return int
     */
    public int getYear() {
        return year;
    }

    /**
     * Method return object nip
     *
     * @return int
     */
    public int getNip() {
        return nip;
    }

    /**
     * Method return object regon
     *
     * @return int
     */
    public int getRegon() {
        return regon;
    }

}