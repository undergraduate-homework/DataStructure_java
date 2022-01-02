import java.io.*;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) {
		
		DepartmentLinkedList attendance_list = new DepartmentLinkedList();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		
		while(true)
		{
			//System.out.println("명령어를 입력하세요");
		
			String department;
			String student_name;
			String student_id;
			int console = 0;
			
			StringTokenizer st = null ;
			String option;
			
			try
			{
				
				st = new StringTokenizer(br.readLine());
				option = st.nextToken();
				option = option.toUpperCase();

				
				if(option.equals("Q"))
				{
					
					break;
				}
				else if(option.equals("P"))
				{
					console = 1;
					
				}
				else if(option.equals("I"))
				{
					console = 2;

				}
				else if(option.equals("D"))
				{

					console = 3;
					
				}
			}
			catch (Exception e)
			{
				System.out.println("잘못된 입력입니다. I,D,P,Q 네가지 옵션 중 하나를 선택하고, 올바른 인자를 입력하세요. 오류 : " + e.toString());
			}
			
			if ( console ==1) {
				attendance_list.PrintAll();
				
				

			}else if (console==2) {
				try {
				department = st.nextToken();
				student_name = st.nextToken();
				student_id = st.nextToken();
				
				attendance_list.insertOrdered(department, student_name, student_id);
				}

				catch (Exception e) {
					System.out.println(" 'I' console error. Please write like 'I ee jiwon 12345' ");
				}
				
				
			}else if (console ==3) {
				try {
				department = st.nextToken();
				student_id = st.nextToken();
				attendance_list.delete(department, student_id);
				}
				
				catch (Exception e) {
					System.out.println(" 'D' console error. Please write like 'D ee 12345' ");
				}
			}

			
		}
	}
}


class DepartmentLinkedList{
	
	protected static class Node<T, E>{
		protected T t;
		protected E e;
		protected Node<T, E> prev;
		protected Node<T, E> next;
		
		public Node(T t, E e, Node<T,E> prev, Node<T,E> next) {
			this.t = t;
			this.e = e;
			this.prev = prev;
			this.next = next;
		}
		public T getT() {
			return this.t;
		}
		public E getE() {
			return this.e;
		}
		public Node<T,E> getPrev() {
			return this.prev;
		}
		public  Node<T,E> getNext() {
			return this.next;
		}

		public void setPrev( Node<T,E> prev) {
			this.prev = prev;
		}
		public void setNext( Node<T,E> next) {
			this.next = next;
		}
	}
	
	private  Node<String , StudentLinkedList> head = null;
	//private  Node<String , StudentLinkedList> trailer = null;
	protected int size = 0;
	
	
	public DepartmentLinkedList() {
		head = new  Node<>(null,null, null, null);
		//trailer = new  Node<>(null, null, head, null);
		//head.setNext(trailer);
	}
	
	public int size() {return size;}
	public boolean isEmpty() {return size==0;}
	
	public  Node<String , StudentLinkedList> first() {
		if(isEmpty() ) {
			return null;
		}
		return head;
	}
	
	public void removeFirst() {
		if (!isEmpty()) {
			head = head.getNext();
		}
	}
	
	public void addFirst(String depart, StudentLinkedList stu, Node<String, StudentLinkedList> first) {
		Node<String, StudentLinkedList> newHead = new Node<>(depart, stu, null, first);
		head = newHead;
		size++;
		
		if( first != null) {
			first.prev = head;
		}
		
	}
	public void addBetween(String depart, StudentLinkedList stu,  Node<String , StudentLinkedList> pre,  Node<String , StudentLinkedList> succ) {
		Node<String , StudentLinkedList> n = new Node<>(depart, stu,pre, succ);
		pre.setNext(n);
		succ.setPrev(n);
		size++;
	}
	
	public void addLast(String depart, StudentLinkedList stu,  Node<String , StudentLinkedList> last) {
		Node<String , StudentLinkedList> n = new Node<>(depart, stu, last, null);
		last.setNext(n);
		size++;
	}
	
	
	public void insertOrdered(String department, String student_name, String student_id){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		if (this.isEmpty())
		{
			
			StudentLinkedList stu = new StudentLinkedList(student_name, student_id);
			addFirst(department, stu, null);
		}else {
			
			Node<String , StudentLinkedList> cur = first();
			
			while( true)
			{
				
				int compare = cur.t.compareTo(department);
				
				if (  compare == 0  ) {
					cur.e.insertOrdered(student_name, student_id);
				
					break;
				}else if ( compare <0) // input department is later than current
				{
					
					
					if(cur.getNext() ==null) // if last department
					{
						StudentLinkedList stu = new StudentLinkedList(student_name, student_id);
						addLast(student_name, stu, cur);
						
						break;
					}
					else {
						cur = cur.getNext(); // move to next department
					}
					
				}
				else if (cur.t ==head.t)
				{
					StudentLinkedList stu = new StudentLinkedList(student_name, student_id);
					addFirst(department, stu, cur);
					break;
				}
				else // input department is earlier than current
				{
					StudentLinkedList stu = new StudentLinkedList(student_name, student_id);
					Node<String , StudentLinkedList> prev = cur.getPrev();
					
					
					addBetween(department, stu, prev, cur);
					
					break;
				}
				
			}
		}
	
		
	}
	
	public void delete(String department, String student_id){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */

		if (!isEmpty()) {
			Node<String, StudentLinkedList> cur = first();
			
			while(cur != null ) {
				int compare = cur.t.compareTo(department);
				
				if( compare==0) {
					boolean Empty = cur.e.delete(student_id);
					if(Empty ==true) {
						if( cur.getNext() ==null ) 
						{
							cur = null;
						}
						else if (cur.getPrev() == null) 
						{
							removeFirst();
						}
						else {
							
							(cur.getPrev()).setNext(cur.getNext());
							(cur.getNext()).setPrev(cur.getPrev());
							
							cur.setPrev(null);
							cur.setNext(null);
						
						}
						
					}
					size--;
					break;
					
				}else if (compare <0) {
					cur= cur.getNext();
				}else {
					break;
				}
				
			}
		}
		
		
	}
	
	public void PrintAll(){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		if (isEmpty()) {
			System.out.println("Empty!");
		}
		else {
			Node<String, StudentLinkedList> cur = first();
			
			while(cur != null ) {
				cur.e.PrintAll(cur.t );
				cur = cur.getNext();
			}
			
			System.out.println("End!");
		}
	
	}

}




class StudentLinkedList extends DepartmentLinkedList{
	

	private  Node<String , String> head = null;
	//private  Node<String , String> trailer = null;
	
	
	public StudentLinkedList () {
		head = new  Node<>(null, null, null, null);
		//trailer = new  Node<>(null, null, head, null);
		//head.setNext(trailer);
	}
	public StudentLinkedList (String name, String id) {
		head = new  Node<>(name, id, null, null);
		
		size = 1;
		//trailer = new  Node<>(null, null, head, null);
		//head.setNext(trailer);		
	}
	

	public  Node<String , String> head() {
		if(isEmpty() ) {
			return null;
		}
		return head;
	}
	
	public void addFirst(String depart, String stu, Node<String, String> first) {
		Node<String, String> newHead = new Node<>(depart, stu, null, first);
		
		head = newHead;
		size++;
		
		if( first != null) {
			first.prev = head;
		}
		
	}
	public void addBetween(String name, String id,  Node<String , String> pre,  Node<String , String> succ) {
		Node<String , String> n = new Node<>(name, id,pre, succ);
		pre.setNext(n);
		succ.setPrev(n);
		this.size++;
	}
	public void addLast(String name, String id,  Node<String , String> last) {
		Node<String , String> n = new Node<>(name, id, last, null);
		last.setNext(n);
		size++;
	}
	
	public void downSize() {
		size--;
	}
	
	
	public void insertOrdered(String student_name, String student_id) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		if( isEmpty()) {
			addFirst(student_name, student_id, null);
		}
		else {
			
			Node<String , String> cur = head();
			
			while( true)
			{
				int curInt = Integer.parseInt( cur.e);
				int inputInt = Integer.parseInt(student_id);
				
				int compare = curInt - inputInt;
				
				if ( compare <0) // input id is later than current
				{

					if(cur.getNext() ==null) // if last id
					{
						addLast(student_name, student_id, cur);
						
						break;
					}
					else {
						cur = cur.getNext(); // move to next id
					}
					
				}

				else if (cur ==head) {
					addFirst( student_name, student_id, cur);
					break;
				}
				else // input id is earlier than current
				{
					Node<String , String> prev = cur.getPrev();
					
					addBetween( student_name, student_id, prev, cur);
					break;
				}
				
			}
		}

	}

	public boolean delete(String student_id){
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		if (!isEmpty()) {
			Node<String, String> cur = head();
			
			while(cur != null ) {
				int curInt = Integer.parseInt( cur.e);
				int inputInt = Integer.parseInt(student_id);
				
				int compare = curInt - inputInt;
			
				
				if( compare==0) {
					if( cur.getNext() ==null ) 
					{
						cur = null;
					}
					else if (cur.getPrev() == null) 
					{
						removeFirst();
					}
					else {
						
						(cur.getPrev()).setNext(cur.getNext());
						(cur.getNext()).setPrev(cur.getPrev());
						
						cur.setPrev(null);
						cur.setNext(null);

					
					}
					
					downSize();
					break;
					
				}else if (compare <0) {
					cur= cur.getNext();
				}else {
					break;
				}
				
			}
		}
		
		return false;
	}
	
	public void PrintAll(String department) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */
		
		Node<String, String> cur = head();
		
		while(cur != null ) {
			System.out.println("("+department+","+ cur.t+","+cur.e+")");
			cur = cur.getNext();
		}
	}
}
