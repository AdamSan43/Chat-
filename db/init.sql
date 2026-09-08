-- Schema for the Global Chat Room app
-- Matches the queries used in chattingapp.database.HandleDatabase

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL, -- bcrypt hashes are always 60 chars
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    content TEXT NOT NULL,
    time VARCHAR(25) NOT NULL -- stored as formatted string ("yyyy-MM-dd HH:mm:ss") in MainScreenController
);
