import java.lang.Thread;
import java.util.Scanner;
public class TuringMachine{
	char[] tape;
	int head,TAPESIZE;
	public TuringMachine(int tapesize){
		this.TAPESIZE=tapesize;
		tape=new char[TAPESIZE];
		head=(TAPESIZE-1)/2;
		for(int i=0;i<TAPESIZE;i++)
		tape[i]='B';
	}
	
	public void write(char bit){
		tape[head]=bit;
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
    head=(TAPESIZE/2)+1;
    for(int i=0;i<TAPESIZE;i++)
    tape[i]='B';
  }
	
	public void printTape(){
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
		Thread.sleep(690);
		}
        catch (InterruptedException e) {
            System.out.println("err");
        }
	System.out.print("\033[H\033[2J");
	driver('o'); 
	}
  }

	
	public static void main(String args[]){
		Scanner reader=new Scanner(System.in);
		TuringMachine Machine=new TuringMachine(Integer.parseInt(args[0]));
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
		}while(choice!='x');
		System.out.println("enter a string");
		String str=reader.next();
		Machine.runString(str);

	}
}
