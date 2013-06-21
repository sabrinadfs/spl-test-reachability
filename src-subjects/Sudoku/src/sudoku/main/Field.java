package sudoku.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * //BASE Stores all possible numbers that can still be set on a sudoku field. A
 * Field can be set to final, only one possibility is left after that.
 * 
 * Attention! There is no 0 in sudoku. All values are stored n-1, that means the
 * possibility 1 is stored as 0 in a Bitset. getFinal() can return 0 if no
 * possibility is left, but if that happens the sudoku cannot be solved.
 * getValue(0) is invalid!
 * 
 */
public class Field  
//#if STATES
implements Cloneable, Serializable 
//#endif
{

	/**
	 * //BASE Numbers of sudoku
	 */
	public static int POSSIBILITIES = 9; // BASE
	protected boolean set; // BASE
	protected boolean initialSet; // BASE
	protected int value; // BASE
	protected List remainingPos; // SOLVER
	protected String color; // COLOR

	/**
	 * //BASE Fill the remainingPos list.
	 */
	public Field() {
		// #if BASE
		this.initialSet = false; // BASE
		this.set = false; // BASE

		// #if COLOR
		this.color = "";
		// #endif

		// #if SOLVER
		remainingPos = new LinkedList();// SOLVER
		for (int i = 1; i <= POSSIBILITIES; i++) {// SOLVER
			remainingPos.add((Object) i);// SOLVER
		}// SOLVER
		// #endif
		// #endif
	}

	/**
	 * //BASE
	 * 
	 */
	public Field(int value, boolean initialSet) {
		// #if BASE
		this.value = value; // BASE
		this.set = true; // BASE
		this.initialSet = initialSet; // BASE

		// #if COLOR
		this.color = "";
		// #endif

		// #if SOLVER
		remainingPos = new LinkedList();// SOLVER
		// #endif
		// #endif
	}

	public Field(int value) {
		// #if BASE
		this.value = value; // BASE
		this.set = true; // BASE
		this.initialSet = false; // BASE

		// #if COLOR
		this.color = "";
		// #endif

		// #if SOLVER
		remainingPos = new LinkedList();// SOLVER
		// #endif
		// #endif
	}

// #if BASE
	/**
	 * //BASE
	 * 
	 * @return
	 */
	public int getValue() { // BASE
		return value; // BASE
	} // BASE

	/**
	 * //BASE
	 * 
	 * @return
	 */
	public boolean isInitialSet() { // BASE
		return initialSet; // BASE
	} // BASE

	/**
	 * //BASE
	 * 
	 * @return
	 */
	public boolean isSet() { // BASE
		return set; // BASE
	} // BASE
// #endif

	public void setInitial(boolean flag) { // GENERATOR
		// #if BASE && GENERATOR
		initialSet = flag; // GENERATOR
		//#endif
	} // GENERATOR

	public Field(List remainingPos) {// SOLVER
		this();// SOLVER
		// #if BASE && SOLVER
		this.remainingPos = remainingPos;// SOLVER
		// #endif
	}// SOLVER

	// #if BASE && SOLVER
	/**
	 * 
	 * @return
	 */
	public List getRemainingPos() {// SOLVER
		return remainingPos;// SOLVER
	}// SOLVER

	public String toString() {// SOLVER
		String output = "";// SOLVER
		if (remainingPos.isEmpty()) {// SOLVER
			output = "[" + value + "]";// SOLVER
		} else {// SOLVER
			output = "{";// SOLVER
			for (int i = 0; i < remainingPos.size(); i++) {// SOLVER
				output += remainingPos.get(i).toString();// SOLVER
			}// SOLVER
			output += "}";// SOLVER
		}// SOLVER
		return output;// SOLVER
	}// SOLVER

	public Object clone() throws CloneNotSupportedException {// SOLVER
		Field clone = (Field) stateClone();// SOLVER //watch out, i can't find
											// the clone method in base fetaure
		LinkedList remainingPosClone = new LinkedList();// SOLVER
		for (int i = 0; i < remainingPos.size(); i++) {// SOLVER
			remainingPosClone.add(new Integer(((Integer) remainingPos.get(i))
					.intValue()));// SOLVER
		}// SOLVER
		clone.remainingPos = remainingPosClone;// SOLVER
		return clone;// SOLVER
	}// SOLVER
// #endif

// #if BASE && STATES
	public Object stateClone() throws CloneNotSupportedException { // STATES
		Field clone = new Field(); // STATES
		clone.initialSet = initialSet; // STATES
		clone.set = set; // STATES
		clone.value = value; // STATES
		return clone; // STATES
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException { // STATES
		aOutputStream.writeBoolean(set); // STATES
		aOutputStream.writeBoolean(initialSet); // STATES
		aOutputStream.writeInt(value); // STATES
		aOutputStream.defaultWriteObject(); // STATES
	}

	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException { // STATES
		aInputStream.defaultReadObject(); // STATES
		set = aInputStream.readBoolean(); // STATES
		initialSet = aInputStream.readBoolean(); // STATES
		value = aInputStream.readInt(); // STATES
	} // STATES
// #endif

}
