package edu.neu.csye6200.bg;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Growth simulation frame
 * @author Xiaobin Gao
 * NUID: 001445384
 */
public class GSFrame extends JFrame {
	
	private Stem base; // Base stem
	private int generation; // Number of generation
	private String rule; // Name of rule
	private JTextField tf; // For number of generation input
	private JComboBox<String> cb; // For rule selection
	private JPanel gsPanel; // For containing gsComponent
	private GSComponent gsComponent; // For drawing tree growth
	private JPanel cmdPanel; // For containing all command-use components
	private JPanel northOfcmdP; // To place into upper cmdPanel
	private JPanel southOfcmdP; // To place into lower cmdPanel
	private JButton start; // The Start button
	private Thread thread = new Thread(); // Inside the Start button for executing tree drawing 
	private ArrayList<Stem> stems = new ArrayList<Stem>(); // For holding stems
	
	/**
	 * Constructor
	 */
	public GSFrame() {
		gsPanel = new JPanel();
		gsComponent = new GSComponent();
		cmdPanel = new JPanel();
		cmdPanel.setLayout(new GridLayout(2, 1)); // GridLayout so as to put northOfcmdP and southOfcmdP in place 
		northOfcmdP = new JPanel();
		southOfcmdP = new JPanel();
		
		gsPanel.setBackground(Color.BLACK);
		gsPanel.add(gsComponent);
		add(gsPanel, BorderLayout.CENTER); // Adds gsPanel to the central region of a GSFrame
		
		makeLabel("Number of Generation: ");
		makeTextField();
		makeLabel("Rule: ");
		makeComboBox();
		start = makeButton("Start", BorderLayout.NORTH);
		makeAllButtons();
		cmdPanel.add(northOfcmdP);
		cmdPanel.add(southOfcmdP);
		add(cmdPanel, BorderLayout.NORTH); // Adds cmdPanel to the northern region of a GSFrame
		pack();
	}
	
	/**
	 * To make a JLabel and add it to northOfcmddP
	 * @param name name of JLabel created
	 */
	private void makeLabel(String name) {
		JLabel label = new JLabel(name, SwingConstants.RIGHT);
		northOfcmdP.add(label);
	}
	
	/**
	 * To make a JTextField and add it to northOfcmdP
	 */
	private void makeTextField() {
		tf = new JTextField("8", 5);
		tf.setHorizontalAlignment(JTextField.RIGHT);
		northOfcmdP.add(tf);
	}
	
	/**
	 * To make a JComboBox and add it to northOfcmdP
	 */
	private void makeComboBox() {
		cb = new JComboBox<String>(); 
		cb.addItem("1");
		cb.addItem("2");
		cb.addItem("3");
		cb.addItem("Random");
		northOfcmdP.add(cb);
	}
	
	/**
	 * To make a JButton and add it to either northOfcmdP or southOfcmdP according to layout
	 * @param name name of JButton created
	 * @param layout either BorderLayout.NORTH or BorderLayout.CENTER
	 * @return the created JButton
	 */
	private JButton makeButton(String name, String layout) {
		JButton button = new JButton(name);
		if (layout.equals(BorderLayout.NORTH)) {
			northOfcmdP.add(button);
		}
		if (layout.equals(BorderLayout.CENTER)) {
			southOfcmdP.add(button);
		}
		return button;
	}
	
	/**
	 * To make the Start button
	 */
	private void makeStartButton() {
		
		// ActionListener added
		start.addActionListener(event -> {
				thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						start.setEnabled(false); // Sets the Start button unable
						tf.setEnabled(false); // Sets text field unable
						cb.setEnabled(false); // Sets combo box unable
						base = new Stem(gsComponent.DEFAULT_WIDTH / 2, gsComponent.DEFAULT_HEIGHT - 20, 300, 0); // Creates base stem
						generation = Integer.parseInt(tf.getText().trim()); // Obtains number of generation from tf
						rule = cb.getItemAt(cb.getSelectedIndex()); // Obtains name of rule from cb
						base = BGRule.execute(base, rule); // Obtains all stems derived from base
						
						// Paints out stems generation by generation
						for (int g = 0; g <= generation; g++) {
							stems = BGGenerationSet.stemsOfAll(base, g); 
							repaint();
							
							// Produces 2s delay every time when painting a new generation
							try {
								Thread.sleep(2000); 
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						start.setEnabled(true); // Enables the Start button again
						tf.setEnabled(true); // Enables text field again
						cb.setEnabled(true); // Enables combo box again
					}
				});
			thread.start(); // thread started
		});
	}
	
	/**
	 * To make the Stop button
	 */
	private void makeStopButton() {
		JButton stop = makeButton("Stop", BorderLayout.NORTH);
		
		// ActionListener added
		stop.addActionListener(event -> {
			
			// A new thread created for painting a whole tree once for all
			Thread st = new Thread(new Runnable() {
				@Override
				public void run() {
						thread.stop(); // Terminates the Start button thread
						stems = BGGenerationSet.stemsOfAll(base, generation); // Gets all stems 
						repaint(); // Paints out all stems
				}
			});
		st.start();
		start.setEnabled(true); // Enables the Start button again
		tf.setEnabled(true); // Enables text field again
		cb.setEnabled(true); // Enables combo box again
		});
	}
	
	/**
	 * To make the Pause button
	 */
	private void makePauseButton() {
		JButton stop = makeButton("Pause", BorderLayout.CENTER);
		
		// ActionListener added
		stop.addActionListener(event -> {
			thread.suspend(); // Pauses the Start button thread
			});
	}
	
	/**
	 * To make the Resume button
	 */
	private void makeResumeButton() {
		JButton stop = makeButton("Resume", BorderLayout.CENTER);
		
		// ActionListener added
		stop.addActionListener(event -> {
			thread.resume(); // Resumes the Start button thread
			});
	}
	
	/**
	 * To set all buttons up
	 */
	private void makeAllButtons() {
		makeStartButton();
		makeStopButton();
		makePauseButton();
		makeResumeButton();
	}
				
	/**
	 * Grwoth simulation component - inner class
	 * @author Xiaobin Gao
	 * NUID: 001445384
	 */
	class GSComponent extends JComponent {
	
		private final int DEFAULT_WIDTH = 400;  
		private final int DEFAULT_HEIGHT = 600; 
		
		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			
			// Sets paint stroke and color
			for (int i = 0; i < stems.size(); i++) {
				if (i == 0) {
					g2.setStroke(new BasicStroke(1.5f));  
					g2.setPaint(new Color(124, 120, 26)); 
				}
				else if (i < 21) {
					g2.setStroke(new BasicStroke(1.0f));
					g2.setPaint(new Color(124, 120, 26));
				}
				else {
					g2.setStroke(new BasicStroke(0.5f));
					g2.setPaint(new Color(146, 83, 100));
				}
				
				Stem s = stems.get(i); // Gets a stem 
				g2.draw(new Line2D.Double(s.startX(), s.startY(), 
						s.endX(), s.endY())); // Draws a stem
				
			}
		}
		
		@Override 
		public Dimension getPreferredSize() {
			return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		}
	}
}







