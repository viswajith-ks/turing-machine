import java.util.Scanner;
public class DFA{
	State Initstate;
	State Currentstate;
	State[] States;
	int statecount;
	
	public DFA(int statecount){
		this.statecount=statecount;
		States=new State[statecount];
	}
	
	public void setStates(){
		Scanner obj=new Scanner(System.in);
		for(int i=0;i<statecount;i++)
			States[i]=new State();
		for(int i=0;i<statecount;i++){
			for(int j=0;j<3;j++){
				System.out.print("q" + i + "(" + j + ")-> q");
				int x=obj.nextInt();
				States[i].neighbors[j]=States[x];
			}
			System.out.print("enter yes if q"+ i +" is a final state ");
			if(obj.next().charAt(0)=='y'){
				States[i].isfinal=true;
			}
		}
		Initstate=States[0];
		Currentstate=Initstate;
	}

	public boolean feed(char symbol){
		if(symbol=='0'&&Currentstate.neighbors[0]!=null){
			Currentstate=Currentstate.neighbors[0];
			return true;
		}
		else if(symbol=='1'&&Currentstate.neighbors[1]!=null){
			Currentstate=Currentstate.neighbors[1];
			return true;
		}
		else if(symbol=='b'&&Currentstate.neighbors[2]!=null){
			Currentstate=Currentstate.neighbors[2];
			return true;
		}
		else
			System.out.println("invalid state");
			return false;
	}

	public boolean isAccepted(String expr){
		Currentstate=Initstate;
		for(int i=0;i<expr.length();i++){
			if(feed(expr.charAt(i))==false){
				return false;
			}
		}
		return(Currentstate.isfinal==true);
	}

	public static void main(String args[]){
		Scanner obj=new Scanner(System.in);
		DFA auto =new DFA(Integer.parseInt(args[0]));
		auto.setStates();
		for(int i=0;i<15;i++){
		System.out.print("Enter the expression : ");
		String expr=obj.nextLine();
		if(auto.isAccepted(expr))
			System.out.println("String is accepted by the DFA\n");
		else
			System.out.println("String is not accepted by the DFA\n");
		}
	}
	
}
