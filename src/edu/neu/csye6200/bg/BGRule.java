package edu.neu.csye6200.bg;

/**
 * @author Xiaobin Gao
 * NUID: 001445384
 */
public class BGRule {
	
	// Angle offsets in radian measure of child stems relative to their parent
	private static double theta1; 
	private static double theta2; 
	
	/**
	 * To calculate all child stems derived from stem s under rule i
	 * @param s base stem
	 * @param i name of rule
	 * @return base stem, which contains all child stems
	 */
	public static Stem execute(Stem s, String i) {
		rule(i);
		if (i.equals("Random")) return execute(s, 1); // execute(s, 1) when the random rule is requested
		else return execute(s, 0); // execute(s, 0) when a rule rather than random rule is requested
	}
	
	/**
	 * To calculate theta1 and theta2 when rule i
	 * @param i name of rule
	 */
	private static void rule(String i) {
		switch(i) {
		case "1" : 
			theta1 = Math.PI / 6;
			theta2 = Math.PI / 3;
			break;
		case "2" :
			theta1 = Math.PI / 6;
			theta2 = Math.PI / 4;
			break;
		case "3" :
			theta1 = Math.PI / 8;
			theta2 = Math.PI / 18;
			break;
		case "Random" :
			theta1 = Math.PI / 3;
			theta2 = Math.PI / 6;
			break;
		default:
			break;
		}		
	}
	
	/**
	 * To calculate all child stems derived from stem s under corresponding rule 
	 * @param s base stem
	 * @param r an integer to determine if the random rule is requested
	 * @return base stem, which contains all child stems
	 */
	private static Stem execute(Stem s, int r) {
		if (s == null) return null;
		if (s.length() > 0.1) {
			double x = s.endX();
			double y = s.endY();
			double len;
			if (s.length() > 200) len = 100;
			else len = s.length() * 0.5;
			double dir = s.direction();
			
			// A rule rather than the random rule is requested when r = 0 
			if (r == 0) {
				s.children[0] = execute(new Stem(x, y, len, dir + theta1), 0);
				s.children[1] = execute(new Stem(x, y, len, dir - theta1), 0);
				s.children[2] = execute(new Stem(x, y, len, dir + theta2), 0);
				s.children[3] = execute(new Stem(x, y, len, dir - theta2), 0);
			}
			
			// The random rule is requested when r = 1 
			if (r == 1) {
				s.children[0] = execute(new Stem(x, y, len, dir + theta1 * Math.random()), 1);
				s.children[1] = execute(new Stem(x, y, len, dir - theta1 * Math.random()), 1);
				s.children[2] = execute(new Stem(x, y, len, dir + theta2 * Math.random()), 1);
				s.children[3] = execute(new Stem(x, y, len, dir - theta2 * Math.random()), 1);
			}
		}
		return s;
	}
}
