import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Calendar;


public class GenerateNewUser implements IGenerateNewUser {
    private boolean isLibraryMember;
    private String username;
    private int numberOfUser;
    private String userId;

    public GenerateNewUser() {

        //loadUserInformationFromFile();
    }

    @Override
    public void becomeMember() {
        System.out.println("Enter your name:");
        String name = new Scanner(System.in).nextLine();
        username = name;
        isLibraryMember = true;
        generateUserId(name);
        saveUserInformationToFile(userId, username);
        System.out.println("You are now a library member!");
    }

    private void generateUserId(String name) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        int usersIn = getUserNumber();

        userId = String.format("%d_%02d_%04d_%s", year, month, usersIn, name.substring(0, 2));
        System.out.println("Your library member ID is: " + userId);
    }

    private int getUserNumber() {
        String filePath = "UserData.txt";

        if (new File(filePath).exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                numberOfUser = (int) reader.lines().count();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return numberOfUser;
    }

    private void saveUserInformationToFile(String userId, String username) {
        try {
            String filePath = "UserData.txt";

            FileWriter writer = new FileWriter(filePath, true);
            writer.write(userId + "," + username + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving user information to text file: " + e.getMessage());
        }
    }

    // New method to load user information from a text file
    private void loadUserInformationFromFile() {
        try {
            // Specify the path to your text file
            String filePath = "UserData.txt";

            // Check if the file exists
            if (new File(filePath).exists()) {
                // Read all lines from the text file and do any necessary processing
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    reader.lines().forEach(System.out::println);
                    // You can process the lines if needed
                }
            } else {
                System.out.println("User information file does not exist. No users have been added yet.");
            }
        } catch (Exception ex) {
            // Handle any exceptions (e.g., file not found, permission issues)
            System.out.println("Error loading user information from text file: " + ex.getMessage());
        }
    }
}


