-- File by Jake O'Reilly

-- Drop check and create database
drop database if exists instrumentShopDB;
create database instrumentShopDB;
use instrumentShopDB;

-- Make instrument table
create table Instruments (
    id int auto_increment primary key,
    name varchar(255),
    price float,
    type varchar(6) -- GUITAR || PIANO || DRUM || WIND
);

-- Populate the instrument table
INSERT INTO Instruments (name, price, type) VALUES
('Fender Vintera 50s Telecaster MN, Sonic Blue', 894.00, 'GUITAR'), -- 08/03/2024: https://www.gear4music.ie/Guitar-and-Bass/Fender-Vintera-50s-Telecaster-MN-Sonic-Blue/2ZSC
('Epiphone Les Paul Muse, Purple Passion Metallic', 468.00, 'GUITAR'), -- 08/03/2024: https://www.gear4music.ie/Guitar-and-Bass/Epiphone-Les-Paul-Muse-Purple-Passion-Metallic/32SL
('Ibanez FRH10N-RGF, Rose Gold Metallic Flat', 563.00, 'GUITAR'), -- 08/03/2024: https://www.gear4music.ie/Guitar-and-Bass/Ibanez-FRH10N-RGF-Rose-Gold-Metallic-Flat/65YH
('Yamaha Stage Custom Hip 20" 4pc Shell Pack, Matte Surf Green', 754.00, 'DRUM'), -- 08/03/2024: https://www.gear4music.ie/Drums-and-Percussion/Yamaha-Stage-Custom-Hip-20-4pc-Shell-Pack-Matte-Surf-Green/3AUE
('Roland A-49 MIDI Controller Keyboard, White', 154.75, 'PIANO'), -- 08/03/2024: https://www.gear4music.ie/Keyboards-and-Pianos/Roland-A-49-MIDI-Controller-Keyboard-White/MZL
('Stagg 12 Key Rainbow Xylophone, With Mallets', 48.00, 'DRUM'), -- 08/03/2024: https://www.gear4music.ie/Drums-and-Percussion/Stagg-12-Key-Rainbow-Xylophone-With-Mallets/37F3
('Odyssey OBE1200 Premiere Junior Oboe', 839.00, 'WIND'), -- 08/03/2024: https://www.gear4music.ie/Woodwind-Brass-Strings/Odyssey-OBE1200-Premiere-Junior-Oboe/W18
('Casio SA 50 Mini Portable Keyboard', 61.20, 'PIANO'), -- 08/03/2024: https://www.gear4music.ie/Keyboards-and-Pianos/Casio-SA-50-Mini-Portable-Keyboard/510J
('Fender Blues Deluxe Harmonica, A', 13.95, 'WIND'), -- 08/03/2024: https://www.gear4music.ie/Woodwind-Brass-Strings/Fender-Blues-Deluxe-Harmonica-A/XKK
('Schecter J-4 Bass, Sea Foam Green', 1134.00, 'GUITAR'); -- 08/03/2024: https://www.gear4music.ie/Guitar-and-Bass/Schecter-J-4-Bass-Sea-Foam-Green/2QQH
