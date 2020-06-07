package edu.neu.csye6200.bg;

import java.util.ArrayList;

/**
 * @author Xiaobin Gao
 * NUID: 001445384
 */
public class BGGenerationSet {
	
	private static ArrayList<Stem> current; // For holding all child stems of generation gs
	private static ArrayList<Stem> all; // For holding all stems after gs generations
	
	/**
	 * To get all child stems of generation gs
	 * @param base base stem
	 * @param gs number of generation
	 * @return all child stems of generation gs
	 */
	private static ArrayList<Stem> stemsOfNext(Stem base, int gs) {
		current = new ArrayList<Stem>();
		current.add(base);
			for (int i = 1; i <= gs; i++) {
				ArrayList<Stem> next = new ArrayList<>();
				for (Stem s : current) {
				next.add(s.children[0]);
				next.add(s.children[1]);
				next.add(s.children[2]);
				next.add(s.children[3]);
				}
				current = next;
		}
		return current;
	}
	
	/**
	 * To get all stems after gs generations 
	 * @param base base stem
	 * @param gs number of generation
	 * @return all stems after gs generations 
	 */
	public static ArrayList<Stem> stemsOfAll(Stem base, int gs) {
		all = new ArrayList<Stem>();
		for (int i = 0; i <= gs; i++) {
			all.addAll(stemsOfNext(base, i));
		}
		return all;
	}
}
