import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the tutors of the praktomat. It stores and administers
 * the tutors.
 * @author Florian Mueller
 */
public class Tutor implements Comparable<Tutor>, AverageGrade {

	/** name of this tutor */
	private String name;

	/** amount of all grades between 1 and 5 given by this tutor */
	private int[] grade;

	/** list of all students from this tutor */
	private List<Student> students;

	/**
	 * Constructs a new tutor with his name.
	 * @param name name of tutor is his id.
	 */
	public Tutor(String name) {
		this.name = name;
		this.students = new ArrayList<Student>();
		this.grade = new int[5];
	}

	/**
	 * Returns the name of this tutor.
	 * @return the name of this tutor
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the name of this tutor.
	 * @return the name of this tutor
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Adds a new student to this tutor.
	 * @param student student to add
	 */
	public void addStudent(Student student) {
		students.add(student);
	}

	/**
	 * Returns the list of students from this tutor.
	 * @return list of students from this tutor
	 */
	public List<Student> getStudents() {
		List<Student> studentsFromTheTutor = new ArrayList<Student>();

		for (Student s : students) {
			studentsFromTheTutor.add(new Student(s.getName(), s.getMatNumber()));
		}
		return studentsFromTheTutor;
	}

	/**
	 * Check whether it is the student from this tutor
	 * @param student student is searched after
	 * @return {@code true} if there exists the student, {@code false} otherwise
	 */
	public boolean containsStudent(Student student) {
		return students.contains(student);
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
	 * Check if both tutors are equally.
	 * @param other tutor
	 * @return {@code true} if both tutors are equally, {@code false} otherwise
	 */
	public boolean equals(Tutor other) {
		return this.name.equals(other.name);
	}

	/**
	 * Compares two tutor objects.
	 * @param t other tutor
	 * @return result of the compare
	 */
	public int compareTo(Tutor t) {
		int result = this.name.compareTo(t.name);

		if (result == 0) {
			result = this.name.compareTo(t.name);
		}
		return result;
	}

}