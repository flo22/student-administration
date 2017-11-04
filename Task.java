import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/**
 * This class represents the tasks of the praktomat. It stores and administers
 * the tasks and their solutions.
 * @author Florian Mueller
 */
public class Task implements AverageGrade {

	/** number of this task */
	private int taskNumber;

	/** description of this task */
	private String description;

	/** amount of all grades between 1 and 5 from this task */
	private int[] grade;

	/** solutions of this task */
	private TreeMap<Integer, Solution> matSol;

	/** students to the solutions */
	private TreeMap<Integer, Student> matStud;

	/**
	 * Constructs the task with a task id and his description.
	 * @param taskNumber id of this task
	 * @param description description of this task
	 */
	public Task(int taskNumber, String description) {
		this.taskNumber = taskNumber;
		this.description = description;
		this.grade = new int[5];
		matSol = new TreeMap<Integer, Solution>();
		matStud = new TreeMap<Integer, Student>();
	}

	/**
	 * Returns the number of this task.
	 * @return number of this task
	 */
	public int getTaskNumber() {
		return this.taskNumber;
	}

	/**
	 * Returns the description of this task.
	 * @return description of this task
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Adds a solution to this task. Only one solution per task and student is allowed.
	 * @param student student submits the solution
	 * @param solutionText solution
	 */
	public void addSolution(Student student, String solutionText) {
		if (solutionText == null) {
			throw new IllegalArgumentException("Error! No solution is given.");
		} else if (student == null) {
			throw new IllegalArgumentException("Error! No student is given.");
		} else if (this.containsStudent(student)) {
			throw new IllegalArgumentException(
					"Error! A solution is already given.");
		}
		Solution solution = new Solution(solutionText);
		matSol.put(student.getMatNumber(), solution);
		matStud.put(student.getMatNumber(), student);
	}

	/**
	 * Check whether it is the student in the task
	 * @param student student is searched after
	 * @return {@code true} if there exists the student, {@code false} otherwise
	 */
	public boolean containsStudent(Student student) {
		return matSol.containsKey(student.getMatNumber());
	}

	/**
	 * Finds the student.
	 * @param student student is searcht after
	 * @return student
	 */
	public Solution findSolution(Student student) {
		return matSol.get(student.getMatNumber());
	}

	/**
	 * Lists all solutions of this task with their students.
	 * @return list of all solutions from this task
	 */
	public List<String> listSolutions() {
		List<String> listSolutions = new ArrayList<String>();
		for (Integer i : matSol.keySet()) {
			listSolutions.add("(" + matStud.get(i).getMatNumber() + ","
					+ matStud.get(i).getName() + "): "
					+ matSol.get(i).getSolutionText());
		}
		return listSolutions;
	}

	/**
	 * Lists all corrections of this task with their students and grades.
	 * @return list of all corrections from this task
	 */
	public List<String> listCorrections() {
		List<String> listCorrections = new ArrayList<String>();
		for (Integer i : matSol.keySet()) {
			if (matSol.get(i).getCorrection() != null) {
				listCorrections.add(matStud.get(i).getMatNumber() + ": "
						+ matSol.get(i).getCorrection().getGrade());
			}
		}
		return listCorrections;
	}

	/**
	 * Summary of all students and their grades in this task.
	 * @return summary of this task
	 */
	public List<String> summaryTask() {
		List<String> summaryTask = new ArrayList<String>();
		int submitted = matSol.size();
		int reviewed = grade[0] + grade[1] + grade[2] + grade[3] + grade[4];
		double avg = this.averageGrade();

		DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
		df.setRoundingMode(RoundingMode.HALF_UP);

		summaryTask.add("submitted: " + Integer.toString(submitted));
		summaryTask.add("reviewed: " + Integer.toString(reviewed));
		if (avg > 0) {
			summaryTask.add("average grade: " + df.format(avg));
		} else {
			summaryTask.add("average grade: -");
		}
		summaryTask.add("distribution: " + Integer.toString(grade[0]) + "x1, "
				+ Integer.toString(grade[1]) + "x2, "
				+ Integer.toString(grade[2]) + "x3, "
				+ Integer.toString(grade[3]) + "x4, "
				+ Integer.toString(grade[4]) + "x5");

		return summaryTask;
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
	 * Lists the task id and description.
	 * @return task id and description
	 */
	public String toString() {
		return "task id(" + this.taskNumber + "): " + this.description;
	}

	/**
	 * Check if both tasks are equally.
	 * @param other task
	 * @return {@code true} if both tasks are equally, {@code false} otherwise
	 */
	public boolean equals(Task other) {
		return this.taskNumber == other.taskNumber;
	}

}
