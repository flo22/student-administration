/**
 * This class implements a simple shell to test the functionalities of the Praktomat.
 * @author Florian Mueller
 */
public final class Shell {

	/** the prompt of this shell */
	private static final String PROMPT = "praktomat> ";

	/** command to terminate the shell */
	private static final String CMD_QUIT = "quit";

	/** command to reset the Praktomat */
	private static final String CMD_RESET = "reset";

	/** command to create a new tutor or choose from an existing */
	private static final String CMD_TUT = "tut";

	/** command to create a new student */
	private static final String CMD_STUD = "stud";

	/** command to create a new task */
	private static final String CMD_TASK = "task";

	/** command to show all existing students */
	private static final String CMD_LIST_STUDENTS = "list-students";

	/** command to submit a solution to a task */
	private static final String CMD_SUBMIT = "submit";

	/** command to create a correction to a solution */
	private static final String CMD_REVIEW = "review";

	/** command to list all the solutions to a task on */
	private static final String CMD_LIST_SOLUTIONS = "list-solutions";

	/** command to show the results of all tasks */
	private static final String CMD_RESULTS = "results";

	/** command to show a summary of the results of the tasks */
	private static final String CMD_SUMMARY_TASK = "summary-task";

	/** command prints information about the individual tutors */
	private static final String CMD_SUMMARY_TUTOR = "summary-tutor";

	/** command prints information about the individual students */
	private static final String CMD_SUMMARY_STUDENT = "summary-student";

	/**
	 * Private Constructor. This is a Utility class that should not be
	 * instantiated.
	 */
	private Shell() {
	}

	/**
	 * main method - realizes the shell
	 * @param args command line arguments - not used here!
	 */
	public static void main(String[] args) {
		boolean quit = false;
		Praktomat praktomat = new Praktomat();

		while (!quit) {
			final String tokens[] = Terminal.askString(PROMPT).trim()
					.split("\\s+");
			final String cmd = tokens[0].toLowerCase();

			if (CMD_TUT.equals(cmd)) {
				addTutor(tokens, praktomat);

			} else if (CMD_STUD.equals(cmd)) {
				addStudent(tokens, praktomat);

			} else if (CMD_TASK.equals(cmd)) {
				addTask(tokens, praktomat);

			} else if (CMD_SUBMIT.equals(cmd)) {
				submit(tokens, praktomat);

			} else if (CMD_REVIEW.equals(cmd)) {
				review(tokens, praktomat);

			} else if (CMD_LIST_SOLUTIONS.equals(cmd)) {
				listSolutions(tokens, praktomat);

			} else if (CMD_SUMMARY_TUTOR.equals(cmd)) {
				if (tokens.length == 1) {
					for (String s : praktomat.summaryTutor()) {
						println(s);
					}
				} else {
					error("Wrong number of parameters.");
				}

			} else if (CMD_SUMMARY_STUDENT.equals(cmd)) {
				if (tokens.length == 1) {
					for (String s : praktomat.summaryStudent()) {
						println(s);
					}
				} else {
					error("Wrong number of parameters.");
				}

			} else if (CMD_SUMMARY_TASK.equals(cmd)) {
				if (tokens.length == 1) {
					for (String s : praktomat.summaryTask()) {
						println(s);
					}
				} else {
					error("Wrong number of parameters.");
				}

			} else if (CMD_RESULTS.equals(cmd)) {
				if (tokens.length == 1) {
					for (String s : praktomat.results()) {
						println(s);
					}
				} else {
					error("Wrong number of parameters.");
				}

			} else if (CMD_LIST_STUDENTS.equals(cmd)) {
				if (tokens.length == 1) {
					for (String s : praktomat.listStudents()) {
						println(s);
					}
				} else {
					error("Wrong number of parameters.");
				}

			} else if (CMD_RESET.equals(cmd)) {
				if (tokens.length == 1) {
					praktomat = new Praktomat();
				} else {
					error("Wrong number of parameters.");
				}

			} else if (CMD_QUIT.equals(cmd)) {
				if (tokens.length == 1) {
					quit = true;
				} else {
					error("Wrong number of parameters.");
				}

			} else {
				error("Unknown command: '" + cmd + "'");
			}
		}
	}

	/**
	 * Performs the given command on the given praktomat
	 * @param tokens command and parameters
	 * @param praktomat praktomat to operate on
	 */
	private static void addTutor(String[] tokens, Praktomat praktomat) {
		if (tokens.length == 2) {
			final String tutorName = tokens[1];
			if (tutorName.matches("[a-z]+")) {
				praktomat.addTutor(tutorName);
			} else {
				error("Name consist of unauthorized signs.");
			}
		} else {
			error("Wrong number of parameters.");
		}
	}

	/**
	 * Performs the given command on the given praktomat
	 * @param tokens command and parameters
	 * @param praktomat praktomat to operate on
	 */
	private static void addStudent(String[] tokens, Praktomat praktomat) {
		if (tokens.length == 3) {
			if (praktomat.existTutor()) {
				final String studentName = tokens[1];
				final String mNumber = tokens[2];
				if (mNumber.matches("[0-9]{5}")) {
					final int matNumber = Integer.parseInt(mNumber);
					if (studentName.matches("[a-z]+")) {
						if (!praktomat.containsStudent(matNumber)) {
							praktomat.addStudent(matNumber, studentName);
						} else {
							error("Student already exists.");
						}
					} else {
						error("Name consist of unauthorized signs.");
					}
				} else {
					error("Matriculation number must be a five digit positiv number.");
				}
			} else {
				error("No tutor created.");
			}
		} else {
			error("Wrong number of parameters.");
		}
	}

	/**
	 * Performs the given command on the given praktomat.
	 * @param tokens command and parameters
	 * @param praktomat praktomat to operate on
	 */
	private static void addTask(String[] tokens, Praktomat praktomat) {
		if (tokens.length == 2) {
			final String taskName = tokens[1];
			if (taskName != null) {
				println(praktomat.addTask(taskName));
			} else {
				error("No task name is given.");
			}

		} else {
			error("Wrong number of parameters.");
		}
	}

	/**
	 * Performs the given command on the given praktomat.
	 * @param tokens command and parameters
	 * @param praktomat praktomat to operate on
	 */
	private static void submit(String[] tokens, Praktomat praktomat) {
		if (tokens.length == 4) {
			if (tokens[1].matches("[1-9]+")) {
				if (tokens[2].matches("[0-9]{5}")) {
					final int taskId = Integer.parseInt(tokens[1]);
					final int matNumber = Integer.parseInt(tokens[2]);
					final String solutionText = tokens[3];
					if (praktomat.containsTask(taskId)) {
						if (praktomat.containsStudent(matNumber)) {
							if (solutionText != null) {
								if (!praktomat.containsSolution(taskId, matNumber)) {
									praktomat.submit(taskId, matNumber, solutionText);
								} else {
									error("A solution is already given.");
								}
							} else {
								error("No solution is given.");
							}
						} else {
							error("Student does not exist.");
						}
					} else {
						error("Task does not exist.");
					}
				} else {
					error("Matriculation number must be a five digit positive Number.");
				}
			} else {
				error("Task number must be a number > 0.");
			}
		} else {
			error("Wrong number of parameters.");
		}
	}

	/**
	 * Performs the given command on the given praktomat
	 * @param tokens command and parameters
	 * @param praktomat praktomat to operate on
	 */
	private static void review(String[] tokens, Praktomat praktomat) {
		if (tokens.length == 5) {
			if (tokens[1].matches("[0-9]*[1-9]{1}")) {
				if (tokens[2].matches("[0-9]{5}")) {
					if (tokens[3].matches("[1-5]{1}")) {
						final int taskId = Integer.parseInt(tokens[1]);
						final int matNumber = Integer.parseInt(tokens[2]);
						final int grade = Integer.parseInt(tokens[3]);
						final String comment = tokens[4];
						if (comment != null) {
							if (praktomat.containsTask(taskId)) {
								if (praktomat.containsStudent(matNumber)) {
									if (praktomat.containsSolution(taskId, matNumber)) {
										println(praktomat.review(taskId, matNumber, grade, comment));			
									} else {
										error("Solution does not exist.");
									}
								} else {
									error("Student does not exist.");
								}
							} else {
								error("Task does not exist.");
							}
						} else {
							error("No comment is given.");
						}
					} else {
						error("Grade must be a one digit number between 1 and 5.");
					}
				} else {
					error("Matriculation number must be a five digit positive Number.");
				}
			} else {
				error("Task number must be a number > 0.");
			}
		} else {
			error("Wrong number of parameters.");
		}
	}

	/**
	 * Performs the given command on the given praktomat
	 * @param tokens command and parameters
	 * @param praktomat praktomat to operate on
	 */
	private static void listSolutions(String[] tokens, Praktomat praktomat) {
		if (tokens.length == 2) {
			if (tokens[1].matches("[0-9]*[1-9]{1}")) {
				final int taskId = Integer.parseInt(tokens[1]);
				if (praktomat.containsTask(taskId)) {
					for (String s : praktomat.listSolutions(taskId)) {
						println(s);
					}
				} else {
					error("Task does not exist.");
				}
			} else {
				error("Task number must be a number > 0.");
			}
		} else {
			error("Wrong number of parameters.");
		}
	}

	/**
	 * Prints an error message.
	 * @param err error message to print
	 */
	private static void error(String err) {
		println("Error! " + err);
	}

	/**
	 * Prints a message.
	 * @param s string to print
	 */
	private static void println(String s) {
		System.out.println(s);
	}

}
