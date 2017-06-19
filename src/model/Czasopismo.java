package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Czasopismo extends Model {
    private String name;
    private double cost;
    private int id_author;
    private int id_publishing_house;
    private Autor autor;
    private Wydawnictwo wydawnictwo;

    /**
     * Construct create new Czasopismo object with reflection
     *
     * @param result ResultSet
     */
    public Czasopismo(ResultSet result) {
        try {
            this.id = result.getInt(1);
            this.name = result.getString(2);
            this.cost = result.getInt(3);
            this.id_author = result.getInt(4);
            this.id_publishing_house = result.getInt(5);
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Construct create new Czasopismo object
     *
     * @param name                String
     * @param cost                double
     * @param id_publishing_house int
     * @param id_author           int
     */
    public Czasopismo(String name, double cost, int id_author, int id_publishing_house) {
        this.id = connect.getID("Czasopismo", name);
        this.name = name;
        this.cost = cost;
        this.id_author = id_author;
        this.id_publishing_house = id_publishing_house;
    }

    /**
     * Method return object id_autor
     * @return int
     * */
    int getId_author() {
        return id_author;
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
     * Method return object name
     * */
    public final String getName() {
        return name;
    }

    /**
     * Method return object cost
     * @return double
     * */
    public double getCost() {
        return cost;
    }


    /**
     * Method return object id_publishing_house
     * @return int
     * */
    int getId_publishing_house() {
        return id_publishing_house;
    }

    /**
     * Method return object autor
     * @return Autor
     * */
    public Autor getAutor() {
        return autor;
    }

    /**
     * Method set object Autor
     * @param autor Autor
     * */
    void setAutor(Autor autor) {
        this.autor = autor;
    }


    /**
     * Method return object wydawnictwo
     * @return Wydawnictwo
     * */
    public Wydawnictwo getWydawnictwo() {
        return wydawnictwo;
    }

    /**
     * Method set object Wydawnictwo
     * @param wydawnictwo Wydawnictwo
     * */
    void setWydawnictwo(Wydawnictwo wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }
}
