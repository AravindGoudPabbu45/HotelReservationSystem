-- Hotel Reservation System - Oracle Database Schema

CREATE TABLE rooms (
    room_id NUMBER PRIMARY KEY,
    room_type VARCHAR2(20),
    price_per_night NUMBER(10,2)
);

CREATE TABLE guests (
    guest_id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    contact_no VARCHAR2(15)
);

CREATE TABLE reservations (
    reservation_id NUMBER PRIMARY KEY,
    guest_id NUMBER REFERENCES guests(guest_id),
    room_id NUMBER REFERENCES rooms(room_id),
    check_in_date DATE,
    check_out_date DATE
);
