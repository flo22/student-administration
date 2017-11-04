/**
 * This class represents the corrections of the solutions. It stores and
 * administers the corrections.
 * @author Florian Mueller
 */
public class Correction {

	/* grade for the solution */
	private int grade;

	/* comment from the tutor of the student */
	private String comment;

	/**
	 * Constructs a new correction.
	 * @param grade grade for the solution
	 * @param comment comment from the tutor of the student
	 */
	public Correction(int grade, String comment) {
		this.grade = grade;
		this.comment = comment;
	}

	/**
	 * Retruns grade of the solution.
	 * @return grade of the solution
	 */
	public int getGrade() {
		return this.grade;
	}

	/**
	 * Returns comment of this correction.
	 * @return comment of this correction
	 */
	public String getComment() {
		return this.comment;
	}

}
