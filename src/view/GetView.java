package view;


import model.Connect;

class GetView extends Frame {

    /**
     * Construct create new GetView object
     *
     * @param view int
     */
    GetView(int view) {
        this.connect = Connect.getConnection();
        addGetView(view);
        this.method = view;
    }

    /**
     * Construct create new GetView object
     *
     * @param view int
     * @param data String
     */
    GetView(int view, String data) {
        addGetView(view, data);
    }

    /**
     * Method generate getter view
     *
     * @param view int
     */
    private void addGetView(int view, String... data) {
        this.destroyCurrentView();
        this.getDataFromDatabase(view, data);
        this.seeComponent(this.redirect, this.title);
        if (this.objectToSee != null) {
            if (this.object + 1 < this.objectToSee.size())
                this.seeComponent(this.moveButton[1]);
            this.checkExistsLabel(this.setText);
            if (view == 0 || view == 2 || view == 4)
                this.seeComponent(this.setText);
            else if (view == 1 || view == 3 || view == 5)
                this.seeComponent(this.setText[0], this.setText[1], this.setText[2], this.setText[3]);
            this.jPanels[0].setLayout(this.flowLayout);
            this.convert(view);
        }
    }

    /**
     * Method convert to models and send data to Labels
     *
     * @param view int
     */
    private void convert(int view) {
        if (this.objectToSee.isEmpty())
            this.setTextLabels("Brak danych");
        else {
            if (view == 0) {
                this.printWydawnictwo();
            } else if (view == 1) {
                this.printAutor();
            } else if (view == 2 || view == 3 || view == 4) {
                this.printCzasopismo(view);
            } else if (view == 5) {
                this.printNumer();
            }
        }
    }
}