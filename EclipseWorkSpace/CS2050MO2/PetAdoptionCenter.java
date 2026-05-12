
/**
 * Update code and add comments explaining concepts 
 */

import java.util.ArrayList;

public class PetAdoptionCenter
{
	public static void main(String[] args)
	{
		// ============================================
		// Part 1: Setup an ArrayList of interface type
		// ============================================
		ArrayList<Pet> pets = new ArrayList<>();

		// Add some example pets
		pets.add(new Bulldog("Bear"));
		pets.add(new Cat("Mittens"));
		

		// Task 1:
		// Add at least one more Pet type class (example: Parrot)
		// Then add at least one object of that type to the list.
		// pets.add(new Parrot("Rio"));
		pets.add(new Frog("Steven"));
		// ============================================
		// Part 2: Explain Polymorphic behavior with the interface
		// ============================================
		System.out.println("--- Meet Our Pets ---");
		for (int i = 0; i < pets.size(); i++)
		{
			Pet currentPet = pets.get(i);
			currentPet.beFriendly();
			currentPet.play();
		}

		// ============================================
		// Part 3: Shared behavior through the interface
		// ============================================
		System.out.println("\n--- Snack Time ---");
		feedAll(pets);

		// ============================================
		// Part 4: ArrayList operations
		// ============================================
		System.out.println("\n--- Adoption Updates ---");
		System.out.println("Total pets before adoption: " + pets.size());

		if (pets.size() > 0)
		{
			Pet adopted = pets.remove(0);
			System.out.println("Adopted out: " + adopted);
		}

		System.out.println("Total pets after adoption: " + pets.size());

		// ============================================
		// Part 5: Search
		// ============================================
		// Call findByName and print the result
		
		Pet found = findByName(pets, "Mittens");
		if (found != null)
		{
		    System.out.println("Found: " + found);
		}
		else
		{
		    System.out.println("Pet not found.");
		}
		// ============================================
		// Part 6: Remove by name
		// ============================================
		// Call removeByName and print the result
		boolean removed = removeByName(pets, "Steven");
		if (removed)
		{
		    System.out.println("Steven was successfully removed.");
		}
		else
		{
		    System.out.println("Pet not found.");
		}
		System.out.println("Pets remaining: " + pets.size());
		// ============================================
		// Part 7: Favorites list
		// ============================================
		// Build a list of pets whose names start with 'M'
		// Print the favorites list
		ArrayList<Pet> favorites = buildFavoritesStartingWith(pets, 'M');
		System.out.println("\n--- Favorites List ---");
		if (favorites.size() == 0)
		{
		    System.out.println("No favorites found.");
		}
		else
		{
		    for (int i = 0; i < favorites.size(); i++)
		    {
		        System.out.println(favorites.get(i));
		    }
		}
	}
	
	
	private static void feedAll(ArrayList<Pet> pets)
	{
		for (int i = 0; i < pets.size(); i++)
		{
			pets.get(i).eat();
		}
	}

	public static Pet findByName(ArrayList<Pet> pets, String nameToFind)
	{
		 for (Pet p : pets)
		    {
		        boolean matchName = p.getName().equalsIgnoreCase(nameToFind);

		        if (matchName)
		        {
		            return p; // Return the first match immediately
		        }
		    }
		// TODO:
		// Return the first pet whose name matches nameToFind
		// Comparison should be case-insensitive
		// Return null if no match is found
		
		return null;
	}

	public static boolean removeByName(ArrayList<Pet> pets, String nameToRemove)
	{
		 for (int i = 0; i < pets.size(); i++)
		    {
		        if (pets.get(i).getName().equalsIgnoreCase(nameToRemove))
		        {
		            pets.remove(i); // Remove the pet at that index
		            return true;    // Found and removed
		        }
		    }
		// TODO:
		// Remove the first matching pet
		// Use an index-based loop
		// Return true if removed, otherwise false
		return false;
	}

	public static ArrayList<Pet> buildFavoritesStartingWith(ArrayList<Pet> pets, char letter)
	{
		ArrayList<Pet> favorites = new ArrayList<>();

	    for (int i = 0; i < pets.size(); i++)
	    {
	        char firstLetter = Character.toLowerCase(pets.get(i).getName().charAt(0));

	        if (firstLetter == Character.toLowerCase(letter))
	        {
	            favorites.add(pets.get(i));
	        }
	    }
		// TODO:
		// Create a new ArrayList<Pet>
		// Add pets whose names start with the given letter
		// Comparison should be case-insensitive
		return favorites;
	}
}

// ============================================
// Abstract superclass
// ============================================
abstract class Animal
{
	private String name;

	public Animal(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}

// ============================================
// Interface
// ============================================
interface Pet
{
	void beFriendly();

	void play();

	void eat();

	String getName();
}

// ============================================
// Concrete classes
// ============================================
class Bulldog extends Animal implements Pet
{
	public Bulldog(String name)
	{
		super(name);
	}

	@Override
	public void beFriendly()
	{
		System.out.println(getName() + " wags tail and leans on your leg.");
	}

	@Override
	public void play()
	{
		System.out.println(getName() + " plays tug-of-war.");
	}

	@Override
	public void eat()
	{
		System.out.println(getName() + " munches crunchy kibble.");
	}

	@Override
	public String toString()
	{
		return "Bulldog(" + getName() + ")";
	}
}

class Cat extends Animal implements Pet
{
	public Cat(String name)
	{
		super(name);
	}

	@Override
	public void beFriendly()
	{
		System.out.println(getName() + " purrs and headbutts your hand.");
	}

	@Override
	public void play()
	{
		System.out.println(getName() + " chases a laser pointer.");
	}

	@Override
	public void eat()
	{
		System.out.println(getName() + " nibbles salmon pate.");
	}

	@Override
	public String toString()
	{
		return "Cat(" + getName() + ")";
	}
}

// Task:
// Add one more class such as Parrot that extends Animal and implements Pet.
class Frog extends Animal implements Pet
{
	public Frog(String name)
	{
		super(name);
	}

	@Override
	public void beFriendly()
	{
		System.out.println(getName() + " hops on your hand.");
	}

	@Override
	public void play()
	{
		System.out.println(getName() + " plays leapfrog.");
	}

	@Override
	public void eat()
	{
		System.out.println(getName() + " munches crunchy bugs.");
	}

	@Override
	public String toString()
	{
		return "Frog(" + getName() + ")";
	}
	
}