import java.util.Scanner;

public class Main {
	
	static int MyMove;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char move [][] = new char [3][3];
		char x = '0';
		int n = 0;
		for(int i = 0 ; i < 3 ; i++)
			for(int j = 0 ; j < 3 ; j++)
			{
				if( i == j && i == 1) move[i][j] = 'M';
				else
				{
					n++;
					if( n == 8){
						move[i][j] = '8';
					}
					else
					{
					x = Integer.toOctalString(n).charAt(0);
					move[i][j] = x;
					}
				}
				
			}
		int which = 0;
		choose ch = new choose();
		ch.option(move);
	}
	

}
