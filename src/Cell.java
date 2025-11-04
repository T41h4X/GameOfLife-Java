/**
 * Represents an individual cell in Conway's Game of Life.
 * Each cell has a state (alive or dead) and tracks its age when alive.
 * 
 * @author Your Name Here
 */
public class Cell {

    /**
     * Whether the cell is currently alive.
     */
    private boolean alive;

    /**
     * The age of the cell (how many generations it has been alive).
     */
    private int age;

    /**
     * Constructs a new Cell with the specified initial state.
     * 
     * @param alive true if the cell should start alive, false if dead
     */
    public Cell(boolean alive) {
        this.alive = alive;
        this.age = alive ? 1 : 0;
    }

    /**
     * Checks if the cell is currently alive.
     * 
     * @return true if the cell is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the cell state to alive.
     * If the cell is already alive, does not reset the age.
     * If the cell was dead, resets age to 1.
     */
    public void setAlive() {
        if (!alive) {
            alive = true;
            age = 1;
        }
    }

    /**
     * Gets the current age of the cell.
     * 
     * @return the age of the cell
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the cell to the specified value.
     * Age must be non-negative. If negative value is provided, does nothing.
     * 
     * @param age the new age for the cell
     */
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    /**
     * Resets the cell to a dead state with age 0.
     */
    public void reset() {
        alive = false;
        age = 0;
    }
}
