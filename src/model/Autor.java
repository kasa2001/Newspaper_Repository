package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Autor extends Model {
    private String name;
    private String surname;
    private int id_publishing_house;
    private Wydawnictwo wydawnictwo;


    /**
     * Construct create new Autor object with reflection
     *
     * @param result ResultSet
     */
    public Autor(ResultSet result) {
        try {
            this.id = result.getInt(1);
            this.name = result.getString(2);
            this.surname = result.getString(3);
            this.id_publishing_house = result.getInt(4);
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e.getMessage());
            System.exit(0);
        }
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
     * Construct create new Autor object
     *
     * @param id_publishing_house int
     * @param name                String
     * @param surname             String
     */
    public Autor(String name, String surname, int id_publishing_house) {
        this.name = name;
        this.surname = surname;
        this.id_publishing_house = id_publishing_house;
        this.id = connect.getID("Autor", name);
    }

    /**
     * Method set Wydawnictwo object
     *
     * @param wydawnictwo Wydawnictwo
     */
    void setWydawnictwo(Wydawnictwo wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }


    /**
     * Method return object wydawnictwo
     *
     * @return Wydawnictwo
     */
    public Wydawnictwo getWydawnictwo() {
        return wydawnictwo;
    }


    /**
     * Method return object name
     *
     * @return String
     */
    public final String getName() {
        return name;
    }


    /**
     * Method return object surname
     *
     * @return String
     */
    public String getSurname() {
        return surname;
    }


    /**
     * Method return object id_publishing_house
     *
     * @return int
     */
    int getId_publishing_house() {
        return id_publishing_house;
    }
}