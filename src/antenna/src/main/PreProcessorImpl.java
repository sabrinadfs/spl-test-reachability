package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import antenna.preprocessor.v3.PPException;
import antenna.preprocessor.v3.Preprocessor;

public class PreProcessorImpl {

	ArrayList<String> javaFilePaths;
	ArrayList<String> otherFilePaths;
	Preprocessor p;
//	private static String inputPath = "../Prevayler_IFDEF";
//	private static String outputPath = "../OutputCEF";
	private static String inputPath, outputPath;


	public PreProcessorImpl(String in, String out) {
		this.inputPath = in;
		this.outputPath = out;
		javaFilePaths = new ArrayList<String>();
		p = new Preprocessor(new LoggerImpl(), new LineFilterImpl());
	}

	public void addFeatures(List<String> features) {
		if (features == null) {
			System.out.println("NO FEATURES PASSED");
			return;
		}
		for (int i = 0; i < features.size(); i++) {
			try {
				p.addDefines(features.get(i));
			} catch (PPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void preProcess(List<String> features) {
		addFeatures(features);
		getAllJavaFiles(inputPath);
		for (int i = 0; i < javaFilePaths.size(); i++) {
			String inPath = javaFilePaths.get(i);
			InputStream is;
			try {
				is = new FileInputStream(inPath);
				String outPath = inPath.replace(inputPath, outputPath);
				System.out.println(outPath);
				if (outPath.equals(inPath)) {
					System.out
							.println("OUTPUT PATH EQUALS INPUT PATH. WILL REWRITE PROJECT");
					System.exit(1);
				}
				// Create file if doesn't exists
				File file = new File(outPath + "");
				if (!file.exists()) {
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
				OutputStream os = new FileOutputStream(file);
//				System.out.println("===================================" + inPath);//remove later
				p.preprocess(is, os, "utf-8");
				
				System.out.println("----------");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (PPException e) {
				e.printStackTrace();
			}
		}
	}

	public void getAllJavaFiles(String path) {
		// -- TEARDOWN --
//		try {
//			FileUtils.deleteDirectory(new File("../OutputCEF"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		// -- End of TEARDOWN --
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles == null)
			return;
		for (int i = 0; i < listOfFiles.length; i++) {
			File currentFile = listOfFiles[i];
			String pathFile = currentFile.getPath();
			String fileName = currentFile.getName();
			if (currentFile.isFile() && fileName.endsWith(".java")) {
//				System.out.println(path + "/" + fileName);
				javaFilePaths.add(path + "/" + fileName);
			} else if (currentFile.isDirectory()) {
				getAllJavaFiles(path + "/" + fileName);
			} else { // copies the rest of the project files to the new project
				String targetPath = currentFile.getPath().replace(inputPath,
						outputPath);
				File targetFile = new File(targetPath);
				try {
					FileUtils.copyFile(currentFile, targetFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
