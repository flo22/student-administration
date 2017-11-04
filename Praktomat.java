import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/**
 * This class represents the Praktomat itself. It stores and administers the
 * tutors, students and tasks of the Praktomat's members and their relationships.
 * @author Florian Mueller
 */
public class Praktomat {

	/** list of all tutors */
	private List<Tutor> tutors;

	/** list of all students */
	private TreeMap<Integer, Student> students;

	/** list of all tasks */
	private List<Task> tasks;

	/** last added or selected tutor */
	private Tutor currentTutor;

	/**
	 * Constructs a new Praktomat with initial tutors, students and tasks.
	 */
	public Praktomat() {
		this.tutors = new LinkedList<Tutor>();
		this.students = new TreeMap<Integer, Student>();
		this.tasks = new LinkedList<Task>();
	}

	/**
	 * Adds the given tutor to the praktomat. The name of the tutor is his id.
	 * @param tutorName name of the tutor is added.
	 */
	public void addTutor(String tutorName) {
		if (tutorName == null) {
			throw new IllegalArgumentException(error("No tutor name is given."));
		} else if (!tutorName.matches("[a-z]+")) {
			throw new IllegalArgumentException(
					error("Name consist of unauthorized signs."));
		} else if (this.containsTutor(tutorName)) {
			for (Tutor t : tutors) {
				if (t.getName().equals(tutorName)) {
					currentTutor = t;
				}
			}
		} else {
			this.tutors.add(currentTutor = new Tutor(tutorName));
		}
	}

	/**
	 * returns whether there exists at least one tutor
	 * @return {@code true} if there exists at least one tutor, {@code false} otherwise
	 */
	public boolean existTutor() {
		return currentTutor != null;
	}

	/**
	 * Adds the given student to the praktomat, if a tutor is selected. The
	 * matriculation number of the student is his id.
	 * @param matNumber matriculation number of student
	 * @param studentName name of student
	 */
	public void addStudent(int matNumber, String studentName) {
		if (studentName == null) {
			throw new IllegalArgumentException(
					error("No student name is given."));
		} else if (this.containsStudent(matNumber)) {
			throw new IllegalArgumentException(error("Student already exists."));
		} else if (currentTutor == null) {
			throw new IllegalArgumentException(error("No tutor is created."));
		} else if (!studentName.matches("[a-z]+")) {
			throw new IllegalArgumentException(
					error("Name consist of unauthorized signs."));
		} else if (!Integer.toString(matNumber).matches("[0-9]{1,5}")) {
			throw new IllegalArgumentException(
					error("Matriculation number must be a five digit positiv number."));
		}
		Student student = new Student(studentName, matNumber);
		students.put(matNumber, student);
		currentTutor.addStudent(student);
	}

	/**
	 * Adds a new task to the praktomat. The task id is automatically assigned
	 * and is incremented by 1 each time.
	 * @param taskName name of task
	 * @return task id acknowledgment
	 */
	public String addTask(String taskName) {
		if (taskName == null) {
			throw new IllegalArgumentException(error("No task name is given."));
		}
		Task task = new Task(tasks.size() + 1, taskName);
		tasks.add(task);
		
		return "task id(" + Integer.toString(tasks.size()) + ")";
	}

	/**
	 * Lists all students in praktomat on.
	 * @return summary of the students in praktomat
	 */
	public List<String> listStudents() {
		List<String> listOfStudents = new ArrayList<String>();
		for (Integer matNumber : students.keySet()) {
			for (Tutor t : tutors) {
				List<Student> studFromTutor = t.getStudents();
				for (Student s : studFromTutor) {
					if (s.getMatNumber() == matNumber) {
						listOfStudents.add("(" + s.getMatNumber() + "," + s.getName() + "): " + t.getName());
					}
				}
			}
		}

		return listOfStudents;
	}

	/**
	 * Submission of a solution in praktomat by the student. The student can
	 * submit a job to only one solution.
	 * @param taskId task id of the solution
	 * @param matNumber matriculation number of the student
	 * @param solutionText solution of the student
	 */
	public void submit(int taskId, int matNumber, String solutionText) {
		if (!this.containsTask(taskId)) {
			throw new IllegalArgumentException(error("Task does not exist."));
		} else if (!this.containsStudent(matNumber)) {
			throw new IllegalArgumentException(error("Student does not exist."));
		} else if (solutionText == null) {
			throw new IllegalArgumentException(error("No solution is given."));
		}

		Student student = this.findStudent(matNumber);
		for (Task t : tasks) {
			if (t.getTaskNumber() == taskId) {
				t.addSolution(student, solutionText);
			}
		}
	}

	/**
	 * Checks whether it is the student in praktomat.
	 * @param matNumber matriculation number of the student
	 * @return {@code true} if there exists the student, {@code false} otherwise
	 */
	public boolean containsStudent(int matNumber) {
		return students.containsKey(matNumber);
	}

	/**
	 * Checks whether it is the task in praktomat
	 * @param taskId task id of the task
	 * @return {@code true} if there exists the task, {@code false} otherwise
	 */
	public boolean containsTask(int taskId) {
		boolean result = false;
		for (Task t : tasks) {
			if (t.getTaskNumber() == taskId) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Checks whether it is the solution in the praktomat.
	 * @param taskId task id of the task
	 * @param matNumber matriculation number of the student
	 * @return {@code true} if there exists the solution, {@code false} otherwise
	 */
	public boolean containsSolution(int taskId, int matNumber) {
		boolean result = false;
		for (Task t : tasks) {
			if (t.getTaskNumber() == taskId) {
				result = t.containsStudent(this.findStudent(matNumber));
			}
		}
		return result;
	}
	
	/**
	 * Checks whether it is the tutor in the praktomat.
	 * @param tutorName name of the tutor
	 * @return {@code true} if there exists the tutor, {@code false} otherwise
	 */
	public boolean containsTutor(String tutorName) {
		boolean result = false;
		for (Tutor t : tutors) {
			if (t.getName().equals(tutorName)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * The tutor assesses the student with a grade and a comment submitted to
	 * its solution.
	 * @param taskId task id of the task
	 * @param matNumber matriculation number of the student
	 * @param grade grade for the solution
	 * @param comment comment for the solution
	 * @return reviewed acknowledgment
	 */
	public String review(int taskId, int matNumber, int grade, String comment) {
		if (comment == null) {
			throw new IllegalArgumentException(error("No comment is given."));
		} else if (!this.containsTask(taskId)) {
			throw new IllegalArgumentException(error("Task does not exist."));
		} else if (!this.containsStudent(matNumber)) {
			throw new IllegalArgumentException(
					error("Student does not exists."));
		} else if (!this.containsSolution(taskId, matNumber)) {
			throw new IllegalArgumentException(
					error("Solution does not exist."));
		}
		Student student = this.findStudent(matNumber);
		Tutor tutor = this.findTutor(student);
		Task task = this.findTask(taskId);
		Solution solution = this.findSolution(taskId, matNumber);
		String reviewed;
		if (solution.getCorrection() != null) {
			int oldGrade = solution.getCorrection().getGrade();
			student.decGrade(oldGrade);
			tutor.decGrade(oldGrade);
			task.decGrade(oldGrade);
		}
		student.incGrade(grade);
		tutor.incGrade(grade);
		task.incGrade(grade);
		solution.addCorrection(grade, comment);
		
		reviewed = tutor + " reviewed (" + student.getMatNumber() + "," + student.getName() 
				+ ") with grade " + Integer.toString(grade);
		return reviewed;
	}

	/**
	 * Lists all students from praktomat with their solution.
	 * @param taskId task id of the task
	 * @return all students with their solution
	 */
	public List<String> listSolutions(int taskId) {
		if (!this.containsTask(taskId)) {
			throw new IllegalArgumentException(error("Task does not exist."));
		}
		Task task = this.findTask(taskId);
		return task.listSolutions();
	}

	/**
	 * Gives the results for all tasks on.
	 * @return results of all tasks
	 */
	public List<String> results() {
		List<String> results = new ArrayList<String>();
		for (Task t : tasks) {
			results.add(t.toString());
			for (String s : t.listCorrections()) {
				results.add(s);
			}
		}
		return results;
	}

	/**
	 * Summary of all tasks which contains the number of submitted, reviewed,
	 * average grade and the distribution.
	 * @return summary of all tasks
	 */
	public List<String> summaryTask() {
		List<String> summaryTask = new ArrayList<String>();
		for (Task t : tasks) {
			summaryTask.add(t.toString());
			for (String s : t.summaryTask()) {
				summaryTask.add(s);
			}
		}
		return summaryTask;
	}

	/**
	 * Summary of all students which contains all students with their average grade.
	 * @return summary of all students
	 */
	public List<String> summaryStudent() {
		List<String> summaryStudent = new ArrayList<String>();
		List<Student> avgStud = new ArrayList<Student>();
		TreeMap<Integer, Student> matStud = new TreeMap<Integer, Student>();
		double avg;

		DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
		df.setRoundingMode(RoundingMode.HALF_UP);

		for (Student s : students.values()) {
			if (s.averageGrade() > 0) {
				avgStud.add(s);
			} else {
				matStud.put(s.getMatNumber(), s);
			}
		}
		Collections.sort(avgStud);

		for (Student s : avgStud) {
			avg = s.averageGrade();
			summaryStudent.add("(" + s.getMatNumber() + "," + s.getName()
					+ "): " + df.format(avg));
		}

		for (Student s : matStud.values()) {
			summaryStudent.add("(" + s.getMatNumber() + "," + s.getName()
					+ "): -");
		}
		return summaryStudent;
	}

	/**
	 * Summary of all tutors which contains the tutor names, amount of their
	 * students, missing reviews, and the average grade given from the tutor.
	 * @return summary of all tutors
	 */
	public List<String> summaryTutor() {
		List<String> summaryTutor = new ArrayList<String>();
		int missingStud = 0;
		String avgStr;

		DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
		df.setRoundingMode(RoundingMode.HALF_UP);

		Collections.sort(tutors);
		for (Tutor t : tutors) {
			if (t.averageGrade() < 0) {
				avgStr = "-";
			} else {
				avgStr = df.format(t.averageGrade());
			}

			missingStud = 0;
			for (Task task : tasks) {
				for (Student stud : t.getStudents()) {
					if (task.containsStudent(stud)) {
						if (task.findSolution(stud).getCorrection() == null) {
							missingStud++;
						}
					}
				}
			}

			summaryTutor.add(t.getName() + ": "
					+ Integer.toString(t.getStudents().size()) + " students, "
					+ Integer.toString(missingStud)
					+ " missing review(s), average grade " + (avgStr));
		}
		return summaryTutor;
	}

	/**
	 * Finds the student.
	 * @param matNumber matriculation number of the student
	 * @return student
	 */
	private Student findStudent(int matNumber) {
		Student student = null;
		for (Student s : students.values()) {
			if (s.getMatNumber() == matNumber) {
				student = s;
			}
		}
		return student;
	}

	/**
	 * Finds the solution.
	 * @param taskId task id of the task
	 * @param matNumber matriculation number of the student
	 * @return solution
	 */
	private Solution findSolution(int taskId, int matNumber) {
		return this.findTask(taskId).findSolution(this.findStudent(matNumber));
	}

	/**
	 * Finds the tutor.
	 * @param student student from the tutor
	 * @return tutor
	 */
	private Tutor findTutor(Student student) {
		assert student != null;
		Tutor tutor = null;
		for (Tutor t : tutors) {
			if (t.containsStudent(student)) {
				tutor = t;
			}
		}
		return tutor;
	}

	/**
	 * Finds the task.
	 * @param taskId task id of the task
	 * @return task
	 */
	private Task findTask(int taskId) {
		return tasks.get(taskId - 1);
	}

	/**
	 * Error concatenate with the description.
	 * @param err error description
	 * @return error with the description
	 */
	private static String error(String err) {
		return ("Error! " + err);
	}

}
