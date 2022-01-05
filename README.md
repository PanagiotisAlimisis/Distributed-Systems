# Distributed-Systems


## Analysis

### System Architecture

![System Architecture](Analysis/System_architecture.jpg)

### Class Diagram

![Class Diagram](Analysis/Class_Diagram.jpg)

### ER Diagram

![Entity Relationship Diagram](Analysis/Entity_Relationship_Diagram.jpg)

### Use Case Diagram

![Use Case Diagram](Analysis/Use_Case_Diagram.jpg)

### Activity Diagram

![Activity Diagram](Analysis/Activity_Diagram.jpg)

### Component Diagram

![Component Diagram](Analysis/Component_Diagram.jpg)


## Database setup

Run this script to create tables and insert some starting data. To be able to run this script you must have downloaded the project and be in the project's home directory.

Run as admin with:
```
sudo mysql < SQL/create_and_insert_data.sql 
```

OR

run as user with:
```
mysql -u $USER -p < SQL/create_and_insert_data.sql
```


After this you are going to have the database schema created and some data in the tables.

