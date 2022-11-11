package com.example.task.controller;

import com.example.task.exception.NotFoundException;
import com.example.task.request.RoomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.task.entity.Point;
import com.example.task.entity.Room;
import com.example.task.service.RoomService;
import com.example.task.util.RoomValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final RoomValidator roomValidator;

    @Autowired
    public RoomController(RoomService roomService, RoomValidator roomValidator) {
        this.roomService = roomService;
        this.roomValidator = roomValidator;
    }

    public Room convertToRoom(RoomRequest oldRequest) {
        String[] request = oldRequest.toString().substring(1).split(",\\(");
        List<Point> points = new ArrayList<>();
        for (String r : request) {
            int y = Integer.parseInt(r.substring(0, r.indexOf(',')).trim());
            int x = Integer.parseInt(r.substring(r.indexOf(',') + 1, r.length() - 1).trim());
            Point point = new Point();
            point.setY(y);
            point.setX(x);

            points.add(point);
        }
        Room room = new Room();
        room.setPoints(points);
        return room;
    }


    @GetMapping
    public String findAllRooms(Model model) {
        List<Room> rooms = roomService.findAllRooms();
        model.addAttribute("rooms", rooms);
        return "list-rooms";
    }

    @PostMapping
    public String createRoom(@ModelAttribute("request") RoomRequest request, Map<String, Object> model) {
        try {
            roomService.createRoom(roomValidator.check(convertToRoom(request)));
        } catch (Exception e) {
            List<Room> room = roomService.findAllRooms();
            model.put("message", "Invalid input");
            model.put("rooms", room);
            return "list-rooms";
        }
        return "redirect:/rooms";
    }


    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") int id, Model model) throws NotFoundException {
        model.addAttribute("com", roomService.findRoomById(id));
        return "update-room";
    }

    @Transactional
    @PostMapping("/updated/{id}")
    public String updateRoom(@ModelAttribute("req") RoomRequest req,
                             @PathVariable("id") int id, Map<String, Object> model) throws NotFoundException {

        try {
            roomService.updateRoomById(id, roomValidator.check(convertToRoom(req)));
        } catch (Exception e) {

            e.printStackTrace();
            Room room = roomService.findRoomById(id);
            model.put("message", "Invalid input");
            model.put("com", room);
            return "update-room";
        }
        return "redirect:/rooms";
    }

    @Transactional
    @GetMapping("/room-delete/{id}")
    public String deleteRoomById(@PathVariable("id") int id) throws NotFoundException {
        roomService.deleteRoomById(id);
        return "redirect:/rooms";
    }

}
