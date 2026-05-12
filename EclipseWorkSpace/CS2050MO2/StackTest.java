/*
 * Stack Example
 * Add comments to explain the code
 */
import java.util.Random;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StackTest
{
	public static void main(String[] args)
	{

		Stack<Integer> stack = new Stack<>();

		System.out.println("Pushing elements: 10, 20, 30");
		stack.push(10);
		stack.push(20);
		stack.push(30);
		

		System.out.println("Stack after pushing: " + stack);

		int popped = stack.pop();
		System.out.println("Popped element: " + popped);
		System.out.println("Stack after popping: " + stack);

		int topElement = stack.peek();
		System.out.println("Top element: " + topElement);

		System.out.println("Is stack empty? " + stack.isEmpty());

		popped = stack.pop();
		System.out.println("Popped element: " + popped);
		System.out.println("Stack after popping: " + stack);

		popped = stack.pop();
		System.out.println("Popped element: " + popped);
		System.out.println("Stack after popping: " + stack);

		popped = stack.pop();
		System.out.println("Popped element: " + popped);
		System.out.println("Stack after popping: " + stack);
	}
}
Stack<Integer> stackstring = new Stack<>();

System.out.println("Pushing elements: 10, 20, 30");

Random rand = new Random();
stringstack.push(10);
stringstack.push(20);
stringstack.push(30);
System.out.println("Stack after pushing: " + stack);

int popped = stackstring.pop();
System.out.println("Popped element: " + popped);
System.out.println("Stack after popping: " + stringstack);

int topElement = stackstring.peek();
System.out.println("Top element: " + topElement);

System.out.println("Is stack empty? " + stringstack.isEmpty());

popped = stackstring.pop();
System.out.println("Popped element: " + popped);
System.out.println("Stack after popping: " + stringstack);

popped = stackstring.pop();
System.out.println("Popped element: " + popped);
System.out.println("Stack after popping: " + stringstack);

popped = stackstring.pop();
System.out.println("Popped element: " + popped);
System.out.println("Stack after popping: " + stringstack);
class Stack<E>
{

	private ArrayList<E> items;

	public Stack()
	{
		items = new ArrayList<>();
	}

	public boolean isEmpty()
	{
		return items.isEmpty();
	}

	public void push(E item)
	{
		items.add(item);
	}

	public E pop()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException("Stack is empty.");
		}

		int topIndex = items.size() - 1;
		return items.remove(topIndex);
	}

	public E peek()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException("Stack is empty.");
		}

		int topIndex = items.size() - 1;
		return items.get(topIndex);
	}

	public int size()
	{
		return items.size();
	}

	@Override
	public String toString()
	{
		if (items.isEmpty())
		{
			return "<<empty stack>>";
		}
		return "bottom -> " + items + " <- top";
	}
}
