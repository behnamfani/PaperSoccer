import javax.swing.JFrame;
import java.awt.*;
import java.util.Scanner;

public class GameWindow extends JFrame
{
	private static Graphics gBuf = null;
	private static GraphPaperCanvas canvas = null;
	private static Image vm = null;
	private int x, y, x1, y1;
	private int w, h;
	private Graphics2D g;	
	
	public GameWindow( int x, int y , char [][] move , int which)
	{
		if ( canvas == null )
		{
			setTitle("Game Board");
			setSize(780,800);
			setLocation(20,50);
			
			canvas = new GraphPaperCanvas(null);
			getContentPane().add(canvas);
			setVisible(true);

			vm = canvas.createImage(1100,950);
			gBuf = vm.getGraphics();
			canvas.setVm(vm);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		this.x = x;
		this.y = y;
		w = 750;
		h = 750;
		x1 = (w/2);
		y1 = (h/2)+4;
		
		
		drawBounds(move);
		gBuf.setColor( Color.BLUE );
	}
	
	
	
	public void drawBounds(char [][] move)
	{
		int n = 0 ;
		Color cur = gBuf.getColor();
		for ( int d=0; d<x; d++ ) {
			n += w/x;
			gBuf.setColor(SystemColor.inactiveCaptionBorder);
			gBuf.drawLine( x+n, y+0, x+n, y+h );
			if ( d == (x/2)-2 || d== (x/2) )
			{
				gBuf.setColor( Color.BLACK);
				gBuf.drawLine( x+n, y+0, x+n, y+(h/y) );
				gBuf.drawLine( x+n, y+(y-1)*(h/y), x+n, y+h-8);
			}
		}
		
		n = 0;
		for ( int d=0; d<y; d++ ){
			n += h/y;
			if( d == 0 || d == (y-2))
			{
				gBuf.setColor( Color.BLACK );
				gBuf.drawLine( x+3, y+n, x+(x/2-1)*(h/x), y+n );
				gBuf.drawLine( x+(x/2+1)*(h/x), y+n, x+h-6, y+n );
				gBuf.setColor( Color.LIGHT_GRAY );
				gBuf.drawLine( x+(x/2-1)*(h/x), y+n, x+(x/2+1)*(h/x), y+n );
			}
			else {
				gBuf.setColor( SystemColor.inactiveCaptionBorder );
				gBuf.drawLine( x+3, y+n, x+h-5, y+n );
			}
		}
		gBuf.setColor( Color.BLACK );
		gBuf.drawLine(10, 10, w+2, 10);
		gBuf.drawLine(10, h+6, w+2, h+6);
		gBuf.drawLine(10, 10, 10, h+5);
		gBuf.drawLine(w+2, 10, w+2, h+5);
		gBuf.setColor( cur );
		canvas.repaint();
		gBuf.setColor(Color.RED);
		gBuf.fillOval((w/2), (h/2)+4, 10, 10);
	}	
	
	
	
	public void setColor( Color c ) 
	{
		gBuf.setColor(c);
	}
	
	
	
	public void ball(int a, String turn)
	{
		Color c = Color.red;
		gBuf.setColor(Color.black);
		if (turn.equals("B")) {
			c = Color.gray;
		}
		
		if( a == 2)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5, y1-(h/y));	
			gBuf.setColor(c);
			gBuf.fillOval(x1, y1-(h/y), 10, 10);
			canvas.repaint();
			y1 = y1-(h/y);
		}
		else if( a == 5)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5+(w/x), y1+5);	
			gBuf.setColor(c);
			gBuf.fillOval(x1+(w/x), y1, 10, 10);
			canvas.repaint();
			x1 = x1 + (w/x);
		}
		else if( a == 4)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5-(w/x), y1+5);	
			gBuf.setColor(c);
			gBuf.fillOval(x1-(w/x), y1, 10, 10);
			canvas.repaint();
			x1 = x1 - (w/x);
		}
		else if(a == 7)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5, y1+(h/y));	
			gBuf.setColor(c);
			gBuf.fillOval(x1, y1+(h/y), 10, 10);
			canvas.repaint();
			y1 = y1+(h/y);
		}
		else if( a == 3)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5+(w/x), y1+5-(h/y));	
			gBuf.setColor(c);
			gBuf.fillOval(x1+(w/x), y1-(h/y), 10, 10);
			canvas.repaint();
			y1 = y1-(h/y);
			x1 = x1 + (w/x);
		}
		else if( a == 1)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5-(w/x), y1+5-(h/y));	
			gBuf.setColor(c);
			gBuf.fillOval(x1-(w/x), y1-(h/y), 10, 10);
			canvas.repaint();
			y1 = y1-(h/y);
			x1 = x1 - (w/x);
		}
		else if(a == 6)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5-(w/x), y1+5+(h/y));	
			gBuf.setColor(c);
			gBuf.fillOval(x1-(w/x), y1+(h/y), 10, 10);
			canvas.repaint();
			y1 = y1+(h/y);
			x1 = x1 - (w/x);
		}
		else if(a == 8)
		{
			gBuf.fillOval(x1, y1, 10, 10);
			gBuf.drawLine(x1+5, y1+5, x1+5+(w/x), y1+5+(h/y));	
			gBuf.setColor(c);
			gBuf.fillOval(x1+(w/x), y1+(h/y), 10, 10);
			canvas.repaint();
			y1 = y1+(h/y);
			x1 = x1 + (w/x);
		}
	}
}
	 

class GraphPaperCanvas extends Canvas
{
	private Image vm;
	
	public GraphPaperCanvas( Image vm )
	{
		this.vm = vm;
		setBackground(new Color(173, 255, 47));
	}
	
	public void setVm( Image vm )
	{
		this.vm = vm;
	}
	
	public void paint( Graphics g )
	{
		g.drawImage(vm,0,0,this);
	}
	
	public void update(Graphics g) { paint(g);}

}
