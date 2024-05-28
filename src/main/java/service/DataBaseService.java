package service;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;
public class DataBaseService {

    private static DataBaseService instance;
    private static Scanner scanner = new Scanner(System.in);
    private static AuditService auditService = AuditService.getInstance();

    private DataBaseService() {}

    public static DataBaseService getInstance() {
        if (instance == null) {
            synchronized (DataBaseService.class) {
                if (instance == null) {
                    instance = new DataBaseService();
                }
            }
        }
        return instance;
    }
    // CREATE

    public static void readLocations(Connection con) {
        auditService.logAction("readLocations");
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            String querySql = "SELECT ID, city FROM LOCATION";
            rs = stmt.executeQuery(querySql);

            System.out.println("All locations:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String city = rs.getString("city");
                System.out.println("ID: " + id + ", City: " + city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void addLocation(Connection con){
        auditService.logAction("addLocation");

        PreparedStatement pstmt = null;

        System.out.println("Introdu numele locatiei");
        String city;
        city = scanner.nextLine();
        try {
            // Prepare the SQL INSERT statement
            String insertSql = "INSERT INTO LOCATION (city) VALUES (?)";
            pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1, city);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public static void removeLocation(Connection con) {

        auditService.logAction("removeLocation");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Create a Statement with the desired ResultSet type
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT city FROM LOCATION";
            rs = stmt.executeQuery(querySql);

            System.out.println("Available locations:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". " + rs.getString("city"));
                count++;
            }

            if (count == 1) {
                System.out.println("No locations available to delete.");
                return;
            }

            // Prompt user to choose location to delete
            System.out.println("Choose the number of the location to delete:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice); // Move to the chosen location
            String cityToDelete = rs.getString("city");

            // Execute the DELETE statement
            String deleteSql = "DELETE FROM LOCATION WHERE city = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteSql);
            pstmt.setString(1, cityToDelete);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Location '" + cityToDelete + "' deleted successfully.");
            } else {
                System.out.println("Failed to delete location.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateLocation(Connection con) {
        auditService.logAction("updateLocation");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Create a Statement with the desired ResultSet type
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT city FROM LOCATION";
            rs = stmt.executeQuery(querySql);

            System.out.println("Available locations:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". " + rs.getString("city"));
                count++;
            }

            if (count == 1) {
                System.out.println("No locations available to update.");
                return;
            }

            // Prompt user to choose location to update
            System.out.println("Choose the number of the location to update:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice); // Move to the chosen location
            String currentCity = rs.getString("city");

            // Prompt user for the new name
            System.out.println("Enter the new name for the location:");
            String newCity = scanner.nextLine();

            // Prepare the UPDATE statement
            String updateSql = "UPDATE LOCATION SET city = ? WHERE city = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, newCity);
            pstmt.setString(2, currentCity);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Location name updated successfully.");
            } else {
                System.out.println("Failed to update location name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void readProducts(Connection con) {
        auditService.logAction("readProducts");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            String querySql = "SELECT ID, name, price FROM PRODUCT";
            rs = stmt.executeQuery(querySql);

            System.out.println("All products:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                System.out.println("Id:" + id + "Name: " + name + ", Price: " + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void addProduct(Connection con){
        auditService.logAction("addProduct");

        PreparedStatement pstmt = null;

        System.out.println("Introdu numele produsului");
        String name;
        name = scanner.nextLine();

        System.out.println("Introdu pretul produsului");
        int price;
        price = scanner.nextInt();
        scanner.nextLine();


        try {
            // Prepare the SQL INSERT statement
            String insertSql = "INSERT INTO PRODUCT (name, price) VALUES (?, ?)";
            pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeProduct(Connection con) {
        auditService.logAction("removeProduct");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Create a Statement with the desired ResultSet type
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, name FROM PRODUCT";
            rs = stmt.executeQuery(querySql);

            System.out.println("Produse disponibile:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", Name: " + rs.getString("name"));
                count++;
            }

            if (count == 1) {
                System.out.println("Nu sunt disponibile produse");
                return;
            }

            // Prompt user to choose product to delete
            System.out.println("Alege numarul produsului pe care vrei sa-l stergi");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice); // Move to the chosen product
            int productId = rs.getInt("ID");
            String productToDelete = rs.getString("name");

            // Execute the DELETE statement
            String deleteSql = "DELETE FROM PRODUCT WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteSql);
            pstmt.setInt(1, productId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produsul '" + productToDelete + "' a fost sters");
            } else {
                System.out.println("Failed to delete product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateProduct(Connection con) {
        auditService.logAction("updateProduct");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Create a Statement with the desired ResultSet type
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, name, price FROM PRODUCT";
            rs = stmt.executeQuery(querySql);

            System.out.println("Produse disponibile:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", Name: " + rs.getString("name") + ", Price: " + rs.getDouble("price"));
                count++;
            }

            if (count == 1) {
                System.out.println("No products available to update.");
                return;
            }

            // Prompt user to choose product to update
            System.out.println("Alege numarul produsului pe care vrei sa-l schimbi");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice); // Move to the chosen product
            int productId = rs.getInt("ID");

            // Prompt user for the new name and price
            System.out.println("Introdu noul nume al produsului");
            String newName = scanner.nextLine();
            System.out.println("Introdu noul pret al produsului");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            // Prepare the UPDATE statement
            String updateSql = "UPDATE PRODUCT SET name = ?, price = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, newName);
            pstmt.setDouble(2, newPrice);
            pstmt.setInt(3, productId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void readEmployees(Connection con) {
        auditService.logAction("readEmployees");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            String querySql = "SELECT ID, firstName, lastName, salary, LOCATION_ID FROM EMPLOYEE";
            rs = stmt.executeQuery(querySql);

            System.out.println("All employees:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                double salary = rs.getDouble("salary");
                int locationId = rs.getInt("LOCATION_ID");
                System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Salary: " + salary + ", Location ID: " + locationId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void addEmployee(Connection con) {
        auditService.logAction("addEmployee");

        PreparedStatement pstmt = null;

        System.out.println("Enter first name of the employee:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name of the employee:");
        String lastName = scanner.nextLine();

        System.out.println("Enter salary of the employee:");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Display available locations
        readLocations(con);

        System.out.println("Enter the ID of the location for the employee:");
        int locationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            String insertSql = "INSERT INTO EMPLOYEE (firstName, lastName, salary, LOCATION_ID) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDouble(3, salary);
            pstmt.setInt(4, locationId);
            pstmt.executeUpdate();

            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeEmployee(Connection con) {
        auditService.logAction("removeEmployee");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, firstName, lastName FROM EMPLOYEE";
            rs = stmt.executeQuery(querySql);

            System.out.println("Employees available:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", First Name: " + rs.getString("firstName") + ", Last Name: " + rs.getString("lastName"));
                count++;
            }

            if (count == 1) {
                System.out.println("No employees available to delete.");
                return;
            }

            System.out.println("Enter the number of the employee you want to delete:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice);
            int employeeId = rs.getInt("ID");
            String employeeToDelete = rs.getString("firstName") + " " + rs.getString("lastName");

            String deleteSql = "DELETE FROM EMPLOYEE WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteSql);
            pstmt.setInt(1, employeeId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee '" + employeeToDelete + "' deleted successfully.");
            } else {
                System.out.println("Failed to delete employee.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateEmployee(Connection con) {
        auditService.logAction("updateEmployee");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, firstName, lastName, salary, LOCATION_ID FROM EMPLOYEE";
            rs = stmt.executeQuery(querySql);

            System.out.println("Employees available:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", First Name: " + rs.getString("firstName") + ", Last Name: " + rs.getString("lastName") + ", Salary: " + rs.getDouble("salary") + ", Location ID: " + rs.getInt("LOCATION_ID"));
                count++;
            }

            if (count == 1) {
                System.out.println("No employees available to update.");
                return;
            }

            System.out.println("Enter the number of the employee you want to update:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice);
            int employeeId = rs.getInt("ID");

            System.out.println("Enter new first name of the employee:");
            String newFirstName = scanner.nextLine();
            System.out.println("Enter new last name of the employee:");
            String newLastName = scanner.nextLine();
            System.out.println("Enter new salary of the employee:");
            double newSalary = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            // Display available locations
            readLocations(con);

            System.out.println("Enter the ID of the new location for the employee:");
            int newLocationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String updateSql = "UPDATE EMPLOYEE SET firstName = ?, lastName = ?, salary = ?, LOCATION_ID = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setDouble(3, newSalary);
            pstmt.setInt(4, newLocationId);
            pstmt.setInt(5, employeeId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Failed to update employee.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void addClient(Connection con) {
        auditService.logAction("addClient");

        PreparedStatement pstmt = null;

        System.out.println("Enter first name of the client:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name of the client:");
        String lastName = scanner.nextLine();

        System.out.println("Enter phone number of the client:");
        String phoneNumber = scanner.nextLine();

        try {
            String insertSql = "INSERT INTO CLIENT (firstName, lastName, phoneNumber) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phoneNumber);
            pstmt.executeUpdate();

            System.out.println("Client added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void readClients(Connection con) {
        auditService.logAction("readClients");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            String querySql = "SELECT ID, firstName, lastName, phoneNumber FROM CLIENT";
            rs = stmt.executeQuery(querySql);

            System.out.println("All clients:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Phone Number: " + phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateClient(Connection con) {
        auditService.logAction("updateClient");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, firstName, lastName, phoneNumber FROM CLIENT";
            rs = stmt.executeQuery(querySql);

            System.out.println("Clients available:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", First Name: " + rs.getString("firstName") + ", Last Name: " + rs.getString("lastName") + ", Phone Number: " + rs.getString("phoneNumber"));
                count++;
            }

            if (count == 1) {
                System.out.println("No clients available to update.");
                return;
            }

            System.out.println("Enter the number of the client you want to update:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice);
            int clientId = rs.getInt("ID");

            System.out.println("Enter new first name of the client:");
            String newFirstName = scanner.nextLine();
            System.out.println("Enter new last name of the client:");
            String newLastName = scanner.nextLine();
            System.out.println("Enter new phone number of the client:");
            String newPhoneNumber = scanner.nextLine();

            String updateSql = "UPDATE CLIENT SET firstName = ?, lastName = ?, phoneNumber = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setString(3, newPhoneNumber);
            pstmt.setInt(4, clientId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client updated successfully.");
            } else {
                System.out.println("Failed to update client.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeClient(Connection con) {
        auditService.logAction("removeClient");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, firstName, lastName FROM CLIENT";
            rs = stmt.executeQuery(querySql);

            System.out.println("Clients available:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", First Name: " + rs.getString("firstName") + ", Last Name: " + rs.getString("lastName"));
                count++;
            }

            if (count == 1) {
                System.out.println("No clients available to delete.");
                return;
            }

            System.out.println("Enter the number of the client you want to delete:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice);
            int clientId = rs.getInt("ID");
            String clientToDelete = rs.getString("firstName") + " " + rs.getString("lastName");

            String deleteSql = "DELETE FROM CLIENT WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteSql);
            pstmt.setInt(1, clientId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client '" + clientToDelete + "' deleted successfully.");
            } else {
                System.out.println("Failed to delete client.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addOrder(Connection con) {
        auditService.logAction("addOrder");

        PreparedStatement orderStmt = null;
        PreparedStatement orderProductStmt = null;

        try {
            // Add new order
            System.out.println("Enter client ID for the order:");
            readClients(con);
            int clientId = scanner.nextInt();
            scanner.nextLine(); // Consume newline



            // Display available locations
            readLocations(con);

            Random rand = new Random();
            System.out.println("Enter the location ID for the order:");
            int locationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String insertOrderSql = "INSERT INTO `ORDER` (CLIENT_ID, LOCATION_ID, totalPrice) VALUES ( ?, ?, ?)";
            orderStmt = con.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, clientId);
            orderStmt.setInt(2, locationId);
            orderStmt.setInt(3,  rand.nextInt(101) + 100);
            orderStmt.executeUpdate();

            // Get the generated order ID
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            int sum = 0;
            // Add products to order
            while (true) {
                System.out.println("Enter product ID to add to order (or 0 to finish):");
                readProducts(con);
                int productId = scanner.nextInt();
                if (productId == 0) break;

                System.out.println("Enter quantity for the product:");
                int quantity = scanner.nextInt();

                String insertOrderProductSql = "INSERT INTO ORDER_PRODUCT (order_id, product_id, quantity) VALUES (?, ?, ?)";
                orderProductStmt = con.prepareStatement(insertOrderProductSql);
                orderProductStmt.setInt(1, orderId);
                orderProductStmt.setInt(2, productId);
                orderProductStmt.setInt(3, quantity);
                orderProductStmt.executeUpdate();

                System.out.println("Product added to order.");
            }

            System.out.println("Order added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (orderStmt != null) orderStmt.close();
                if (orderProductStmt != null) orderProductStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeOrder(Connection con) {
        auditService.logAction("removeOrder");

        PreparedStatement deleteOrderStmt = null;
        PreparedStatement deleteOrderProductsStmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, CLIENT_ID, totalPrice FROM `ORDER`";
            rs = stmt.executeQuery(querySql);

            System.out.println("Orders available:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", Client ID: " + rs.getInt("CLIENT_ID") + ", Total Price: " + rs.getDouble("totalPrice"));
                count++;
            }

            if (count == 1) {
                System.out.println("No orders available to delete.");
                return;
            }

            System.out.println("Enter the number of the order you want to delete:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice);
            int orderId = rs.getInt("ID");

            // Delete associated order products
            String deleteOrderProductsSql = "DELETE FROM ORDER_PRODUCT WHERE order_id = ?";
            deleteOrderProductsStmt = con.prepareStatement(deleteOrderProductsSql);
            deleteOrderProductsStmt.setInt(1, orderId);
            deleteOrderProductsStmt.executeUpdate();

            // Delete the order
            String deleteOrderSql = "DELETE FROM `ORDER` WHERE ID = ?";
            deleteOrderStmt = con.prepareStatement(deleteOrderSql);
            deleteOrderStmt.setInt(1, orderId);
            int rowsAffected = deleteOrderStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order and its products deleted successfully.");
            } else {
                System.out.println("Failed to delete order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (deleteOrderStmt != null) deleteOrderStmt.close();
                if (deleteOrderProductsStmt != null) deleteOrderProductsStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateOrder(Connection con) {
        auditService.logAction("updateOrder");

        PreparedStatement updateOrderStmt = null;
        PreparedStatement updateOrderProductStmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String querySql = "SELECT ID, CLIENT_ID, totalPrice, LOCATION_ID FROM `ORDER`";
            rs = stmt.executeQuery(querySql);

            System.out.println("Orders available:");
            int count = 1;
            while (rs.next()) {
                System.out.println(count + ". ID: " + rs.getInt("ID") + ", Client ID: " + rs.getInt("CLIENT_ID") + ", Total Price: " + rs.getDouble("totalPrice") + ", Location ID: " + rs.getInt("LOCATION_ID"));
                count++;
            }

            if (count == 1) {
                System.out.println("No orders available to update.");
                return;
            }

            System.out.println("Enter the number of the order you want to update:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice < 1 || choice >= count) {
                System.out.println("Invalid choice.");
                return;
            }

            rs.absolute(choice);
            int orderId = rs.getInt("ID");

            readClients(con);
            System.out.println("Enter new client ID for the order:");
            int newClientId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println("Enter new total price for the order:");
            double newTotalPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            // Display available locations
            readLocations(con);

            System.out.println("Enter new location ID for the order:");
            int newLocationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Update order
            String updateOrderSql = "UPDATE `ORDER` SET CLIENT_ID = ?, totalPrice = ?, LOCATION_ID = ? WHERE ID = ?";
            updateOrderStmt = con.prepareStatement(updateOrderSql);
            updateOrderStmt.setInt(1, newClientId);
            updateOrderStmt.setDouble(2, newTotalPrice);
            updateOrderStmt.setInt(3, newLocationId);
            updateOrderStmt.setInt(4, orderId);
            int orderRowsAffected = updateOrderStmt.executeUpdate();

            if (orderRowsAffected > 0) {
                System.out.println("Order updated successfully.");

                // Optionally update order products
                System.out.println("Do you want to update products for this order? (yes/no)");
                String updateProducts = scanner.nextLine();

                if (updateProducts.equalsIgnoreCase("yes")) {
                    while (true) {
                        System.out.println("Enter product ID to update (or 0 to finish):");
                        int productId = scanner.nextInt();
                        if (productId == 0) break;

                        System.out.println("Enter new quantity for the product:");
                        int newQuantity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String updateOrderProductSql = "UPDATE ORDER_PRODUCT SET quantity = ? WHERE order_id = ? AND product_id = ?";
                        updateOrderProductStmt = con.prepareStatement(updateOrderProductSql);
                        updateOrderProductStmt.setInt(1, newQuantity);
                        updateOrderProductStmt.setInt(2, orderId);
                        updateOrderProductStmt.setInt(3, productId);
                        updateOrderProductStmt.executeUpdate();

                        System.out.println("Product updated for order.");
                    }
                }
            } else {
                System.out.println("Failed to update order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (updateOrderStmt != null) updateOrderStmt.close();
                if (updateOrderProductStmt != null) updateOrderProductStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void readOrders(Connection con) {
        auditService.logAction("readOrders");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            String querySql = "SELECT O.ID, O.CLIENT_ID, O.totalPrice, O.LOCATION_ID, OP.product_id, OP.quantity " +
                    "FROM `ORDER` O LEFT JOIN ORDER_PRODUCT OP ON O.ID = OP.order_id";
            rs = stmt.executeQuery(querySql);

            System.out.println("All orders with products:");
            while (rs.next()) {
                int orderId = rs.getInt("ID");
                int clientId = rs.getInt("CLIENT_ID");
                double totalPrice = rs.getDouble("totalPrice");
                int locationId = rs.getInt("LOCATION_ID");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                System.out.println("Order ID: " + orderId + ", Client ID: " + clientId + ", Total Price: " + totalPrice + ", Location ID: " + locationId +
                        ", Product ID: " + productId + ", Quantity: " + quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
