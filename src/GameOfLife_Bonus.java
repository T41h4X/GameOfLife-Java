// BONUS VERSION WITH AGE-BASED COLORING!

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The GameOfLife_Bonus class is the main GUI application for Conway's Game of Life.
 * It acts as the view and controller, handling user interactions and
 * rendering the grid based on the state of the Simulation.
 *
 * BONUS FEATURE: Cells are colored based on their age - newer cells are brighter,
 * older cells are darker, and recently dead cells fade out over time.
 */
public class GameOfLife_Bonus extends JFrame {

    /**
     * The Simulation instance that handles the grid logic and evolution rules.
     */
    private Simulation simulation;

    /**
     * A Timer object to control the periodic evolution of the simulation.
     */
    private Timer timer;

    /**
     * The main JPanel that serves as the container for all UI components.
     */
    private JPanel mainPanel;

    /**
     * The button to start the simulation's periodic evolution.
     */
    private JButton startButton;

    /**
     * The button to pause the simulation's periodic evolution.
     */
    private JButton pauseButton;

    /**
     * The button to reset the simulation grid to its initial state.
     */
    private JButton resetButton;

    /**
     * The button to advance the simulation by one generation.
     */
    private JButton stepButton;

    /**
     * The button to load a pattern from an RLE file into the simulation.
     */
    private JButton loadRleButton;

    /**
     * A JSlider to control the speed of the simulation's evolution.
     */
    private JSlider speedSlider;

    /**
     * A JLabel to display the average age of alive cells in the simulation.
     */
    private JLabel averageAgeLabel;

    /**
     * A JLabel to display the total number of alive cells in the simulation.
     */
    private JLabel aliveCellsLabel;

    /**
     * A JLabel to display the maximum age of any alive cell in the simulation.
     */
    private JLabel maxAgeLabel;

    /**
     * A JLabel to display the number of generations elapsed in the simulation.
     */
    private JLabel generationLabel;

    /**
     * A JPanel responsible for rendering the simulation grid.
     */
    private JPanel gridPanel;

    /**
     * A JPanel responsible for displaying simulation statistics.
     */
    private JPanel statsPanel;

    /**
     * A JPanel containing buttons and controls for user interaction.
     */
    private JPanel controlPanel;

    /**
     * A JPanel that combines the control panel and stats panel into one container.
     */
    private JPanel combinedPanel;

    /**
     * A button to resize the grid to 25x25 cells.
     */
    private JButton size25Button;

    /**
     * A button to resize the grid to 50x50 cells.
     */
    private JButton size50Button;

    /**
     * A button to resize the grid to 100x100 cells.
     */
    private JButton size100Button;

    /**
     * A button to resize the grid to 175x175 cells.
     */
    private JButton size175Button;

    /**
     * The size of each cell in pixels, used for rendering the grid.
     */
    private int cellSize = 10;

    /**
     * The number of rows in the simulation grid.
     */
    private int gridRows;

    /**
     * The number of columns in the simulation grid.
     */
    private int gridCols;

    /**
     * BONUS FEATURE: HashMap to track recently dead cells for fade effect.
     */
    private HashMap<String, Integer> deadCellFadeMap;

    /**
     * Constructs the GameOfLife_Bonus application, initializing the GUI and linking
     * to the Simulation logic.
     */
    public GameOfLife_Bonus() {
        setTitle("GMU CS 310 - Project 1 - Game of Life (WITH BONUS COLORS!)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));

        // Initialize bonus feature
        deadCellFadeMap = new HashMap<>();

        initializeTitle();
        initializeControls();
        initializeStatsPanel();
        initializeGridPanel();
        combinePanels();
        initializeTimer();

        // Add the main panel to the frame
        add(mainPanel);
        pack();
        setVisible(true);
        gridPanel.repaint();

        // default grid size is 50x50
        this.simulation = new Simulation(50, 50);
        updateStatistics();
    }

    /**
     * Initializes the GUI title.
     */
    private void initializeTitle() {
        JLabel titleLabel = new JLabel("GMU CS 310 - Project 1 - Game of Life (BONUS: Age-Based Colors!)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
    }

    /**
     * Initializes the control panel containing buttons, sliders, and other UI elements.
     */
    private void initializeControls() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        stepButton = new JButton("Step");
        loadRleButton = new JButton("Load RLE");
        size25Button = new JButton("25x25");
        size50Button = new JButton("50x50");
        size100Button = new JButton("100x100");
        size175Button = new JButton("175x175");

        startButton.addActionListener(e -> timer.start());
        pauseButton.addActionListener(e -> timer.stop());
        resetButton.addActionListener(e -> resetGrid());
        stepButton.addActionListener(e -> stepGeneration());
        loadRleButton.addActionListener(e -> loadRleFile());
        size25Button.addActionListener(e -> setGridAndCellSize(25, 20));
        size50Button.addActionListener(e -> setGridAndCellSize(50, 10));
        size100Button.addActionListener(e -> setGridAndCellSize(100, 5));
        size175Button.addActionListener(e -> setGridAndCellSize(175, 3));

        speedSlider = new JSlider(JSlider.HORIZONTAL, 10, 600, 30);
        speedSlider.setPreferredSize(new Dimension(50, 40));
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setMinorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setToolTipText("Adjust Simulation Speed");
        speedSlider.addChangeListener(e -> timer.setDelay(speedSlider.getValue()));

        controlPanel.add(startButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(pauseButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(resetButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(stepButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(loadRleButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Choose a Grid Size:"));
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(size25Button);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(size50Button);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(size100Button);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(size175Button);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Speed (Fast <-> Slow):"));
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(speedSlider);
        controlPanel.add(Box.createVerticalStrut(20));

        // BONUS: Add color legend
        JLabel colorLegend = new JLabel("<html><b>Color Legend:</b><br/>" +
                "Bright Green = Young cells<br/>" +
                "Dark Green = Old cells<br/>" +
                "Gray = Recently dead cells</html>");
        colorLegend.setFont(new Font("Arial", Font.PLAIN, 10));
        controlPanel.add(colorLegend);
    }

    /**
     * Initializes the stats panel.
     */
    private void initializeStatsPanel() {
        statsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        averageAgeLabel = new JLabel("Average Age: 0", SwingConstants.CENTER);
        aliveCellsLabel = new JLabel("Alive Cells: 0", SwingConstants.CENTER);
        maxAgeLabel = new JLabel("Max Age: 0", SwingConstants.CENTER);
        generationLabel = new JLabel("Generations: 0", SwingConstants.CENTER);

        statsPanel.add(averageAgeLabel);
        statsPanel.add(aliveCellsLabel);
        statsPanel.add(maxAgeLabel);
        statsPanel.add(generationLabel);
    }

    /**
     * Combines control and stats panel.
     */
    private void combinePanels() {
        combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));
        combinedPanel.add(controlPanel, BorderLayout.NORTH);
        combinedPanel.add(Box.createVerticalStrut(10));
        combinedPanel.add(statsPanel);
        mainPanel.add(combinedPanel, BorderLayout.EAST);
    }

    /**
     * Initializes the grid panel where the grid is rendered.
     */
    private void initializeGridPanel() {
        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };

        gridPanel.setPreferredSize(new Dimension(550, 550));
        gridPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                int col = evt.getX() / cellSize;
                int row = evt.getY() / cellSize;
                simulation.toggleCell(row, col);
                gridPanel.repaint();
            }
        });

        gridPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                int col = evt.getX() / cellSize;
                int row = evt.getY() / cellSize;
                simulation.toggleCell(row, col);
                gridPanel.repaint();
            }
        });

        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the timer for automatic evolution of the simulation.
     */
    private void initializeTimer() {
        timer = new Timer(100, e -> {
            simulation.evolve();
            updateDeadCellFadeMap(); // BONUS: Update fade map
            updateStatistics();
            gridPanel.repaint();
        });
    }

    /**
     * BONUS FEATURE: Updates the fade map for recently dead cells.
     */
    private void updateDeadCellFadeMap() {
        // Decrease fade values for existing dead cells
        deadCellFadeMap.entrySet().removeIf(entry -> {
            entry.setValue(entry.getValue() - 1);
            return entry.getValue() <= 0;
        });
    }

    /**
     * BONUS FEATURE: Gets color based on cell age and state.
     *
     * @param cell the cell to get color for
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the color to use for this cell
     */
    private Color getCellColor(Cell cell, int row, int col) {
        if (cell.isAlive()) {
            int age = cell.getAge();
            int maxAge = Math.max(1, simulation.getMaxAge());

            // Color based on age - newer cells are brighter green, older cells are darker
            float ageRatio = (float) age / maxAge;
            int green = (int) (255 * (1.0f - ageRatio * 0.7f)); // From bright to dark green
            int red = (int) (100 * ageRatio); // Add some red for very old cells

            return new Color(red, green, 0);
        } else {
            // Check if this cell was recently alive (fade effect)
            String cellKey = row + "," + col;
            Integer fadeValue = deadCellFadeMap.get(cellKey);
            if (fadeValue != null && fadeValue > 0) {
                int grayLevel = fadeValue * 30; // Fade from gray to white
                return new Color(grayLevel, grayLevel, grayLevel);
            }
            return Color.WHITE; // Default dead cell color
        }
    }

    /**
     * Draws the grid and cells on the grid panel.
     * BONUS: Uses age-based coloring for cells.
     *
     * @param g the Graphics object used for rendering
     */
    private void drawGrid(Graphics g) {
        DynamicArray<DynamicArray<Cell>> grid = simulation.getGrid();

        // Track cells that just died for fade effect
        for (int row = 0; row < simulation.getRows(); row++) {
            for (int col = 0; col < simulation.getCols(); col++) {
                Cell cell = grid.get(row).get(col);
                String cellKey = row + "," + col;

                if (!cell.isAlive() && cell.getAge() > 0) {
                    // Cell just died, add to fade map
                    if (!deadCellFadeMap.containsKey(cellKey)) {
                        deadCellFadeMap.put(cellKey, 5); // Fade for 5 generations
                    }
                } else if (cell.isAlive()) {
                    // Remove from fade map if cell is alive
                    deadCellFadeMap.remove(cellKey);
                }

                // Set cell color based on state and age
                Color cellColor = getCellColor(cell, row, col);
                g.setColor(cellColor);
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                // Draw grid lines
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    /**
     * Resets the simulation grid to its initial state.
     */
    private void resetGrid() {
        timer.stop();
        simulation.reset();
        deadCellFadeMap.clear(); // BONUS: Clear fade map
        updateStatistics();
        gridPanel.repaint();
    }

    /**
     * Advances the simulation by one generation.
     */
    private void stepGeneration() {
        simulation.evolve();
        updateDeadCellFadeMap(); // BONUS: Update fade map
        updateStatistics();
        gridPanel.repaint();
    }

    /**
     * Fetches statistics from Simulation and updates the GUI.
     */
    private void updateStatistics() {
        averageAgeLabel.setText(String.format("Average Age: %.2f", simulation.getAverageAge()));
        aliveCellsLabel.setText("Alive Cells: " + simulation.getAliveCells());
        maxAgeLabel.setText("Max Age: " + simulation.getMaxAge());
        generationLabel.setText("Generations: " + simulation.getGenerations());
    }

    /**
     * Updates grid and cell sizing based on pre-set buttons.
     *
     * @param newSize the new size of the grid (length/width)
     * @param newCellSize the new size of the cell in pixels
     */
    private void setGridAndCellSize(int newSize, int newCellSize) {
        // Stop the simulation timer if running
        timer.stop();

        // Create a new Simulation object with the new size
        simulation = new Simulation(newSize, newSize);
        this.cellSize = newCellSize;
        deadCellFadeMap.clear(); // BONUS: Clear fade map

        // Adjust gridPanel dimensions
        gridPanel.repaint();

        // Update gridRows and gridCols for consistency
        simulation.setRows(newSize);
        simulation.setCols(newSize);

        // Reset statistics and repaint
        updateStatistics();
        gridPanel.repaint();
    }

    /**
     * File handler triggered by Load RLE button.
     * Passes array of lines to parseRLE in Simulation class.
     */
    private void loadRleFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                DynamicArray<String> lines = new DynamicArray<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.startsWith("#")) { // Ignore comments
                            lines.add(line);
                        }
                    }
                }
                simulation.parseRle(lines);
                deadCellFadeMap.clear(); // BONUS: Clear fade map when loading new pattern

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to load RLE file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        gridPanel.repaint();
    }

    /**
     * The main method to launch the Game of Life application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameOfLife_Bonus::new);
    }
}