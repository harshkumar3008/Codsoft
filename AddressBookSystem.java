import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Contact {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    
    public String toString() {
        return "Contact{name='" + name + "', phoneNumber='" + phoneNumber + "', emailAddress='" + emailAddress + "'}";
    }
}

class AddressBook {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public boolean removeContact(String name) {
        return contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
    }

    public List<Contact> searchContact(String name) {
        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Address book is empty.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    public void loadContacts(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    addContact(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load contacts from file: " + e.getMessage());
        }
    }

    public void saveContacts(String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Contact contact : contacts) {
                out.println(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmailAddress());
            }
        } catch (IOException e) {
            System.out.println("Failed to save contacts: " + e.getMessage());
        }
    }
}

public class AddressBookSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AddressBook addressBook = new AddressBook();
    private static final String STORAGE_FILE = "contacts.txt";

    public static void main(String[] args) {
        addressBook.loadContacts(STORAGE_FILE);
        while (true) {
            System.out.println("\nAddress Book System:");
            System.out.println("1. Add a Contact");
            System.out.println("2. Remove a Contact");
            System.out.println("3. Search for a Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addNewContact();
                    break;
                case "2":
                    removeContact();
                    break;
                case "3":
                    searchForContact();
                    break;
                case "4":
                    addressBook.displayAllContacts();
                    break;
                case "5":
                    addressBook.saveContacts(STORAGE_FILE);
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void addNewContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();
        addressBook.addContact(new Contact(name, phone, email));
        System.out.println("Contact added successfully.");
    }

    private static void removeContact() {
        System.out.print("Enter the name of the contact to remove: ");
        String name = scanner.nextLine();
        if (addressBook.removeContact(name)) {
            System.out.println("Contact removed successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void searchForContact() {
        System.out.print("Enter the name of the contact to search for: ");
        String name = scanner.nextLine();
        List<Contact> results = addressBook.searchContact(name);
        if (results.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            results.forEach(System.out::println);
        }
    }
}
