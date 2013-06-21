package main;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SchemataParser {

	public static void main(String[] args) throws Exception {
		List<String> paths = getLines(args[0]);
		for (int i = 0; i < paths.size(); i++) {
			String classPath = paths.get(i);
			saveClasse(generate(classPath), classPath);
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
	
	static String[] features = new String[]{"SPL","LOGGING","LOGGING_FINER","LOGGING_CONFIG","LOGGING_SEVERE",
		"LOGGING_EVICTOR","LOGGING_CLEANER","LOGGING_RECOVERY","LOGGING_DBLOGHANDLER","LOGGING_CONSOLEHANDLER",
		"LOGGING_INFO","LOGGING_BASE","LOGGING_FILEHANDLER","LOGGING_FINE","LOGGING_FINEST","ConcurrTrans",
		"LATCHES","TRANSACTIONS","CHECKLEAKS","FSYNC","Persistance","CHECKSUM","IIO","OldIO","SYNCHRONIZEDIO",
		"IO","NewIO","NIOAccess","CHUNCKEDNIO","NIO","DIRECTNIO","ENVIRONMENT_LOCKING","Checkpointer","CP_BYTES",
		"CP_TIME","CHECKPOINTER_DAEMON","DISKFULLERRO","FILEHANDLECACHE","IICLEANER","CLEANERDAEMON","CLEANER",
		"LOOKAHEADCACHE","STATISTICS","BTree","INCOMPRESSOR","IEVICTOR","CRITICAL_EVICTION","EVICTORDAEMON",
		"EVICTOR","VERIFIER"," Ops"," DELETEOP"," RENAMEOP"," TRUNCATEOP","MEMORY_BUDGET"};

	private static String containsFeature(String str){
		for (String feature : features) {
			if(str.contains(feature)){
				return feature;
			}
		}
		return "";
	}
	
	/**
	 * Replace the "variation declaration" (Variation.BASE) by 
	 * "choose declaration" (VariationDriver.dr.choose(VariationDriver.BASE))
	 * for each line in the class file.
	 */
	public static List<String> generate(String classPath) {
		List<String> lines = getLines(classPath);
		String newLine = "";
		String line = "";
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			if (line != null) {
//				if (line.contains("AtomExp")) {
//					String feature = containsFeature(line); 
//					if(!feature.equals("")){
//						newLine = line.replace(line, "//#if " + feature);
//						lines.set(i, newLine);
//					}
//				}
				if (line.contains("FeatureNotSelectedException")) {
					newLine = line.replace(line, "//#endif");
					lines.set(i, newLine);
				}
//				if (line.contains("import com.sleepycat.schemata")) {
//					newLine = line.replace(line, "");
//					lines.set(i, newLine);
//				}
			}
		}
		return lines;
	}

	private static void saveClasse(List<String> code, String classPath) throws Exception {
		 FileOutputStream output = null;
		 try {
		 output = new FileOutputStream(classPath);
		 String otherLine = "\n";
		for (String line : code) {
			if(line != null){
				 output.write(line.getBytes());
				 output.write(otherLine.getBytes());
			}
		}
		 output.close();
		 System.out.println(classPath);
		 } catch (Exception e) {
		 System.err.println( e );
		 System.exit( 1 );
		 }
	}

}
