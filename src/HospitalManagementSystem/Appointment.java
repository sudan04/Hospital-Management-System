package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Appointment {
    private Connection connection;
    private Scanner scanner;

    public Appointment(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void bookAppointment(Patient patient, Doctor doctor) {
        System.out.println("Enter patient Id:");
        int p_id = scanner.nextInt();
        System.out.println("Enter doctor Id:");
        int d_id = scanner.nextInt();
        System.out.println("Enter Appointment date(YYYY-MM-DD):");
        scanner.nextLine();
        String a_date = scanner.nextLine();
        if (patient.getPatientById(p_id) && doctor.getDoctorById(d_id)) {
            if (checkDoctorAvailability(d_id, a_date)) {
                String query = "INSERT INTO appointments (patients_id, doctors_id, appointment_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, p_id);
                    pstmt.setInt(2, d_id);
                    pstmt.setDate(3, java.sql.Date.valueOf(a_date));
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0)
                        System.out.println("Appointment booked!!");
                    else
                        System.out.println("Appointment booking failed!!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor is not available on this date!!");
            }
        } else {
            System.out.println("Either doctor or Patient doesn't exist:");
            return;
        }
    }

    public boolean checkDoctorAvailability(int id, String date) {
        String query = "SELECT * FROM appointments WHERE doctors_id = ? AND appointment_date = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void viewAppointments() {
        String query = "SELECT * FROM appointments";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Appointments: ");
            System.out.println("+--------+--------------+-------------+------------------+");
            System.out.println("|  S.N.  | Patient_ID   | Doctors_Id  | Appointment_Date |");
            System.out.println("+--------+--------------+-------------+------------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                int p_id = rs.getInt("patients_id");
                int d_id = rs.getInt("doctors_id");
                Date date = rs.getDate("appointment_date");

                System.out.printf("| %-6d | %-12d | %-11d | %-16s |\n", id, p_id, d_id, date.toString());
                System.out.println("+--------+--------------+-------------+------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
