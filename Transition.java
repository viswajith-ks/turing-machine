/**
 * Represents a single transition in a Turing Machine.
 * It contains the symbol to write, the direction to move the head,
 * and the next state to transition to.
 */
public class Transition {
    private final char symbolToWrite;
    private final char moveDirection;
    private final State nextState;

    public Transition(char symbolToWrite, char moveDirection, State nextState) {
        this.symbolToWrite = symbolToWrite;
        this.moveDirection = moveDirection;
        this.nextState = nextState;
    }

    public char getSymbolToWrite() {
        return symbolToWrite;
    }

    public char getMoveDirection() {
        return moveDirection;
    }

    public State getNextState() {
        return nextState;
    }
}
