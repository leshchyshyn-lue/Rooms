package room.task.util;

import org.springframework.stereotype.Component;
import room.task.entity.Room;
import room.task.exception.InvalidInputException;

@Component
public class RoomValidator {

    public Room check(Room room) throws InvalidInputException {
        int length = room.getPoints().size();
        int[] arrY = new int[length];
        int[] arrX = new int[length];

        for (int i = 0; i < length; i++) {
            arrY[i] = room.getPoints().get(i).getY();
            arrX[i] = room.getPoints().get(i).getX();
        }
        if (arrY[0] != arrX[length - 1]) {
            throw new InvalidInputException("Invalid input");
        }
        for (int i = 0; i < length - 1; i++) {
            if (arrY[i + 1] != arrX[i] || arrX[i + 1] == arrY[i]) {
                throw new InvalidInputException("Invalid input");
            }
        }
        return room;
    }
}
