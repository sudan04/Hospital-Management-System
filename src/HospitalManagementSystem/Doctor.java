package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Doctor {
	private Connection connection;
	 
	 public Doctor(Connection connection) {
		 this.connection= connection;
	 }
	 
	 
	 // method to view all Doctors
	 public void viewDoctors() {
		 try {
			Statement stmt= connection.createStatement();
			String query= "Select * from doctors";
			ResultSet rs= stmt.executeQuery(query);
			System.out.println("Doctors: ");
			System.out.println("+------------+-------------------------+-----------------------+");
			System.out.println("| Doctors Id | Name                    |   Specialization      |");         
			System.out.println("+------------+-------------------------+-----------------------+");
			
			while(rs.next()) {
				int id= rs.getInt("id");
				String name= rs.getString("name");
				String spec= rs.getString("specialization");
				
				System.out.printf("| %-10d | %-23s | %-21s |\n", id, name, spec);
				System.out.println("+------------+-------------------------+-----------------------+");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 // method to check doctors by id
	 public boolean getDoctorById(int id) {
		 try {
			Statement stmt= connection.createStatement();
			ResultSet rs= stmt.executeQuery(String.format("Select * from doctors where id= %d", id));
			
			if(rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return false;
		 
	 }

}
