package com.example.task.entity;

import javax.persistence.*;

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "y")
    private int y;
    @Column(name = "x")
    private int x;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Point() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String toString() {
        return "(" + y + "," + x + ")";
    }

}
