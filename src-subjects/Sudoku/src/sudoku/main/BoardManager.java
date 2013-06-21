package sudoku.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BoardManager {

	protected Board board; // BASE
	protected List sudokuViews; // BASE
	protected Stack history; // STATES

	public BoardManager() { // BASE
		// #if BASE
		sudokuViews = new LinkedList(); // BASE
		// #if STATES
		history = new Stack();// STATES
		// #endif
		// #endif
	} // BASE

	protected void updateSudokuViews() { // BASE
		// #if BASE
		for (int i = 0; i < sudokuViews.size(); i++) { // BASE
		// ((Gui) sudokuViews.get(i)).update(getBoard()); //BASE
		} // BASE
		// #endif
	} // BASE

	public void loadFile(File f) throws IOException { // BASE
		// #if BASE
		preLoadWrapper(); // BASE
		board = new Board(); // BASE
		BufferedReader fileReader = new BufferedReader(new FileReader(f)); // BASE

		int digit = (Field.POSSIBILITIES / 10) + 1; // BASE

		int row = 0; // BASE
		while (row < Field.POSSIBILITIES) { // BASE
			String sudokuLine = fileReader.readLine(); // BASE
			int value; // BASE
			char c; // BASE

			int extendedInt; // BASE
			char extendedC; // BASE

			if (digit == 1) { // BASE
				for (int i = 0; i < Field.POSSIBILITIES; i++) { // BASE
					c = sudokuLine.charAt(i); // BASE

					if (c != '.') { // BASE
						value = Integer.parseInt(Character.toString(c)); // BASE
						setFieldPrivate(Structure.ROW, row, i, new Field(value,
								true)); // BASE
					} // BASE
				} // BASE
			} else if (digit == 2) { // BASE
				for (int i = 0; i < Field.POSSIBILITIES * digit; i = i + digit) { // BASE
					c = sudokuLine.charAt(i); // BASE
					extendedC = sudokuLine.charAt(i + 1); // BASE

					if (c != '.') { // BASE
						value = Integer.parseInt(Character.toString(c)) * 10; // BASE
						extendedInt = Integer.parseInt(Character
								.toString(extendedC)); // BASE
						value += extendedInt; // BASE
						setFieldPrivate(Structure.ROW, row, (i / digit),
								new Field(value, true)); // BASE
					} // BASE
				} // BASE
			} // BASE

			row++; // BASE
		} // BASE
		updateSudokuViews(); // BASE
		// #endif
	} // BASE

	protected void setFieldPrivate(Structure structure, int structNr,
			int element, Field f) { // BASE
		// #if BASE
		board.setField(structure, structNr, element, f); // BASE
		// #endif
	} // BASE

	protected void preSetFieldWrapper(Structure structure, int structNr,
			int element, Field f) {
		// #if BASE && STATES
		try {// STATES
			history.push(board.clone());// STATES
		} catch (CloneNotSupportedException e) {// STATES
		}
		// #endif

		// BASE do nothing
	}

	protected void preLoadWrapper() { // BASE
		// #if BASE && STATES
		history.clear();// STATES
		// #endif
		// BASE do nothing
	} // BASE

	public void setField(Structure structure, int structNr, int element, Field f) { // BASE
		// #if BASE
		preSetFieldWrapper(structure, structNr, element, f); // BASE
		setFieldPrivate(structure, structNr, element, f); // BASE
		updateSudokuViews(); // BASE
		// #endif
	} // BASE

	public Field getField(Structure structure, int structNr, int element) { // BASE
		// #if BASE
		return getBoard().getField(structure, structNr, element); // BASE
		// #endif
	} // BASE

	public Board getBoard() {
		// #if BASE
		if (board == null) { // BASE
			board = new Board(); // BASE
		} // BASE
		return board; // BASE
		// return this.board; // SOLVER extrange
		// #endif
	}

	public void setBoard(Board board) { // BASE
		// #if BASE
		this.board = board; // BASE
		// #endif
	} // BASE

	public void setPossibilities(int possibilities) {// EXTENDEDSUDOKU
		// #if EXTENDEDSUDOKU
		Field.POSSIBILITIES = possibilities;// EXTENDEDSUDOKU
		this.board = null;// EXTENDEDSUDOKU
		updateSudokuViews();// EXTENDEDSUDOKU
		// #endif
	}// EXTENDEDSUDOKU

	public void loadSudoku(Board board) { // GENERATOR
		// #if GENERATOR
		preLoadWrapper(); // GENERATOR
		this.board = board; // GENERATOR
		updateSudokuViews(); // GENERATOR
		// #endif
	} // GENERATOR

	public void setBusy(boolean busy) { // SOLVER
		// #if SOLVER
		for (int i = 0; i < sudokuViews.size(); i++) { // SOLVER
		// ((Gui) sudokuViews.get(i)).setBusy(busy); //SOLVER
		} // SOLVER
		// #endif
	} // SOLVER

	protected boolean busy; // SOLVER

	// #if SOLVER
	protected boolean trySetFieldPrivate(Structure structure, int structNr,
			int element, Field f) { // SOLVER
		return board.trySetField(structure, structNr, element, f); // SOLVER
	} // SOLVER

	public boolean trySetField(Structure structure, int structNr, int element,
			Field f) {// SOLVER
		preSetFieldWrapper(structure, structNr, element, f);// SOLVER
		boolean set = trySetFieldPrivate(structure, structNr, element, f);// SOLVER
		if (set) {// SOLVER
			updateSudokuViews();// SOLVER
			return true;// SOLVER
		} else {// SOLVER
			undo();// SOLVER
			return false;// SOLVER
		}// SOLVER
	}// SOLVER

	public boolean tryLoadFile(File f) throws IOException { // SOLVER
		preLoadWrapper(); // SOLVER
		board = new Board(); // SOLVER
		BufferedReader fileReader = new BufferedReader(new FileReader(f)); // SOLVER

		int digit = (Field.POSSIBILITIES / 10) + 1;// SOLVER

		int row = 0;// SOLVER
		while (row < Field.POSSIBILITIES) {// SOLVER
			String sudokuLine = fileReader.readLine();// SOLVER
			int value;// SOLVER
			char c;// SOLVER
			int extendedInt;// SOLVER
			char extendedC;// SOLVER

			if (digit == 1) {// SOLVER
				for (int i = 0; i < Field.POSSIBILITIES; i++) {// SOLVER
					c = sudokuLine.charAt(i);// SOLVER

					if (c != '.') {// SOLVER
						value = Integer.parseInt(Character.toString(c));// SOLVER
						if (!trySetFieldPrivate(Structure.ROW, row, i,
								new Field(value, true))) {// SOLVER
							board = null;// SOLVER
							updateSudokuViews();// SOLVER
							return false;// SOLVER
						}// SOLVER
					}// SOLVER
				}// SOLVER
			} else if (digit == 2) {// SOLVER
				for (int i = 0; i < Field.POSSIBILITIES * digit; i = i + digit) {// SOLVER
					c = sudokuLine.charAt(i);// SOLVER

					extendedC = sudokuLine.charAt(i + 1);// SOLVER

					if (c != '.' && extendedC != '.') {// SOLVER
						value = Integer.parseInt(Character.toString(c)) * 10;// SOLVER
						extendedInt = Integer.parseInt(Character
								.toString(extendedC));// SOLVER
						value += extendedInt;// SOLVER
						if (!trySetFieldPrivate(Structure.ROW, row,
								(i / digit), new Field(value, true))) {// SOLVER
							board = null;// SOLVER
							updateSudokuViews();// SOLVER
							return false;// SOLVER
						} // SOLVER
					} // SOLVER
				}// SOLVER
			}// SOLVER

			row++;// SOLVER
		}// SOLVER
		updateSudokuViews();// SOLVER
		return true;// SOLVER
	} // SOLVER

	public boolean solutionHint() { // SOLVER
		if (board.isSolved()) // SOLVER
			return true; // SOLVER
		try { // SOLVER
			setBusy(true); // SOLVER
			List solutions = solve((Board) board.clone()); // SOLVER
			if (solutions.isEmpty()) { // SOLVER
				setBusy(false); // SOLVER
				return false; // SOLVER
			} // SOLVER
			for (int i = 0; i < Field.POSSIBILITIES; i++)
				// SOLVER
				for (int j = 0; j < Field.POSSIBILITIES; j++)
					// SOLVER
					if (!board.getField(Structure.ROW, i, j).isSet() // SOLVER
							&& ((Board) solutions.get(0)).getField(
									Structure.ROW, i, j).isSet()) { // SOLVER
						trySetField(Structure.ROW, i, j, // SOLVER
								((Board) solutions.get(0)).getField(
										Structure.ROW, i, j)); // SOLVER
						updateSudokuViews(); // SOLVER
						return true; // SOLVER
					} // SOLVER
			setBusy(false); // SOLVER
		} catch (CloneNotSupportedException e) { // SOLVER
		}
		return false; // SOLVER
	} // SOLVER

	protected List solve(Board board) { // SOLVER
		List solutions = new LinkedList(); // SOLVER
		List solvers = new LinkedList(); // SOLVER
		solvers.add(new ForcedField()); // SOLVER
		solvers.add(new ForcedNumber()); // SOLVER
		Guesser guesser = new Guesser(); // SOLVER
		for (int i = 0; i < solvers.size(); i++)
			// SOLVER
			if (!((Solver) solvers.get(i)).trySolve(board)) // SOLVER
				return solutions; // SOLVER
		if (!board.isSolved()) { // SOLVER
			List guessed = guesser.guess(board); // SOLVER
			for (int i = 0; i < guessed.size(); i++)
				// SOLVER
				solutions.addAll(solve(((Board) guessed.get(i)))); // SOLVER
		} else { // SOLVER
			solutions.add(board); // SOLVER
		} // SOLVER
		return solutions; // SOLVER
	} // SOLVER
	// #endif

	public void loadState(File f) throws IOException, ClassNotFoundException {// STATES
		// #if STATES
		ObjectInput i = new ObjectInputStream(new FileInputStream(f));// STATES
		// board = (Board) i.readObject();//STATES
		// history = (Stack) i.readObject();//STATES
		updateSudokuViews();// STATES
		// #endif
	}// STATES

	public void saveState(File f) throws IOException {// STATES
		// #if STATES
		ObjectOutput o = new ObjectOutputStream(new FileOutputStream(f));// STATES
		o.writeObject(getBoard());// STATES
		o.writeObject(history);// STATES
		o.close(); // STATES
		// #endif
	} // STATES

	public void undo() { // UNDO
		//#if UNDO
		if (!history.empty()) { // UNDO
			board = (Board) history.pop(); // UNDO
			updateSudokuViews(); // UNDO
		} // UNDO
		// #endif
	} // UNDO

}
