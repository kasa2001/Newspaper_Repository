package view;

class DeleteView extends Frame {

    /**
     * Construct create new DeleteView object
     *
     * @param table boolean
     */
    DeleteView(boolean table) {
        this.table = table;
        if (!this.table)
            this.method = 2;
        else
            this.method = 5;
        addDeleteView();
    }

    /**
     * Method add delete view
     */
    private void addDeleteView() {
        this.destroyCurrentView();
        this.seeComponent(this.redirect);
        if (!this.table) {
            this.deleteCzasopismo();
        } else {
            this.deleteNumer();
        }
        if (!this.objectToSee.isEmpty()) {
            this.seeComponent(this.delete);
            if (this.objectToSee.size() > 1) {
                this.seeComponent(this.moveButton[1]);
            }
        }
    }

    /**
     * Method create deleteCzasopismo view
     */
    private void deleteCzasopismo() {
        this.getDataFromDatabase(2);
        if (!this.objectToSee.isEmpty())
            this.printCzasopismo(2);
        else
            this.setTextLabels("Brak danych");
        this.seeComponent(this.setText);
    }

    /**
     * Method create deleteNumer view
     */
    private void deleteNumer() {
        this.getDataFromDatabase(5);
        if (!this.objectToSee.isEmpty())
            this.printNumer();
        else
            this.setTextLabels("Brak danych");
        this.seeComponent(this.setText[0], this.setText[1], this.setText[2], this.setText[3]);
    }
}