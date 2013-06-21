package sudoku.main;

import java.io.File;
import java.io.IOException;

public class SudokuFacade {

	private Board board;
	private BoardManager bm; // BASE
	// #if GENERATOR
	private SudokuGenerator sGen; // GENERATOR
	//#endif

	public SudokuFacade() {
		// #if BASE
		this.bm = new BoardManager();
		// #if GENERATOR
		this.sGen = new SudokuGenerator();
		// #endif
		// #endif
	}

	public void setField(Structure structure, int structNr, int element,
			int field) {
		this.board = bm.getBoard();
		Field f = new Field(field);
		System.out.println(f.toString());
		board.setField(structure, structNr, element, f);
	}

	/**
	 * Executes Load without GUI.
	 */
	public void loadFile(File f) {
		// #if BASE
		if (f != null) { // BASE
			try { // BASE
				bm.loadFile(f); // BASE
			} catch (IOException ex) { // BASE
				ex.printStackTrace(); // BASE
			} // BASE
		} // BASE

		// #if SOLVER
		if (f != null) { // SOLVER
			try { // SOLVER
				if (!bm.tryLoadFile(f)) // SOLVER
					System.out.println("Invalid sudoku! File was not loaded."); // SOLVER
			} catch (IOException ex) { // SOLVER
				System.out.println("Invalid sudoku! File was not loaded."); // SOLVER
			} // SOLVER
		} // SOLVER
		// #endif
		// #endif
	}

	/**
	 * Executes GenerateSudoku without GUI.
	 */
	public void GenerateSudoku() {
		// #if GENERATOR
		Board b = sGen.generate(); // GENERATOR
		// #endif
	}

	/**
	 * Executes LoadSatate without GUI.
	 */
	public void LoadState(File f) {
		// #if STATES
		if (f != null) { // STATES
			try { // STATES
				bm.loadState(f); // STATES
			} catch (IOException ex) { // STATES
				ex.printStackTrace(); // STATES
			} catch (ClassNotFoundException ex) { // STATES
				ex.printStackTrace(); // STATES
			} // STATES
		} // STATES
		// #endif
	}

	/**
	 * Executes SaveSatate without GUI.
	 */
	public void SaveState(File f) {
		// #if STATES
		if (f != null) { // STATES
			try { // STATES
				bm.saveState(f); // STATES
			} catch (IOException ex) { // STATES
				ex.printStackTrace(); // STATES
			} // STATES
		} // STATES
		// #endif
	}

	/**
	 * Executes SetPossibilities without GUI.
	 */
	public void setPossibilities(int numberOfPossibilities) { // EXTENDEDSUDOKU
		// #if EXTENDEDSUDOKU
		bm.setPossibilities(numberOfPossibilities); // EXTENDEDSUDOKU
		// #endif
	}

	/**
	 * Executes SolutionHint without GUI.
	 */
	public void solutionHint() {
		// #if SOLVER
		if (!bm.solutionHint())// SOLVER
			System.out.println("Sudoku not solvable!");// SOLVER
		// #endif
	}

	/**
	 * Executes Undo without GUI.
	 */
	public void undo() {
		// #if UNDO
		bm.undo();
		// #endif
	}

}
