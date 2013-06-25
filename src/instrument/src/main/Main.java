package main;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	public static void main(String[] args) throws Exception {
		String inputfile = args[0];
		String scopefile = args[1];
		
		List<Scope> scopes = parseScopes(scopefile);
		
		InputStream file = new FileInputStream(inputfile);
		CompilationUnit program = JavaParser.parse(file);
		AnnotationVisitor visitor = new AnnotationVisitor(scopes);
		List<MethodScopeInfo> structureInfo = new ArrayList<MethodScopeInfo>();
		
		program.accept(visitor,structureInfo);
		
		for (MethodScopeInfo st : structureInfo) {
			System.out.println(st);
		}
	}

	private static Pattern SCOPE_MATCHER = Pattern.compile("SCOPE:\\[(\\d+),(\\d+),(true|false)#\\[(.+)\\]\\]");  
	
	private static List<Scope> parseScopes(String scopefile) throws Exception {
		Scanner reader = new Scanner(new File(scopefile));
		List<Scope> scopes = new ArrayList<Scope>();
		
		while (reader.hasNextLine()) {
			Matcher m = SCOPE_MATCHER.matcher(reader.nextLine());
			if (m.matches()) {
				int start = Integer.parseInt(m.group(1));
				int end = Integer.parseInt(m.group(2));
				boolean active = Boolean.parseBoolean(m.group(3));
				String featureString = m.group(4);
				String[] features = featureString.split(" ,");
				
				Scope scope = new Scope(start,end,active,features);
				scopes.add(scope);
			}
		}
		reader.close();
		return scopes;
	}
}