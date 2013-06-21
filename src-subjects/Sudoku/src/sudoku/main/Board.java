package sudoku.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * //BASE Implementation of a State
 * 
 */
public class Board
// #if STATES
		implements Cloneable, Serializable
// #endif
{

	/**
	 * //BASE Number of elements of the Sudoku //BASE
	 */
	public static int ELEMENTS = Field.POSSIBILITIES * Field.POSSIBILITIES; // BASE

	protected Field[] board; // BASE

	/**
	 * //BASE
	 * 
	 */
	public Board() { // BASE
		// #if BASE
		this.board = new Field[ELEMENTS]; // BASE
		for (int i = 0; i < ELEMENTS; i++) { // BASE
			this.board[i] = new Field(); // BASE
		} // BASE
			// #endif
	} // BASE

	/**
	 * //BASE Resolves field addresses.
	 * 
	 * @param struct
	 * @param structNr
	 * @param element
	 * @return
	 */
	public Field getField(Structure struct, int structNr, int element) { // BASE
		// #if BASE
		return board[getIndex(struct, structNr, element)]; // BASE
		// #endif
	} // BASE

	protected int getIndex(Structure str, int nr, int ele) { // BASE
		// #if BASE
		int sqrt = (int) Math.round(Math.sqrt(Field.POSSIBILITIES)); // BASE
		// BASE
		if (str.name().equals("COL")) // BASE
			return nr + (ele * Field.POSSIBILITIES); // BASE
		else if (str.name().equals("ROW")) // BASE
			return (nr * Field.POSSIBILITIES) + ele; // BASE
		else if (str.name().equals("BOX")) // BASE
			return Field.POSSIBILITIES * (nr / sqrt * sqrt + ele / sqrt)
					+ (nr % sqrt * sqrt + ele % sqrt); // BASE
		else
			// BASE
			return -1; // BASE
		// #endif
	} // BASE

	public void setField(Structure structure, int structNr, int element, Field f) { // BASE
		// #if BASE
		board[getIndex(structure, structNr, element)] = f; // BASE
		// #endif
	} // BASE

	public void removeRandomSetField() {// GENARATOR
		// #if BASE && GENERATOR
		Random r = new Random(999);// GENARATOR
		int size = Field.POSSIBILITIES * Field.POSSIBILITIES; // GENARATOR
		int rIndex = r.nextInt(size); // GENARATOR
		int counter = 0; // GENARATOR
		while ((board[rIndex].value <= 0) && counter < size) { // GENARATOR
			rIndex = ((rIndex + counter) % size); // GENARATOR
			counter++; // GENARATOR
		}

		// rIndex known
		// recreate field

		Board output = new Board(); // GENARATOR

		for (int i = 0; i < Field.POSSIBILITIES; i++) { // GENARATOR
			for (int j = 0; j < Field.POSSIBILITIES; j++) { // GENARATOR
				if (getIndex(Structure.ROW, i, j) != rIndex) { // GENARATOR
					Field f = getField(Structure.ROW, i, j); // GENARATOR
					if (f.isSet()) // GENARATOR
						output.trySetField(Structure.ROW, i, j,
								new Field(f.getValue())); // GENARATOR
				} // GENARATOR
			} // GENARATOR
		}
		board = output.board;
		// #endif

	}

// #if BASE && SOLVER
	public boolean isSolved() { // SOLVER
		for (int i = 0; i < board.length; i++)
			// SOLVER
			if (!board[i].isSet()) // SOLVER
				return false; // SOLVER
		return true; // SOLVER
	} // SOLVER

	public boolean trySetField(Structure str, int strIndex, int element, Field f) { // SOLVER
		boolean validRemoveAction = removeValueFromStructures(
				getIndex(str, strIndex, element), f.getValue()); // SOLVER
		if (validRemoveAction
				&& getField(str, strIndex, element).getRemainingPos().contains(
						(Object) f.getValue())) { // SOLVER
			setField(str, strIndex, element, f); // SOLVER
			return true; // SOLVER
		} else
			// SOLVER
			return false; // SOLVER
	} // SOLVER
	
	protected boolean removeValueFromStructures(int index, int value) { // SOLVER
		List relatedFieldIndices = getRelatedFieldIndices(index); // SOLVER
		for (int i = 0; i < relatedFieldIndices.size(); i++) { // SOLVER
			if (!board[(Integer) relatedFieldIndices.get(i)].isSet()) { // SOLVER
				List remainingPos = board[(Integer) relatedFieldIndices.get(i)]
						.getRemainingPos(); // SOLVER
				if (remainingPos.contains(value) && remainingPos.size() <= 1) // SOLVER
					return false; // SOLVER
				remainingPos.remove((Object) value); // SOLVER
				board[(Integer) relatedFieldIndices.get(i)] = new Field(
						remainingPos); // SOLVER
			} // SOLVER
		} // SOLVER
		return true; // SOLVER
	} // SOLVER
	
	protected int getStructureIndex(int index, Structure str) { // SOLVER
		int sqrt = (int) Math.round(Math.sqrt(Field.POSSIBILITIES)); // SOLVER
		if (str.name().equals("ROW")) // SOLVER
			return index / Field.POSSIBILITIES; // SOLVER
		else if (str.name().equals("COL")) // SOLVER
			return index % Field.POSSIBILITIES; // SOLVER
		else if (str.name().equals("BOX")) // SOLVER
			return sqrt * (index / (sqrt * Field.POSSIBILITIES))
					+ (index % Field.POSSIBILITIES) / sqrt; // SOLVER
		else
			// SOLVER
			return -1; // SOLVER
	} // SOLVER

	protected List getRelatedFieldIndices(int index) { // SOLVER
		List indices = new LinkedList(); // SOLVER
		Structure str; // SOLVER
		int strIndex; // SOLVER
		int indexProcessing; // SOLVER
		for (int i = 0; i < Structure.values().length; i++) { // SOLVER
			str = Structure.values()[i]; // SOLVER
			strIndex = getStructureIndex(index, str); // SOLVER
			for (int j = 0; j < Field.POSSIBILITIES; j++) { // SOLVER
				indexProcessing = getIndex(str, strIndex, j); // SOLVER
				if (!(indices.contains(indexProcessing) || indexProcessing == index)) { // SOLVER
					indices.add(indexProcessing); // SOLVER
				} // SOLVER
			} // SOLVER
		} // SOLVER
		return indices; // SOLVER
	} // SOLVER
// #endif

	// #if BASE && STATES
	public Object clone() throws CloneNotSupportedException { // STATES
		Board clone = new Board(); // STATES
		for (int i = 0; i < board.length; i++) { // STATES
			//#if SOLVER
			clone.board[i] = (Field) board[i].clone(); // STATES
			//#endif
		} // STATES
		return clone; // STATES
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException { // STATES
		aOutputStream.writeObject(board); // STATES
		aOutputStream.defaultWriteObject(); // STATES
	} // STATES

	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException { // STATES
		aInputStream.defaultReadObject(); // STATES
		board = (Field[]) aInputStream.readObject(); // STATES
	} // STATES
	// #endif

}
