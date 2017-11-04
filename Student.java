/**
 * This class represents the students of the praktomat. It stores and
 * administers the students.
 * @author Florian Mueller
 */
public class Student implements AverageGrade, Comparable<Student> {

	/** matriculation number of this student */
	private int matNumber;

	/** name of this student */
	private String name;

	/** amount of all grades between 1 and 5 from this student */
	private int[] grade;

	/**
	 * Constructs a new student with his name and matriculation number.
	 * @param name name of student
	 * @param matNumber matriculation number of student
	 */
	public Student(String name, int matNumber) {
		this.matNumber = matNumber;
		this.name = name;
		this.grade = new int[5];
	}

	/**
	 * Returns the name of this student.
	 * @return the name of this student
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the matriculations number of this student.
	 * @return the matriculations number of this student
	 */
	public int getMatNumber() {
		return this.matNumber;
	}

	/**
	 * Returns name of this student.
	 * @return name of this student
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Increments a amount of grades.
	 * @param grade grade
	 */
	public void incGrade(int grade) {
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException(
					"Error! Grade must be between 1 and 5.");
		}
		this.grade[grade - 1]++;
	}

	/**
	 * Decrements a amount of grades.
	 * @param grade grade
	 */
	public void decGrade(int grade) {
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException(
					"Error! Grade must be between 1 and 5.");
		}
		this.grade[grade - 1]--;
	}

	/**
	 * Returns the average grade of this object. The average grade is a
	 * non-negative double value. A negative value is returned, if no average
	 * grade for the current object exists.
	 * @return average grade of this object, or a negative number
	 */
	public double averageGrade() {
		double result = 0;
		double counter = 0;
		for (int i = 0; i < grade.length; i++) {
			counter = counter + grade[i] * (i + 1);
			result = result + grade[i];
		}
		if (result != 0) {
			result = counter / result;
		} else {
			result = -1;
		}
		return result;
	}

	/**
	 * Check if both students are equally.
	 * @param other student
	 * @return {@code true} if both students are equally, {@code false} otherwise
	 */
	public boolean equals(Student other) {
		return this.matNumber == other.matNumber;
	}

	/**
	 * Compares two student objects.
	 * @param s other student
	 * @return result of the compare
	 */
	public int compareTo(Student s) {
		int thisAvg = (int) (this.averageGrade() * 100);
		int otherAvg = (int) (s.averageGrade() * 100);
		int result = 0;
		if (s != null && !this.equals(s)) {
			if (thisAvg > otherAvg) {
				result = 1;
			} else if (thisAvg < otherAvg) {
				result = -1;
			} else if (this.getMatNumber() > s.getMatNumber()) {
				result = 1;
			} else {
				result = -1;
			}	
		}
		return result;
	}

}
