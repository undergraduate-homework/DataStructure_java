import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Hash Table=new Hash();
		LinkedList<String> Text=new LinkedList<>();
		int student_id=0;

		while (true)
		{
			try
			{
				StringTokenizer st = new StringTokenizer(br.readLine());
				String option = st.nextToken();
				option = option.toUpperCase();

				if(option.equals("Q"))
				{
					break;
				}
				else if(option.equals("I")) {
					String sequence = st.nextToken("\n").toUpperCase().trim();
					student_id++;
					Table.insertSequence(sequence, student_id);
					Text.add(sequence);
				}
				else if(option.equals("S"))
				{
					String pattern = st.nextToken("\n").toUpperCase().trim();
					Table.retrievalPattern(pattern, Text);
				}
				else if(option.equals("P")) {
					String strHashValue = st.nextToken().trim();
					int HashValue = Integer.parseInt(strHashValue);
					Table.getAVLTree(HashValue).PrintByHashValue(HashValue);
				}
			}
			catch (IOException e)
			{
				System.out.println("Wrong input : " + e.toString());
			}
		}
	}

}

class Hash{
	private int TableSize=0;
	private AVLTree AVLTrees[] = null;

	public Hash(){
		TableSize=64;
		AVLTrees=new AVLTree[64];

		for(int i=0; i<64; i++){
			AVLTrees[i]=new AVLTree(i);
		}
	}
	public int CalcHashValue(String HashValueStr){
		HashValueStr = HashValueStr.replace("A", "1");
		HashValueStr = HashValueStr.replace("T", "2");
		HashValueStr = HashValueStr.replace("G", "3");
		HashValueStr = HashValueStr.replace("C", "4");
		int HashValue = Integer.parseInt(HashValueStr);
		return HashValue % 64;
	}
	public AVLTree getAVLTree(int HashValue){
		return AVLTrees[HashValue];
	}
	public void insertSequence(String sequence, int student_id){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		ListNode head = new ListNode();
		ListNode curr = head;
		
		for(int i=0; i< sequence.length() -5; i++ ) {
			int rest = CalcHashValue( sequence.substring(i, i+6)  );
			
			ListNode next = new ListNode(student_id, i+1);
			curr.setNext(next);
			next.setPrev(curr);
			curr = next;
			
			//TreeNode child = new TreeNode(sequence.substring(i, i+6) );
			int NumOfElement =  AVLTrees[rest].InsertNode(curr, sequence.substring(i, i+6) );

		}
	}
	public void retrievalPattern(String pattern, LinkedList<String> Text){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		String substr = pattern.substring(0, 6);
		int hashValue =  CalcHashValue( substr)  ;
		
		TreeNode a =  AVLTrees[hashValue].SearchNode( substr );  // 
		
		if( a == null ) {
			System.out.println("Not Found");
		}else if (  pattern.length() == 6 ){
			a.PrintNode();
		}else {
			
			ListNode cur = a.getHead();
			
			int num = 0;
			
			//System.out.println(substr);
			
			while(cur.getNext() != null ) {
			
				String st = Text.get(cur.getStudent_id() -1);
				
				/*System.out.println("st " +st.length());
				
				System.out.println( cur.getStart_pos() );
				System.out.println( pattern.length() );*/
				
				if( st.length() >= cur.getStart_pos() + pattern.length() -1 ) {
					if ( Text.get(cur.getStudent_id()-1 ).substring( cur.getStart_pos()-1, cur.getStart_pos() + pattern.length() -1).equals(pattern )  ) {
						if( num>0 ) {
							System.out.print(" ");
							
						}
						num++;
						
						System.out.print("("+cur.getStudent_id()+", "+cur.getStart_pos() +")" );
					}
					
				}
				
				cur = cur.getNext();
			}
			if( num>0) {
				System.out.println();
			}else {
				System.out.println("Not Found");
			}
			
			; //
		}
	}
}

class AVLTree{
	private int HashValue;
	private int NumberOfElement;
	private TreeNode SuperParent=null;
	private TreeNode NodeForPrint=null;

	public AVLTree(int HashValue){
		initAVLTree(HashValue);
	}
	public void initAVLTree(int HashValue){
		this.HashValue=HashValue;
		NumberOfElement=0;
		SuperParent=new TreeNode();
		//NodeForPrint=new TreeNode();
	}
	public int getNumberOfElement(){
		return NumberOfElement;
	}
	public int getHashvalue(){
		return HashValue;
	}
	
	int BalNum(TreeNode a) {
		return a == null ? 0 : a.getBalnceFactor();
	}
	
	void updateBalNum(TreeNode a) {
		a.setBalanceFactor( 1+Math.max( this.BalNum(a.getLeft() ) , this.BalNum(a.getRight()) )  );
	}

	/*  ##### NOTICE: you should use PrintByHashValue, but you can change other functions to implement AVL tree ###### */
	public int InsertNode(ListNode NewNode, String substr){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		TreeNode cur = this.SuperParent;
		
		this.insert( cur , NewNode, substr);
		
		this.NumberOfElement ++;
		return getNumberOfElement();
	}
	
	public TreeNode insert(TreeNode cur, ListNode input, String substr) {
		
		if(cur == null ) {
			
			cur = new TreeNode(input);
			cur.SetPattern(substr);
			this.updateBalNum(cur);
		}
		
		else if ( (cur.equals(this.SuperParent)) && ( cur.getPattern() == null ) ) {
			cur.initList(substr);
			cur.pushList(input);
		}
		
		else {
			int compare = cur.getPattern().compareTo(substr);
			if ( compare >0 ) 
			{
				cur.setLeft(this.insert(cur.getLeft() , input, substr) );
				cur.getLeft().setParent(cur);
				
			}else if (compare==0) {
				cur.pushList(input);
				this.NumberOfElement --;
				
				return cur;
				
			}else {
				cur.setRight(this.insert(cur.getRight() , input, substr) );
				cur.getRight().setParent(cur);
			}
		}
		
		
		return this.MakeTreeBalanced(cur);
	}
	
	public TreeNode RotateLeft(TreeNode x){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		TreeNode y = x.getRight();
		

		x.setRight(y.getLeft());
	
		if (y.getLeft() != null) {
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		
		if ( this.SuperParent.equals(x)) {
			this.SuperParent = y;      
		} else if (x.equals(x.getParent().getLeft()) ) {
			x.getParent().setLeft( y);
		} else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);
		x.setParent(y);
		
		this.updateBalNum(x);
		
		
		this.updateBalNum(y);
		
		return y;
	}
	public TreeNode RotateRight(TreeNode x){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		TreeNode y = x.getLeft() ;
		x.setLeft(y.getRight());
		if (y.getRight() != null) {
			y.getRight().setParent(x);
		}
		y.setParent(x.getParent());
		
		if ( this.SuperParent.equals(x)  ) { 
			
			this.SuperParent = y;
		} else if (x.equals(x.getParent().getRight()) ) {

			x.getParent().setRight( y);
			

		} else {
			
			x.getParent().setLeft(y);
		}
		y.setRight(x);
		x.setParent(y);
		

		this.updateBalNum(x);
		this.updateBalNum(y);

		return y;
	}
	public TreeNode MakeTreeBalanced(TreeNode NewNode){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		this.updateBalNum(NewNode);
		
		int bal = this.getBalance(NewNode)  ;
		
		if( bal>1 ) {
			int compare = this.BalNum( NewNode.getLeft().getLeft()) - this.BalNum(NewNode.getLeft().getRight());
			NewNode.getPattern().compareTo(NewNode.getLeft().getPattern());
			
			if (compare > 0 ){
				
				return this.RotateRight(NewNode);
			
			}else {
				
				NewNode.setLeft( this.RotateLeft(NewNode.getLeft() ) );
				return this.RotateRight(NewNode);
			}
			
		}
		if (bal <-1) {
			//System.out.println(NewNode.getPattern());
			int compare = this.BalNum( NewNode.getRight().getRight()) - this.BalNum(NewNode.getRight().getLeft());
			
			if( compare > 0) {
				return this.RotateLeft(NewNode);
			}
			else {
				NewNode.setRight( this.RotateRight(NewNode.getRight() ) );
				

				return this.RotateLeft(NewNode);
			}
		}
		
		return NewNode;
		
	}
	
	public int getBalance(TreeNode cur) {
		return (cur == null) ? 0 :  this.BalNum(cur.getLeft()) - this.BalNum(cur.getRight());
	}
	
	
	public TreeNode SearchNode(String Pattern){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		TreeNode cur = this.SuperParent;
		
		if ( this.getNumberOfElement() >0 ) {
			while( cur != null ) {
				int compare = cur.getPattern().compareTo(Pattern);
				
				if ( compare == 0   ) {
					
					return cur;
				}else if ( compare < 0) {
					cur = cur.getRight();
				}else {
					cur = cur.getLeft();
				}
				
			}
		}
		
		return null;
		
		
	}
	public void PreOrederTraverse(TreeNode Start, int num){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */

		if( Start == null) {
			//System.out.print("null ");
			return ;
		}

		
		System.out.print( Start.getPattern()  );
		
		if ( num >1) {
			System.out.print(" " );
		}
		
		PreOrederTraverse(Start.getLeft(), --num  );
		PreOrederTraverse(Start.getRight(), --num );
		
		
	}
	public void PrintByHashValue(int HashValue){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		if(getNumberOfElement() >0 ) {
			
			//System.out.println(this.getNumberOfElement() );
			
			this.NodeForPrint = this.SuperParent;
			
			PreOrederTraverse(  this.NodeForPrint, this.getNumberOfElement() ) ; //
			
			System.out.println();
		}else{
			System.out.println("Empty!");
		}
	}
}

class TreeNode{
	private int BalanceFactor=0;

	// TreeNode has subsequence & positions as its element
	// ex) subsequence = 'AATCGC'
	// ex) positions = [(1, 1), (1, 7), (2, 5)]
	private String subsequence;

	// This is an example of implementation of "positions" with linkedlist
	// you can implement {positions|(student_id, start_index)} with any data structures as you want
	private ListNode head=null;
	private ListNode tail=null;
	//////////////////////////////////

	private TreeNode parent=null;
	private TreeNode left=null;
	private TreeNode right=null;

	public TreeNode(){
		initList();
	}
	public TreeNode(String Pattern){
		initList(Pattern);
	}
	public TreeNode(ListNode NewNode){
		//initList(NewNode.getPattern());
		pushList(NewNode);
	}

	
	public void initList(){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		this.BalanceFactor = 0;
		this.subsequence = null; ///
	}
	public void initList(String Pattern){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		this.subsequence = Pattern;
	}
	public void pushList(ListNode NewNode){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		//System.out.println(( NewNode == null));
		
		if( head == null ) {
			head = NewNode;
			tail = head;
		}else
		{
			tail.setNext(NewNode);
			NewNode.setPrev(tail);
			
			//System.out.print("("+tail.getStudent_id()+", "+tail.getStart_pos() +") " );
			
			tail = NewNode;
			//System.out.print("("+tail.getStudent_id()+", "+tail.getStart_pos() +") " );
			
		}
	}
	
	public ListNode getHead() {
		return this.head;
	}

	public void setParent(TreeNode Parent){
		this.parent=Parent;
	}
	public void setLeft(TreeNode Left){
		this.left=Left;
	}
	public void setRight(TreeNode Right){
		this.right=Right;
	}
	public void SetPattern(String Pattern){
		this.subsequence =new String(Pattern);
	}
	public void setBalanceFactor(int newBalanceFactor){
		this.BalanceFactor=newBalanceFactor;
	}
	public String getPattern(){
		return this.subsequence;
	}
	public TreeNode getParent(){
		return this.parent;
	}
	public TreeNode getLeft(){
		return this.left;
	}
	public TreeNode getRight(){
		return this.right;
	}
	public int getBalnceFactor(){
		return BalanceFactor;
	}

	public void PrintNode(){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		ListNode cur = head;
		
		while( !( cur.equals( this.tail) )   ) {
			System.out.print("("+cur.getStudent_id()+", "+cur.getStart_pos() +") " );
			
			cur = cur.getNext();
		}
		
		if(this.tail != null ) {
			System.out.println("("+cur.getStudent_id()+ ", "+cur.getStart_pos()  +")" );
		}else {
			System.out.println("Not Found");
		}
	}
	

	public ListNode SearchByIndex(int index){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		return null;
	}

}

class ListNode{
	private int student_id;
	private int start_pos;

	private ListNode next=null;
	private ListNode prev=null;

	public ListNode(){
	}
	public ListNode(int student_id, int start_pos){
		this.student_id =student_id;
		this.start_pos =start_pos;
	}
	public void setNext(ListNode NextNode){this.next=NextNode;}
	public void setPrev(ListNode PrevNode){this.prev=PrevNode;}
	public int getStudent_id(){return student_id;}
	public int getStart_pos(){return start_pos;}
	public ListNode getNext(){return next;}
	public ListNode getPrev(){return prev;}
}