import java.util.*;

public class PaperSoccer {
	
	public static Scanner in = new Scanner(System.in);
	
	//Random Bot:
	Random rand = new Random();
	int botChoice;
	
	public static Dot map[][];
	String name1;
	String name2;
	int m;
	int n;
	static int m1Goal;
	static int m2Goal;
	
	public static String turn;
	
	public static int itoop;
	public static int jtoop;
	public static int moveInput;

	
	public PaperSoccer(int m, int n, String name1, String name2, int m1Goal, int m2Goal) {
		this.m = m;
		this.n = n;
		itoop = m/2;
		jtoop = n/2;
		this.m1Goal = m1Goal;
		this.m2Goal = m2Goal;
		
		this.turn = name1;
		
		makeMap(m, n);
		map[itoop][jtoop].visited = true;
		
		this.name1 = name1;
		this.name2 = name2;
	}
	
	
	void makeMap(int m, int n) {
		PaperSoccer.map = new Dot[m][n];
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				map[i][j] = new Dot();
			}
		}
		
	}
	
	String returnTurn(){
	    
	    if(itoop == 0 && jtoop>= m1Goal && jtoop <= m2Goal) {
	      System.out.println("Congratulations " + name2 +"!\n");
	      String s = new String();
	      s = "W2";
	      return s;
	    }
	    if(itoop == m-1 && jtoop >= m1Goal && jtoop <= m2Goal) {
	      System.out.println("Congratulations " + name1 +"!\n");
	      String s = new String();
	      s = "W1";
	      return s;
	    }
	    
//	    if(jtoop == m1Goal && itoop == 1 || jtoop == m2Goal && itoop ==1 || jtoop == m1Goal && itoop == m-2 
//	        || jtoop == m2Goal && itoop == m-2){
	//
//	      }
//	      else if( jtoop != 0 && jtoop != n -1 && itoop != 0 && itoop != m -1 || (jtoop != m1Goal && itoop != 1 )) {
	      else if( jtoop != 0 && jtoop != n -1 && itoop != 0 && itoop != m -1 
	    		  && itoop != m - 2 && itoop != 1 || (jtoop > m1Goal && jtoop < m2Goal && itoop == 1) || (jtoop > m1Goal && jtoop < m2Goal && itoop == m-2)) {
	        
	        if(!map[itoop][jtoop].isVisited()) {
	          if(turn.equals(name1)) {
	            turn = name2;
	          }else {
	            turn = name1;
	          }
	        }
	      }
	    return turn;
	  }
	
	String[][] play() {


		map[itoop][jtoop].setVisited(true);
		
		System.out.println("Turn: " + turn);
		
		
		int forbiden[] = map[itoop][jtoop].forbidenNeighbors();
		String [][]s = showOptions(forbiden);
		
		return s;
	
	}
	
	String[][] showOptions(int forbiden[]) {
		
		System.out.println("You can go to these places: ");
		//Top Left:
		if(itoop==1 && jtoop==0) {
			String [][]equal = new String[1][1];
			equal[0][0] = "equal";
			return equal ;
			//MOSAVI
		}
		//Left:
		else if(jtoop == 0 && itoop != 0 && itoop!=1 && itoop!= m-2 && itoop != m-1) {
			String opt[][] = new String[3][2];
			int temp = 1;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 2; b++) {
					if(a == 1 & b == 0)
						opt[a][b] = "M";
					else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}
				temp++;
			}
			opt[0][0] = "X";
			opt[2][0] = "X";
			return opt;
		}
		//Down Left:
		else if(jtoop == 0 && itoop == m-2) {
			//MOSAVI
			String [][]equal = new String[1][1];
			equal[0][0] = "equal";
			return equal ;
		}
		//Top:
		else if(itoop==1 && jtoop!= 0 && jtoop!= n-1 && jtoop!= m1Goal && jtoop != m2Goal && jtoop != 4) {
			String[][] opt = new String[2][3];
			int temp = 3;
			for(int a = 0; a < 2; a++) {
				for(int b = 0; b < 3; b++) {
					if(a == 0 & b == 1)
						opt[a][b]= "M";
					else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp + 1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}
			}
			opt[0][0] = "X";
			opt[0][2] = "X";
			return opt;
		}
		//Top Right:
		else if(itoop==1 && jtoop==n-1) {
			//Mosavi
			String [][]equal = new String[1][1];
			equal[0][0] = "equal";
			return equal ;
		}
		//Right:
		else if(jtoop==n-1 && itoop != 0 && itoop != 1 && itoop != m-2 && itoop != m-1) {
			String opt[][] = new String[3][2];
			int temp = 0;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 2; b++) {
					if(a == 1 & b == 1)
						opt[a][b] = "M";
					else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp + 1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}
				temp++;
			}
			opt[0][1] = "X";
			opt[2][1] = "X";
			return opt;
		}
		//Down Right:
		else if(jtoop==n-1 && itoop== m-2) {
			//MOsavi
			String [][]equal = new String[1][1];
			equal[0][0] = "equal";
			return equal ;
		}
		//Down:
		else if(itoop==m-2 && jtoop!=0 && jtoop!= n-1 && jtoop != m1Goal && jtoop!= m2Goal && jtoop != 4) {
			String opt[][] = new String[2][3];
			int temp = 0;
			for(int a = 0; a < 2; a++) {
				for(int b = 0; b < 3; b++) {
					if(a == 1 & b == 1)
						opt[a][b] = "M";
					else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}

			}
			opt[1][0] = "X";
			opt[1][2] = "X";
			return opt;
		}
		//TIRAK bala chap
		else if(itoop == 1 && jtoop == m1Goal) {
			String opt[][] = new String[3][3];
			int temp = 0;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 3; b++) {
					if( a == 1 && b == 1) {
						opt[a][b] = "M";
					}else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}

			}
			opt[0][0] = "X";
			opt[0][1] = "X";
			opt[1][0] = "X";
			return opt;
		}
		//Tirak bala rast
		else if(itoop == 1 && jtoop == m2Goal) {
				String opt[][] = new String[3][3];
				int temp = 0;
				for(int a = 0; a < 3; a++) {
					for(int b = 0; b < 3; b++) {
						if( a == 1 && b == 1) {
							opt[a][b] = "M";
						}else {
							if(forbiden[temp] == 1) {
								opt[a][b] = Integer.toString(temp+1);
							}else {
								opt[a][b] = "X";
							}
							temp++;
						}
					}

				}
				opt[0][2] = "X";
				opt[0][1] = "X";
				opt[1][2] = "X";
				return opt;
		}
		//Tirak payin chap
		else if(itoop == m-2 && jtoop == m1Goal) {
			String opt[][] = new String[3][3];
			int temp = 0;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 3; b++) {
					if( a == 1 && b == 1) {
						opt[a][b] = "M";
					}else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}

			}
			opt[1][0] = "X";
			opt[2][1] = "X";
			opt[2][0] = "X";
			return opt;
		}
		//Tirake payin rast
		else if(itoop == m-2 && jtoop == m2Goal) {
			String opt[][] = new String[3][3];
			int temp = 0;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 3; b++) {
					if( a == 2 && b == 2) {
						opt[a][b] = "M";
					}else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}

			}
			opt[1][2] = "X";
			opt[2][1] = "X";
			opt[2][2] = "X";
			return opt;
		}
		else if(itoop == 1 && jtoop == 4 || itoop == m - 2 && jtoop == 4){
			String [][] opt = new String[3][3]; 
			//Middle Points
			int temp = 0;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 3; b++) {
					if( a == 1 && b == 1) {
						opt[a][b] = "M";
					}else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}
			}
			return opt;
		}
		else {
			String [][] opt = new String[3][3]; 
			//Middle Points
			int temp = 0;
			for(int a = 0; a < 3; a++) {
				for(int b = 0; b < 3; b++) {
					if( a == 1 && b == 1) {
						opt[a][b] = "M";
					}else {
						if(forbiden[temp] == 1) {
							opt[a][b] = Integer.toString(temp+1);
						}else {
							opt[a][b] = "X";
						}
						temp++;
					}
				}
			}
			return opt;
		}
		
	}

	
	int robotMove(int i, int j, int[] forbiden) {
		System.out.println("Select where you want to go!");
		int input = rand.nextInt(8) + 1;
		
		if(forbiden[input-1] == 0) {
			System.out.println("You can't move to this place!");
			input = robotMove(i, j, forbiden);
			return input;
		}
		
		switch(input) {
			case 1:
				map[i][j].setN1(true);
				map[i-1][j-1].setN8(true);
				break;
			case 2:
				map[i][j].setN2(true);
				map[i-1][j].setN7(true);
				break;
			case 3:
				map[i][j].setN3(true);
				map[i-1][j+1].setN6(true);
				break;
			case 4:
				map[i][j].setN4(true);
				map[i][j-1].setN5(true);
				break;
			case 5:
				map[i][j].setN5(true);
				map[i][j+1].setN4(true);
				break;
			case 6:
				map[i][j].setN6(true);
				map[i+1][j-1].setN3(true);
				break;
			case 7:
				map[i][j].setN7(true);
				map[i+1][j].setN2(true);
				break;
			case 8:
				map[i][j].setN8(true);
				map[i+1][j+1].setN1(true);
				break;
		}
		
		return input;
				
	}
	
	
	int move(int[] forbiden) {
		System.out.println("Select where you want to go!");
		
//		
//		if(forbiden[moveInput-1] == 0) {
//			System.out.println("You can't move to this place!");
//			moveInput = move(i, j, forbiden);
//			return 0;
//		}
		
		switch(moveInput) {
			case 1:
				map[itoop][jtoop].setN1(true);
				map[itoop-1][jtoop-1].setN8(true);
				break;
			case 2:
				map[itoop][jtoop].setN2(true);
				map[itoop-1][jtoop].setN7(true);
				break;
			case 3:
				map[itoop][jtoop].setN3(true);
				map[itoop-1][jtoop+1].setN6(true);
				break;
			case 4:
				map[itoop][jtoop].setN4(true);
				map[itoop][jtoop-1].setN5(true);
				break;
			case 5:
				map[itoop][jtoop].setN5(true);
				map[itoop][jtoop+1].setN4(true);
				break;
			case 6:
				map[itoop][jtoop].setN6(true);
				map[itoop+1][jtoop-1].setN3(true);
				break;
			case 7:
				map[itoop][jtoop].setN7(true);
				map[itoop+1][jtoop].setN2(true);
				break;
			case 8:
				map[itoop][jtoop].setN8(true);
				map[itoop+1][jtoop+1].setN1(true);
				break;
		}
		
		return moveInput;
				
	}


	int[] nextMoveCoordinate(int input) {
		int res[] = new int[2];
		switch(input) {
			case 1:
				itoop--;
				jtoop--;
				break;
			case 2:
				itoop--;
				break;
			case 3:
				itoop--;
				jtoop++;
				break;
			case 4:
				jtoop--;
				break;
			case 5:
				jtoop++;
				break;
			case 6:
				itoop++;
				jtoop--;
				break;
			case 7:
				itoop++;
				
				break;
			case 8:
				itoop++;
				jtoop++;
				break;
		}
		return res;
	}

}
