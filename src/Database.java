import java.sql.*;

public class Database {

    // Assign the database url to a variable (just so I don't have to type it out every time)
    private final String databaseUrl = "jdbc:ucanaccess://data/PROJECT_4.accdb";

    // Database connection method (this just tells Java where the Access database is and opens the connection)
    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(databaseUrl);
    }

    // Create patient method
    public String createPatient(String firstName, String lastName, String dob, String phone, String email) {

        // SQL Create operation (the question marks are placeholders for patient data)
        String sql = "INSERT INTO Patient (FirstName, LastName, DateOfBirth, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)";

        // Assign the operation to a PreparedStatement variable
        try (Connection conn = getConnection();

            PreparedStatement ps = conn.prepareStatement(sql)) {

            // Fill in the question marks with patient data
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, dob);
            ps.setString(4, phone);
            ps.setString(5, email);

            // Run the insert command
            int rows = ps.executeUpdate();

            // Error handling
            if (rows > 0) {

                return "Patient created successfully: " + firstName + " " + lastName;
            }

        } catch (Exception e) {

            return "Create error: " + e.getMessage();
        }

        return "Nothing was returned.";
    }

    // Read patient method
    public String readPatient(int patientId) {

        // SQL Read operation (this searches for a patient by ID)
        String sql = "SELECT * FROM Patient WHERE PatientID = ?";

        try (Connection conn = getConnection();

            // Assign the operation to a PreparedStatement variable
            PreparedStatement ps = conn.prepareStatement(sql)) {

            // Replace the placeholder with the patients ID
            ps.setInt(1, patientId);

            // Run the search and store the returned data in the rs variable
            ResultSet rs = ps.executeQuery();

            // Check if a patient is found
            if (rs.next()) {

                // If found, read and return the patients data
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String dob = rs.getString("DateOfBirth");
                String phone = rs.getString("PhoneNumber");
                String email = rs.getString("Email");

                return "Patient Found:\n"
                        + "Name: " + firstName + " " + lastName + "\n"
                        + "Date of Birth: " + dob + "\n"
                        + "Phone: " + phone + "\n"
                        + "Email: " + email;
            }

        // Error handling
        } catch (Exception e) {

            return "Read error: " + e.getMessage();
        }

        return "Nothing was returned. No matching patient record was found.";
    }

    // Update patient method
    public String updatePatient(int patientId, String firstName, String lastName, String dob, String phone, String email) {

        // SQL Update operation (updates the data of an existing patient)
        String sql = "UPDATE Patient SET FirstName=?, LastName=?, DateOfBirth=?, PhoneNumber=?, Email=? WHERE PatientID=?";

        try (Connection conn = getConnection();

            // Assign the operation to a PreparedStatement variable
            PreparedStatement ps = conn.prepareStatement(sql)) {

            // Fill in the placeholders
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, dob);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setInt(6, patientId);

            // Run the update
            int rows = ps.executeUpdate();

            if (rows > 0) {

                return "Patient #" + patientId + " updated successfully.";
            }

        // Error handling
        } catch (Exception e) {

            return "Update error: " + e.getMessage();
        }

        return "Nothing was returned. No matching patient record was found.";
    }

    // Delete patient method
    public String deletePatient(int patientId) {

        // SQL delete operation
        String sql = "DELETE FROM Patient WHERE PatientID=?";

        try (Connection conn = getConnection();

            // Assign the operation to a PreparedStatement variable
            PreparedStatement ps = conn.prepareStatement(sql)) {

            // Fill in the patient ID
            ps.setInt(1, patientId);

            // Run the delete
            int rows = ps.executeUpdate();

            if (rows > 0) {

                return "Patient #" + patientId + " deleted successfully.";
            }

        // Error handling
        } catch (Exception e) {

            return "Delete error: " + e.getMessage();
        }

        return "Nothing was returned. No matching patient record was found.";
    }
}