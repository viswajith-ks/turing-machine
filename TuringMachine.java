import java.util.Scanner;

public class TuringMachine {
    // Use a dedicated constant for the blank symbol
    private static final char BLANK_SYMBOL = '_';

    private final FiniteControl finiteControl;
    private final char[] tape;
    private int head;
    private final int TAPE_SIZE;

    public TuringMachine(int tapeSize, int stateCount) {
        this.TAPE_SIZE = tapeSize;
        this.tape = new char[TAPE_SIZE];
        this.finiteControl = new FiniteControl(stateCount);
        reset();
    }

    public void reset() {
        this.head = TAPE_SIZE / 2;
        for (int i = 0; i < TAPE_SIZE; i++) {
            tape[i] = BLANK_SYMBOL;
        }
        this.finiteControl.reset();
    }

    public void defineTransitions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Define Machine Transitions ---");

        for (int i = 0; i < finiteControl.getStateCount(); i++) {
            State currentState = finiteControl.getState(i);
            System.out.print("Is q" + i + " a Final state? (yes/no): ");
            if (scanner.next().equalsIgnoreCase("yes")) {
                currentState.setFinal(true);
            }

            System.out.println("Define transitions for state q" + i + ". Format: TCMN");
            System.out.println("T=Tape Symbol (use '" + BLANK_SYMBOL + "' for blank), C=Char to Write, M=Move (L,R), N=Next State");
            System.out.println("Enter 'done' when finished with this state.");

            while (true) {
                System.out.print("q" + i + "> ");
                String input = scanner.next().toUpperCase();
                if (input.equals("DONE")) {
                    break;
                }

                if (input.length() < 4) {
                    System.out.println("Invalid format. Must be at least 4 characters (TCMN). Try again.");
                    continue;
                }

                try {
                    char tapeSymbol = input.charAt(0);
                    char writeSymbol = input.charAt(1);
                    char moveDirection = Character.toLowerCase(input.charAt(2));
                    int nextStateNum = Integer.parseInt(input.substring(3));

                    State nextState = finiteControl.getState(nextStateNum);

                    if ((moveDirection != 'l' && moveDirection != 'r') || nextState == null) {
                        System.out.println("Invalid transition parameter. Check your Move Direction or Next State number.");
                        continue;
                    }

                    Transition newTransition = new Transition(writeSymbol, moveDirection, nextState);
                    currentState.addTransition(tapeSymbol, newTransition);
                    System.out.println("  -> Added: on '" + tapeSymbol + "', write '" + writeSymbol + "', move '" + moveDirection + "', go to q" + nextStateNum);

                } catch (Exception e) {
                    System.out.println("Invalid format. Please follow TCMN (e.g., AXR1).");
                }
            }
        }
    }

    public void fillTape(String input) {
        int tapeStart = (TAPE_SIZE / 2) - (input.length() / 2);
        for (int i = 0; i < input.length(); i++) {
            tape[tapeStart + i] = Character.toUpperCase(input.charAt(i));
        }
        this.head = tapeStart;
    }

    public void printTape() {
        System.out.print("Tape: ");
        int start = Math.max(0, head - 30);
        int end = Math.min(TAPE_SIZE - 1, head + 30);
        if (start > 0) System.out.print("...");
        for (int i = start; i <= end; i++) {
             if (i == head) {
                System.out.print("\033[42m\033[31;1;5m" + tape[i] + "\033[0m");
            } else {
                System.out.print(tape[i]);
            }
        }
        if (end < TAPE_SIZE - 1) System.out.print("...");
        System.out.println();
    }

    public boolean runMachine() {
        System.out.println("\n--- Running Machine ---");
        printTape();
        int stepCount = 0;
        while (!finiteControl.getCurrentState().isFinal()) {
             if(stepCount++ > 1000) {
                System.out.println("HALT: Exceeded maximum steps (1000). Machine may be in an infinite loop.");
                return false;
            }
            char currentSymbol = tape[head];
            Transition transition = finiteControl.getCurrentState().getTransitionFor(currentSymbol);
            if (transition == null) {
                System.out.println("HALT: No transition defined for symbol '" + currentSymbol + "' at current state.");
                return false;
            }
            tape[head] = transition.getSymbolToWrite();
            if (transition.getMoveDirection() == 'l') {
                head--;
            } else {
                head++;
            }
            if (head < 0 || head >= TAPE_SIZE) {
                System.out.println("HALT: Tape head moved out of bounds.");
                return false;
            }
            finiteControl.setCurrentState(transition.getNextState());
            printTape();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java TuringMachine <tape_size> <number_of_states>");
            return;
        }
        try {
            int tapeSize = Integer.parseInt(args[0]);
            int stateCount = Integer.parseInt(args[1]);
            TuringMachine machine = new TuringMachine(tapeSize, stateCount);
            Scanner reader = new Scanner(System.in);
            machine.defineTransitions();
            while (true) {
                machine.reset();
                System.out.print("\nEnter the initial tape string (or 'exit' to quit): ");
                String input = reader.next();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting simulator.");
                    break;
                }
                machine.fillTape(input);
                if (machine.runMachine()) {
                    System.out.println("\nResult: String ACCEPTED");
                } else {
                    System.out.println("\nResult: String REJECTED");
                }
            }
            reader.close();
        } catch (NumberFormatException e) {
            System.out.println("Error: Tape size and number of states must be valid integers.");
        }
    }
}
