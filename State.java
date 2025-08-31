import java.util.HashMap;
import java.util.Map;

/**
 * Represents a state in the Turing Machine's finite control.
 * It holds a map of transitions for different tape symbols.
 */
public class State {
    private boolean isFinal;
    private final Map<Character, Transition> transitions;

    public State() {
        this.isFinal = false;
        this.transitions = new HashMap<>();
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Adds a transition rule to this state.
     * @param readSymbol The symbol read from the tape that triggers this transition.
     * @param transition The transition object containing the action to perform.
     */
    public void addTransition(char readSymbol, Transition transition) {
        transitions.put(readSymbol, transition);
    }

    /**
     * Retrieves the transition for a given tape symbol.
     * @param symbol The symbol currently under the tape head.
     * @return The corresponding Transition object, or null if no transition exists.
     */
    public Transition getTransitionFor(char symbol) {
        return transitions.get(symbol);
    }
}
