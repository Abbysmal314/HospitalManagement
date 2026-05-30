

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

    public class Client {

        private static final String SERVER_ADDRESS = "localhost";
        private static final int PORT = 5000;

        public static void main(String[] args) {

            try (

                    Socket socket = new Socket(SERVER_ADDRESS, PORT);

                    BufferedReader serverInput =
                            new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

                    PrintWriter serverOutput =
                            new PrintWriter(socket.getOutputStream(), true);

                    Scanner scanner = new Scanner(System.in)

            ) {

                System.out.println("Connected to Hospital Management Server");

                boolean running = true;

                while (running) {

                    System.out.println("\n===== PATIENT MENU =====");
                    System.out.println("1. Create Patient");
                    System.out.println("2. Read Patient");
                    System.out.println("3. Update Patient");
                    System.out.println("4. Delete Patient");
                    System.out.println("5. Exit");
                    System.out.print("Choose option: ");

                    int choice = Integer.parseInt(scanner.nextLine());

                    String request = "";

                    switch (choice) {

                        case 1:

                            System.out.print("First Name: ");
                            String firstName = scanner.nextLine();

                            System.out.print("Last Name: ");
                            String lastName = scanner.nextLine();

                            System.out.print("Date of Birth (YYYY-MM-DD): ");
                            String dob = scanner.nextLine();

                            System.out.print("Phone Number: ");
                            String phone = scanner.nextLine();

                            System.out.print("Email: ");
                            String email = scanner.nextLine();

                            request = "CREATE|" +
                                    firstName + "|" +
                                    lastName + "|" +
                                    dob + "|" +
                                    phone + "|" +
                                    email;

                            break;

                        case 2:

                            System.out.print("Patient ID: ");
                            int readId = Integer.parseInt(scanner.nextLine());

                            request = "READ|" + readId;

                            break;

                        case 3:

                            System.out.print("Patient ID: ");
                            int updateId = Integer.parseInt(scanner.nextLine());

                            System.out.print("First Name: ");
                            firstName = scanner.nextLine();

                            System.out.print("Last Name: ");
                            lastName = scanner.nextLine();

                            System.out.print("Date of Birth (YYYY-MM-DD): ");
                            dob = scanner.nextLine();

                            System.out.print("Phone Number: ");
                            phone = scanner.nextLine();

                            System.out.print("Email: ");
                            email = scanner.nextLine();

                            request = "UPDATE|" +
                                    updateId + "|" +
                                    firstName + "|" +
                                    lastName + "|" +
                                    dob + "|" +
                                    phone + "|" +
                                    email;

                            break;

                        case 4:

                            System.out.print("Patient ID: ");
                            int deleteId = Integer.parseInt(scanner.nextLine());

                            request = "DELETE|" + deleteId;

                            break;

                        case 5:

                            running = false;
                            System.out.println("Goodbye.");
                            continue;

                        default:

                            System.out.println("Invalid choice.");
                            continue;
                    }

                    serverOutput.println(request);

                    String response = serverInput.readLine();

                    System.out.println("\nSERVER RESPONSE");
                    System.out.println(response.replace("|", "\n"));
                }

            } catch (Exception e) {

                System.out.println("Client Error: " + e.getMessage());
            }
        }
    }








