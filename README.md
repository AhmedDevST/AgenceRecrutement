# Agence Recrutement

## Project Overview
Agence Recrutement is a Java-based recruitment agency management system. It provides functionalities for managing job offers, job seekers, companies, subscriptions, and more. The project is designed to streamline the recruitment process for agencies by offering a user-friendly interface and robust backend support.

## Features
- Manage job offers, job seekers, and companies.
- Add, modify, and delete categories, announcements, and journals.
- Dashboard for tracking recruitment activities.
- User authentication and role-based access.
- Subscription management.
- Postulation and preferences management.

## Project Structure
The project follows a modular structure:

- **src/main/java/files/agencerecrutement**: Contains the main application logic, controllers, DAO classes, and models.
  - **Controller**: Handles user interactions and application logic.
  - **DAO**: Data Access Objects for database operations.
  - **Model**: Represents the data structures used in the application.
- **src/main/resources/files/agencerecrutement**: Contains resources such as images, styles, and views.
- **lib**: Includes external libraries like MySQL Connector.

## Prerequisites
- Java Development Kit (JDK) 11 or higher.
- Apache Maven for dependency management.
- MySQL database.

## Setup Instructions
1. Clone the repository to your local machine.
2. Import the project into your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
3. Configure the MySQL database:
   - Import the `agencerecrutement DB.sql` file located in the `lib` folder to set up the database schema.
   - Update database connection details in the `Utilitaire.java` file.
4. Build the project using Maven:
   ```
   ./mvnw clean install
   ```
5. Run the application by executing the `MainApp.java` file.

## Tools and Technologies

The project leverages the following tools and technologies:.

### 🛠️ Technologies & Languages
![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white)  
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)  
![FXML](https://img.shields.io/badge/FXML-FF2D20?style=flat&logo=fxml&logoColor=white)  
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)  

### 🔧 Tools
![Apache Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=flat&logo=apache-maven&logoColor=white)  
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat&logo=intellij-idea&logoColor=white)  
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=flat&logo=eclipse&logoColor=white)  
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)  
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)

## Screenshots

Here are some screenshots of the application:

### Login Page
![Login Page](lib/screen/Login.png)

### Job Offers
![Job Offers](lib/screen/annoncesDem.png)

### Company Subscription Management
![Company Subscription Management](lib/screen/gestionAbo_entreprise.png)


### Recruit Job Seeker
![Recruit Job Seeker](lib/screen/recruterDemandeur.png)