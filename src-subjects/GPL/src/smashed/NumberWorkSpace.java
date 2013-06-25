package smashed;


// ***********************************************************************
   
public class NumberWorkSpace implements  WorkSpace {
/*@(Number)*/
 
	int vertexCounter;
/*@(Number)*/
 

	public NumberWorkSpace() {
        vertexCounter = 0;
    }
/*@(Number)*/
 

	public void preVisitAction( Vertex v )
    {
        // This assigns the values on the way in
        if ( v.visited!=true )
            v.VertexNumber = vertexCounter++;
    }

	public void checkNeighborAction(Vertex vsource, Vertex vtarget) {
		// TODO Auto-generated method stub
		
	}

	public void init_vertex(Vertex v) {
		// TODO Auto-generated method stub
		
	}

	public void nextRegionAction(Vertex v) {
		// TODO Auto-generated method stub
		
	}

	public void postVisitAction(Vertex v) {
		// TODO Auto-generated method stub
		
	}
}
