# Java Turing Machine Simulator

A command-line Turing Machine (TM) simulator implemented in Java. This project allows users to define a Turing Machine's transition function, provide an input string on the tape, and watch the step-by-step execution as the machine processes the input.

This implementation was created to better understand the fundamental principles of the theory of computation.

---

## ✨ Features

-   **Configurable Tape Size**: Set the size of the TM tape via a command-line argument.
-   **Dynamic State Definition**: Interactively define the states and transition functions for the machine at runtime.
-   **Step-by-Step Visualization**: The program prints the state of the tape after every move, with the current head position highlighted for clarity.
-   **Two Modes of Operation**:
    1.  **Manual Mode**: Directly control the tape head and write symbols.
    2.  **Run Mode**: Load a string onto the tape and let the defined machine run to completion.

---

## 🚀 How to Compile and Run

1.  **Compile the Java files:**
    ```bash
    javac State.java DFA.java TuringMachine.java
    ```

2.  **Run the program:**
    The program takes two command-line arguments: the **tape size** and the **number of states**.
    ```bash
    java TuringMachine <tape_size> <number_of_states>
    ```
    For example, to run with a tape of size 100 and 9 states:
    ```bash
    java TuringMachine 100 9
    ```

---

## 🛠️ Defining a Machine's Transitions

After launching, the program will first enter a manual mode. After exiting manual mode (by pressing `x`), you will be prompted to define the machine's states and transitions.

The transitions are defined using a compact `TCMN` format:

-   `T`: **Tape Symbol** read by the head (`0`, `1`, or `b` for blank).
-   `C`: **Character to Write** on the tape (`0`, `1`, or `b` for blank).
-   `M`: **Move Direction** for the head (`l` for left, `r` for right).
-   `N`: **Next State** number (e.g., `0` for state `q0`, `1` for `q1`).

### Example: Machine for L = {0ⁿ1ⁿ⁺¹ | n ≥ 0}

The following is the input required to define a Turing Machine that accepts the language $L = \{0^n1^{n+1} \mid n \ge 0\}$. For this example, you would run the program with 9 states (`java TuringMachine 100 9`).

-   **q0 (Initial State)**:
    -   On reading `0`: Write `X`, move Right, go to `q1`. (`0Xr1`)
    -   On reading `Y`: Write `Y`, move Right, go to `q4`. (`bYr4`)
-   **q1**:
    -   On reading `0`: Write `0`, move Right, go to `q1`. (`00r1`)
    -   On reading `1`: Write `Y`, move Left, go to `q2`. (`1Yl2`)
    -   On reading `Y`: Write `Y`, move Right, go to `q1`. (`bYr1`)
-   **q2**:
    -   On reading `0`: Write `0`, move Left, go to `q2`. (`00l2`)
    -   On reading `X`: Write `X`, move Right, go to `q0`. (`bXr0`)
    -   On reading `Y`: Write `Y`, move Left, go to `q2`. (`bYl2`)
-   **q4 (Final Check)**:
    -   *...and so on for the remaining states.*

You would enter these `TCMN` strings one by one when prompted for each state's transitions. After defining all transitions for a state, you type `done` to move to the next state.
