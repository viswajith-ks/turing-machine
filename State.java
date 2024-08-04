import java.util.Scanner;
class State{
	public boolean isfinal;
	State[] neighbors;
	public State(){
		isfinal=false;
		neighbors=new State[3];
	}	
}
