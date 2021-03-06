import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends Canvas implements MouseListener {

	private JFrame frame;
	private static ArrayList<Point> points = new ArrayList<Point>();
	private static ArrayList<myLine> lines = new ArrayList<myLine>();
	private static LinkedList<Integer> weights = new LinkedList<Integer>();
	private static LinkedList<Integer> answer = new LinkedList<Integer>();
	private static Set<Integer> visitedNodes = new LinkedHashSet<>();
	private static Set<Integer> unvisitedNodes = new LinkedHashSet<>();
	private static boolean btnCustomPointIsPressed = true, btnRegularPointIsPressed = false, btnLineIsPressed = false, btnHamiltonIsPressed = false;
	private int oldX, oldY, newX, newY, tmp_sum;
	private String tmpLineLength;
	private int[][] weightMatrix;
	private int[] tmp_answer;
	private int V , pathCount = 1, solutionWeight = Integer.MAX_VALUE, count = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
					window.frame.setTitle("GraphX");
					window.frame.add(window);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(800,800));
		frame.setPreferredSize(new Dimension(800,800));
		addMouseListener(this);
		
		// Creating and placing buttons on screen
		JButton btnCustomPoint = new JButton("Custom point");
		JButton btnLine = new JButton("Connecting line");
		JButton btnHamilton = new JButton("Do Hamilton search");
		JButton btnRegularPoint = new JButton("Regular Point");
		JPanel btnPanel = new JPanel();
		btnPanel.add(btnCustomPoint); btnPanel.add(btnRegularPoint); btnPanel.add(btnLine); btnPanel.add(btnHamilton);  //Adding buttons on Panel
		btnPanel.setBackground(new Color(219,44,108));
		frame.getContentPane().add(btnPanel, BorderLayout.SOUTH); // Adding panel with buttons on Main Frame
		
		
		//Buttons logic
		btnCustomPoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCustomPointIsPressed = true;
				btnRegularPointIsPressed = false;
				btnLineIsPressed = false;
				btnHamiltonIsPressed = false;
			}
		});
		btnRegularPoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCustomPointIsPressed = false;
				btnRegularPointIsPressed = true;
				btnLineIsPressed = false;
				btnHamiltonIsPressed = false;
				
			}
			
		});
		btnLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCustomPointIsPressed = false;
				btnRegularPointIsPressed = false;
				btnLineIsPressed = true;
				btnHamiltonIsPressed = false;
			}
		});
		btnHamilton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCustomPointIsPressed = false;
				btnRegularPointIsPressed = false;
				btnLineIsPressed = false;
				btnHamiltonIsPressed = true;
				
				weightMatrix = new int[lines.size()][lines.size()];
				for(int i = 0; i < lines.size(); i++) {
						myLine thisLine = lines.get(i);
						weightMatrix[thisLine.getPoint1()][thisLine.getPoint2()] = thisLine.getLength();
						weightMatrix[thisLine.getPoint2()][thisLine.getPoint1()] = thisLine.getLength();
				}
				
				tmp_answer = new int[points.size()];
				V = points.size();
				doHamilton(0);
				
				System.out.println("\nThe answer is : ");
	            for(int i = 0;i < answer.size(); i++)
	            	System.out.print(" " + (answer.get(i)+1));
			}
			
		});
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.cyan);
		
		//Everything about drawing nodes of the graph.
		for(int i = 0; i < points.size(); i++) {
			fillPoint(g, points.get(i));
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.setColor(new Color(32,67,122));
			points.get(i).setNumber(i);
			g.drawString(Integer.toString(i+1), points.get(i).getX() - 7, points.get(i).getY() + 7);
			g.setColor(Color.cyan);
		}
		
		//Everything about drawing line, putting its weight on the frame.
		for(int i = 0; i < lines.size(); i++) {
			g.setColor(Color.black);
			g.drawLine(lines.get(i).getOldX(), lines.get(i).getOldY(), lines.get(i).getNewX(), lines.get(i).getNewY());
			if(lines.get(i).haveLength() == false) {
				tmpLineLength = JOptionPane.showInputDialog("Введите длину");
				g.drawString(tmpLineLength, lines.get(i).getCenterX(), lines.get(i).getCenterY());
				lines.get(i).setLength(Integer.parseInt(tmpLineLength));
				lines.get(i).setHaveLength(true);
			}
			g.drawString(Integer.toString(lines.get(i).getLength()), lines.get(i).getCenterX(), lines.get(i).getCenterY());
			g.setColor(Color.cyan);
		}
		
			
	}
	private void fillPoint(Graphics g, Point p) {
		g.fillOval(p.getX() - p.getRadius(), p.getY() - p.getRadius(), 2*p.getRadius(), 2*p.getRadius());
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(btnRegularPointIsPressed) {
			points.add(new Point(arg0.getX(), arg0.getY(), 30));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		if(btnCustomPointIsPressed) {
			points.add(new Point(arg0.getX(), arg0.getY(), 0));
		}
		
		//Action with a line. Putting the beginning of the line in the center of the point.
		if(btnLineIsPressed) {
		oldX = arg0.getX();
		oldY = arg0.getY();
		for(int i = 0; i < points.size(); i++) {
			Point currentPoint = points.get(i);
			if( (oldX > currentPoint.getX() - currentPoint.getRadius() && oldX < currentPoint.getX() + currentPoint.getRadius()
			     ) && (oldY > currentPoint.getY() - currentPoint.getRadius() && oldY < currentPoint.getY() + currentPoint.getRadius())) {
				oldX = currentPoint.getX();
				oldY = currentPoint.getY();
			}
		}
		
		
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		if(btnCustomPointIsPressed) {
			//Setting point radius = (X where pressed) - (X where released). This is in order to think, we draw it by ourselves
			points.get(points.size()-1).setRadius(Math.abs(points.get(points.size()-1).getX() - arg0.getX()));
		}
		
		// Activity with line. Connect line to centers of adjacent points.
		if(btnLineIsPressed) {
			for(int i = 0; i < points.size(); i++) {
				Point currentPoint = points.get(i);
				if( (arg0.getX() > currentPoint.getX() - currentPoint.getRadius() && arg0.getX() < currentPoint.getX() + currentPoint.getRadius()
				     ) && (arg0.getY() > currentPoint.getY() - currentPoint.getRadius() && arg0.getY() < currentPoint.getY() + currentPoint.getRadius())) {
					newX = currentPoint.getX();
					newY = currentPoint.getY();
					lines.add(new myLine(oldX, oldY, newX, newY));
					lines.get(lines.size()-1).setPoint2(currentPoint.getNumber()); //Indicating which point is connected by this line;
					break;
				}
			}
			for(int i = 0; i < points.size(); i++) {
				Point currentPoint = points.get(i);
				if(currentPoint.getX() == oldX)
					lines.get(lines.size()-1).setPoint1(currentPoint.getNumber()); //Indicating which point is connected by this line;
			}
			
		}
		
		repaint();
	}
	
	private void doHamilton(int nodeNumber) {		
		/** solution **/
        if (weightMatrix[nodeNumber][0] != 0 && pathCount == V) {
        	System.out.println("Solution found");        	
        	//If we found path with weight less than we already have, replace it.
        	if(findPathWeight(tmp_answer) < solutionWeight) {
        			solutionWeight = tmp_sum;
        			for(int i : tmp_answer)
        				answer.add(tmp_answer[i]);
        	}
        System.out.println("Solution weight is " + solutionWeight);
        }
        
        /** all vertices selected but last vertex not linked to 0 **/
        if (pathCount == V)
            return;
 
        for (int v = 0; v < V; v++){
            /** if connected **/
            if (weightMatrix[nodeNumber][v] != 0 && weightMatrix[nodeNumber][v] != -1){
                /** add to path **/            
                tmp_answer[pathCount++] = v;    
                /** remove connection **/  
                weights.add(weightMatrix[nodeNumber][v]);
                weightMatrix[nodeNumber][v] = 0;
                weightMatrix[v][nodeNumber] = 0;
                System.out.println("Добавили вершину " + v + ". Новая матрица: ");
                for(int i = 0; i < V; i++) {                	
                	for(int j = 0; j < V; j++)
                		System.out.print(weightMatrix[i][j] + " ");
                	System.out.println();
                }
                /** if vertex not already selected  solve recursively **/
                if (!isPresent(v))
                    doHamilton(v);
 
                /** restore connection **/
                weightMatrix[nodeNumber][v] = weights.getLast();
                weightMatrix[v][nodeNumber] = weights.pop();
                /** remove path **/
                //answer[--pathCount] = -1;
                pathCount--;
            }
            
        }
	}
	
	
	//   0		10		35		400
	//   10		0		10		111
	//   35		10		0		10
	//   400	111		10		0
	// Обход - 430. Ответ - 75.
	private void gregoryAlg(int node) {
		
        
        
	}
	
	private int dijkstra(Point source, Point destination) {
		
		unvisitedNodes.add(source.getNumber());
		while(unvisitedNodes.size() != 0) {
			Point thisPoint = lowestDistance(unvisitedNodes);
		}
		
		
		
		return 5;
	}
	
	private Point lowestDistance(Set <Integer> nodes) {
		Point ans = null;
		int lowestDistance = Integer.MAX_VALUE;
		for(int i = 0; i < nodes.size(); i++) {
			
		}
	}
	
	private myLine findLine(Point p1, Point p2) {
		myLine ans = null;
		for(int i = 0; i < points.size(); i++) {
			for(int j = 0; j < lines.size(); j++) {
				if((lines.get(j).getPoint1() == p1.getNumber() && lines.get(j).getPoint2() == p2.getNumber()) || (lines.get(j).getPoint1() == p2.getNumber() && lines.get(j).getPoint2() == p1.getNumber()))
					ans = lines.get(j);
			}
		}
		return ans;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    /** function to check if path is already selected **/
    public boolean isPresent(int v)
    {
        for (int i = 0; i < pathCount - 1; i++)
            if (tmp_answer[i] == v)
                return true;
        return false;                
    }
    
    // Given array of points find the path, that connect them exactly the same order and return path's weight
    private int findPathWeight (int[] arr) {
    	for(int i = 0; i < arr.length - 1; i++) {
    		for(int j = 0; j < lines.size(); j++) {
    			if(lines.get(j).getPoint1() == arr[i] && lines.get(j).getPoint2() == arr[i+1])
    				tmp_sum += lines.get(j).getLength();
    		}
    	}
    	
    	// Checking the connecting of the first node and the last one
    	for(int j = 0; j < lines.size(); j++) {
    		if((lines.get(j).getPoint1() == arr[0] && lines.get(j).getPoint2() == arr[arr.length - 1]) ||
   					   (lines.get(j).getPoint1() == arr[arr.length - 1] && lines.get(j).getPoint2() == arr[0]))
   				{tmp_sum += lines.get(j).getLength(); break;}
    	}
    	return tmp_sum;
    }

}
