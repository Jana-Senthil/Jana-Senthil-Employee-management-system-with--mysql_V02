# Employee Management System

A Java-based Employee Management System developed using Java, JDBC, and MySQL.

## Technologies Used

- Java
- JDBC
- MySQL
- SQL
- IntelliJ IDEA
- Maven

## Features

### Admin
- Add employees
- Update employee details
- Add managers
- Update manager details
- Manage users
- Activate and deactivate accounts
- Permanently delete inactive employees and managers

### Employee
- Login
- View employee details
- Update profile
- Apply for leave
- View attendance

### Manager
- Login
- Manage employees
- Manage employee status
- Process leave requests

### Security
- User authentication
- Role-based access
- Password hashing using SHA-256
- Active/inactive account validation

### Database
- Employee details
- Manager details
- User accounts
- Departments
- Attendance
- Leave requests

### Database Transactions

Permanent deletion uses database transactions to ensure that related records are deleted safely. If an operation fails, the transaction is rolled back.

## Project Structure

```text
src/
└── main/
    └── java/
        └── org/example/
            ├── dao/
            ├── model/
            ├── service/
            ├── ui/
            ├── util/
            └── validation/
