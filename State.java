import java.util.Scanner;
class State {
    public boolean isfinal;
    State[] neighbors;
    char[] write;
    char[] move;
    public State() {
        isfinal = false;
        neighbors = new State[3];
        write = new char[3];
        move = new char[3];
    }
}
