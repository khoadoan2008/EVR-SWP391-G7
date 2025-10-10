package repository;

import entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByUserUserId(Integer userId);
}
