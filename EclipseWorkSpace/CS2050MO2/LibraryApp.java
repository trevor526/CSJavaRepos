import java.util.Scanner;

//library menu

public class LibraryApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Library lib = new Library("City Library", 2, 3);

        while (true) {

            System.out.println("\n1. Add Print Book");
            System.out.println("2. Add E-Book");
            System.out.println("3. Print All Books");
            System.out.println("4. Print Oldest");
            System.out.println("5. Exit");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1 || choice == 2) {

                System.out.println("Title:");
                String title = input.nextLine();

                System.out.println("Author:");
                String author = input.nextLine();

                int year = -1;
                while (year < 0) {
                    System.out.println("Year:");
                    if (input.hasNextInt()) {
                        year = input.nextInt();
                        if (year < 0) System.out.println("Cannot be negative.");
                    } else {
                        System.out.println("Invalid input.");
                        input.next();
                    }
                }
                input.nextLine();

                //Book is either print or ebook
                
                Book newBook = (choice == 1)
                        ? new PrintBook(title, author, year)
                        : new EBook(title, author, year);

                if (!lib.addBook(newBook)) {
                    System.out.println("Library is FULL.");
                }

            } else if (choice == 3) {
                lib.printAllBooks();

            } else if (choice == 4) {
                lib.printOldest();

            } else {
                break;
            }
        }

        input.close();
    }
}


// Abstract book class

abstract class Book {

    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title  = (title  == null || title.isEmpty())  ? "Unknown Title"  : title;
        this.author = (author == null || author.isEmpty()) ? "Unknown Author" : author;
        this.year   = (year < 0) ? 0 : year;
    }

    // Getters
    public String getTitle()  { return title;  }
    public String getAuthor() { return author; }
    public int    getYear()   { return year;   }

    public abstract int    getLoanDays();
    public abstract double getDailyLateFee();

    public final double calculateLateFee(int daysLate) {
        double lateFee = 0;
        if (daysLate > 0) {
            lateFee = daysLate * getDailyLateFee();
        }
        return lateFee;
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + author + " (" + year + ")";
    }
}


// PrintBook 

class PrintBook extends Book {

    public PrintBook(String title, String author, int year) {
        super(title, author, year);   
    }

    @Override
    public int getLoanDays() {
        return 21;                    // print books: 3-week loan
    }

    @Override
    public double getDailyLateFee() {
        return 0.25;                  // $0.25 per day late
    }

    @Override
    public String toString() {
        // "Title" by Author (year) [Print Book, 21 days, $0.25/day]
        return super.toString() +
               " [Print Book, " + getLoanDays() + " days, $" + getDailyLateFee() + "/day]";
    }
}

// EBook — concrete subclass

class EBook extends Book {

    public EBook(String title, String author, int year) {
        super(title, author, year);   
    }

    @Override
    public int getLoanDays() {
        return 14;                    // e-books: 2-week loan
    }

    @Override
    public double getDailyLateFee() {
        return 0.10;                  // $0.10 per day late
    }

    @Override
    public String toString() {
        // "Title" by Author (year) [E-Book, 14 days, $0.10/day]
        return super.toString() +
               " [E-Book, " + getLoanDays() + " days, $" + getDailyLateFee() + "/day]";
    }
}

//library

class Library { 

    private String name;
    private Book[][] bookShelf;      
    private int numberOfShelves;
    private int shelfCapacity;
    private int currentShelf;
    private int currentSlot;
    private boolean isFull;

    public Library(String name, int shelves, int shelfCapacity) {

        this.name = (name == null || name.isEmpty()) ? "Unnamed Library" : name;

        if (shelves      <= 0) shelves      = 1;
        if (shelfCapacity <= 0) shelfCapacity = 1;

        this.numberOfShelves = shelves;
        this.shelfCapacity   = shelfCapacity;

        bookShelf    = new Book[shelves][shelfCapacity];
        currentShelf = 0;
        currentSlot  = 0;
        isFull       = false;
    }

    public String getName() { return name; }

    public boolean addBook(Book book) {

        if (book == null || isFull) return false;

        bookShelf[currentShelf][currentSlot] = book;

        System.out.println("Book placed on Shelf " + currentShelf +
                           ", Slot " + currentSlot);
        currentSlot++;

        if (currentSlot == shelfCapacity) {
            currentSlot = 0;
            currentShelf++;
        }

        if (currentShelf == numberOfShelves) isFull = true;

        return true;
    }

    public void printAllBooks() {

        if (countBooks() == 0) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.println("\nLibrary: " + name);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-6s %-6s %s\n", "Shelf", "Slot", "Book Details");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (bookShelf[i][j] != null) {
                    // toString() calls the correct subclass version — polymorphism at work
                    System.out.printf("%-6d %-6d %s\n", i, j, bookShelf[i][j].toString());
                }
            }
        }
    }

    public void printOldest() {

        if (countBooks() == 0) {
            System.out.println("Library is empty.");
            return;
        }

        int oldestYear = Integer.MAX_VALUE;

        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (bookShelf[i][j] != null) {
                    if (bookShelf[i][j].getYear() < oldestYear) {
                        oldestYear = bookShelf[i][j].getYear();
                    }
                }
            }
        }

        System.out.println("\nOldest Year: " + oldestYear);
        System.out.println("Book(s) from that year:");

        for (int i = 0; i < numberOfShelves; i++) {
            for (int j = 0; j < shelfCapacity; j++) {
                if (bookShelf[i][j] != null &&
                        bookShelf[i][j].getYear() == oldestYear) {
                    System.out.println("Shelf " + i + ", Slot " + j +
                            " → " + bookShelf[i][j]);
                }
            }
        }
    }

    private int countBooks() {
        int total = 0;
        for (int i = 0; i < numberOfShelves; i++)
            for (int j = 0; j < shelfCapacity; j++)
                if (bookShelf[i][j] != null) total++;
        return total;
    }
}