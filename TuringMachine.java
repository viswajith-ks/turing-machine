import java.lang.Thread;
import java.util.Scanner;
public class TuringMachine{
  DFA automata;
	char[] tape;
	int head,TAPESIZE;
	public TuringMachine(int tapesize,int statecount){
		this.TAPESIZE=tapesize;
		tape=new char[TAPESIZE];
		head=(TAPESIZE-1)/2;
		for(int i=0;i<TAPESIZE;i++)
		tape[i]='B';
		automata=new DFA(statecount);
	}
	
	public void setAutomata(){ //unreadable code ahead
	  Scanner obj=new Scanner(System.in);
    System.out.println("Enter in the form TCMN where T is the current symbol in tape,C is the change in tape cell, M is the movement of the head and N is the next state's number");
    System.out.println("Enter TCMN where T={0,1,b} C={0,1,b} M={l,r} N={0-" + automata.STATECOUNT + "}. enter done after completing the transition definition is done.");
    for(int i=0;i<automata.STATECOUNT;i++){
    System.out.print("Final state? ");
    if(obj.next().contains("yes"))
    automata.States[i].isfinal=true;
	System.out.println("for state " + i);
      String transition=obj.next();
      if(transition.equals("done")==true)
      continue;
      do{
        transition=transition.replace("b","2");
        char input=transition.charAt(1);
        if(transition.charAt(1)=='2')
        input='B';
        System.out.println(transition);
        automata.States[i].move[Character.getNumericValue(transition.charAt(0))]=transition.charAt(2);
        automata.States[i].write[Character.getNumericValue(transition.charAt(0))]=input;
        automata.States[i].neighbors[Character.getNumericValue(transition.charAt(0))]=automata.States[Character.getNumericValue(transition.charAt(3))];
        transition=obj.next();
	    }while(transition.equals("done")==false);
	  }
	}
	
	public boolean isEmpty(){
	for(int i=0;i<TAPESIZE;i++){
	if(tape[i]=='0'||tape[i]=='1')
	return false;
	}
	return true;
  }

	public void write(char symbol){
	  tape[head]=symbol;
	}
	
	public char read(){
		return tape[head];
	}
	
	public void moveLeft(){
		head--;
	}
	
	public void moveRight(){
		head++;
	}
	
	public void reset(){
    head=(TAPESIZE/2)-1;
    for(int i=0;i<TAPESIZE;i++)
    tape[i]='B';
  }
  
  public void fillTape(String str){
    for (int i=0;i<str.length();i++){
	if(str.charAt(i)=='b')
		write('B');
	else
      write(str.charAt(i));
      moveRight();
    }
  }
	
	public void printTape(){
	  if(isEmpty()==true){
	  System.out.println("TAPE EMPTY (full of blank symbols)");
	  return;
	  }
		int start=0,end=0;
		for(int i=0; i<TAPESIZE; i++){
			if(tape[i]!='B'){
				start=i;
				break;
			}
		}
		for(int i=TAPESIZE-1; i>=0; i--){
			if(tape[i]!='B'){
				end=i;
				break;
			}
		}
		start--;
		end++;
		if(head<start)
			start=head;
		if(head>end)
			end=head;
		for(int i=start;i<=end;i++){
		  if(i==head)
        System.out.print("\033[42;2m\033[31;1;5m["+tape[i]+"]\033[0m"); // ansi escape codes for bootifulness.
      else
			  System.out.print("["+tape[i]+"]");
		}
		System.out.println();
	}
	
	public void driver(char x){
    switch(x){
    case 'w'://write 1
    case '1': write('1');
    break;
    case 'a'://go left
    case 'l': if(head>0)
                moveLeft();
              else
                System.out.println("reached left limit");
    break;
    case 's'://in 0
    case '0': write('0');
    break;
    case 'd'://go right
    case 'r': if(head<TAPESIZE-1)
                moveRight();
              else
                System.out.println("reached right limit");
    break;
    case 'b': write('b');
	break;
    case 'p': System.out.println("[" + read() + "]");
    break;
    case 'o': printTape();
    break;
    default: System.out.println("err");
    }
  }

  public void runString(String str){
	for(int i=0;i<str.length();i++){
		driver(str.charAt(i));
		try{
		Thread.sleep(296);
		}
        catch (InterruptedException e) {
            System.out.println("err");
        }
	System.out.println();
	driver('o'); 
	}
  }
  
  public boolean runMachine(){
    int start=0,end=0;
		for(int i=0; i<TAPESIZE; i++){
			if(tape[i]!='B'){
				start=i;
				break;
			}
		}
		for(int i=TAPESIZE-1; i>=0; i--){
			if(tape[i]!='B'){
				end=i;
				break;
			}
		}
		head=start;
		System.out.println(read());
		System.out.println(head);
		automata.Currentstate=automata.Initstate;
		printTape();
		while(!(automata.Currentstate.isfinal==true)&&head<=TAPESIZE&&head>=0){
		System.out.println("--------------------------------------------------------------------------");
		int input;
		if(read()=='0')
		input=0;
		else if(read()=='1')
		input=1;
		else
		input=2;
		char temp=automata.Currentstate.write[input];
		char tem=automata.Currentstate.move[input];
		if(automata.feed(read())==false)
		return false;
		write(temp);
		printTape();
		if(tem=='l')
		moveLeft();
		else
		moveRight();
		printTape();
		try{
		Thread.sleep(69);
		}catch(InterruptedException e){
		}
		}
		System.out.println("--------------------------------------------------------------------------");
		return(automata.Currentstate.isfinal==true);
  }

	
	public static void main(String args[]){
		Scanner reader=new Scanner(System.in);
		TuringMachine Machine=new TuringMachine(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		System.out.println("the legendary turing machine. has a tape and read write heads (0,1) and move operations (left right. blank spaces are marked with 'B'");
		char choice='x';
		System.out.println(" <a/l> go left\t <s/0> write 0\t <w/1> write 1\n <d/r> go right\t <p> read\t <o> display tape\n <x> EXIT \n\t");
		choice=reader.next().charAt(0);
		while(choice!='x'){
		    if("w1axls0drpo".contains(Character.toString(choice)))
		      Machine.driver(choice);
		    else{
		      System.out.println("invalid choice");
		      System.out.println(" <a/l> go left\t <s/0> write 0\t <w/1> write 1\n <d/r> go right\t <p> read\t <o> display tape\n <x> EXIT \n\tchoice: "); 
		    }
		    choice=reader.next().charAt(0);
		    System.out.println();
		}
		Machine.setAutomata();
		String str;
		while(1==1){
		Machine.reset();
		System.out.println("enter the tape contents or exit to exit");
		str=reader.next();
		if(str.equals("exit")==true)
		return;
		Machine.fillTape(str);
		if(Machine.runMachine()==true)
		System.out.println("String is accepted by the Turing Machine");
		else
		System.out.println("String is not accepted by the Turing Machine");
		}
	}
}
