package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Patient {
 private Connection connection;
 private Scanner scanner;
 
 public Patient(Connection connection, Scanner scanner) {
	 this.connection= connection;
	 this.scanner= scanner;
 }
 
 
 // method to add Patient into hospital
 public void addPatient() {
	 System.out.println("Enter patient name: ");
	 scanner.nextLine();
     String name = scanner.nextLine(); 
     System.out.println("Enter patient age: ");
     int age = scanner.nextInt();
     scanner.nextLine(); 
     System.out.println("Enter patient gender: ");
     String gender = scanner.nextLine(); 
	 
	 try {
		Statement stmt=  connection.createStatement();
		String query= String.format("Insert into patients(name, age, gender) "+
		"values('%s', %d,'%s')", name, age, gender);
		int rowsAffected= stmt.executeUpdate(query);
		if(rowsAffected>0) System.out.println("Patient admitted successfully ");
		else System.out.println("Patient admission failed");
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
 }
 
 // method to view all Patient
 public void viewPatients() {
	 try {
		Statement stmt= connection.createStatement();
		String query= "Select * from patients";
		ResultSet rs= stmt.executeQuery(query);
		System.out.println("Patients: ");
		System.out.println("+------------+-------------------------+----------+------------+");
		System.out.println("| Patient Id | Name                    | Age      |  Gender    |");
		System.out.println("+------------+-------------------------+----------+------------+");
		
		while(rs.next()) {
			int id= rs.getInt("id");
			String name= rs.getString("name");
			int age= rs.getInt("age");
			String gender= rs.getString("gender");
			
			System.out.printf("| %-10d | %-23s | %-8d | %-10s |\n", id, name, age, gender);
			System.out.println("+------------+-------------------------+----------+------------+");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
 }
 
 // method to check patients by id
 public boolean getPatientById(int id) {
	 try {
		Statement stmt= connection.createStatement();
		ResultSet rs= stmt.executeQuery(String.format("Select * from patients where id= %d", id));
		
		if(rs.next()) return true;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	 
	 return false;
	 
 }
}
