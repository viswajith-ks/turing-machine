/**
 * Manages the states and state transitions of the Turing Machine.
 * This acts as the "finite control unit" of the TM.
 */
public class FiniteControl {
    private final State[] states;
    private State currentState;
    private final int stateCount;

    public FiniteControl(int stateCount) {
        this.stateCount = stateCount;
        this.states = new State[stateCount];
        for (int i = 0; i < stateCount; i++) {
            states[i] = new State();
        }
        // q0 is always the initial state
        this.currentState = states[0];
    }

    public State getState(int index) {
        if (index >= 0 && index < stateCount) {
            return states[index];
        }
        return null;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public int getStateCount() {
        return stateCount;
    }

    public void reset() {
        this.currentState = states[0];
    }
}
