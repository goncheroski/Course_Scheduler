# Course Scheduler

Course Scheduler is a desktop application developed as a database systems project. It demonstrates how a university can manage course offerings, student enrollment, and waitlists using Java, JDBC, SQL, and Apache Derby. The project emphasizes object-oriented design, relational database concepts, and business logic for course registration.

## Features

- Create and manage semesters
- Add and manage courses
- Create class sections with enrollment limits
- Register students for classes
- Automatically place students on a waitlist when a class is full
- Automatically promote the next waitlisted student when a seat becomes available
- View enrolled and waitlisted students
- Display student schedules
- Store all data in a relational database

## Technologies Used

- Java
- Java Swing
- JDBC
- Apache Derby Database
- SQL
- NetBeans

## Project Structure

```
Course_Scheduler
│
├── MainFrame.java          # Main application interface
├── DBConnection.java       # Database connection
│
├── Queries/
│   ├── CourseQueries.java
│   ├── StudentQueries.java
│   ├── SemesterQueries.java
│   ├── ClassQueries.java
│   ├── ScheduleQueries.java
│   └── MultiTableQueries.java
│
├── Models/
│   ├── StudentEntry.java
│   ├── CourseEntry.java
│   ├── SemesterEntry.java
│   ├── ScheduleEntry.java
│   └── ClassEntry.java
│
└── Database/
```

## Database Design

The application uses an Apache Derby relational database with tables for:

- Students
- Courses
- Semesters
- Classes
- Schedules

The Schedule table tracks whether a student is:

- **Scheduled (S)**
- **Waitlisted (W)**

A timestamp is stored with each registration to preserve waitlist order.

## How It Works

1. Create a semester.
2. Add courses.
3. Create class sections with a maximum capacity.
4. Add students.
5. Register students for classes.
6. If a class is full, additional students are automatically added to the waitlist.
7. When an enrolled student drops a class, the next waitlisted student is automatically enrolled.

## Skills Demonstrated

- Object-Oriented Programming
- Java Swing GUI Development
- JDBC Database Connectivity
- SQL CRUD Operations
- SQL Joins
- Prepared Statements
- Relational Database Design
- Business Logic Implementation
- Data Access Layer Design
