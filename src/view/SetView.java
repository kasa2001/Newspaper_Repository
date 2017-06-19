package view;

import model.Connect;

public class SetView extends Frame implements View{


    /**
     * Construct create new frame
     * @param view int
     * */
    SetView(int view){
        this.connect = Connect.getConnection();
        this.destroyCurrentView();
        this.seeComponent(this.redirect, this.submit);
        this.addSetView(view);
    }

    /**
     * Method add setter view
     * @param view int
     */
    private void addSetView(int view) {
        switch(view){
            case 0:
                this.setWydawnictwoView();
                break;
            case 1:
                this.setAutorView();
                break;
            case 2:
                this.setCzasopismoView();
                break;
            case 3:
                this.setNumerView();
                break;
        }
    }

    /**
     * Method generate view to set new Wydawnictwo object
     * */
    public void setWydawnictwoView(){
        this.seeComponent(this.setText);
        this.seeComponent(this.getData);
        this.seeComponent(this.redirect, this.submit);
        this.setTextLabels("Podaj nazwę wydawnictwa: ","Podaj nip: ", "Podaj regon: ", "Podaj rok powstania: ");
    }

    /**
     * Method generate view to set new Autor object
     * */
    public void setAutorView(){
        this.seeComponent(this.setText[0],this.setText[1],this.setText[2]);
        this.seeComponent(this.getData[0], this.getData[1], this.getData[2]);
        this.setTextLabels("Podaj imię autora: ","Podaj nazwisko autora: ", "Podaj id wydawnictwa: ");
    }

    /**
     * Method generate view to set new Czasopismo object
     * */
    public void setCzasopismoView(){
        this.seeComponent(this.setText);
        this.seeComponent(this.getData);
        this.setTextLabels("Podaj nazwę czasopisma: ", "Podaj cenę czasopisma: ", "Podaj id autora: ", "Podaj id wydawnictwa: ");
    }

    /**
     * Method generate view to set new Numer object
     * */
    public void setNumerView(){
        this.seeComponent(this.setText[0],this.setText[1],this.setText[2]);
        this.seeComponent(this.getData[0], this.getData[1], this.getData[2]);
        this.setTextLabels("Podaj numer czasopisma: ","Podaj id czasopisma: ", "Podaj stronę na której jest spis treści: ");
    }

}