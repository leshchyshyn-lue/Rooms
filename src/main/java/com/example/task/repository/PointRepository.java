package com.example.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.task.entity.Point;


@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    void removeAllPointsByRoomId(int id);
}
