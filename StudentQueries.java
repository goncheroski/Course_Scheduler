/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author gonch
 */
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static ResultSet resultSet;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;

    public static boolean addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();

        try {
            addStudent = connection.prepareStatement(
                "insert into student (studentID, firstName, lastName) values (?, ?, ?)"
            );

            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());

            addStudent.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();

        try {
            getAllStudents = connection.prepareStatement(
                "select studentID, firstName, lastName from student"
            );

            resultSet = getAllStudents.executeQuery();

            while (resultSet.next()) {
                students.add(new StudentEntry(
                    resultSet.getString("studentID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return students;
    }
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();

        try {
            getStudent = connection.prepareStatement(
                "select studentID, firstName, lastName from student where studentID = ?"
            );

            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();

            if (resultSet.next()) {
                return new StudentEntry(
                    resultSet.getString("studentID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName")
                );
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        
        try {
            dropStudent = connection.prepareStatement(
                "delete from student where studentID = ?"
            );

            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
