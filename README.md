A real time , multi client chat room application built in java. Includes a Javafx desktop gui made using Scenebuilder, a custom TCP socket (server/client architecture), 
and a MySQL database for accounts and message logging.
sensitive information (passwords) are hashed using dbCrypt before stored into the database. 

Features
Real time messaging - multiple clients can connect and chat simultaneously over TCP sockets.

User authentication - Users can create an account and log in

secure password storage - passwords are hashed using jBCrypt

chat history - every message gets logged and stored

multi threaded server - handles concurrent client connections


Tech stack
UI - JavaFX + FXML (built with scenebuilder)

Networking - Java Sockets (TCP)

Password security - jBCrypt

Language - Java 21




Prerequisites

Before you start, make sure you have:

JDK 21 installed and make sure JAVA_HOME points to your JDK 21 (for Maven) and
Docker Desktop is installed and running

Getting started

1. Clone the repo
git clone https://github.com/AdamSan43/Chat-.git cd Chat-
(Make sure you navigate to the correct directory in the terminal where you cloned the repo into

2. Set up .env, (for easy setup, simply rename the  .env.example to .env
  
4. Start Docker desktop

5. docker compose up -d
   (starts MySQL set up, creating the users and messages tables)
   if you encounter that the port is already in use, change the DB_PORT in your .env to be a different port
 
6. (optional)-  docker exec -it chat-mysql mysql -u root -p -e "USE chatdb; SHOW TABLES;"
enter the password in your .env and confirm the tables exist

7.  ./mvnw compile org.codehaus.mojo:exec-maven-plugin:3.1.0:java "-Dexec.mainClass=chattingapp.Server.ChatServer" 
this runs the server, keep this terminal open

8. On your second terminal (navigate to the correct directory in your terminal where you downloaded the application first) then run./mvnw javafx:run 



