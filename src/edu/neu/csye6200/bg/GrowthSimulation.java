package edu.neu.csye6200.bg;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author Xiaobin Gao
 * NUID: 001445384
 */
public class GrowthSimulation {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GSFrame gsFrame = new GSFrame();
			gsFrame.setTitle("Growth Simulation");
			gsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gsFrame.setVisible(true);
		});
	}	
}

