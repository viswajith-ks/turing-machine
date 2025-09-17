# Java Turing Machine Simulator

A command-line Turing Machine simulator implemented in Java. This project allows users to define a Turing Machine's transition function, provide an input string on the tape, and watch the step-by-step execution as the machine processes the input.

This implementation was created to better understand the fundamental principles of the theory of computation.

## ✨ Features

  * **Configurable Machine:** Set the tape size and the number of states for the TM via command-line arguments.
  * **Interactive Transition Definition:** A guided, runtime prompt to define the states and transition functions for your machine.
  * **Step-by-Step Visualization:** The program prints the state of the tape after every move, with the current head position highlighted for clarity.
  * **Robust Input Handling:** Gracefully handles invalid transition formats and provides clear feedback.

## 🚀 How to Compile and Run

#### 1\. Compile the Java files:

```sh
javac Transition.java State.java FiniteControl.java TuringMachine.java
```

#### 2\. Run the program:

The program takes two command-line arguments: the **tape size** and the **number of states**.

```sh
java TuringMachine <tape_size> <number_of_states>
```

For example, to run with a tape of size 80 and 5 states:

```sh
java TuringMachine 80 5
```

## 🛠️ Defining a Machine's Transitions

The transitions are defined using a compact **`TCMN`** format:

  * **T**: **T**ape Symbol read by the head. Use **`_`** for the blank symbol.
  * **C**: **C**haracter to Write on the tape.
  * **M**: **M**ove Direction for the head (**`L`** for left, **`R`** for right).
  * **N**: **N**ext State number (e.g., **`0`** for state `q0`).

After defining all transitions for a state, type **`done`** to move to the next state.

### Example: Machine for L = {aⁿbⁿ | n ≥ 1}

This machine checks if a string consists of one or more `a`'s followed by an equal number of `b`'s. It works by matching each `a` with a corresponding `b` in a back-and-forth cycle. For this example, you need **5 states**.

1.  **State `q0` (Start of cycle, find next `a`):**

      * `Is q0 a Final state? (yes/no):` **`no`**
      * `q0>` **`AXR1`** (On `a`, write `X`, move Right to `q1`)
      * `q0>` **`YYR3`** (On `Y`, write `Y`, move Right to `q3` for final check)
      * `q0>` **`done`**

2.  **State `q1` (Scan right to find a `b`):**

      * `Is q1 a Final state? (yes/no):` **`no`**
      * `q1>` **`AAR1`** (Skip `a`'s)
      * `q1>` **`YYR1`** (Skip `Y`'s)
      * `q1>` **`BYL2`** (On `b`, write `Y`, move Left to `q2`)
      * `q1>` **`done`**

3.  **State `q2` (Scan left to return to start marker):**

      * `Is q2 a Final state? (yes/no):` **`no`**
      * `q2>` **`AAL2`** (Skip `a`'s)
      * `q2>` **`YYL2`** (Skip `Y`'s)
      * `q2>` **`XXR0`** (On `X`, write `X`, move Right back to `q0`)
      * `q2>` **`done`**

4.  **State `q3` (Verification scan, ensure no `b`'s are left):**

      * `Is q3 a Final state? (yes/no):` **`no`**
      * `q3>` **`YYR3`** (Skip `Y`'s)
      * `q3>` **`__R4`** (On `_` blank, write `_`, move to final state `q4`)
      * `q3>` **`done`**

5.  **State `q4` (Final/Accept state):**

      * `Is q4 a Final state? (yes/no):` **`yes`**
      * `q4>` **`done`**

Now, the machine is defined. When prompted, you can test it:

  * `Enter the initial tape string (or 'exit' to quit):` **`aaabbb`**

      * The machine will run, and the final tape will show **`XXXYYY`**.
      * Result: **String ACCEPTED**

  * `Enter the initial tape string (or 'exit' to quit):` **`aaab`**

      * The machine will halt because in state `q1` it will find a blank instead of a `b`.
      * Result: **String REJECTED**
