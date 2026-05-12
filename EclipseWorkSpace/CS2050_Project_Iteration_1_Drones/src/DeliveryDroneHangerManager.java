import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Manages a drone hangar inventory system.
 * 
 * This program allows users to load drone data from a CSV file, display inventory,
 * search drones, sort inventory, and count drones by manufacturer. It also manages
 * a maintenance queue for drone servicing.
 * 
 * @author Trevor Strong
 * @version 2.0 (4/28/26)
 */
public class DeliveryDroneHangerManager {

	/** Counter for generating unique drone IDs. */
	public static int droneIDCounter = 1000;
	
	/** HashMap storing drones indexed by their drone ID. */
	public static HashMap<String, Drone> droneMap = new HashMap<>();
	
	/** Queue for managing drones in maintenance. */
	public static Queue<Drone> maintenanceQueue = new LinkedList<>();
		
	/**
	 * Main method serving as the entry point for the application.
	 * Displays a menu and processes user selections.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Drone> drones = new ArrayList<>();
       

        while (true) {
        	System.out.println("\n1. Load Drones from CSV");
            System.out.println("2. Display Hangar Inventory");
            System.out.println("3. Search Drones (Manufacturer & Type)");
            System.out.println("4. View Inventory Sorted by Payload");
            System.out.println("5. View Inventory Sorted by Year");
            System.out.println("6. Count Drones by Manufacturer");
            System.out.println("7. Search Drone by ID");
            System.out.println("8. Add Drone to Maintenance Queue");
            System.out.println("9. View Next Drone in Queue");
            System.out.println("10. Process Next Drone in Queue");
            System.out.println("11. Display Queue");
            System.out.println("12. Exit");

         // Validates that the user enters an integer menu choice.
         // Prevents InputMismatchException from invalid input.
         if (!input.hasNextInt()) {
             System.out.println("Invalid input. Enter an integer.");
             input.next();
             continue;
         }

         // Reads menu choice
         int choice = input.nextInt();
         input.nextLine();

         // Choice 1: Load drones from CSV
         if (choice == 1) {
             System.out.print("Enter CSV file name: ");
             String fileName = input.nextLine();

             loadFromCSV(fileName, drones);
         }

         // Choice 2: Display hangar inventory
         else if (choice == 2) {
             displayInventory(drones);
         }

         // Choice 3: Search drones by manufacturer and type
         else if (choice == 3) {
             System.out.print("Enter manufacturer: ");
             String manufacturer = input.nextLine();

             System.out.print("Enter drone type (Standard or Priority): ");
             String type = input.nextLine();

             ArrayList<Drone> results = searchDrones(drones, manufacturer, type);

             if (results.isEmpty()) {
                 System.out.println("No drone found.");
             } else {
                 displayDrones(results);
             }
         }

         // Choice 4: View inventory sorted by payload
         else if (choice == 4) {
             ArrayList<Drone> sorted = sortByPayload(drones);

             if (sorted.isEmpty()) {
                 System.out.println("No drones in the hangar.");
             } else {
                 System.out.println("=== Drones sorted by Payload (Least to Greatest) ===");
                 displayDrones(sorted);
             }
         }

         // Choice 5: View inventory sorted by year
         else if (choice == 5) {
             ArrayList<Drone> sorted = sortByYear(drones);

             if (sorted.isEmpty()) {
                 System.out.println("No drones in the hangar.");
             } else {
                 System.out.println("=== Drones sorted by Year (Oldest to Newest) ===");
                 displayDrones(sorted);
             }
         }

         // Choice 6: Count drones by manufacturer
         else if (choice == 6) {
        	    if (drones.isEmpty()) {
        	        System.out.println("No drones in the hangar.");
        	        continue;
        	    }

        	    System.out.print("Enter manufacturer: ");
        	    String manufacturer = input.nextLine();

        	    int count = countByManufacturer(drones, manufacturer);

        	    System.out.println("Total drones for manufacturer " + manufacturer + ": " + count);
        	}

         // Choice 7: Search drone by ID
         else if (choice == 7) {
             System.out.print("Enter drone ID: ");
             String droneID = input.nextLine();

             Drone foundDrone = searchDroneByID(droneID);

             if (foundDrone == null) {
                 System.out.println("Drone not found.");
             } else {
                 foundDrone.displayInfo();
             }
         }

         // Choice 8: Add drone to maintenance queue
         else if (choice == 8) {
             System.out.print("Enter drone ID to add to queue: ");
             String droneID = input.nextLine();

             sendToMaintenance(droneID);
         }

         // Choice 9: View next drone in queue
         else if (choice == 9) {
             viewNextDrone();
         }

         // Choice 10: Process next drone in queue
         else if (choice == 10) {
             processMaintenance();
         }

         // Choice 11: Display maintenance queue
         else if (choice == 11) {
             displayMaintenanceQueue();
         }

         // Choice 12: Exit program
         else if (choice == 12) {
             input.close();
             System.out.println("Exiting program...");
             break;
         }

         // Handles invalid menu selections outside valid range
         else {
             System.out.println("Invalid menu choice. Please select 1-12.");
         }
        }
	}

	/**
	 * Searches for drones by manufacturer and type.
	 * 
	 * @param drones The list of drones to search.
	 * @param manufacturer The manufacturer name to match.
	 * @param type The drone type to match ("Standard", "Priority", "S", or "P").
	 * @return An ArrayList of drones matching both criteria.
	 */
	public static ArrayList<Drone> searchDrones(ArrayList<Drone> drones, String manufacturer, String type) {
	    ArrayList<Drone> results = new ArrayList<>();

	    for (Drone d : drones) {
	        boolean matchManufacturer = d.getManu().equalsIgnoreCase(manufacturer);
	       
	        boolean matchType =
	        		(type.equalsIgnoreCase("Standard") && d.getType() == 'S') ||
	        		(type.equalsIgnoreCase("Priority") && d.getType() == 'P') ||
	        		(type.equalsIgnoreCase("S") && d.getType() == 'S') ||
	        		(type.equalsIgnoreCase("P") && d.getType() == 'P');
	        if (matchManufacturer && matchType) {
	            results.add(d);
	        }
	    }

	    return results;
	}
    
	/**
	 * Loads drone data from a CSV file.
	 * 
	 * Clears existing inventory and loads new drones from the specified CSV file.
	 * Expected CSV format: type, manufacturer, year, payload
	 * 
	 * @param fileName The name of the CSV file to load.
	 * @param drones The ArrayList to populate with loaded drones.
	 */
    public static void loadFromCSV(String fileName, ArrayList<Drone> drones) {
    	drones.clear();
    	droneMap.clear();
    	maintenanceQueue.clear();
    	droneIDCounter = 1000;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            int lineNumber = 1;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {

                try {
                	
                	if (lineNumber == 1 && line.toLowerCase().contains("type")) {
                	    lineNumber++;
                	    continue;
                	}
                 
                    if (isFirstLine) {
                        if (line.startsWith("\uFEFF")) {
                            line = line.substring(1);
                        }
                        isFirstLine = false;
                    }

                    String[] parts = line.split(",");

                    if (parts.length != 4) {
                        throw new Exception("Wrong number of values");
                    }

                    char type = parts[0].trim().charAt(0);
                    String manu = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    double payload = Double.parseDouble(parts[3].trim());

                    type = Character.toUpperCase(type);

                    String droneID = "D" + droneIDCounter++;

                    Drone d;

                    if (type == 'S') {
                        d = new StandardDrone(droneID, manu, year, payload);
                    } else if (type == 'P') {
                        d = new PriorityDrone(droneID, manu, year, payload);
                    } else {
                        throw new Exception("Invalid drone type");
                    }

                    drones.add(d);
                    droneMap.put(droneID, d);
                   
                } catch (Exception e) {
                    System.out.println("Error in line " + lineNumber + ": " + line);
                }

                lineNumber++;
            }

        } catch (IOException e) {
            System.out.println("Could not open file: " + fileName);
        }
    }
    
	/**
	 * Counts the number of drones from a specific manufacturer.
	 * 
	 * @param drones The list of drones to search.
	 * @param manufacturer The manufacturer name to count.
	 * @return The number of drones from the specified manufacturer.
	 */
    public static int countByManufacturer(ArrayList<Drone> drones, String manufacturer) {

        int count = 0;

        for (Drone d : drones) {
            if (d.getManu().equalsIgnoreCase(manufacturer)) {
                count++;
            }
        }

        return count;
    }

	/**
	 * Adds a drone to the maintenance queue.
	 * 
	 * @param droneID The ID of the drone to send to maintenance.
	 */
    public static void sendToMaintenance(String droneID) {
        droneID = droneID.toUpperCase();

        Drone drone = droneMap.get(droneID);

        if (drone == null) {
            System.out.println("Drone not found.");
            return;
        }

        if (maintenanceQueue.contains(drone)) {
            System.out.println("Drone is already in the maintenance queue.");
            return;
        }

        maintenanceQueue.add(drone);
        System.out.println(droneID + " added to maintenance queue.");
        
    }
    
	/**
	 * Processes the next drone in the maintenance queue.
	 * Removes and displays the drone at the front of the queue.
	 */
    public static void processMaintenance() {
        if (maintenanceQueue.isEmpty()) {
            System.out.println("No drones to process.");
        } else {
            System.out.print("Processing drone: ");
            maintenanceQueue.poll().displayInfo();
        }
    }
    
	/**
	 * Searches for a drone by its ID.
	 * 
	 * @param droneID The ID of the drone to find.
	 * @return The Drone object if found, null otherwise.
	 */
    public static Drone searchDroneByID(String droneID) {
    if (droneID == null) {
        return null;
    }

    return droneMap.get(droneID.toUpperCase());
    }
    
	/**
	 * Displays the current hangar inventory.
	 * 
	 * @param drones The list of drones to display.
	 */
    public static void displayInventory(ArrayList<Drone> drones) {
    	if (drones.isEmpty()) {
    		System.out.println("No drones in the hangar.");
    	} else {
    		System.out.println("\n=== Hangar Inventory ===");
    		displayDrones(drones);
    	}
    	}

	/**
	 * Displays information for a list of drones.
	 * 
	 * @param drones The list of drones to display.
	 */
    public static void displayDrones(ArrayList<Drone> drones) {
        for (Drone d : drones) {
            d.displayInfo();
        }
    }
    
	/**
	 * Displays all drones currently in the maintenance queue.
	 */
    public static void displayMaintenanceQueue() {
        if (maintenanceQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.println("Maintenance Queue:");

        int position = 1;

        for (Drone drone : maintenanceQueue) {
            System.out.print(position + ". ");
            drone.displayInfo();
            position++;
        }
    }
    
	/**
	 * Displays the next drone in the maintenance queue without removing it.
	 */
    public static void viewNextDrone() {
        if (maintenanceQueue.isEmpty()) {
            System.out.println("No drones in queue.");
        } else {
            System.out.print("Next drone in queue: ");
            maintenanceQueue.peek().displayInfo();
        }
    }

	/**
	 * Sorts drones by their cargo capacity in ascending order.
	 * 
	 * @param drones The list of drones to sort.
	 * @return A new sorted ArrayList of drones.
	 */
    public static ArrayList<Drone> sortByPayload (ArrayList<Drone> drones){
    	ArrayList<Drone> sorted = new ArrayList<>(drones);
    	Collections.sort(sorted, new Comparator<Drone>() {
    		@Override
    		public int compare(Drone d1, Drone d2) {
    			return Double.compare(d1.getCargoCapacity(), d2.getCargoCapacity());
    		}
    	});
    	return sorted;

    }

	/**
	 * Sorts drones by their year in ascending order (oldest to newest).
	 * 
	 * @param drones The list of drones to sort.
	 * @return A new sorted ArrayList of drones.
	 */
    public static ArrayList<Drone> sortByYear(ArrayList<Drone> drones) {
        ArrayList<Drone> sorted = new ArrayList<>(drones);
        Collections.sort(sorted, new Comparator<Drone>() {
            @Override
            public int compare(Drone d1, Drone d2) {
                return Integer.compare(d1.getYear(), d2.getYear());
            }
        });
        return sorted;
    }

} // END DeliveryDroneHangerManager


/**
 * Abstract class representing a delivery drone.
 * Serves as the base class for all drone types.
 */
abstract class Drone {
	private String droneID;
	private char type;
    private String manu;
    private double cargoCapacity;
    private int year;

    /**
     * Constructs a Drone with the specified attributes.
     * 
     * @param droneID The unique identifier for the drone.
     * @param type The type of drone (S for Standard, P for Priority).
     * @param manufacturer The manufacturer of the drone.
     * @param cargoCapacity The cargo capacity in kilograms.
     * @param year The year the drone was manufactured.
     */
    public Drone(String droneID, char type, String manufacturer, double cargoCapacity, int year) {
        this.droneID = droneID;
    	this.type = type;
        this.manu = manufacturer;
        this.cargoCapacity = cargoCapacity;
        this.year = year;
    }

    /**
     * Displays information about this drone.
     * Implementation varies by drone subclass.
     */
    public abstract void displayInfo();

    /**
     * Gets the drone ID.
     * @return The drone ID.
     */
    public String getDroneID() {return droneID; }
    
    /**
     * Gets the drone type.
     * @return The drone type character.
     */
    public char getType() { return type; }
    
    /**
     * Gets the manufacturer name.
     * @return The manufacturer name.
     */
    public String getManu() { return manu; }
    
    /**
     * Gets the cargo capacity.
     * @return The cargo capacity in kilograms.
     */
    public double getCargoCapacity() { return cargoCapacity; }
    
    /**
     * Gets the year manufactured.
     * @return The year the drone was manufactured.
     */
    public int getYear() { return year; }
}

/**
 * Represents a standard delivery drone.
 * Extends the Drone class with standard drone functionality.
 */
class StandardDrone extends Drone {
    
    /**
     * Constructs a StandardDrone with the specified attributes.
     * 
     * @param droneID The unique identifier for the drone.
     * @param manu The manufacturer of the drone.
     * @param year The year the drone was manufactured.
     * @param cargoCapacity The cargo capacity in kilograms.
     */
    public StandardDrone(String droneID, String manu, int year, double cargoCapacity) {
        super(droneID, 'S', manu, cargoCapacity, year);
    }

    /**
     * Displays the drone information in a formatted string.
     */
    @Override
    public void displayInfo() {
    	System.out.println(getDroneID() + " | Standard Drone - " + getManu() + 
                " | Year: " + getYear() + " | Payload: " + getCargoCapacity() + " kg");
    }
}

/**
 * Represents a priority delivery drone.
 * Extends the Drone class with priority drone functionality.
 */
class PriorityDrone extends Drone {
    
    /**
     * Constructs a PriorityDrone with the specified attributes.
     * 
     * @param droneID The unique identifier for the drone.
     * @param manu The manufacturer of the drone.
     * @param year The year the drone was manufactured.
     * @param cargoCapacity The cargo capacity in kilograms.
     */
    public PriorityDrone(String droneID, String manu, int year, double cargoCapacity) {
        super(droneID, 'P', manu, cargoCapacity, year);
    }

    /**
     * Displays the drone information in a formatted string.
     */
    @Override
    public void displayInfo() {
    	System.out.println(getDroneID() + " | Priority Drone - " + getManu() + 
                " | Year: " + getYear() + " | Payload: " + getCargoCapacity() + " kg");
    }

}