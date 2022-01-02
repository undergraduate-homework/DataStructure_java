import java.util.Scanner;
import java.util.LinkedList;



public class Main {
	public static void main(String[] args) {
		LinkedList<int[]> GraphInfo=new LinkedList<>();
		
		//File file = new File("/Users/mckin/Downloads/Assignment5/testcase_for_student/inputs/1.txt"); 
		
		
		int D = 0;
		
		int numOfNodes = 0;
		int numOfEdges = 0;
		
		graph cities= null;
		

		// Input graph
		// (N, E) = (# of Nodes, # of edges)
		Scanner scanner = new Scanner(  System.in);
		numOfNodes = scanner.nextInt();
		numOfEdges = scanner.nextInt();


		// (src, dst, distance)
		for (int i = 0; i < numOfEdges; i++) {
			int[] list = new int[3];
			list[0] = scanner.nextInt(); // src
			list[1] = scanner.nextInt(); // dst
			list[2] = scanner.nextInt(); // distance
			GraphInfo.add(list);
		}

		// D = reachable city distance threshold
		D = scanner.nextInt();
		
		scanner.close();

		/*  ################ IMPLEMENT YOUR CODES ##################### */
		cities = new graph(numOfNodes);
		
		cities.makeGraph(GraphInfo);
		
		
		
		
		cities.output();
		
		
		while(  !( cities.printAns(D) ) ) {
			D++;
		}

	}
}

class graph{
	//private LinkedList<Node, Integer > edge = null;
	
	private Node[] cities = null;
	
	private int numEdges = 0;
	
	public int numEdges() {
		return numEdges;
	}
	
	public graph(int numNode){
		cities = new Node[numNode];
		
		for(int i=0; i< numNode; i++) {
			
			cities[i] = new Node(numNode);
			cities[i].NodeIndex = i;

		}
	}
	
	public void makeGraph(LinkedList<int[]> GraphInfo ) {
		//Iterator<int[]> iterator = GraphInfo.iterator();
		
		for( int[] list: GraphInfo ) {
			//int[] list = iterator.next();
			
			cities[list[0] -1 ].addEdge( cities[list[1]-1], list[2]);
			
			this.numEdges ++;
		}
	}
	
	public void output( ) {
		if( this.numEdges ==0) {
			System.out.println("There are No Edges");
			return;
		}
		
		//System.out.println(cities.length);
		
		
		for(int i=0; i< cities.length; i++ ) {
			
			Dijkstra( i );
			
		}
		
		
		
	}
	
	public void Dijkstra( int index ) {
		priorityQueue Q = new priorityQueue( /*this.cities.length  ,  this.cities*/ );
		

		for(int i=0; i< this.cities.length;i ++) {
			if(i == index ) {
				//System.out.println( index );
				this.cities[i].dist[index] = 0;
			}else{
				this.cities[i].dist[index] = -1;
			}
			
			Q.insert( cities[i].dist[index] , this.cities[i] );
			
			// setEntry(I)
		}
		
		int ml = 0;
		
		while( !( Q.isEmpty()) ) {
			
			NodeInfo I = Q.removeMin();	
			
			Node u = I.node;
			
			//System.out.println( "u dist "+ u.dist[index] + " "+ Integer.toString(u.NodeIndex+1) );
			
			if( u.dist[index] != -1 ) {
			
				for( NodeInfo Edge : u.getEdge() ) {
					
					//System.out.println( "edge "+Edge.dist + " " +Integer.toString( Edge.node.NodeIndex +1));
				
					Node z = Edge.node;
					int r = u.dist[index] + Edge.dist  ;
					
					if( r < z.dist[index] || z.dist[index] == -1 ) {
						z.dist[index] = r;
		
						
						//System.out.println( z.NodeIndex +" "+ z.parent[index] );
						
						z.parent[index] = new NodeInfo(u, r) ;
						
						//System.out.println( z.NodeIndex +" "+ z.parent[index].node.NodeIndex );
						
						Q.replaceKey(  r, z );
						
					}
					
					//System.out.println("new dist "+ z.dist[index] + " "+ Integer.toString(z.NodeIndex+1) );
				}
				
			}
		}
	}
	public boolean printAns(int D) {
		
		int maxCity = -1;
		int maxAccess = -1;
		
		int minDist = 0;
		
		//int minedge = -1;
		
		int[] access = new int[this.cities.length];
		int[] totalDist = new int[this.cities.length];
		
		
		for(int i=0; i< access.length; i++) {
			access[i] = 0;
			totalDist[i] = 0;
		}
		
		for(int i=0; i< access.length; i++) {
			boolean nonAccessCheck = false;
			
			for(int j=0; j< this.cities.length ; j++ ) {
				if(  this.cities[j].dist[i] != -1  && this.cities[j].dist[i] <= D && i!=j ) {
					access[i]++;	
					totalDist[i] += this.cities[j].dist[i];
					
				}
				
			}
			
			
			if( maxAccess == -1 && access[i] >0 ) {
				maxCity = i;
				maxAccess = access[i];
				minDist = totalDist[i];
				

			}else if( maxAccess < access[i] ) {
				maxCity = i;
				maxAccess = access[i];
				minDist = totalDist[i];
				
				
				
			}else if (maxAccess == access[i] ) {
				if( minDist > totalDist[i] ) {
					
					maxCity = i;
					
					minDist = totalDist[i];
				}
			}
		}
		
		if(maxCity != -1) {
			maxAccess = access[maxCity];
			minDist = totalDist[maxCity];
			
			maxCity++;
			
			System.out.println("Best city " + maxCity );
			System.out.println("Accessibility "+ maxAccess);	
			System.out.println("Total distance "+ minDist);
			
			maxCity--;
			
				
			for(int j=0; j< this.cities.length ; j++ ) {
				
				if( j != maxCity ) {
				
					String tmp = "";
					
					//System.out.println(j +" "+ cities[j].dist[maxCity] );
						
					if( this.cities[j].dist[maxCity] <= D && this.cities[j].dist[maxCity] != -1 ) {
						
								
						Node a = this.cities[j] ;
						tmp = tmp +  Integer.toString(a.NodeIndex +1);
								
						NodeInfo par = a.parent[maxCity];
								
						while( par != null ) {
							tmp += par.node.NodeIndex +1 ;
							par = par.node.parent[maxCity] ;
							
							//System.out.println( par.node.NodeIndex +1 );
									
						}
								
						System.out.print("Path");
							
						for( int k=0; k<tmp.length(); k++ ) {
							System.out.print(" "+ tmp.charAt( tmp.length() -k-1 ) );
						}
						System.out.println();
						System.out.println("Distance "+this.cities[j].dist[maxCity]);
					}
				}
			}
			
			
			return true;
		}
		
		
		return false;
	}
		
}


class Node{
	private LinkedList< NodeInfo > edge = new LinkedList<>();
	
	//public LinkedList<Node, Integer>[] pathes = null;
	public int[] dist = null;
	
	public  NodeInfo[] parent = null; ///
	
	public int numEdges = 0;
	public int NodeIndex;
	
	public Node(int numNodes) {
		dist = new int[numNodes];
		
		this.parent = new NodeInfo[numNodes];
	}
	public void addEdge(Node city, int distance) {
		//NodeInfo 
		this.edge.add(  new NodeInfo(city, distance) );
		
		numEdges ++;
	}
	
	public LinkedList<NodeInfo> getEdge() {
		return this.edge;
	}
	


}

class NodeInfo{
	public NodeInfo() {
		
	}
	
	public NodeInfo(Node node, int dist) {
		this.node = node;
		this.dist = dist;
	}
	
	public Node node = null;
	public int dist = 0;
}

class priorityQueue{
	public priorityQueue( /*int nodeNum,  Node[] nodes*/ ){
		

		queue = new NodeInfo[capacity];
		
		for(int i=0; i<capacity; i++) {
			queue[i] = new NodeInfo();
		}
		

	}
	
	private NodeInfo[] queue = null;
	
	private int capacity = 1;
	private int size = 0;
	
	//public int distance;
	
	public int numE = 0;
	
	public void doubleCapa() {
		NodeInfo[] newNodes = new NodeInfo[capacity];

		
		for(int i=0; i< capacity;i++) {
			newNodes[i] = this.queue[i];
		}
		
		this.queue = new NodeInfo[2*capacity +1];
		
		for(int i=0; i< capacity; i++) {
			queue[i] = newNodes[i];
		}
		
		this.capacity = this.capacity*2 +1;
	}
	
	public void insert(int priority, Node pos) {
		int i;
		
		if( this.size == this.capacity) {
			doubleCapa();
		}

		
		queue[size] = new NodeInfo(pos, priority);

		i = size++;
		

		
		if( queue[i].dist != -1 ) {
			while( ( i>0) && ( queue[(i-1)/2].dist == -1  ||  queue[ (i-1)/2].dist > queue[i].dist ) ) {
				swap( i, (i-1)/2 );
				i = (i-1)/2;
			}
		}
		
	}
	
	public void swap(int i, int j) {
		NodeInfo temp1 = queue[i];
		queue[i] = queue[j];
		queue[j] = temp1;
	}
	
	
	public void replaceKey( int r, Node z ) {
		
		for(int i=0; i< this.size; i++) {
			if( this.queue[i].node.equals(z) ) {

				while( ( i>0) && ( queue[(i-1)/2].dist == -1  ||  queue[ (i-1)/2].dist > queue[i].dist ) ) {
					swap( i, (i-1)/2 );
					i = (i-1)/2;
				}
				
				
				return;
			}
		}
		insert(r, z); 
		
	}
	
	public NodeInfo removeMin() {
		int max = -1;
		int i = 0;
		
		NodeInfo maxQ = null;
		
		if(size>0) {
			max = queue[0].dist;
			maxQ = queue[0];
			
			queue[0] = queue[--size];
			
			while( 2*i+2 <size &&  (queue[2*i+1].dist != -1 &&  queue[i].dist > queue[2*i+1].dist ) || ( queue[2*i+2].dist != -1 && queue[i].dist > queue[2*i+2].dist ) )
			{
				if( queue[2*i+1].dist == -1) {
					swap( i, 2*i+2);
				}else if ( queue[2*i+2].dist == -1) {
					swap( i, 2*i+1);
				}else 
				{
					if( queue[2*i+1 ].dist > queue[2*i+2].dist ) {
						swap( i, 2*i+2);
					}else {
						swap( i, 2*i+1);
					}
				}
			}
			if( (2*i+1 < size ) && ( queue[2*i+1].dist != -1 || queue[i].dist > queue[2*i+1].dist ) ){
				swap( i, 2*i+1 );
			}
		}
		
		return maxQ;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
}

