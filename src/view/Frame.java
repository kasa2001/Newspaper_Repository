package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import exception.*;

/**
 * Class create a new Frame
 */
public class Frame extends JFrame {
    JPanel[] jPanels;
    JLabel title;
    private JButton[] getButtons;
    private JButton[] setButtons;
    private JButton[] deleteButtons;
    JLabel[] setText;
    JButton submit;
    JButton redirect;
    JButton delete;
    JTextArea[] getData;
    Connect connect;
    private GridLayout gridLayout = new GridLayout(9, 1);
    FlowLayout flowLayout = new FlowLayout();
    private TemporaryBase temporaryBase;
    List objectToSee;
    JButton[] moveButton = new JButton[2];
    int object;
    int method;
    boolean table;
    private Frame frame;

    /**
     * Construct create application view
     */
    public Frame() {
        super("Baza czasopism");
        this.connect = Connect.getConnection();
        this.temporaryBase = TemporaryBase.getTemporaryBase();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connect.closeConnect();
                super.windowClosing(e);
            }
        });
        this.createComponents();
        this.createComponents();
        this.setComponentLayout();
        this.placeAllComponent();
        this.mainView();
        this.addMainText();
        this.setStandardButtonAction();
        this.setGetButtonsAction();
        this.setSetButtonsAction();
        this.setDeleteButtonsAction();
        pack();
        setVisible(true);
    }

    /**
     * Method set size components on application
     *
     * @param x         int
     * @param y         int
     * @param component varargs
     */
    private void setSize(int x, int y, JComponent... component) {
        for (int i = 0; i < component.length; i++)
            component[i].setPreferredSize(new Dimension(x, y));

    }

    /**
     * Method create components for application
     */
    private void createComponents() {
        this.createPanel();
        this.createButton();
        this.createTextArea();
        this.createLabel();
        this.setSize(500, 20, this.setText);
        this.setSize(500, 20, this.getData);
    }

    /**
     * Method create new JLabel object
     */
    private void createLabel() {
        this.setText = new JLabel[5];
        this.title = new JLabel("Czasopismo");
        for (int i = 0; i < setText.length; i++)
            setText[i] = new JLabel();
    }

    /**
     * Method create new JTextArea object
     */
    private void createTextArea() {
        this.getData = new JTextArea[4];
        for (int i = 0; i < getData.length; i++)
            getData[i] = new JTextArea();
    }

    /**
     * Method create new JButton object
     */
    private void createButton() {
        this.getButtons = new JButton[6];
        this.setButtons = new JButton[4];
        this.deleteButtons = new JButton[2];
        this.redirect = new JButton("Powrót do menu");
        this.submit = new JButton("Wyślij dane");
        for (int i = 0; i < this.moveButton.length; i++)
            moveButton[i] = new JButton();
        this.delete = new JButton("Usuń rekord");
    }

    /**
     * Method set layout for panels
     */
    private void setComponentLayout() {
        jPanels[3].setLayout(new FlowLayout());
        jPanels[2].setLayout(new GridLayout(6, 1));
        jPanels[1].setLayout(new GridLayout(6, 1));
        jPanels[0].setLayout(new GridLayout(9, 1));
    }

    /**
     * Method place all component in application
     */
    private void placeAllComponent() {
        this.checkExistsButton(getButtons);
        this.checkExistsButton(setButtons);
        this.checkExistsButton(deleteButtons);
        this.addComponents(2, this.getButtons);
        this.addComponents(1, this.setButtons);
        this.addComponents(1, this.deleteButtons);
        this.addComponents(4, this.moveButton[0], this.redirect, this.submit, this.delete, this.moveButton[1]);
        this.addComponents(0, this.setText[0], this.getData[0], this.setText[1], this.getData[1], this.setText[2], this.getData[2], this.setText[3], this.getData[3], this.setText[4]);
        destroyCurrentView();
    }

    /**
     * Method add main panel
     */
    private void createPanel() {
        this.jPanels = new JPanel[5];
        for (int i = 0; i < jPanels.length; i++)
            jPanels[i] = new JPanel();
        add(jPanels[0]);
        add(BorderLayout.EAST, jPanels[1]);
        add(BorderLayout.WEST, jPanels[2]);
        add(BorderLayout.NORTH, jPanels[3]);
        add(BorderLayout.SOUTH, jPanels[4]);
    }

    /**
     * Method add components in selected panel
     *
     * @param panel      int
     * @param components varargs
     */
    private void addComponents(int panel, JComponent... components) {
        for (JComponent jComponent : components) {
            this.jPanels[panel].add(jComponent);
        }
    }

    /**
     * Method add main text for const components
     */
    private void addMainText() {
        this.jPanels[3].add(this.title);
        this.getButtons[0].setText("Wyświetl wydawnictwa");
        this.getButtons[1].setText("Wyświetl autorów");
        this.getButtons[2].setText("Wyświetl tytuły");
        this.getButtons[3].setText("Wyświetl tytuły wydawane przez wydawnictwo");
        this.getButtons[4].setText("Wyświetl tytuły wydawane przez autora");
        this.getButtons[5].setText("Wyświetl numery tytułu");
        this.setButtons[0].setText("Dodaj wydawnictwo");
        this.setButtons[1].setText("Dodaj autora");
        this.setButtons[2].setText("Dodaj czasopismo");
        this.setButtons[3].setText("Dodaj numer czasopisma");
        this.deleteButtons[0].setText("Usuń czasopismo");
        this.deleteButtons[1].setText("Usuń numer");
        this.moveButton[0].setText("Poprzedni");
        this.moveButton[1].setText("Następny");
    }

    /**
     * Method set action for standard button
     */
    private void setStandardButtonAction() {
        this.submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect.prepareSQL(setText[0],getData);
                closeFrame(e);
            }
        });
        this.redirect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeFrame(e);
            }
        });
        this.moveButton[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftButton();
            }
        });
        this.moveButton[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightButton();
            }
        });
        this.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!table) {
                    connect.delete("Czasopismo", (Model) objectToSee.get(object), true);
                    connect.delete("Numer", (Model) objectToSee.get(object), false);
                    temporaryBase.deleteNumer(true, ((Model) objectToSee.get(object)).getId());
                    temporaryBase.deleteCzasopismo((Czasopismo) objectToSee.get(object));
                } else {
                    connect.delete("Numer", (Model) objectToSee.get(object), true);
                    temporaryBase.deleteNumer(false, ((Model) objectToSee.get(object)).getId());
                }
                closeFrame(e);
            }
        });
    }

    /**
     * Method set action for getter buttons
     */
    private void setGetButtonsAction() {
        this.getButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new GetView(0);
            }
        });
        this.getButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new GetView(1);
            }
        });
        this.getButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new GetView(2);
            }
        });
        this.getButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new GetView(3, getData[0].getText());
            }
        });
        this.getButtons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new GetView(4, getData[0].getText());
            }
        });
        this.getButtons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new GetView(5);
            }
        });

    }

    /**
     * Method set action for setter buttons
     */
    private void setSetButtonsAction() {
        this.setButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new SetView(0);
            }
        });
        this.setButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new SetView(1);
            }
        });
        this.setButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new SetView(2);
            }
        });
        this.setButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new SetView(3);
            }
        });
    }

    /**
     * Method set action for delete buttons
     */
    private void setDeleteButtonsAction() {
        this.deleteButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new DeleteView(false);
            }
        });
        this.deleteButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new DeleteView(true);
            }
        });
    }

    /**
     * Action for preview button
     */
    private void leftButton() {
        this.object--;
        if (this.object == 0) hideComponent(this.moveButton[0]);
        seeComponent(this.moveButton[1]);
        try {
            this.selectMethod();
        } catch (SwitchException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Action for next button
     */
    private void rightButton() {
        this.object++;
        seeComponent(this.moveButton[0]);
        if (this.objectToSee.size() == (this.object + 1)) hideComponent(this.moveButton[1]);
        try {
            selectMethod();
        } catch (SwitchException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Method switch method for generate view
     *
     * @throws SwitchException if not selected one of possible method
     */
    private void selectMethod() throws SwitchException {
        switch (this.method) {
            case 0:
                this.printWydawnictwo();
                break;
            case 1:
                this.printAutor();
                break;
            case 2:
                this.printCzasopismo(this.method);
                break;
            case 3:
                this.printCzasopismo(this.method);
                break;
            case 4:
                this.printCzasopismo(this.method);
                break;
            case 5:
                this.printNumer();
                break;
            default:
                throw new SwitchException("Błędny parametr");
        }
    }

    /**
     * Method close frame
     *
     * @param e ActionEvent
     */
    private void closeFrame(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        JPanel jPanel = (JPanel) jButton.getParent();
        JPanel panel = (JPanel) jPanel.getParent();
        JLayeredPane el = (JLayeredPane) panel.getParent();
        JRootPane er = (JRootPane) el.getParent();
        JFrame frame = (JFrame) er.getParent();
        frame.dispose();
    }

    /**
     * Method add main menu view
     */
    private void mainView() {
        this.seeComponent(this.getButtons);
        this.seeComponent(this.setButtons);
        this.seeComponent(this.deleteButtons);
        this.seeComponent(this.title);
        this.seeComponent(this.setText[0], this.getData[0]);
        this.setText[0].setText("Podaj id: ");
        this.jPanels[0].setLayout(gridLayout);
    }

    /**
     * Method check exists buttons
     *
     * @param button varargs
     */
    private void checkExistsButton(JButton... button) {
        for (int i = 0; i < button.length; i++)
            if (button[i] == null)
                button[i] = new JButton();
        this.setSize(300, 100, button);
    }

    /**
     * Method check exists labels
     *
     * @param label varargs
     */
    void checkExistsLabel(JLabel... label) {
        for (int i = 0; i < label.length; i++)
            if (label[i] == null)
                label[i] = new JLabel();
    }

    /**
     * Method hidden components
     *
     * @param components varargs
     */
    private void hideComponent(JComponent... components) {
        for (int i = 0; i < components.length; i++)
            components[i].setVisible(false);
    }

    /**
     * Method hidden all view
     */
    void destroyCurrentView() {
        this.object = 0;
        for (JPanel panel : jPanels) {
            Component[] components = panel.getComponents();
            for (Component component : components) {
                component.setVisible(false);
            }
        }
        this.setTextLabels("", "", "", "", "");
    }

    /**
     * Method discovered the components
     *
     * @param components JComponent varargs
     */
    void seeComponent(JComponent... components) {
        for (int i = 0; i < components.length; i++) {
            components[i].setVisible(true);
        }
    }

    /**
     * Method print data about wydawnictwo
     */
    void printWydawnictwo() {
        Wydawnictwo model = (Wydawnictwo) this.objectToSee.get(object);
        this.setTextLabels("Id wydawnictwa: " + model.getId(), "Nazwa wydawnicta: " + model.getName(), "NIP: " + Integer.toString(model.getNip()), "REGON: " + Integer.toString(model.getRegon()), "Rok powstania: " + Integer.toString(model.getYear()));
    }

    /**
     * Method print data about Autor
     */
    void printAutor() {
        Autor model = (Autor) this.objectToSee.get(object);
        Wydawnictwo wydawnictwo = model.getWydawnictwo();
        if (wydawnictwo != null)
            this.setTextLabels("Id autora: " + model.getId(), "Imię: " + model.getName(), "Nazwisko: " + model.getSurname(), "Pracuje dla: " + wydawnictwo.getName());
    }

    /**
     * Method print data about Czasopismo
     */
    void printCzasopismo(int view) {
        Czasopismo model = (Czasopismo) this.objectToSee.get(object);
        if (view == 2) {
            Autor autor = model.getAutor();
            Wydawnictwo wydawnictwo = model.getWydawnictwo();
            this.setTextLabels("Id czasopisma: " + model.getId(), "Nazwa czasopisma: " + model.getName(), "Cena: " + Double.toString(model.getCost()), "Nazwa wydawnictwa: " + wydawnictwo.getName(), "Imię autora: " + autor.getName());
        } else if (view == 3) {
            Wydawnictwo wydawnictwo = model.getWydawnictwo();
            this.setTextLabels("Id czasopisma: " + model.getId(), "Nazwa czasopisma: " + model.getName(), "Cena: " + Double.toString(model.getCost()), "Nazwa wydawnictwa: " + wydawnictwo.getName());
        } else {
            Autor autor = model.getAutor();
            this.setTextLabels("Id czasopisma: " + model.getId(), "Nazwa czasopisma: " + model.getName(), "Cena: " + Double.toString(model.getCost()), "Imię autora: " + autor.getName(), "Nazwisko autora: " + autor.getSurname());
        }
    }

    /**
     * Method print data about Numer
     */
    void printNumer() {
        Numer model = (Numer) this.objectToSee.get(object);
        Czasopismo czasopismo = model.getCzasopismo();
        this.setTextLabels("Id numeru: " + model.getId(), "Nazwa czasopisma: " + czasopismo.getName(), "Numer: " + Integer.toString(model.getNumber()), "Spis treści jest na stronie: " + Integer.toString(model.getContent_table()));
    }

    /**
     * Method add text to labels
     */
    void setTextLabels(String... data) {
        for (int i = 0; i < data.length; i++) {
            this.setText[i].setText(data[i]);
        }
    }

    /**
     * Method get data from temporary database in application
     *
     * @param view int
     */
    void getDataFromDatabase(int view, String... data) {
        if (view == 0) this.objectToSee = temporaryBase.getWydawnictwa();
        else if (view == 1) this.objectToSee = temporaryBase.getAutors();
        else if (view == 2) this.objectToSee = temporaryBase.getCzasopismos();
        else if (view == 3) {
            if (checkString(data[0]))
                this.objectToSee = temporaryBase.getPublishingHouseNewspaper(Integer.parseInt(data[0]));
            else {
                this.setTextLabels("Błąd. Podano niewłaściwy format");
                this.seeComponent(setText[0]);
            }
        } else if (view == 4) {
            if (checkString(data))
                this.objectToSee = temporaryBase.getAuthorCzasopismo(Integer.parseInt(data[0]));
            else {
                this.setTextLabels("Błąd. Podano niewłaściwy format");
                this.seeComponent(setText[0]);
            }
        } else if (view == 5) this.objectToSee = temporaryBase.getNumers();
    }

    /**
     * Method check propriety of String
     *
     * @param data String varargs
     * @return boolean
     */
    private boolean checkString(String... data) {
        boolean good = true;
        endCkeck:
        for (String dane : data) {
            if (dane.length() == 0) {
                good = false;
                break;
            }
            for (int i = 0; i < dane.length(); i++)
                if (dane.charAt(i) <= '0' || dane.charAt(i) >= '9') {
                    good = false;
                    break endCkeck;
                }
        }
        return good;
    }


}