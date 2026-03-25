package com.carbonwaste;

import com.carbonwaste.dao.WasteDAO;
import com.carbonwaste.model.WasteEntry;
import com.carbonwaste.util.Calculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WasteDAO dao = new WasteDAO();

        while (true) {
            System.out.println("\n--- CarbonWaste Menu ---");
            System.out.println("1. Log Trip | 2. View All or Specific | 3. Update Dist | 4. Delete | 5. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Fuel (Petrol/Diesel/LPG/CNG): ");
                    String fuel = sc.next();
                    System.out.print("Distance (km): ");
                    double d = sc.nextDouble();
                    System.out.print("Mileage: ");
                    double m = sc.nextDouble();

                    double co2 = Calculator.calculate(fuel, d, m);
                    WasteEntry newEntry = new WasteEntry(fuel, d, m, co2);
                    dao.saveLog(newEntry);
                    break;

                case 2:
                    System.out.println("1. View All Logs");
                    System.out.println("2. View Specific ID");
                    System.out.print("Choice: ");
                    int viewType = sc.nextInt();

                    if (viewType == 1) {
                        dao.viewLogs(0); // 0 triggers the "Select All" logic
                    } else {
                        System.out.print("Enter ID: ");
                        int searchId = sc.nextInt();
                        dao.viewLogs(searchId);
                    }

                    if (viewType == 1) {
                        System.out.printf("GRAND TOTAL WASTE: %.2f kg%n", dao.getTotalWaste());
                    }
                    break;

                case 3:
                    System.out.print("ID to Update: ");
                    int uId = sc.nextInt();
                    System.out.print("New Distance: ");
                    double nD = sc.nextDouble();
                    // Using 15.0 as default mileage for re-calculation logic
                    double nCo2 = Calculator.calculate("petrol", nD, 15.0);
                    dao.updateLog(uId, nD, nCo2);
                    break;

                case 4:
                    System.out.print("ID to Delete: ");
                    int dId = sc.nextInt();
                    dao.deleteLog(dId);
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    return;
            }
        }
    }
}