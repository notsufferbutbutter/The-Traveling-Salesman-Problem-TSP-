import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class SimulationView extends JPanel {

    private final SimulationController controller;

    private static final int CITY_RADIUS = 7;
    private static final double MAX_PHEROMONE = 5.0;

    private static final Color CANVAS_BACKGROUND = new Color(240, 249, 244);
    private static final Color CITY_FILL_COLOR = new Color(56, 142, 60);
    private static final Color CITY_BORDER_COLOR = new Color(27, 94, 32);
    private static final Color EDGE_COLOR_LOW = new Color(200, 230, 201);
    private static final Color EDGE_COLOR_HIGH = new Color(46, 125, 50);

    public SimulationView(SimulationController controller) {
        this.controller = controller;
        this.setBackground(CANVAS_BACKGROUND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawEdges(g2d);
        drawCities(g2d);
    }

    private void drawEdges(Graphics2D g2d) {
        List<Kanten> edges = controller.getEdges();
        if (edges == null) return;

        for (Kanten edge : edges) {
            City c1 = edge.startCity;
            City c2 = edge.endCity;

            double ratio = Math.min(edge.getPheromone() / MAX_PHEROMONE, 1.0);

            int r = (int) (EDGE_COLOR_LOW.getRed() + ratio * (EDGE_COLOR_HIGH.getRed() - EDGE_COLOR_LOW.getRed()));
            int g = (int) (EDGE_COLOR_LOW.getGreen() + ratio * (EDGE_COLOR_HIGH.getGreen() - EDGE_COLOR_LOW.getGreen()));
            int b = (int) (EDGE_COLOR_LOW.getBlue() + ratio * (EDGE_COLOR_HIGH.getBlue() - EDGE_COLOR_LOW.getBlue()));
            Color pheromoneColor = new Color(r, g, b);

            float strokeWidth = 1.5f + (float) (ratio * 3.0f);
            g2d.setColor(pheromoneColor);
            g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY());
        }
    }

    private void drawCities(Graphics2D g2d) {
        for (City city : controller.getCities()) {
            int x = city.getX() - CITY_RADIUS;
            int y = city.getY() - CITY_RADIUS;

            Ellipse2D outer = new Ellipse2D.Double(x - 1, y - 1, (CITY_RADIUS * 2) + 2, (CITY_RADIUS * 2) + 2);
            Ellipse2D inner = new Ellipse2D.Double(x, y, CITY_RADIUS * 2, CITY_RADIUS * 2);

            g2d.setColor(CITY_BORDER_COLOR);
            g2d.fill(outer);

            g2d.setColor(CITY_FILL_COLOR);
            g2d.fill(inner);
        }
    }

    public void redraw() {
        repaint();
    }

}
