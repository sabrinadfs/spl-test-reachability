package smashed;


import java.io.IOException;


public class Main {
	/*@(Prog)*/

	public static void splStart___(){}
	
	private static StringBuffer splBuffer = new StringBuffer();
	
	public static void splPrint___(String s)
	{
		splBuffer.append(s + "\n");
	}
	
	public static void main( String[] args ) {
		splStart___();
		mainBody___(args);
		splEnd___();
	} 
	
	public static void splEnd___()
	{
		// Actually print to console
//		System.out.println(splBuffer.toString());
	}
	
	public static void mainBody___(String[] args)
	{
		// Step 1: create graph object

		Graph g = new  Graph();

		// Step 2: sets up the benchmark file to read
		try
		{
			
			splPrint___("[" + args[0] + "]");
			g.runBenchmark( args[0] );

			// Step 3: reads number of vertices, number of edges
			// and weights
			int num_vertices = 0;
			int num_edges = 0;
			int dummy = 0;

			num_vertices = g.readNumber();
			num_edges = g.readNumber();
			dummy = g.readNumber();
			dummy = g.readNumber();
			dummy = g.readNumber();

			// Step 4: reserves space for vertices, edges and weights
			Vertex V[] = new  Vertex[num_vertices];
			int weights[] = new int[num_edges];
			int startVertices[] = new int[num_edges];
			int endVertices[] = new int[num_edges];

			// Step 5: creates the vertices objects
			int i=0;
			for ( i=0; i<num_vertices; i++ ) {
				V[i] = new Vertex().assignName( "v"+i );
				g.addVertex( V[i] );
			}

			// Step 6:        reads the edges
			for( i=0; i<num_edges; i++ )
			{
				startVertices[i] = g.readNumber();
				endVertices[i] = g.readNumber();
			}

			// Step 7: reads the weights
			for( i=0; i<num_edges; i++ )
			{
				weights[i] = g.readNumber();
			}

			// Stops the benchmark reading
			g.stopBenchmark();

			// Step 8: Adds the edges
			for ( i=0; i<num_edges; i++ )
			{
				g.addAnEdge( V[startVertices[i]], V[endVertices[i]],weights[i] );
			}

			// Executes the selected features
			Graph.startProfile();
			splPrint___("arg1: {" + args[1] + "}");
			Vertex rootVertex = g.findsVertex( args[1].trim() ); 
			g.run(rootVertex);

			Graph.stopProfile();
			splPrint___( "******************************************" );
			splPrint___("<BASE___ graph>");
			g.display();
			Graph.resumeProfile();

			// End profiling    
			Graph.endProfile();
		}
		catch(IOException e){}
	}

	/**
	 * This function is supposed to assign each feature
	 * a boolean value based on a configuration file
	 * But for now, it assigns a random value to each feature variable
	 * so that the compiler doesn't do constant propagation
	 * (which would not be correct for analyzing a product line)
	 * This method must be called at the beginning of the program
	 
	public static void loadFeatures()
	{
		Set<Configuration> configs = FeatureUtility.getFeatureModelConfigurations(null);
	//	Main.printToConsole("configurations size: " + configs.size());
		
		BASE___ = true;
		DIRECTED___ = Verify.getBoolean();
		UNDIRECTED___ = Verify.getBoolean();
		WEIGHTED___ = Verify.getBoolean();
		SEARCH___ = Verify.getBoolean();
		BFS___ = Verify.getBoolean();
		DFS___ = Verify.getBoolean();
	
		CONNECTED___ = Verify.getBoolean();
		CYCLE___ = Verify.getBoolean();
		MSTKRUSKAL___ = Verify.getBoolean();
		MSTPRIM___ = Verify.getBoolean();
		NUMBER___ = Verify.getBoolean();
		SHORTEST___ = Verify.getBoolean();
		STRONGLYCONNECTED___ = Verify.getBoolean();
		TRANSPOSE___ = Verify.getBoolean();
			
	//	Verify.ignoreIf(!FeatureUtility.isValidConfiguration(Main.class, configs));	
	} */

//	public static boolean BASE___;
//	public static boolean DIRECTED___;
//	public static boolean UNDIRECTED___;
//	public static boolean WEIGHTED___;
//	public static boolean SEARCH___;
//	public static boolean BFS___;
//	public static boolean DFS___;
//
//	public static boolean NUMBER___;
//	public static boolean CONNECTED___;
//	public static boolean STRONGLYCONNECTED___;
//	public static boolean TRANSPOSE___;
//	public static boolean CYCLE___;
//	public static boolean MSTPRIM___;
//	public static boolean MSTKRUSKAL___;
//	public static boolean SHORTEST___;
}
