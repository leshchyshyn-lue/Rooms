package room.task.request;


public class RoomRequest {

    public String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public RoomRequest() {

    }

    public RoomRequest(String string) {
        this.string = string;
    }


    @Override
    public String toString() {
        return string;
    }
}
