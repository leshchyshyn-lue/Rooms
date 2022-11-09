package room.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import room.task.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
