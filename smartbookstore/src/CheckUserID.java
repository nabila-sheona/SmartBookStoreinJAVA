import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;



public class CheckUserID implements ICheckUserId {
    @Override
    public boolean checkUserId(String userId) {
        try {
            // Specify the path to your user information file
            String filePath = "UserData.txt";

            // Check if the file exists
            if (new File(filePath).exists()) {
                // Read all lines from the text file
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }

                // Check if the provided userId is in the list of user IDs
                for (String line : lines) {
                    String[] parts = line.split(",");
                    String loadedUserId = parts[0].trim(); // Trim to remove extra spaces

                    // Make the comparison case-insensitive
                    if (loadedUserId.equalsIgnoreCase(userId)) {
                        // UserId found in the file
                        return true;
                    }
                }

                // UserId not found in the file
                return false;
            } else {
                System.out.println("User information file does not exist. No users have been added yet.");
                return false;
            }
        } catch (IOException ex) {
            // Handle any exceptions (e.g., file not found, permission issues)
            System.out.println("Error checking user ID in text file: " + ex.getMessage());
            return false;
        }
    }
}
