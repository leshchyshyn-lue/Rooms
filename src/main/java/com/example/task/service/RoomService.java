package com.example.task.service;

import com.example.task.exception.InvalidInputException;
import com.example.task.exception.NotFoundException;
import com.example.task.repository.PointRepository;
import com.example.task.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.task.entity.Point;
import com.example.task.entity.Room;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final PointRepository pointRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, PointRepository pointRepository) {
        this.roomRepository = roomRepository;
        this.pointRepository = pointRepository;
    }

    public Room findRoomById(int id) throws NotFoundException {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No room with id: " + id + " was found "));
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        Room savedRoom = new Room();
        savedRoom.setPoints(room.getPoints());
        roomRepository.save(savedRoom);
        for (Point p : room.getPoints()) {
            Point point = new Point();
            point.setRoom(savedRoom);
            point.setY(p.getY());
            point.setX(p.getX());
            pointRepository.save(point);
        }


        return savedRoom;
    }


    public Room updateRoomById(int id, Room newRoom) throws NotFoundException {
        Room room = findRoomById(id);
        pointRepository.removeAllPointsByRoomId(room.getId());
        for (Point p : newRoom.getPoints()) {
            Point point = new Point();
            point.setRoom(room);
            point.setY(p.getY());
            point.setX(p.getX());
            pointRepository.save(point);

        }
        room.setPoints(newRoom.getPoints());
        return roomRepository.save(room);
    }

    public void deleteRoomById(int id) throws NotFoundException {
        findRoomById(id);
        pointRepository.removeAllPointsByRoomId(id);
        roomRepository.deleteById(id);
    }
}
