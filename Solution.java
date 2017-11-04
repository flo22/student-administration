/**
 * This class represents the solutions of the tasks. It stores and administers
 * the solutions and corrections.
 * @author Florian Mueller
 */
public class Solution {

	/** solution text of this solution */
	private String solutionText;

	/** correction of this solution */
	private Correction correction;

	/**
	 * Constructs a new solution with his solution text.
	 * @param solutionText solution text from the student
	 */
	public Solution(String solutionText) {
		this.solutionText = solutionText;
	}

	/**
	 * Adds a new correction to this solution which contains a grade and comment.
	 * @param grade grade for this solution
	 * @param comment comment for this solution
	 */
	public void addCorrection(int grade, String comment) {
		if (!Integer.toString(grade).matches("[1-5{1}]")) {
			throw new IllegalArgumentException(
					"Error! Grade must be a one digit number between one and five.");
		} else if (comment == null) {
			throw new IllegalArgumentException("Error! No comment is given.");
		}
		correction = new Correction(grade, comment);
	}

	/**
	 * Returns the solution text of this solution.
	 * @return the solution text of this solution
	 */
	public String getSolutionText() {
		return this.solutionText;
	}

	/**
	 * Returns the correction of this solution.
	 * @return the correction of this solution
	 */
	public Correction getCorrection() {
		return this.correction;
	}

}
