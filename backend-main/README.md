<h1 align="center">Library Managment System</h1>

<p>A library management system is an automation system used to manage a
library such as check out and return of books and calculating any fines for any
late returns</p>

## Features
- Issue a book to the user, if the user has issued less than or equal to 5 books and the book is available.
- Return a book and maintain the log for the user activity.
- Calculate fine if any.
- Junit testing testing for all the layers using Mockito framework.

## ER Diagram
<a href="#"><img width="100%" height="auto" src="https://github.com/btcpatil/adityabansal7_LibraryMangementSystem/blob/main/Resources/ER_diagram.png" height="175px"/></a>

## Built With
- Java
- SpringBoot
- MySql
- Junit
- Mockito

## Assumptions And Prerequisites
- To test the application at least one user should be stored in the database.
- One user can issue max 5 books to check this edge case at least 6 or more books should be stored in the database.
- While storing the book always availability of the book should be <strong>true</strong> if it is <strong>false</strong> no one can issue that particular book.
- Always only one copy of the individual book is present.
- To check the returnBook API it asks return date in String format date should be passed as query parm in “yyyy-mm-dd” format.

## Installation and Run
- Before running the application update the database configuration in the application.properties file.
```
server.port=8008
#db specific properties
spring.datasource.url=jdbc:mysql://localhost:3306/lms
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=patil
```

## API End Points

### `Create User`: http://localhost:8008/user/registerUser
This API will store the new user in the database it can't accept two users with the same email id.

### `Store Book`: http://localhost:8008/book/addNewBook
This API will store the new book in the database.

### `Issue Book`: http://localhost:8008/libraryLog/issueBook/userId/{userId}/bookId/{bookId}

This API will issue a book to the eligible user who issued less than or equal to 5 books, the book should be available and returns the due date to return the book.
The due date to return the book is always today’s date + 8 days.

### `Return Book`: http://localhost:8008/libraryLog/returnBook/bookId/{bookId}/userId/{userId}?returnDate=2023-02-15 {any future date}

This will take the book return back and the returned book is now available to issue again. If the user is eligible for a fine then it calculates the fine according to the number of days late submission(per day 10rs) and this information will be stored in the LibraryLog database for the future use.

## Future Updates

All the user activities are stored in a separate database to add new features without any complications.
- To calculate the total fine collected by individual users or by all users.
- Track the user in any damage found in the book.


## Author

**Manjunath Patil**
