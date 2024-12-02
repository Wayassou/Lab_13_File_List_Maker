import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ListMaker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_EXTENSION = ".txt";
    private static boolean needsToBeSaved = false; // Dirty flag to track unsaved changes
    private static List<String> list = new ArrayList<>();
    private static String currentFileName = null;

    public static void main(String[] args) {
        String choice;
        do {
            printMenu();
            choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "M":
                    moveItem();
                    break;
                case "C":
                    clearList();
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openList();
                    break;
                case "S":
                    saveList();
                    break;
                case "Q":
                    quitProgram();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("Q"));
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("M - Move an item");
        System.out.println("C - Clear the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list from disk");
        System.out.println("S - Save the current list");
        System.out.println("Q - Quit");
        System.out.print("Choose an option: ");
    }

    private static void addItem() {
        System.out.print("Enter an item to add: ");
        list.add(scanner.nextLine().trim());
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        viewList();
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine().trim());
        if (isValidIndex(index)) {
            list.remove(index);
            needsToBeSaved = true;
            System.out.println("Item deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem() {
        System.out.print("Enter an item to insert: ");
        String item = scanner.nextLine().trim();
        System.out.print("Enter the position to insert at: ");
        int index = Integer.parseInt(scanner.nextLine().trim());
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
            System.out.println("Item inserted.");
        } else {
            System.out.println("Invalid position.");
        }
    }

    private static void moveItem() {
        viewList();
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter the new position: ");
        int toIndex = Integer.parseInt(scanner.nextLine().trim());
        if (isValidIndex(fromIndex) && toIndex >= 0 && toIndex <= list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
            System.out.println("Item moved.");
        } else {
            System.out.println("Invalid indices.");
        }
    }

    private static void clearList() {
        System.out.print("Are you sure you want to clear the list? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("List contents:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void openList() {
        if (needsToBeSaved) {
            promptToSave();
        }
        System.out.print("Enter the filename to open: ");
        String fileName = scanner.nextLine().trim();
        if (!fileName.endsWith(FILE_EXTENSION)) {
            fileName += FILE_EXTENSION;
        }
        try {
            list = Files.readAllLines(Paths.get(fileName));
            currentFileName = fileName;
            needsToBeSaved = false;
            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private static void saveList() {
        if (currentFileName == null) {
            System.out.print("Enter a filename to save the list: ");
            currentFileName = scanner.nextLine().trim();
            if (!currentFileName.endsWith(FILE_EXTENSION)) {
                currentFileName += FILE_EXTENSION;
            }
        }
        try {
            Files.write(Paths.get(currentFileName), list);
            needsToBeSaved = false;
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void quitProgram() {
        if (needsToBeSaved) {
            promptToSave();
        }
        System.out.println("Goodbye!");
    }

    private static void promptToSave() {
        System.out.print("You have unsaved changes. Would you like to save? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            saveList();
        }
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index < list.size();
    }
}

