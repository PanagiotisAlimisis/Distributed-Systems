# Distributed-Systems


See live at: https://back-ds.herokuapp.com/freetransportation. Login with email: admin@mail.com & password: 123.


## Project local setup

```
git clone git@github.com:PanagiotisAlimisis/Distributed-Systems.git
cd Distributed-Systems
mysql -u $USER -p < SQL/create_and_insert_data.sql
cd Backend
chmod u+x start_server.sh
./start_server.sh

```

## Database setup

The previous code block setups the database at step 4. But if you want to run it separately you can run one of these two commands to create tables and insert some starting data. To be able to run this script you must have downloaded the project and be in the project's home directory.

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

