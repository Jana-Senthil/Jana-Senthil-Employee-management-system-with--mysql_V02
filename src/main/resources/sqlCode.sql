create table department_details(
                                   department_id int AUTO_INCREMENT ,
                                   department_name varchar(20) not null,
                                   location varchar(20) not null,

                                   constraint pk_department_id
                                       primary key(department_id)
);

create table employee_details(
                                 employee_id int AUTO_INCREMENT,
                                 employee_name varchar(20),
                                 employee_salary decimal(10,2) not null,
                                 employee_email varchar(100) unique not null,
                                 employee_phone varchar(15) unique not null,
                                 status varchar(10) not null,
                                 designation varchar(20),

                                 department_id int not null,

                                 constraint fk_department_id
                                     foreign key (department_id)
                                         references department_details(department_id),

                                 constraint pk_employee_id
                                     primary key (employee_id),
                                 constraint uq_employee_email
                                     unique (employee_email),
                                 constraint uq_employee_phone
                                     unique (employee_phone)

);

create table manager_details(
                                manager_id int AUTO_INCREMENT,
                                manager_name varchar(20) not null,
                                manager_phone varchar(15) unique not null,
                                manager_email varchar(100) unique not null,

                                constraint pk_manager_id
                                    primary key (manager_id),
                                constraint uq_manager_phone
                                    unique (manager_phone),
                                constraint uq_manager_email
                                    unique (manager_email)
);

create table attendance (
                            employee_id int not null,
                            attendance_date date not null,
                            session_no int not null,
                            check_in time not null,
                            check_out time,
                            mode enum('OFFICE', 'WFH') not null,

                            constraint pk_attendece_detail
                                primary key(employee_id,attendance_date,session_no),
                            constraint fk_employee_id
                                foreign key (employee_id)
                                    references employee_details(employee_id)
);

CREATE TABLE leave_request (
                               leave_id INT AUTO_INCREMENT,
                               employee_id INT NOT NULL,
                               leave_type ENUM('CASUAL','SICK','EARNED','EMERGENCY') NOT NULL,
                               start_date DATE NOT NULL,
                               end_date DATE NOT NULL,
                               reason VARCHAR(255) NOT NULL,
                               applied_date DATE NOT NULL,
                               status ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING',
                               manager_id int,
                               manager_comment VARCHAR(255),

                               CONSTRAINT pk_leave
                                   PRIMARY KEY (leave_id),

                               CONSTRAINT fk_leave_employee
                                   FOREIGN KEY (employee_id)
                                       REFERENCES employee_details(employee_id),

                               constraint fk_manage_id
                                   foreign key (manager_id)
                                       references manager_details(manager_id)
);


CREATE TABLE users (

                       user_id INT AUTO_INCREMENT,

                       username VARCHAR(50) NOT NULL UNIQUE,

                       password_hash VARCHAR(255) NOT NULL,

                       role ENUM('EMPLOYEE','MANAGER','ADMIN') NOT NULL,

                       email VARCHAR(100) NOT NULL UNIQUE,

                       phone VARCHAR(15) NOT NULL UNIQUE,

                       employee_id INT,

                       manager_id INT,

                       CONSTRAINT pk_users
                           PRIMARY KEY(user_id),

                       CONSTRAINT fk_users_employee
                           FOREIGN KEY(employee_id)
                               REFERENCES employee_details(employee_id),

                       CONSTRAINT fk_users_manager
                           FOREIGN KEY(manager_id)
                               REFERENCES manager_details(manager_id)
);