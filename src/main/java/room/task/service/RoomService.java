package room.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import room.task.entity.Point;
import room.task.entity.Room;
import room.task.exception.InvalidInputException;
import room.task.exception.NotFoundException;
import room.task.repository.PointRepository;
import room.task.repository.RoomRepository;

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
        List<Room> rooms = roomRepository.findAll();
        return rooms;
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


    public Room updateRoomById(int id, Room newRoom) throws NotFoundException, InvalidInputException {
        if (newRoom.getPoints().size() < 4) {
            throw new InvalidInputException("Invalid input");
        }
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
        roomRepository.deleteById(id);
    }
}
