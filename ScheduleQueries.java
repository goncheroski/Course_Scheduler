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
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static ResultSet resultSet;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement getAllSchedulesByStudent;

    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();

        try {
            addScheduleEntry = connection.prepareStatement(
                "insert into schedule (semester, courseCode, studentID, status, timestamp) values (?, ?, ?, ?, ?)"
            );

            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());

            addScheduleEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleList = new ArrayList<>();

        try {
            getScheduleByStudent = connection.prepareStatement(
                "select * from schedule where semester = ? and studentID = ?"
            );

            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);

            resultSet = getScheduleByStudent.executeQuery();

            while (resultSet.next()) {
                scheduleList.add(new ScheduleEntry(
                    resultSet.getString("semester"),
                    resultSet.getString("courseCode"),
                    resultSet.getString("studentID"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return scheduleList;
    }

    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;

        try {
            getScheduledStudentCount = connection.prepareStatement(
                "select count(*) as total from schedule where semester = ? and courseCode = ? and status = 'S'"
            );

            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);

            resultSet = getScheduledStudentCount.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return count;
    }
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlist = new ArrayList<>();

        try {
            getWaitlistedStudentsByClass = connection.prepareStatement(
                "select * from schedule where semester = ? and courseCode = ? and status = 'W' order by timestamp"
            );

            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);

            resultSet = getWaitlistedStudentsByClass.executeQuery();

            while (resultSet.next()) {
                waitlist.add(new ScheduleEntry(
                    resultSet.getString("semester"),
                    resultSet.getString("courseCode"),
                    resultSet.getString("studentID"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return waitlist;
    }

    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();

        try {
            dropStudentScheduleByCourse = connection.prepareStatement(
                "delete from schedule where semester = ? and studentID = ? and courseCode = ?"
            );

            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);

            dropStudentScheduleByCourse.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }   

    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();

        try {
            dropScheduleByCourse = connection.prepareStatement(
                "delete from schedule where semester = ? and courseCode = ?"
            );

            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);

            dropScheduleByCourse.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void updateScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();

        try {
            updateScheduleEntry = connection.prepareStatement(
                "update schedule set status = ?, timestamp = ? where semester = ? and courseCode = ? and studentID = ?"
            );

            updateScheduleEntry.setString(1, entry.getStatus());
            updateScheduleEntry.setTimestamp(2, entry.getTimestamp());
            updateScheduleEntry.setString(3, entry.getSemester());
            updateScheduleEntry.setString(4, entry.getCourseCode());
            updateScheduleEntry.setString(5, entry.getStudentID());

            updateScheduleEntry.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<ScheduleEntry> getAllSchedulesByStudent(String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleList = new ArrayList<>();

        try {
            getAllSchedulesByStudent = connection.prepareStatement(
                "select * from schedule where studentID = ?"
            );

            getAllSchedulesByStudent.setString(1, studentID);
            resultSet = getAllSchedulesByStudent.executeQuery();

            while (resultSet.next()) {
                scheduleList.add(new ScheduleEntry(
                    resultSet.getString("semester"),
                    resultSet.getString("courseCode"),
                    resultSet.getString("studentID"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return scheduleList;
    }
}
