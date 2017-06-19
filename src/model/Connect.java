package model;

import exception.SwitchException;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static model.TemporaryBase.temporaryBase;

public class Connect {

    static Connect connect;
    private Connection connection;
    private Statement statement;
    private PreparedStatement pstat;

    /**
     * Private construct to connection with database
     */
    private Connect() {
        this.connection = this.newConnect();
    }

    /**
     * Method connect with database
     *
     * @return java.sql.Connection/null
     */
    private Connection newConnect() {
        java.sql.Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Czasopisma.db");
            this.statement = connection.createStatement();
            this.createTable();
        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
            return null;
        }
        return connection;
    }

    /**
     * Method create table in database if table is not exists
     */
    private void createTable() {
        try {
            this.statement.execute("CREATE TABLE IF NOT EXISTS `Czasopismo`(`id` integer primary key AUTOINCREMENT not null, `name` VARCHAR(255) not null, `cost` DOUBLE not null, `id_publishing_house` int not null, `id_author` int not null)");
            this.statement.execute("CREATE TABLE IF NOT EXISTS `Wydawnictwo`(`id` integer not null primary key AUTOINCREMENT, `name` VARCHAR(255) not null, `nip` int not null, `regon` int not null, `year` int not null)");
            this.statement.execute("CREATE TABLE IF NOT EXISTS `Numer`(`id` integer not null primary key AUTOINCREMENT, `number` int not null,`id_newspaper` int not null, `content_table` int not null)");
            this.statement.execute("CREATE TABLE IF NOT EXISTS `Autor`(`id` integer not null primary key AUTOINCREMENT, `name` VARCHAR(255) not null, `surname` varchar(255) not null, `id_publishing_house` int not null)");
        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }

    /**
     * Synchronized method for getting Connect object
     *
     * @return Connect
     */
    public static synchronized Connect getConnection() {
        if (connect == null)
            connect = new Connect();
        return connect;
    }

    /**
     * Method end connection with database
     */
    public void closeConnect() {
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }

    /**
     * Method select data from database and create reflectively object
     *
     * @return List
     */
    List<Model> select(String table) {
        List<Model> models = new ArrayList<>();
        try {
            Class klasa = Class.forName("model." + table);
            this.pstat = connection.prepareStatement("SELECT * FROM `" + table + "`");
            ResultSet result = this.pstat.executeQuery();
            Constructor constructor = klasa.getConstructor(ResultSet.class);
            while (result.next()) {
                models.add((Model) constructor.newInstance(result));
            }
        } catch (SQLException e) {
            System.err.println("Błąd SQl: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Brak klasy: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Brak konstruktora: " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Nie wywołano kostruktora: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Brak dostępu do konstruktora: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.err.println("Problem z wywołaniem kostruktora: " + e.getMessage());
        }
        return models;
    }

    /**
     * Method get id from table
     *
     * @param number int
     * @param table String
     * @return int
     */
    int getID(String table, int number) {
        try {
            this.pstat = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE `number`=" + number + " ORDER BY `id` DESC");
            return sendSelect();
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e);
        }
        return -1;
    }

    /**
     * Method get id from table
     *
     * @param data  String
     * @param table String
     * @return int
     */
    int getID(String table, String data) {
        try {
            this.pstat = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE `name`='" + data + "' ORDER BY `id` DESC");
            return sendSelect();
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e);
        }
        return -1;
    }

    /**
     * Method send query and return id
     *
     * @return int
     */
    private int sendSelect() throws SQLException {
        ResultSet result = this.pstat.executeQuery();
        return result.getInt(1);
    }

    /**
     * Method insert new data to database
     *
     * @param query String
     */
    private void insertData(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (Exception e) {
            System.err.println("Błąd SQL: " + e);
        }
    }

    /**
     * Method delete data from database
     * This method can be delete from Czasopisma and Numer
     *
     * @param table String
     * @param model Model
     * @param one   boolean
     */
    public void delete(String table, Model model, boolean one) {
        try {
            if (one)
                this.pstat = this.connection.prepareStatement("DELETE FROM `" + table + "` WHERE `id`=" + model.getId());
            else {
                this.pstat = this.connection.prepareStatement("DELETE FROM `Numer` WHERE `id_newspaper`=" + model.getId());
            }
            this.pstat.execute();
        } catch (SQLException e) {
            System.err.println("Błąd SQL: " + e.getMessage());
        }
    }

    /**
     * Method check propriety of String
     *
     * @return String
     * @throws SwitchException if not selected one of possible method
     */
    private String getTableInfo(JLabel setText) throws SwitchException {
        String data = setText.getText();
        switch (data) {
            case "Podaj nazwę wydawnictwa: ":
                return "Wydawnictwo";
            case "Podaj imię autora: ":
                return "Autor";
            case "Podaj nazwę czasopisma: ":
                return "Czasopismo";
            case "Podaj numer czasopisma: ":
                return "Numer";
            default:
                throw new SwitchException("Błąd switcha");
        }
    }

    /**
     * Method get data about field
     *
     * @return array String
     * @throws SwitchException if not selected one of possible method
     */
    private String[] getField(String data) throws SwitchException {
        switch (data) {
            case "Wydawnictwo":
                return this.wydawnictwoField();
            case "Autor":
                return this.autorField();
            case "Czasopismo":
                return this.czasopismoField();
            case "Numer":
                return this.numerField();
            default:
                throw new SwitchException("Błąd switcha");
        }
    }

    /**
     * Method return field in wydawnictwo table
     *
     * @return String array
     */
    private String[] wydawnictwoField() {
        String data[] = {"name", "nip", "regon", "year"};
        return data;
    }

    /**
     * Method return field in autor table
     *
     * @return String array
     */
    private String[] autorField() {
        String data[] = {"name", "surname", "id_publishing_house"};
        return data;
    }

    /**
     * Method return field in czasopismo table
     *
     * @return String array
     */
    private String[] czasopismoField() {
        String data[] = {"name", "cost", "id_author", "id_publishing_house"};
        return data;
    }

    /**
     * Method return field in numer table
     *
     * @return String array
     */
    private String[] numerField() {
        String data[] = {"number", "id_newspaper", "content_table"};
        return data;
    }

    /**
     * Method switch method for preparing sql query
     *
     * @throws SwitchException if not selected one of possible method
     */
    private String getValues(String table, JTextArea... getData) throws SwitchException {
        StringBuilder bf = new StringBuilder();
        switch (table) {
            case "Wydawnictwo":
                bf.append(this.valueWydawnictwo(getData));
                break;
            case "Autor":
                bf.append(this.valueAutor(getData));
                break;
            case "Czasopismo":
                bf.append(this.valueCzasopismo(getData));
                break;
            case "Numer":
                bf.append(this.valueNumer(getData));
                break;
            default:
                throw new SwitchException("Błąd switcha");
        }
        return bf.toString();
    }

    /**
     * Method return field in value from wydawnictwo table
     *
     * @return String
     */
    private String valueWydawnictwo(JTextArea... getData) {
        StringBuilder sb = new StringBuilder();
        sb.append("'" + getData[0].getText() + "', ");
        sb.append("'" + getData[1].getText() + "', '" + getData[2].getText() + "', '" + getData[3].getText() + "')");
        return sb.toString();
    }

    /**
     * Method return field in value from autor table
     *
     * @return String
     */
    private String valueAutor(JTextArea... getData) {
        StringBuilder sb = new StringBuilder();
        sb.append("'" + getData[0].getText() + "', '" + getData[1].getText() + "', ");
        sb.append("'" + getData[2].getText() + "')");
        return sb.toString();
    }

    /**
     * Method return field in value from czasopismo table
     *
     * @return String
     */
    private String valueCzasopismo(JTextArea... getData) {
        StringBuilder sb = new StringBuilder();
        sb.append("'" + getData[0].getText() + "', ");
        sb.append("'" + Double.parseDouble(getData[1].getText()) + "', ");
        sb.append("'" + getData[2].getText() + "', '" + getData[3].getText() + "')");
        return sb.toString();
    }

    /**
     * Method return field in value from numer table
     *
     * @return String
     */
    private String valueNumer(JTextArea... getData) {
        StringBuilder sb = new StringBuilder();
        sb.append("'" + getData[0].getText() + "', '" + getData[1].getText() + "', '" + getData[2].getText() + "')");
        return sb.toString();
    }

    /**
     * Method prepare new SQL query and send to insert method
     */
    public void prepareSQL(JLabel setText, JTextArea... getData) {
        StringBuilder sb = new StringBuilder();
        try {
            String data = this.getTableInfo(setText);
            sb.append("INSERT INTO `" + data + "` (");
            String[] strings = this.getField(data);
            for (int i = 0; i < strings.length; i++) {
                sb.append("`" + strings[i] + "` ");
                if (i != strings.length - 1) sb.append(",");
            }
            sb.append(") VALUES (" + this.getValues(data, getData));
            connect.insertData(sb.toString());
            addToTemporaryBase(data, getData);
        } catch (SwitchException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method switch method for create new object in temporary database
     *
     * @throws SwitchException if not selected one of possible method
     */
    private void addToTemporaryBase(String table, JTextArea... getData) throws SwitchException {
        switch (table) {
            case "Wydawnictwo":
                temporaryBase.setWydawnictwa(new Wydawnictwo(Integer.parseInt(getData[3].getText()), Integer.parseInt(getData[1].getText()), Integer.parseInt(getData[2].getText()), getData[0].getText()));
                break;
            case "Czasopismo":
                temporaryBase.setCzasopismos(new Czasopismo(getData[0].getText(), Double.parseDouble(getData[1].getText()), Integer.parseInt(getData[2].getText()), Integer.parseInt(getData[3].getText())));
                break;
            case "Numer":
                temporaryBase.setNumers(new Numer(Integer.parseInt(getData[0].getText()), Integer.parseInt(getData[1].getText()), Integer.parseInt(getData[2].getText())));
                break;
            case "Autor":
                temporaryBase.setAutors(new Autor(getData[0].getText(), getData[1].getText(), Integer.parseInt(getData[2].getText())));
                break;
            default:
                throw new SwitchException("Błąd switcha");
        }
    }


}