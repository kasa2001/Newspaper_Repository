package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Numer extends Model {
    private int number;
    private int id_newspaper;
    private int content_table;
    private Czasopismo czasopismo;

    /**
     * Construct create new Numer object with reflection
     *
     * @param result ResultSet
     */
    public Numer(ResultSet result) {
        try {
            this.id = result.getInt(1);
            this.number = result.getInt(2);
            this.id_newspaper = result.getInt(3);
            this.content_table = result.getInt(4);
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Construct create new Numer object
     *
     * @param number        int
     * @param content_table int
     * @param id_newspaper  int
     */
    public Numer(int number, int id_newspaper, int content_table) {
        this.id = connect.getID("Numer", number);
        this.number = number;
        this.id_newspaper = id_newspaper;
        this.content_table = content_table;
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
     * Method return object number
     *
     * @return int
     */
    public int getNumber() {
        return number;
    }

    /**
     * Method return object id_newspaper
     *
     * @return int
     */
    int getId_newspaper() {
        return id_newspaper;
    }

    /**
     * Method return object content_table
     *
     * @return int
     */
    public int getContent_table() {
        return content_table;
    }

    /**
     * Method return object czasopismo
     *
     * @return Czasopismo
     */
    public Czasopismo getCzasopismo() {
        return czasopismo;
    }

    /**
     * Method set object czasopismo
     *
     * @param czasopismo Czasopismo
     */
    void setCzasopismo(Czasopismo czasopismo) {
        this.czasopismo = czasopismo;
    }

}
