package com.example.task.util;

import com.example.task.exception.InvalidInputException;
import org.springframework.stereotype.Component;
import com.example.task.entity.Room;

@Component
public class RoomValidator {

    public Room check(Room room) throws InvalidInputException {
        String message = "Invalid input";
        int length = room.getPoints().size();
        if (length < 4) {
            throw new InvalidInputException(message);
        }


        int[] arrY = new int[length];
        int[] arrX = new int[length];
        for (int i = 0; i < length; i++) {
            arrY[i] = room.getPoints().get(i).getY();
            arrX[i] = room.getPoints().get(i).getX();
            if (arrY[i] > 300 || arrX[i] > 300) {
                throw new InvalidInputException(message);
            }
        }
        if (arrY[0] != arrX[length - 1]) {
            throw new InvalidInputException(message);
        }
        for (int i = 0; i < length - 1; i++) {
            if (arrY[i + 1] != arrX[i] || arrX[i + 1] == arrY[i]) {
                throw new InvalidInputException(message);
            }
        }
        return room;
        // DOCKER
        // можливо розширення кімнати просто в циклі(Якшо менше чим то шо видно + шось там)
    }
}
