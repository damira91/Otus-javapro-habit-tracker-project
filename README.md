## Habit tracker ##
This web application helps users manage and track their daily habits. Users can create and monitor different habits and also categorize them for better focus.

## Main  functionality of the application: ##
+ User's registration and authorization
+ CRUD of habits 
+ CRUD of users 
+ CRUD of categories
+ CRUD of practice 

## Entity - Relation Diagram (ERD) ##
![ER Diagram](https://github.com/damira91/Otus-javapro-habit-tracker-project/blob/main/Habit%20tracker%20ER.drawio.png)
+ **User:** manages user's credentials
+ **Profile:** manages user information
+ **Habit:** shows individual habits that user want to track
+ **Category:** categorizes user's habits
+ **Practice:** tracks user's habit
  
## API endpoints ##		

|HTTP Methods | URL                             | Functionality                       |
|-------------|---------------------------------|-------------------------------------|
|POST         |/api/users/register/             |Register a user                      |
|POST         |/api/users/login/                |Login registered user                |
|GET          |/api/users/profile/              |Get the logged-in user's profile     |
|PUT          |/api/users/profile/              |Update the users profile             |
|GET          |/api/categories/                 |Get all categories for logged-in user|
|GET          |/api/categories/{id}/            |Get category by id                   |
|POST         |/api/categories/                 |Create a new category                |
|PUT          |/api/categories/{id}/            |Update created category by its id    |
|DELETE       |/api/categories/{id}/            |Delete category by its id            |
|GET          |/api/habits/                     |Get all logged-in user's habits      |
|POST         |/api/categories/{id}/habits/     |Create a new habit for loged-in user |
|PUT          |/api/habits/{id}/                |Update created habit by its id       |
|GET          |/api/habits/{id}/                |Get user's habit by its id           |
|DELETE       |/api/categories/{id}/habits/{id}/|Delete habit by its id               |
|POST         |/api/practices/                  |Create a new practice for a habit    |
|GET          |/api/prractices/                 |Get all practices                    |
|GET          |/api/practices/{id}/             |Get practice by id                   |
|GET          |/api/practices/date/{date}       |Get all practices for a given date   |
|PUT          |/api/practices/{id}/             |Update practice by id                |
|DELETE       | /api/practices/{id}/            |Delete practice by id                |
|-------------|---------------------------------|-------------------------------------|
## Use cases and analytics ##

+ **Registration and authorization:** The user registers and logs in.
+ **Creating a habit:** The user creates a new habit, sets the parameters.
+ **Habit Tracking:** The user marks the completion of a habit.
+ **View statistics:** The user sees statistics on the performance of their habits.

## Data movement ##

The user interacts with the Postman → The request is sent to the backend → The backend processes the request and interacts with the database (PostgreSQL) → The result is returned to the Postman and displayed to the user.
