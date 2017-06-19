package model;

import java.util.ArrayList;
import java.util.List;
import static model.Connect.connect;

public class TemporaryBase {

    private List<Wydawnictwo> wydawnictwa = new ArrayList<>();
    private List<Numer> numers = new ArrayList<>();
    private List<Autor> autors = new ArrayList<>();
    private List<Czasopismo> czasopismos = new ArrayList<>();
    private static TemporaryBase temporaryBase;

    /**
     * Private construct class Temporary base
     * */
    private TemporaryBase() {
        connect = Connect.getConnection();
        this.convert(connect.select("Wydawnictwo"), 1);
        this.convert(connect.select("Numer"), 2);
        this.convert(connect.select("Autor"), 3);
        this.convert(connect.select("Czasopismo"), 4);
    }

    /**
     * Method convert data from reflection
     * */
    private void convert(List<Model> models, int number) {
        for (Model model : models) {
            switch (number) {
                case 1:
                    this.wydawnictwa.add((Wydawnictwo) model);
                    break;
                case 2:
                    this.numers.add((Numer) model);
                    break;
                case 3:
                    this.autors.add((Autor) model);
                    break;
                case 4:
                    this.czasopismos.add((Czasopismo) model);
            }
        }
    }

    /**
     * Method return wydawnictwo object list
     * @return list
     * */
    public List<Wydawnictwo> getWydawnictwa() {
        return wydawnictwa;
    }

    /**
     * Method return numer object list
     * @return list
     * */
    public List<Numer> getNumers() {
        List<Numer> numers = new ArrayList<>();
        for (Czasopismo czasopismo : this.czasopismos)
            for (Numer numer : this.numers)
                if (numer.getId_newspaper() == czasopismo.getId()) {
                    numer.setCzasopismo(czasopismo);
                    numers.add(numer);
                }
        return numers;
    }

    /**
     * Method return czasopismo object list
     * @return list
     * */
    public List<Czasopismo> getCzasopismos() {
        List<Czasopismo> czasopismos = new ArrayList<>();
        for (Czasopismo czasopismo : this.czasopismos)
            for (Autor autor : this.autors)
                if (autor.getId() == czasopismo.getId_author())
                    for (Wydawnictwo wydawnictwo : wydawnictwa)
                        if (wydawnictwo.getId() == czasopismo.getId_publishing_house()) {
                            czasopismo.setWydawnictwo(wydawnictwo);
                            czasopismo.setAutor(autor);
                            czasopismos.add(czasopismo);
                        }
        return czasopismos;
    }

    /**
     * Method return czasopismo object list which the author wrote
     * @return list
     * */
    public List<Czasopismo> getAuthorCzasopismo(int id) {
        List<Czasopismo> models = new ArrayList<>();
        for (Autor autor : autors)
            if (autor.getId() == id)
                for (int i = 0; i < czasopismos.size(); i++) {
                    Czasopismo czasopismo = czasopismos.get(i);
                    if (czasopismo.getId_author() == id) {
                        czasopismo.setAutor(autor);
                        models.add(czasopismo);
                    }
                }
        return models;
    }


    /**
     * Method return czasopismo object list belong to the publishing house
     * @return list
     * */
    public List<Czasopismo> getPublishingHouseNewspaper(int id) {
        List<Czasopismo> czasopismos = new ArrayList<>();
        for (Wydawnictwo wydawnictwo : wydawnictwa) {
            if (wydawnictwo.getId() == id) {
                for (Czasopismo czasopismo : this.czasopismos)
                    if (wydawnictwo.getId() == czasopismo.getId_publishing_house()) {
                        czasopismo.setWydawnictwo(wydawnictwo);
                        czasopismos.add(czasopismo);
                    }
            }
        }
        return czasopismos;
    }

    /**
     * Method return autor object list
     * @return list
     * */
    public List<Autor> getAutors() {
        List<Autor> autors = new ArrayList<>();
        for (Autor autor : this.autors) {
            for (Wydawnictwo wydawnictwo : wydawnictwa) {
                if (autor.getId_publishing_house() == wydawnictwo.getId()){
                    autor.setWydawnictwo(wydawnictwo);
                    autors.add(autor);
                }
            }
        }
        return autors;
    }

    /**
     * Method remove czasopismo object from temporary database
     * */
    public void deleteCzasopismo(Czasopismo czasopismo) {
        this.czasopismos.remove(czasopismo);
    }

    /**
     * Method remove numer object from temporary database
     * */
    public void deleteNumer(boolean how, int id) {
        for (int i = 0; i < this.numers.size(); i++) {
            Numer numer = this.numers.get(i);
            if (!how) {
                if (numer.getId() == id) {
                    this.numers.remove(numer);
                    break;
                }
            }else{
                if (numer.getId_newspaper() == id){
                    this.numers.remove(numer);
                }
            }
        }
    }

    /**
     * Method add wydawnictwo object to list
     * */
    public void setWydawnictwa(Wydawnictwo wydawnictwa) {
        this.wydawnictwa.add(wydawnictwa);
    }

    /**
     * Method add numer object to list
     * */
    public void setNumers(Numer numer) {
        this.numers.add(numer);
    }

    /**
     * Method add autor object to list
     * */
    public void setAutors(Autor autor) {
        this.autors.add(autor);
    }

    /**
     * Method add czasopismo object to list
     * */
    public void setCzasopismos(Czasopismo czasopismo) {
        this.czasopismos.add(czasopismo);
    }

    /**
     * Method create a new object or passes a references for exists object
     * @return TemporaryBase
     * */
    public static synchronized TemporaryBase getTemporaryBase(){
        if (temporaryBase == null){
            temporaryBase = new TemporaryBase();
        }
        return temporaryBase;
    }
}
