

CREATE DATABASE sharehouse_event_manager;

CREATE TABLE users(
user_id INT AUTO_INCREMENT PRIMARY KEY,
user_name VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(50) NOT NULL,
display_name VARCHAR(50) NOT NULL
);

CREATE TABLE events(
event_id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(50) NOT NULL,
category VARCHAR(20) NOT NULL,
event_date DATETIME NOT NULL,
location VARCHAR(100) NOT NULL,
capacity INT NOT NULL,
description VARCHAR(250),
user_id INT NOT NULL,
FOREIGN KEY(user_id) REFERENCES users(user_id) 
);


CREATE TABLE participants(
participant_id INT AUTO_INCREMENT PRIMARY KEY,
event_id INT NOT NULL,
user_id INT NOT NULL,
FOREIGN KEY(event_id) REFERENCES events(event_id), 
FOREIGN KEY(user_id) REFERENCES users(user_id),
UNIQUE(event_id,user_id)  
);

INSERT INTO users(user_name,password,display_name)
VALUES
('alice','pass001','Alice'),
('bob','pass002','Bob'),
('charlie','pass003','Charlie'),
('david','pass004','David'),
('emma','pass005','Emma'),
('frank','pass006','Frank'),
('grace','pass007','Grace'),
('henry','pass008','Henry');

INSERT INTO events(title,category,event_date,location,capacity,description,user_id)
VALUES
('BBQ','食べ物','2026-06-21 14:00','屋上',20,'入居者の皆さんと一緒に食事や飲み物を楽しみましょう',1),
('ビーチ','旅行','2026-07-11 09:00','江の島',25,'入居者向けの日帰りビーチツアー',2),
('ボードゲーム','ゲーム','2026-06-28 18:00','ラウンジ',12,'ゲームを楽しんだり、交流したり',3),
('バスケットボール','スポーツ','2026-06-25 12:00','大塚公園',4,'カジュアルなバスケットボールイベント',4),
('勉強会','勉強','2026-06-30 18:00','コワーキングスペース',8,'プログラミング学習イベント',5),
('プールパーティ','遊び','2026-08-25 17:00','屋上',8,'屋上でプールパーティしよう',6);

INSERT INTO participants(event_id,user_id)
VALUES
(1,1),
(1,2),
(1,3),

(2,1),
(2,2),
(2,3),
(2,4),
(2,5),

(3,2),
(3,6),

(4,1),
(4,4),
(4,7),
(4,8),

(5,5);


















UPDATE events SET title = :title, category = :category, event_date = :eventDate, 
location = :location, capacity = :capacity, description = :description
     WHERE event_id = :eventId"