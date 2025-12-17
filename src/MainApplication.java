import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainApplication extends JFrame {

    private final SimulationController controller;
    private final SimulationView simulationView;
    private JTextField antCountField;
    private JTextField alphaField;
    private JTextField betaField;
    private JTextField rhoField;

    private static final Color PRIMARY_GREEN = new Color(56, 142, 60);
    private static final Color PRIMARY_GREEN_DARK = new Color(27, 94, 32);
    private static final Color PANEL_BACKGROUND = new Color(13, 71, 46);
    private static final Color PANEL_BACKGROUND_DARK = new Color(9, 53, 34);
    private static final Color TEXT_PRIMARY = new Color(236, 253, 245);



    public MainApplication() {
        // 1. Controller mit temporärer/null View-Referenz initialisieren
        // ACHTUNG: Der SimulationController-Konstruktor muss null erlauben, oder wir nutzen den Setter.
        // Nehmen wir an, der Konstruktor erlaubt null:
        this.controller = new SimulationController(null);

        // 2. View mit dem korrekten Controller erstellen
        this.simulationView = new SimulationView(this.controller);

        // 3. Die korrekte View-Referenz dem Controller geben
        this.controller.setView(this.simulationView);

        // 2. Fenster-Setup
        setTitle("Ameisenalgorithmus - Lieferservice Simulation");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(PANEL_BACKGROUND_DARK);

        // 3. Steuerelemente hinzufügen (Nord- oder Ostbereich)
        JPanel controlPanel = createControlPanel();

        add(controlPanel, BorderLayout.EAST);
        add(simulationView, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --- UI-Komponenten und Logik ---

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 10, 10));
        panel.setPreferredSize(new Dimension(400, 900));
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Steuerung", SwingConstants.LEFT);
        styleTitleLabel(titleLabel);
        panel.add(titleLabel);
        panel.add(new JLabel());

        JButton startButton = new JButton("Start Optimierung");
        stylePrimaryButton(startButton);

        JButton stopButton = new JButton("Stopp und Ergebnis");
        styleSecondaryButton(stopButton);

        panel.add(startButton);
        panel.add(stopButton);

        //Anzahl der Städte Button
        // 1. Label + Textfeld für Anzahl der Städte
        JLabel cityLabel = new JLabel("Anzahl Städte:");
        styleLabel(cityLabel);
        panel.add(cityLabel);
        JTextField cityCountField = new JTextField("10"); // Standardwert 10
        styleTextField(cityCountField);
        panel.add(cityCountField);

        // 2. Button zum Generieren
        JButton randomCitiesButton = new JButton("Städte generieren");
        styleSecondaryButton(randomCitiesButton);
        randomCitiesButton.addActionListener(e -> {
            try {
                // Wert aus Textfeld auslesen
                int numCities = Integer.parseInt(cityCountField.getText());
                if (numCities < 1) numCities = 1; // Minimum absichern

                // Städte generieren
                controller.generateRandomCities(numCities, simulationView.getWidth(), simulationView.getHeight());
            } catch (NumberFormatException ex) {
                System.out.println("Ungültige Eingabe für Anzahl der Städte!");
            }
        });
        panel.add(randomCitiesButton);

        //Zeitlimit Button
        JButton timerButton = new JButton("Start mit Dauer 10s");
        styleSecondaryButton(timerButton);
        panel.add(timerButton);

        //Armeisenanzahl Button
        JLabel antLabel = new JLabel("Ameisenanzahl:");
        styleLabel(antLabel);
        panel.add(antLabel);
        antCountField = new JTextField("10");
        styleTextField(antCountField);
        panel.add(antCountField);

        // Eingabefelder für Alpha, Beta, Rho und NumAnts

    JLabel alphaLabel = new JLabel("Alpha (Pheromon)");
    styleLabel(alphaLabel);
    panel.add(alphaLabel);
    alphaField = new JTextField("1.0");
    styleTextField(alphaField);
        panel.add(alphaField);

        // Beta
        JLabel betaLabel = new JLabel("Beta (Heuristik)"); // Beschriftung
        styleLabel(betaLabel);
        panel.add(betaLabel);
        betaField = new JTextField("2.0"); // Standardwert
        styleTextField(betaField);
        panel.add(betaField);

        // Rho
        JLabel rhoLabel = new JLabel("Rho (Verdampfung)"); // Beschriftung
        styleLabel(rhoLabel);
        panel.add(rhoLabel);
        rhoField = new JTextField("0.5"); // Standardwert
        styleTextField(rhoField);
        panel.add(rhoField);

        return panel;
    }

    private void styleTitleLabel(JLabel label) {
        label.setForeground(TEXT_PRIMARY);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 20f));
    }

    private void styleLabel(JLabel label) {
        label.setForeground(TEXT_PRIMARY);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 13f));
    }

    private void styleTextField(JTextField field) {
        field.setBackground(PANEL_BACKGROUND_DARK);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_GREEN_DARK, 1),
                new EmptyBorder(4, 6, 4, 6)
        ));
    }

    private void stylePrimaryButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(PRIMARY_GREEN);
        button.setForeground(TEXT_PRIMARY);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        button.setBorder(new EmptyBorder(8, 12, 8, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(PANEL_BACKGROUND_DARK);
        button.setForeground(TEXT_PRIMARY);
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12f));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_GREEN, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(MainApplication::new);
    }
}
