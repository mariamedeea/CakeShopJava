import java.sql.Connection;
import java.util.Scanner;

import service.*;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        DataBaseService myCakeShop = DataBaseService.getInstance();
        DbFunctions db = new DbFunctions();
        Connection connection = db.connect_to_db("mycakeshop", "root", "root");

//        myCakeShop.addLocation(connection);
//        myCakeShop.removeLocation(connection);
//        myCakeShop.readLocations(connection);
//        myCakeShop.updateLocation(connection);
        myCakeShop.readLocations(connection);


//
////        myCakeShop.addProduct(connection);
////        myCakeShop.readProducts(connection);
////        myCakeShop.removeProduct(connection);
////        myCakeShop.readProducts(connection);
////        myCakeShop.updateProduct(connection);
////        myCakeShop.readProducts(connection);
//
////        myCakeShop.addEmployee(connection);
////        myCakeShop.addEmployee(connection);
////        myCakeShop.readEmployees(connection);
////        myCakeShop.removeEmployee(connection);
////        myCakeShop.readEmployees(connection);
////        myCakeShop.updateEmployee(connection);
////        myCakeShop.readEmployees(connection);
//
////        myCakeShop.addClient(connection);
////        myCakeShop.readClients(connection);
////        myCakeShop.removeClient(connection);
////        myCakeShop.readClients(connection);
////        myCakeShop.updateClient(connection);
////        myCakeShop.readClients(connection);

        myCakeShop.addOrder(connection);
//        myCakeShop.updateOrder(connection);
//        myCakeShop.readOrders(connection);
    }
}
