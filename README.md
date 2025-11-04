# Conway's Game of Life - Java Implementation

A Java implementation of Conway's Game of Life with a graphical user interface built using Java Swing. This project was developed as part of AOOP coursework and features a complete simulation with interactive controls, pattern loading, and statistical tracking.

## Features

- **Interactive Grid**: Click and drag to create live cells on the grid
- **Simulation Controls**: Start, pause, reset, and step through generations
- **Multiple Grid Sizes**: Support for 25x25, 50x50, 100x100, and 175x175 grids
- **Speed Control**: Adjustable simulation speed with slider control
- **Pattern Loading**: Load RLE (Run Length Encoded) pattern files
- **Statistics Tracking**: 
  - Live cell count
  - Average age of living cells
  - Maximum age of any living cell
  - Generation counter
- **Pre-loaded Patterns**: Includes classic patterns like Glider and Gosper Glider Gun

## Project Structure

```
GameOfLife_assignment/
├── src/
│   ├── Cell.java                    # Individual cell representation
│   ├── DynamicArray.java           # Custom dynamic array implementation
│   ├── GameOfLife_Standard.java    # Main GUI application
│   ├── GameOfLife_Bonus.java       # Enhanced version with additional features
│   ├── Simulation.java             # Core simulation logic
│   ├── glider.rle                  # Glider pattern file
│   └── gosper_glider.rle          # Gosper Glider Gun pattern file
├── out/                            # Compiled class files
└── .idea/                          # IntelliJ IDEA project files
```

## Classes Overview

### Cell.java
Represents an individual cell in the Game of Life grid. Each cell tracks:
- Alive/dead state
- Age (number of generations the cell has been alive)

### DynamicArray.java
A custom implementation of a dynamic array data structure used throughout the project for grid management.

### Simulation.java
The core simulation engine that handles:
- Grid initialization and management
- Game of Life evolution rules
- Cell neighbor counting
- Statistics calculation
- RLE pattern parsing and loading

### GameOfLife_Standard.java
The main GUI application featuring:
- Interactive grid display
- Control buttons (Start, Pause, Reset, Step)
- Grid size selection
- Speed control slider
- Statistics display
- File loading capabilities

### GameOfLife_Bonus.java
Enhanced version with additional features and improvements over the standard implementation.

## Game of Life Rules

The simulation follows Conway's original rules:

1. **Underpopulation**: A live cell with fewer than 2 live neighbors dies
2. **Survival**: A live cell with 2 or 3 live neighbors survives to the next generation
3. **Overpopulation**: A live cell with more than 3 live neighbors dies
4. **Reproduction**: A dead cell with exactly 3 live neighbors becomes alive

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java IDE (IntelliJ IDEA, Eclipse, etc.) or command line

### Running from IDE
1. Open the project in your Java IDE
2. Navigate to `src/GameOfLife_Standard.java`
3. Run the main method

### Running from Command Line
1. Navigate to the project directory
2. Compile the Java files:
   ```bash
   javac -d out src/*.java
   ```
3. Run the application:
   ```bash
   java -cp out GameOfLife_Standard
   ```

## Usage

1. **Creating Patterns**: Click or click-and-drag on the grid to create live cells
2. **Starting Simulation**: Click the "Start" button to begin automatic evolution
3. **Controlling Speed**: Use the speed slider to adjust how fast generations evolve
4. **Stepping Through**: Use "Step" to advance one generation at a time
5. **Changing Grid Size**: Select different grid sizes (25x25, 50x50, 100x100, 175x175)
6. **Loading Patterns**: Click "Load RLE" to load pre-made patterns from RLE files
7. **Resetting**: Click "Reset" to clear the grid and start over

## RLE Pattern Files

The project includes two classic Game of Life patterns:

- **glider.rle**: A simple 5-cell pattern that moves across the grid
- **gosper_glider.rle**: The famous Gosper Glider Gun that continuously produces gliders

You can find more RLE patterns at [LifeWiki](https://conwaylife.com/wiki/Main_Page).

## Technical Implementation

- **Big-O Complexity**: Key methods are implemented with specified time complexity requirements
- **Memory Management**: Uses custom DynamicArray for efficient grid storage
- **Event Handling**: Responsive GUI with mouse interaction and timer-based evolution
- **File I/O**: Robust RLE file parsing with error handling

## Development Notes

This project demonstrates:
- Object-oriented programming principles
- GUI development with Java Swing
- File I/O operations
- Algorithm implementation with complexity constraints
- Event-driven programming

## Author

Developed as part of AOOP coursework.

## License


This project is for educational purposes as part of university coursework.
