//#if BASE
package sudoku.main;

/**
 * //BASE
 * 
 * 
 */
public class Structure extends AbstractEnum { // BASE
	public static final Structure ROW = new Structure("ROW"); // BASE
	public static final Structure COL = new Structure("COL"); // BASE
	public static final Structure BOX = new Structure("BOX"); // BASE

	public static Structure[] values() { // BASE
		return (Structure[]) values0(Structure.class, // BASE
				new Structure[count(Structure.class)]); // BASE
	} // BASE

	public static Structure valueOf(final String name) { // BASE
		return (Structure) valueOf0(Structure.class, name); // BASE
	} // BASE

	private Structure(final String name) { // BASE
		super(name); // BASE
	} // BASE
} // BASE
// #endif