import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.SystemColor;

public class TextArea extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextArea textArea_1;
	private JTextArea textArea;
	public static int x ;
	public PaperSoccer p = new PaperSoccer(13, 9, "A", "B", 3, 5);
	public Bot bot = new Bot();
	static String who = "A";
	static ArrayList<String> available = new ArrayList<String>();
	boolean ok = false;
	
	/**
	 * @wbp.nonvisual location=440,149
	 */
	private final JPanel panel = new JPanel();
	
	public TextArea() {
		setBackground(Color.BLUE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(780, 300, 350, 350);
		setTitle("Game Panel");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		
		textArea = new JTextArea();
		textArea.setBackground(new Color(135, 206, 250));
		textArea.setForeground(new Color(0, 0, 0));
		textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.BLACK);
		textField_1.setBackground(new Color(245, 245, 245));
		textField_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		contentPane.add(textField_1, BorderLayout.SOUTH);
		textField_1.setColumns(10);
		
		textArea_1 = new JTextArea();
		textArea_1.setForeground(new Color(0, 0, 0));
		textArea_1.setBackground(new Color(135, 206, 250));
		textArea_1.setFont(new Font("Modern No. 20", Font.BOLD, 17));
		textArea_1.setEditable(false);
		
		contentPane.add(textArea_1, BorderLayout.NORTH);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textArea, textField_1}));
		
	}
	
	
	public void getkey(char [][] move, int which, GameWindow game )
	{
		//Player VS Player************************************************
		if( which == 1){
		
		for(int i = 1 ; i < 9 ; i++)
		{
			available.add(Integer.toString(i));
		}
		textArea_1.setText("");
		textArea_1.setForeground(Color.red);
		textArea_1.append("Player A");
		
		textArea.setText("");
		textArea.append("(A's goal is up)\n");
		
		textArea.append("(B's goal is down)\n");
		textArea.append("\n");
		textArea.append(" Your Available moves : "+"\n");
	
		for(int i = 0 ; i < 3 ; i++)
		{
			textArea.append("\n");
			textArea.append("");
			for(int j = 0 ; j < 3 ;j++)
			{
				textArea.append("\t" + move[i][j]);
			}
			textArea.append("\n");
		}
		
		KeyListener k = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e){}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()== KeyEvent.VK_ENTER){
				if( !textField_1.getText().equals("") && textField_1.getText().length() == 1){
					char ch = textField_1.getText().charAt(0);
					for( int i = 0 ; i < available.size() ; i++)
					{
						ok = false;
						if( ch == 	available.get(i).charAt(0))
						{
							ok = true;
							i = available.size();
							break;
					}
					}
					textField_1.setText("");
					if(ok == true)
					{
						x = ch - '0';
						PaperSoccer.moveInput = x;
						int forbiden[] = p.map[p.itoop][p.jtoop].forbidenNeighbors();
						p.move(forbiden);
						p.nextMoveCoordinate(x);
						who = p.returnTurn();
						if( who.equals("A")) {
							textArea_1.setText("");
							textArea_1.setForeground(Color.red); 
							textArea_1.append(" Player A");
							game.ball(x,who);
							}
						else if( who.equals("B")) {
							textArea_1.setText("");
							textArea_1.setForeground(Color.gray); 
							textArea_1.append("Player B");
							game.ball(x,who);
						}
						
						String [][] show = p.play();
						if(!show[0][0].equals("equal"))
						{
							available = new ArrayList<String>();
							textArea.setText("");
							for(int i = 0 ; i < 2 ; i++) textArea.append("\n");
							
							textArea.append(" Your Available moves : "+"\n");
							
							for(int i = 0 ; i < show.length ; i++)
							{
								textArea.append("\n");
								textArea.append("");
								for(int j = 0 ; j < show[0].length ;j++)
								{
									textArea.append("\t" + show[i][j]);
									if(!show[i][j].equals("X") && !show[i][j].equals("M"))
									{
										available.add(show[i][j]);
									}
								}
								textArea.append("\n");
							}
							textField_1.setText("");
						}
						else
						{
							textField_1.setEditable(false);
							textArea_1.setForeground(Color.darkGray);
							textArea_1.setText("DRAW");
							textArea.setText("");
						}
						if(who.equals("W1")){
							textField_1.setEditable(false);
							textArea_1.setForeground(Color.red);
							textArea_1.setText("Player A wins");
							textArea.setText("");
							game.ball(x,"A");
						}
						else if(who.equals("W2")){
							textField_1.setEditable(false);
							textArea_1.setForeground(Color.gray);
							textArea_1.setText("Player B wins");
							textArea.setText("");
							game.ball(x,"B");
					
					}
					}
				
				}
			}
		}		
		};
		
		textField_1.addKeyListener(k);
		}
		
		//Player VS Bot*************************************************
		if(which == 2)
		{
	
			for(int i = 1 ; i < 9 ; i++)
			{
				available.add(Integer.toString(i));
			}
			textArea_1.setText("");
			textArea_1.setForeground(Color.red);
			textArea_1.append("Player A");
			
			textArea.setText("");
			textArea.append("(A's goal is up)\n");
			
			textArea.append("(Bot's goal is down)\n");
			textArea.append("\n");
			textArea.append(" Your Available moves : "+"\n");
		
			for(int i = 0 ; i < 3 ; i++)
			{
				textArea.append("\n");
				textArea.append("");
				for(int j = 0 ; j < 3 ;j++)
				{
					textArea.append("\t" + move[i][j]);
				}
				textArea.append("\n");
			}
			
			KeyListener k = new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e){}
				
				@Override
				public void keyReleased(KeyEvent e) {}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyChar()== KeyEvent.VK_ENTER){
		
						if( who.equals("A") && !textField_1.getText().equals("") && textField_1.getText().length() == 1) {
							char ch = textField_1.getText().charAt(0);
							for( int i = 0 ; i < available.size() ; i++)
							{
								ok = false;
								if( ch == 	available.get(i).charAt(0))
								{
									ok = true;
									i = available.size();
									break;
								}
							}
							textField_1.setText("");
							if(ok == true)
							{
								x = ch - '0';
								PaperSoccer.moveInput = x;
								int forbiden[] = p.map[p.itoop][p.jtoop].forbidenNeighbors();
								p.move(forbiden);
								p.nextMoveCoordinate(x);
								who = p.returnTurn();
								if( who.equals("B"))
								{
									textArea_1.setText("");
									textArea_1.setForeground(Color.gray);
									textArea_1.append("Bot\n");
									textArea_1.append("Click Enter");

								}
								game.ball(x,who);
							}
					}
					else if( who.equals("B")) {
							textField_1.setText("H");
							bot.minMaxCount = 0;
							bot.miniMax(p.map[p.itoop][p.jtoop],"B", p.itoop, p.jtoop);
							x = (bot.action + 1);
							System.out.println("action = "+bot.action);
							
							for( int i = 0 ; i < available.size() ; i++)
							{
								ok = false;
								if( x == 	available.get(i).charAt(0)- '0')
								{
									ok = true;
									i = available.size();
									break;
								}
							}

							if(ok == true)
							{
								PaperSoccer.moveInput = x;
								int forbiden[] = p.map[p.itoop][p.jtoop].forbidenNeighbors();
								p.move(forbiden);
								p.nextMoveCoordinate(x);
								who = p.returnTurn();
								if(who.equals("A"))
								{
									textField_1.setText("");
									textArea_1.setText("");
									textArea_1.setForeground(Color.red);
									textArea_1.append("Player A");
								}
								game.ball(x,who);
								textField_1.setText("");
							}
					}
						
						String [][] show = p.play();
						if(!show[0][0].equals("equal"))
						{
							available = new ArrayList<String>();
							textArea.setText("");
							for(int i = 0 ; i < 2 ; i++) textArea.append("\n");
							
							textArea.append(" Your Available moves : "+"\n");
							
							for(int i = 0 ; i < show.length ; i++)
							{
								textArea.append("\n");
								textArea.append("");
								for(int j = 0 ; j < show[0].length ;j++)
								{
									textArea.append("\t" + show[i][j]);
									if(!show[i][j].equals("X") && !show[i][j].equals("M"))
									{
										available.add(show[i][j]);
									}
								}
								textArea.append("\n");
							}
							if(available.isEmpty())
							{
								textField_1.setEditable(false);
								textArea_1.setForeground(Color.darkGray);
								textArea_1.setText("DRAW");
								textArea.setText("");
								textField_1.removeKeyListener(this);
							}
							textField_1.setText("");
						}
						else
						{
							textField_1.setEditable(false);
							textArea_1.setForeground(Color.darkGray);
							textArea_1.setText("DRAW");
							textArea.setText("");
						}
						if(who.equals("W1")){
							textField_1.setEditable(false);
							textArea_1.setForeground(Color.red);
							textArea_1.setText("Player A wins");
							textArea.setText("");
							game.ball(x,"A");
							textField_1.removeKeyListener(this);
						}
						else if(who.equals("W2")){
							textField_1.setEditable(false);
							textArea_1.setForeground(Color.gray);
							textArea_1.setText("Bot wins");
							textArea.setText("");
							game.ball(x,"B");
							textField_1.removeKeyListener(this);
					}
					}
				}
			
		};

						
			textField_1.addKeyListener(k);
			}
	}
	
}
