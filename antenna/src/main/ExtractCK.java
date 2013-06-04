package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractCK {

	static Pattern padrao = Pattern
			.compile("[\\s]*if[\\s]*\\([\\s]*Variation.*[\\s]*");

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		List<String> ck = new ArrayList<String>();
		// get the CK of each class as input args
		for (String classPath : args) {
			storeCK(extractCK(classPath));
		}
	}

	private static List<String> getLines(String classPath) {
		List<String> lines = new ArrayList<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(classPath));
			String line = "";
			while (line != null) {
				line = in.readLine();
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lines;
	}

	public static List<String> extractCK(String classPath) {
		List<String> localCK = new ArrayList<String>();
		String className = getClassName(classPath);
		List<String> lines = getLines(classPath);
		String line = "";
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (line != null) {
				Matcher pesquisa = padrao.matcher(line);
				if (pesquisa.matches()) {
					i = getCKLines(className, localCK, lines, i, line);
				}
			}
		}
		return localCK;
	}

	private static int getCKLines(String className, List<String> ck,
			List<String> lines, int i, String line) {
		String featureName = getFeatureName(line);

		if (line.contains("{")) { // IF with some instructions
			Stack stack = new Stack();
			stack.push("{");

			while (!stack.empty()) {
				i++;
				if (i < lines.size() - 1)
					line = lines.get(i);

				if (line.trim().startsWith("//"))
					continue;

				if (line != null && padrao.matcher(line).matches()) {//&& line.contains("if(Variation.")
					int j = i;
					getCKLines(className, ck, lines, j++, line);
				} else {
					String ckLine = className + ":" + (i + 1) + " "
							+ featureName;
					if (!ck.contains(ckLine))
						ck.add(ckLine); // put this line in CK
				}

				
				for(int j = 0; j < line.length(); j++){
					if(line.charAt(j) == '{'){
						stack.push("{");
					}
					
					if(line.charAt(j) == '}'){
						stack.pop();
					}
				}
				
//				if (line != null && line.contains("{"))
//					stack.push("{");
//				else if (line != null && line.contains("}"))
//					stack.pop();
			}

			ck.remove(ck.size() - 1);

		} else { // IF with just one instruction
			String ckLine = className + ":" + (i + 2) + " " + featureName;
			if (!ck.contains(ckLine))
				ck.add(ckLine); // put this line in CK
		}
		return i;
	}

	private static String getFeatureName(String line) {
		String label = "Variation.";
		int beginIndex = line.indexOf(label) + label.length();
		int endIndex = line.indexOf(")");
		String featureName = line.substring(beginIndex, endIndex);
		return featureName;
	}

	/**
	 * @param pathClass
	 *            Path from class
	 * @return The name of the class
	 */
	private static String getClassName(String pathClass) {
		Scanner sc = new Scanner(pathClass).useDelimiter("src/");
		String lastField = "";
		while (sc.hasNext())
			lastField = sc.next();
		return lastField;
	}

	private static void storeCK(List<String> ck) throws Exception {
		// FileOutputStream output = null;
		// String nameArq = "project.ck";
		// try {
		// output = new FileOutputStream(nameArq);
		// String otherLine = "\n";
		for (String line : ck) {
			System.out.println(line);
			// output.write(line.getBytes());
			// output.write(otherLine.getBytes());
		}
		// output.close();
		// } catch (Exception e) {
		// System.err.println( e );
		// System.exit( 1 );
		// }
	}

}
