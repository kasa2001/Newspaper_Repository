package model;


public abstract class Model {
    int id;
    Connect connect = Connect.getConnection();

    /**
     * Method return object id
     * @return int
     * */
    public abstract int getId();
}
