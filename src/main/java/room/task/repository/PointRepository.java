package room.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import room.task.entity.Point;


@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    void removeAllPointsByRoomId(int id);
}
