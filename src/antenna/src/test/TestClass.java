package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import antenna.preprocessor.v3.PPException;
import antenna.preprocessor.v3.Preprocessor;

public class TestClass {
	
	
	public static void main(String[] args) {
		TestClass ts = new TestClass();
		String projectPath = "../CEF/src";
		ts.getAllJavaFiles(projectPath);
		LoggerImpl logger = new LoggerImpl();
		LineFilterImpl lineFilter = new LineFilterImpl();
		Preprocessor p = new Preprocessor(logger, lineFilter);
		InputStream is = null;
		OutputStream os = null;
		try {
			
			is = new FileInputStream("input.java");
			os = new FileOutputStream("output.java");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			ArrayList<String> features = new ArrayList<String>();
			features.add("LOGGING");
			addFeatures(p,features);
			p.preprocess(is, os, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PPException e) {
			e.printStackTrace();
		}
	}
	
	private static void addFeatures(Preprocessor p, ArrayList<String> features) {
		for(int i = 0; i < features.size(); i++) {
			try {
				p.addDefines(features.get(i));
			} catch (PPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void preProcess(ArrayList<String> features, String projectPath) {
		
	}
	
	private void getAllJavaFiles(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles == null) return;
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".java")) {
		        System.out.println(path+"/"+listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        getAllJavaFiles(path+"/"+listOfFiles[i].getName());
		      }
		    }
	}
	
}
