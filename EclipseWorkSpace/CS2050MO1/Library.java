import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
public class Library {

    private String name;
    private Book[][] bookShelf;
    private int numberOfShelves;
    private int shelfCapacity;
    private int currentShelf;
    private int currentSlot;
    private boolean isFull;

    
    // Constructor (Safe Initialization)
    public Library(String name, int shelves, int shelfCapacity) {

        this.name = (name == null || name.isEmpty()) ? "Unnamed Library" : name;

        if (shelves <= 0) {
            shelves = 1;
        }

        if (shelfCapacity <= 0) {
            shelfCapacity = 1;
        }

        this.numberOfShelves = shelves;
        this.shelfCapacity = shelfCapacity;

        bookShelf = new Book[shelves][shelfCapacity];

        currentShelf = 0;
        currentSlot = 0;
        isFull = false;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Story 3 – Add Book Automatically
    public boolean addBook(Book book) {

        if (book == null || isFull) {
            return false;
        }

        bookShelf[currentShelf][currentSlot] = book;

        System.out.println("Book placed on Shelf " + currentShelf +
                           ", Slot " + currentSlot);

        currentSlot++;

        if (currentSlot == shelfCapacity) {
            currentSlot = 0;
            currentShelf++;
        }

        if (currentShelf == numberOfShelves) {
            isFull = true;
        }

        return true;
    }

    // Story 4 – Display All Books in Table Format
    public void printAllBooks() {

        if (countBooks() == 0) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.println("\nLibrary: " + name);
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-6s %-6s %-20s %-20s %-6s\n",
                "Shelf", "Slot", "Title", "Author", "Year");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {

                if (bookShelf[i][j] != null) {

                    Book b = bookShelf[i][j];

                    System.out.printf("%-6d %-6d %-20s %-20s %-6d\n",
                            i,
                            j,
                            b.getBookTitle(),
                            b.getBookAuthor(),
                            b.getBookAge());
                }
            }
        }
    }

    // Story 5 – Report Books Per Shelf
    public void printShelfReport() {

        System.out.println("\nShelf Usage Report:");

        for (int i = 0; i < numberOfShelves; i++) {
            System.out.println("Shelf " + i + ": " + countPerShelf(i) + " books");
        }
    }

    public int countPerShelf(int shelfNumber) {

        if (shelfNumber < 0 || shelfNumber >= numberOfShelves) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < shelfCapacity; i++) {
            if (bookShelf[shelfNumber][i] != null) {
                count++;
            }
        }

        return count;
    }

    // Story 6 – Find Oldest Book
    public void printOldest() {

        if (countBooks() == 0) {
            System.out.println("Library is empty.");
            return;
        }

        int oldestYear = Integer.MAX_VALUE;

        // Find oldest year
        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {

                if (bookShelf[i][j] != null) {

                    int year = bookShelf[i][j].getBookAge();

                    if (year < oldestYear) {
                        oldestYear = year;
                    }
                }
            }
        }

        System.out.println("\nOldest Year: " + oldestYear);
        System.out.println("Book(s) from that year:");

        // Print ALL books from that year
        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {

                if (bookShelf[i][j] != null &&
                        bookShelf[i][j].getBookAge() == oldestYear) {

                    System.out.println("Shelf " + i + ", Slot " + j +
                            " → " + bookShelf[i][j]);
                }
            }
        }
    }

    // Private Helper
    private int countBooks() {

        int total = 0;

        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (bookShelf[i][j] != null) {
                    total++;
                }
            }
        }

        return total;
    }
        
        
    }

