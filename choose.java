import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class choose {

	private JFrame jframe;
	private JPanel contentPane;
	private JTextField textField;
	JTextArea textArea = new JTextArea();
	public int choose = 0;
	
	public choose() {
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setBounds(400, 400, 350, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		jframe.setContentPane(contentPane);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		textField.setBackground(new Color(245, 245, 245));
		
		textArea.setForeground(Color.DARK_GRAY);
		textArea.setFont(new Font("Papyrus", Font.BOLD | Font.ITALIC, 15));
		contentPane.add(textArea, BorderLayout.CENTER);
		textArea.setPreferredSize(new Dimension(250,150));
		textArea.setBackground(new Color(135, 206, 250));
		textArea.setEditable(false);
		
		jframe.setVisible(true);
		
	}
	
	public void option(char [][] move)
	{
		textArea.setText(" WelCome to PaperSocceR\n");
		textArea.append("\n");
		textArea.append(" 1) Player vs Player");
		textArea.append("\n");
		textArea.append("\n");
		textArea.append(" 2) Player vs Bot");
		
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e){}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()== KeyEvent.VK_ENTER){
					if( !textField.getText().equals("")){
						char ch = textField.getText().charAt(0);
						if( ch == '1')
						{
							choose = ch - '0';
							
							GameWindow game = new GameWindow(8, 12 ,move, choose );
							TextArea frame = new TextArea();
							frame.setVisible(true);
							frame.getkey(move, choose, game);
							
							jframe.setVisible(false);
						}
						else if( ch == '2')
						{
							choose = ch - '0';
				
							GameWindow game = new GameWindow(8, 12 ,move, choose );
							TextArea frame = new TextArea();
							frame.setVisible(true);
							frame.getkey(move, choose, game);
							
							jframe.setVisible(false);
						}
						else
						{
							textField.setText("");
						}
					}
			
				}
			}
		});
	}

}
