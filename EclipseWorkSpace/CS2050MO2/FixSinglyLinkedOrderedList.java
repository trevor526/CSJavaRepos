
/**
 * Lab: Fix Singly Linked Ordered List ----------------------------------- The
 * insertNode() and deleteNode() methods contain logic bugs. 1. Predict what
 * each method should do (draw before/after pictures). 2. Use the debugger or
 * print statements to trace previous/current. 3. Fix the code so the list stays
 * in sorted order after insertions and nodes are correctly deleted when found.
 *
 * Add comments above your fixes explaining what was wrong and why.
 */

public class FixSinglyLinkedOrderedList
{

	// Test the Singly Linked List
	public static void main(String[] args)
	{

		SinglyLinkedListFix list = new SinglyLinkedListFix();
		list.printList();

		// Use your unit testing to ensure it handles all cases
		list.insertNode(5);

		list.printList();

		list.printList();
		list.deleteNode(1);

		list.printList();

	}

}

class SinglyLinkedListFix
{
	NodeFix head;

	public void insertNode(int number)
	{
		NodeFix newNode = new NodeFix(number);
		NodeFix current = head;
		NodeFix previous = null;

	// Traverse while current exists AND current's value is less than number
	// This finds the correct sorted position to insert
		while (current != null && current.data < number)
		{
			previous = current;
			current = current.next;
		}

	// Bug 1: switch current with previous to have 
	// the new node placed at the head not at the end
		if (previous == null)
		{
			newNode.next = head;
			head = newNode;
		} else
		{
	// Bug 2: link newNode to current before previous. The list
	// will be lost without it
			newNode.next = current;
			previous.next = newNode;
	//Bug 3: Never sets newNode.next = current
		}
	}
	
	public void deleteNode(int number)
	{
	//Bug 4: Check current != null first not current.next != null
	
		NodeFix current = head;
		NodeFix previous = null;

		while (current != null && current.data != number)
		{
			previous = current;
			current = current.next;
		}

		if (previous == null)
		{
			head = current.next;
		} else
		{
	//Bug 5: 
			previous.next = current.next; // Bug #5: Should be previous.next = current.next
		}
	}

	public void printList()
	{
		NodeFix current = head;
		while (current != null)
		{
			System.out.print(current.data + " → ");
			current = current.next;
		}
		System.out.println("null");
	}

	private static class NodeFix
	{
		int data;
		NodeFix next;

		public NodeFix(int data)
		{
			this.data = data;
			this.next = null;
		}
	}
}

