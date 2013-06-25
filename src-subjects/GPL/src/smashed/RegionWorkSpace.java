package smashed;


// *****************************************************************
   
public class RegionWorkSpace implements  WorkSpace {
/*@(Connected)*/
 
	int counter;
/*@(Connected)*/
 

	public RegionWorkSpace() {
        counter = 0;
    }
/*@(Connected)*/
 

	public void init_vertex( Vertex v ) {
        v.componentNumber = -1;
    }
/*@(Connected)*/
 
      
	public void postVisitAction( Vertex v ) {
        v.componentNumber = counter;
    }
/*@(Connected)*/
 

	public void nextRegionAction( Vertex v ) {
        counter ++;
    }


	public void checkNeighborAction(Vertex vsource, Vertex vtarget) {
		// TODO Auto-generated method stub
		
	}

	public void preVisitAction(Vertex v) {
		// TODO Auto-generated method stub
		
	}
}
