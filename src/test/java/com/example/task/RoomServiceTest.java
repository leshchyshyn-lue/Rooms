package com.example.task;


import com.example.task.exception.InvalidInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.task.entity.Point;
import com.example.task.entity.Room;
import com.example.task.exception.NotFoundException;
import com.example.task.repository.PointRepository;
import com.example.task.repository.RoomRepository;
import com.example.task.service.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

    private final static int ROOM_ID = 1;

    private final static int POINT_Y = 2;
    private final static int POINT_X = 3;

    private final static int POINT_ID = 4;


    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private PointRepository pointRepository;

    @Test
    public void testFindRoomByIdSuccess() throws NotFoundException {
        Room room = new Room();
        room.setId(ROOM_ID);

        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        Room result = roomService.findRoomById(room.getId());
        assertEquals(room, result);
    }

    @Test
    public void testFindRoomByIdFailNotFoundException() {
        Room room = new Room();
        room.setId(ROOM_ID);

        when(roomRepository.findById(room.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> roomService.findRoomById(room.getId()));
    }

    @Test
    public void testFindAllRoomsSuccess() {
        Room room = new Room();
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> result = roomService.findAllRooms();

        assertEquals(rooms, result);
    }


    @Test
    public void testCreateRoomSuccess() {
        List<Point> points = new ArrayList<>();

        Point point = new Point();
        point.setY(POINT_Y);
        point.setX(POINT_X);
        point.setId(POINT_ID);

        Room oldRoom = new Room();
        oldRoom.setPoints(points);

        Room newRoom = new Room();

        point.setRoom(newRoom);
        points.add(point);

        newRoom.setId(ROOM_ID);
        newRoom.setPoints(points);

       // when(roomRepository.save(oldRoom)).thenReturn(newRoom);
        Room roomResult = roomService.createRoom(oldRoom);
       // when(pointRepository.save(point)).thenReturn(point);

        assertEquals(roomResult.getPoints(), newRoom.getPoints());

    }

    @Test
    public void testUpdateRoomByIdSuccess() throws NotFoundException {
        List<Point> oldPoints = new ArrayList<>();
        List<Point> newPoints = new ArrayList<>();

        Point point = new Point();
        point.setY(POINT_Y);
        point.setX(POINT_X);
        point.setId(POINT_ID);

        Room oldRoom = new Room();

        point.setRoom(oldRoom);
        oldPoints.add(point);

        oldRoom.setId(ROOM_ID);
        oldRoom.setPoints(oldPoints);

        Room newRoom = new Room();

        point.setRoom(newRoom);
        newPoints.add(point);

        newRoom.setId(oldRoom.getId());
        newRoom.setPoints(newPoints);



        when(roomRepository.findById(oldRoom.getId())).thenReturn(Optional.of(oldRoom));

        doNothing().when(pointRepository).removeAllPointsByRoomId(oldRoom.getId());


        when(roomRepository.save(oldRoom)).thenReturn(newRoom);
        Room result = roomService.updateRoomById(oldRoom.getId(), newRoom);

        assertEquals(result, newRoom);
        assertEquals(result.getPoints(), newRoom.getPoints());
    }



    @Test
    public void testDeleteRoomByIdFailNotFoundException(){
        Room room = new Room();
        room.setId(ROOM_ID);

        assertThrows(NotFoundException.class, () -> roomService.deleteRoomById(room.getId()));

    }




}
