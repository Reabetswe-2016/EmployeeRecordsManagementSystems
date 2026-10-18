/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeerecordsmanagementsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManager {

    private static final String FILE_NAME = "employees.txt";

    private ArrayList<Employee> employees;
    private Scanner input;

// Constructor
    public EmployeeManager() {
        employees = new ArrayList<>();
        input = new Scanner(System.in);

        File file = new File(FILE_NAME);

        if (file.exists()) {
            employees = loadEmployees();
            System.out.println(
                    employees.size() + " employee records loaded."
            );
        } else {
            System.out.println("No existing employee file found.");
            System.out.println("Starting with an empty employee list.");
        }
    }

// Display the menu
    public void displayMenu() {
        System.out.println("\n======================================");
        System.out.println(" EMPLOYEE RECORDS MANAGEMENT");
        System.out.println("======================================");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Calculate Average Salary");
        System.out.println("7. Display Highest Paid Employee");
        System.out.println("8. Save Records to File");
        System.out.println("9. Reload Records from File");
        System.out.println("10. Exit");
        System.out.println("11. Search Employees by Department");
        System.out.println("12. Sort Employees by Salary");
        System.out.print("\nEnter your choice: ");
    }

// Run the application
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            try {
                int choice = Integer.parseInt(input.nextLine().trim());

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;

                    case 2:
                        displayEmployees();
                        break;

                    case 3:
                        searchEmployeeMenu();
                        break;

                    case 4:
                        updateEmployee();
                        break;

                    case 5:
                        deleteEmployee();
                        break;

                    case 6:
                        displayAverageSalary();
                        break;

                    case 7:
                        displayHighestPaidEmployee();
                        break;

                    case 8:
                        saveEmployees(employees);
                        break;

                    case 9:
                        reloadEmployees();
                        break;

                    case 10:
                        System.out.println("\nSaving employee records...");
                        saveEmployees(employees);
                        System.out.println(
                                "Thank you for using Employee Records Management System."
                        );
                        running = false;
                        break;
                    case 11:
                        searchEmployeesByDepartment();
                        break;
                    case 12:
                        sortEmployeesBySalary();
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice. Please enter 1 to 12."
                        );
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }

// Add an employee
    public void addEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(input.nextLine().trim());

            if (employeeId <= 0) {
                System.out.println(
                        "Employee ID must be greater than zero."
                );
                return;
            }

            if (searchEmployee(employees, employeeId) != null) {
                System.out.println(
                        "This employee ID already exists."
                );
                return;
            }

            System.out.print("Enter Employee Name: ");
            String name = input.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Employee name cannot be empty.");
                return;
            }

            System.out.print("Enter Department: ");
            String department = input.nextLine().trim();

            if (department.isEmpty()) {
                System.out.println("Department cannot be empty.");
                return;
            }

            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(input.nextLine().trim());

            if (!Double.isFinite(salary) || salary <= 0) {
                System.out.println(
                        "Salary must be greater than zero."
                );
                return;
            }

            Employee employee = new Employee(
                    employeeId, name, department, salary
            );

            employees.add(employee);

            System.out.println("Employee added successfully.");

            saveEmployees(employees);

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid employee ID or salary. Please enter valid numbers."
            );
        }
    }

// Display all employees
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "%-10s %-23s %-18s %15s%n",
                "ID", "NAME", "DEPARTMENT", "SALARY"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Employee employee : employees) {
            System.out.printf(
                    "%-10d %-23s %-18s R%14.2f%n",
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.println("Total Employees: " + employees.size());
    }

// Search for an employee by ID
    public static Employee searchEmployee(
            ArrayList<Employee> employees, int employeeId) {

        for (Employee employee : employees) {
            if (employee.getEmployeeId() == employeeId) {
                return employee;
            }
        }

        return null;
    }

// Search employee menu
    public void searchEmployeeMenu() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(input.nextLine().trim());

            Employee employee = searchEmployee(
                    employees, employeeId
            );

            if (employee == null) {
                System.out.println("Employee not found.");
            } else {
                System.out.println("\nEmployee Found");
                System.out.println("----------------------------");
                System.out.println(
                        "ID: " + employee.getEmployeeId()
                );
                System.out.println(
                        "Name: " + employee.getName()
                );
                System.out.println(
                        "Department: " + employee.getDepartment()
                );
                System.out.printf(
                        "Salary: R%.2f%n",
                        employee.getSalary()
                );
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid employee ID. Enter a whole number."
            );
        }
    }

// Update an employee
    public void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int employeeId = Integer.parseInt(input.nextLine().trim());

            Employee employee = searchEmployee(
                    employees, employeeId
            );

            if (employee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println("\nCurrent Employee:");
            System.out.println(
                    employee.getName() + " - "
                    + employee.getDepartment() + " - R"
                    + String.format("%.2f", employee.getSalary())
            );

            System.out.print("Enter new name: ");
            String name = input.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter new department: ");
            String department = input.nextLine().trim();

            if (department.isEmpty()) {
                System.out.println("Department cannot be empty.");
                return;
            }

            System.out.print("Enter new salary: ");
            double salary = Double.parseDouble(input.nextLine().trim());

            if (!Double.isFinite(salary) || salary <= 0) {
                System.out.println(
                        "Salary must be greater than zero."
                );
                return;
            }

            employee.setName(name);
            employee.setDepartment(department);
            employee.setSalary(salary);

            System.out.println("Employee updated successfully.");

            saveEmployees(employees);

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid ID or salary. Please enter valid numbers."
            );
        }
    }

// Delete an employee
    public void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int employeeId = Integer.parseInt(input.nextLine().trim());

            Employee employee = searchEmployee(
                    employees, employeeId
            );

            if (employee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println(
                    "Employee found: " + employee.getName()
            );

            System.out.print(
                    "Are you sure you want to delete this employee? (Y/N): "
            );

            String confirmation = input.nextLine().trim();

            if (confirmation.equalsIgnoreCase("Y")) {
                employees.remove(employee);

                System.out.println("Employee deleted successfully.");

                saveEmployees(employees);

            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid employee ID. Enter a whole number."
            );
        }
    }

// Calculate average salary
    public static double calculateAverageSalary(
            ArrayList<Employee> employees) {

        if (employees.isEmpty()) {
            return 0.0;
        }

        double totalSalary = 0.0;

        for (Employee employee : employees) {
            totalSalary += employee.getSalary();
        }

        return totalSalary / employees.size();
    }

// Display salary statistics
    public void displayAverageSalary() {
        if (employees.isEmpty()) {
            System.out.println(
                    "No employees available to calculate salaries."
            );
            return;
        }

        double totalSalary = 0.0;

        for (Employee employee : employees) {
            totalSalary += employee.getSalary();
        }

        System.out.println(
                "Number of Employees: " + employees.size()
        );

        System.out.printf(
                "Total Salary: R%.2f%n", totalSalary
        );

        System.out.printf(
                "Average Salary: R%.2f%n",
                calculateAverageSalary(employees)
        );
    }

// Find the highest-paid employee
    public static Employee findHighestPaidEmployee(
            ArrayList<Employee> employees) {

        if (employees.isEmpty()) {
            return null;
        }

        Employee highestPaid = employees.get(0);

        for (Employee employee : employees) {
            if (employee.getSalary() > highestPaid.getSalary()) {
                highestPaid = employee;
            }
        }

        return highestPaid;
    }

// Display the highest-paid employee
    public void displayHighestPaidEmployee() {
        Employee employee = findHighestPaidEmployee(employees);

        if (employee == null) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println("\nHighest Paid Employee");
        System.out.println("-------------------------");

        System.out.println(
                "ID: " + employee.getEmployeeId()
        );

        System.out.println(
                "Name: " + employee.getName()
        );

        System.out.println(
                "Department: " + employee.getDepartment()
        );

        System.out.printf(
                "Salary: R%.2f%n", employee.getSalary()
        );
    }
    // Search employees by department

    public void searchEmployeesByDepartment() {

        System.out.print("Enter department to search: ");
        String departmentToFind = input.nextLine().trim();

        if (departmentToFind.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }

        boolean found = false;
        int count = 0;

        System.out.println("\nEmployees in Department: "
                + departmentToFind);

        System.out.println("---------------------------------------------");

        for (Employee employee : employees) {

            if (employee.getDepartment()
                    .equalsIgnoreCase(departmentToFind)) {

                System.out.printf(
                        "ID: %d | Name: %s | Department: %s | Salary: R%.2f%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getDepartment(),
                        employee.getSalary()
                );

                found = true;
                count++;
            }
        }

        if (found) {
            System.out.println("---------------------------------------------");
            System.out.println("Total employees found: " + count);
        } else {
            System.out.println(
                    "No employees found in that department."
            );
        }
    }
// Sort employees by salary

    public void sortEmployeesBySalary() {

        if (employees.isEmpty()) {
            System.out.println("No employee records available to sort.");
            return;
        }

        System.out.println("\nSort Employees by Salary");
        System.out.println("A. Lowest to Highest");
        System.out.println("D. Highest to Lowest");
        System.out.print("Choose sorting order: ");

        String order = input.nextLine().trim();

        if (order.equalsIgnoreCase("A")) {

            employees.sort(
                    (employee1, employee2)
                    -> Double.compare(
                            employee1.getSalary(),
                            employee2.getSalary()
                    )
            );

            System.out.println("\nEmployees sorted from lowest to highest salary.");

        } else if (order.equalsIgnoreCase("D")) {

            employees.sort(
                    (employee1, employee2)
                    -> Double.compare(
                            employee2.getSalary(),
                            employee1.getSalary()
                    )
            );

            System.out.println("\nEmployees sorted from highest to lowest salary.");

        } else {
            System.out.println(
                    "Invalid choice. Please enter A or D."
            );
            return;
        }

        displayEmployees();

// Save the sorted order to the file
        saveEmployees(employees);
    }

// Save employee records to employees.txt
    public static void saveEmployees(
            ArrayList<Employee> employees) {

        File file = new File(FILE_NAME);

        System.out.println("Saving to: " + file.getAbsolutePath());

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {

            for (Employee employee : employees) {
                writer.write(employee.toString());
                writer.newLine();
            }

            System.out.println(
                    "Employee records saved successfully."
            );

        } catch (IOException e) {
            System.out.println("Error saving employee records.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

// Load employee records from employees.txt
    public static ArrayList<Employee> loadEmployees() {
        ArrayList<Employee> loadedEmployees = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return loadedEmployees;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",", -1);

                    if (data.length != 4) {
                        System.out.println(
                                "Warning: Invalid employee record found."
                        );
                        System.out.println("Record skipped.");
                        continue;
                    }

                    int employeeId = Integer.parseInt(
                            data[0].trim()
                    );

                    String name = data[1].trim();
                    String department = data[2].trim();

                    double salary = Double.parseDouble(
                            data[3].trim()
                    );

                    if (employeeId <= 0
                            || name.isEmpty()
                            || department.isEmpty()
                            || !Double.isFinite(salary)
                            || salary <= 0
                            || searchEmployee(
                                    loadedEmployees, employeeId
                            ) != null) {

                        System.out.println(
                                "Warning: Invalid or duplicate employee record."
                        );

                        System.out.println("Record skipped.");
                        continue;
                    }

                    Employee employee = new Employee(
                            employeeId, name, department, salary
                    );

                    loadedEmployees.add(employee);

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Warning: Invalid employee ID or salary found."
                    );

                    System.out.println("Record skipped.");
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Error reading employee records: "
                    + e.getMessage()
            );
        }

        return loadedEmployees;
    }

// Reload employee records from the file
    public void reloadEmployees() {
        System.out.println("Reloading employee records...");

        employees = loadEmployees();

        System.out.println(
                employees.size()
                + " employee records loaded successfully."
        );
    }
}
