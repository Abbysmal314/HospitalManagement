public class ClientHandler {
    private Database database;

    public ClientHandler() {
        database = new Database();
    }

    public String handleRequest(String request) {

        try {

            String[] parts = request.split("\\|");

            String operation = parts[0].toUpperCase();

            switch (operation) {

                case "CREATE":

                    if (parts.length != 6) {
                        return "Invalid CREATE format.";
                    }

                    return database.createPatient(
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5]
                    );

                case "READ":

                    if (parts.length != 2) {
                        return "Invalid READ format.";
                    }

                    int readId = Integer.parseInt(parts[1]);

                    return database.readPatient(readId);

                case "UPDATE":

                    if (parts.length != 7) {
                        return "Invalid UPDATE format.";
                    }

                    int updateId = Integer.parseInt(parts[1]);

                    return database.updatePatient(
                            updateId,
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5],
                            parts[6]
                    );

                case "DELETE":

                    if (parts.length != 2) {
                        return "Invalid DELETE format.";
                    }

                    int deleteId = Integer.parseInt(parts[1]);

                    return database.deletePatient(deleteId);

                default:

                    return "Unknown command.";

            }

        } catch (Exception e) {

            return "Request error: " + e.getMessage();
        }
    }
}


