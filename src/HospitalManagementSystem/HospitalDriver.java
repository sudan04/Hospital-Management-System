package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class HospitalDriver {
	private static final String url= "jdbc:postgresql://localhost:5432/hospital";
	private static final String userName= "postgres";
	private static final String password= "123";
 public static void main(String[] args) {
	Scanner scanner= new Scanner(System.in);
	try {
		Connection conn= DriverManager.getConnection(url, userName, password);
		Patient patient= new Patient(conn, scanner);
		Doctor doctor= new Doctor(conn);
		Appointment appoint= new Appointment(conn, scanner);
		while(true) {
			System.out.println("Hospital Management System");
			System.out.println("1. Add Patient: ");
			System.out.println("2. View patients: ");
			System.out.println("3. View Doctors: ");
			System.out.println("4. Book Appointments: ");
			System.out.println("5. View Appointments: ");
			System.out.println("6. Exit:");
			
			System.out.println("Enter your choice:");
			int ch= scanner.nextInt();
			
			switch(ch) {
			case 1:
				patient.addPatient();
				break;
				
			case 2:
				patient.viewPatients();
				break;
			case 3:
				doctor.viewDoctors();
				break;
			case 4:
				appoint.bookAppointment(patient, doctor);
				break;
			case 5:
				appoint.viewAppointments();
				break;
			case 6:
				return;
			default:
				System.out.println("Please enter a valid choice!!");
			}
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
 }
}
