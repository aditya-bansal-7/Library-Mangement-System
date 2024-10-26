# Library Management System

The Library Management System is a web application designed to manage library resources, including books, members, and transactions. This system has a RESTful API backend built with Java Spring Boot and a user-friendly frontend developed using React.

## Table of Contents

- [Features](#features)
- [Technologies](#tech-stack)
- [Installation](#installation)
  - [Frontend](#frontend)
  - [Backend](#backend)
- [API Documentation](#api-documentation)


## Features

- User authentication (registration and login)
- Add, update, delete books 
- Search and filter books
- Manage user accounts, block and unblock them
- Record borrowing and returning of books

## Tech Stack

**Backend:** Java, Spring Boot, MySQL

**Frontend:** React, Tailwind CSS, Axios


## Installation


### Frontend

1. **Navigate to the frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install the dependencies:**
   ```bash
   npm install
   ```
3. **Start the development server:**
    ```bash
   npm run dev
   ```
The frontend will be accessible at http://localhost:5173

### Backend

1. **Create a database:**
  
    Create a new database named `lms` in your database management system (e.g., *MySQL*, *PostgreSQL*).

    For example, if you're using **MySQL**:
     ```sql
     CREATE DATABASE lms;
     ```

2. **Navigate to the backend directory:**
     ```bash
     cd backend-main
     ```

3. **Configure the application properties:**
    Open `src/main/resources/application.properties` and configure your database connection settings:
     ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/lms
      spring.datasource.username=root
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
     ```
    Replace `root` and `your_password` with your actual database credentials.

4. **Build & Start the development server:**
    ```bash
    mvn clean install
    mvn spring-boot:run
   ```
The backend API will be available at http://localhost:8008.

## API Documentation

### User Endpoints

- **POST /user/registerUser**: Register a new user.
- **GET /user/getUser/{userId}**: Retrieve user details by user ID.
- **POST /user/login**: Log in a user with email and password.
- **GET /user/allUsers**: Retrieve a list of all users.
- **PUT /user/blockUser/{userId}**: Block a user by user ID.
- **PUT /user/unblockUser/{userId}**: Unblock a user by user ID.
- **PUT /user/updateFine/{userId}**: Update the fine amount for a user.

### Book Endpoints

- **POST /api/books/addNewBook**: Add a new book to the library.
- **GET /api/books/getBook/{bookId}**: Retrieve book details by book ID.
- **POST /api/books/search**: Search for books based on a query and user ID.
- **GET /api/books/getAllBooks**: Retrieve a list of all books.
- **GET /api/books/book/{bookId}**: Retrieve book details by book ID.
