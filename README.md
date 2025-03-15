# Board Game Borrowing System (Group 6)

## Team Members & Roles

| Name                  | Role                                                                      | Effort (Hours) |
| --------------------- | ------------------------------------------------------------------------- | -------------- |
| Panayiotis Saropoulos | UML Class Diagram                                                         | 1              |
| Kevin Jiang           | UML Class Diagram Design Improvement, model classes and JPA annotations (all classes), CRUD implementation for all classes, and tests for all classes               | approximately 35 hours             |
| Hakkim Bekkari        | Persistence Layer, Manage Borrowing Requests, Submit Borrowed Game Review | 3              |
| Artiom Volodin        | Persistence Layer, Create Event                                           | 2              |
| Andres Gonzalez       | Project Management, UML improvement, Testing, Repo Management, Add Game to Collection                          | 18              |
| Logan Ma              | Testing, Register to an Event                                             | 1              |
| Marshall Moussavi     | Report Writing, Testing, Submit Borrowed Game Review                      | 3              |

## Project Overview

This project is a **Board Game Borrowing System** that allows users to browse, borrow, and manage board games. Game owners can list their games for borrowing, manage incoming requests, and schedule game events. Users can also register for events, submit reviews for borrowed games, and interact within the system.

See the project report here: [Project Report](https://docs.google.com/document/d/1hCBOQyv-SNZ_06jHwz9L2WM6tvUJASui5_qTRZZchs8/edit?usp=sharing)

## Core Functionalities:

- **Game Borrowing System:** Users can browse available board games and submit borrowing requests.
- **Game Management:** Game owners can add, edit, and delete games in their collection.
- **Event System:** Users can create, browse, and register for board game events.
- **Review System:** Users can submit reviews for games they have borrowed.
- **Notifications:** Game owners receive notifications about borrowing requests, and users get updates on their request status.

## Deliverables & Task Assignments

| Task                                | Assigned To                                                | Deadline                                       |
| ----------------------------------- | ---------------------------------------------------------- | ---------------------------------------------- |
| UML Class Diagram & Code Generation | Panayiotis, Kevin                                          | Sunday, Feb 9                                  |
| Persistence Layer (JPA, DAO)        | Hakkim, Artiom, Kevin                                      | Wednesday, Feb 12                              |
| Testing Persistence Layer           | Andres, Logan (primary), Marshall, Kevin (secondary)       | Saturday, Feb 15                               |
| Build System Configuration (Maven)  | Andres, Kevin                                              | Monday, Feb 10                                 |
| Repo Management & Issue Tracking    | Andres, Hakkim, Marshall                                   | Issues: Sunday, Feb 9 / Report: Sunday, Feb 16 |

## Running Tests

To correctly run the tests, log as postgres user and create a postgreSQL database with the name **board_game** at port 5432 (CREATE DATABASE board_game). Then, update the application.properties file with your postgres password.

