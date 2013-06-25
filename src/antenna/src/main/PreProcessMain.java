package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class that preprocesses a given input.
 * 
 * @author sabrinasouto
 */
public class PreProcessMain {

	public static void main(String[] args) {
		String subject_path = args[0];
		String output_path = args[1];

		List<String> features = new ArrayList<String>();
		for (int i = 2; i < args.length; i++) {
			features.add(args[i]);
		}
		PreProcessorImpl preProcessor = new PreProcessorImpl(
				subject_path, output_path);
		
//		System.out.println(features.toString());
		
		preProcessor.preProcess(features);
	}
}