package com.cg.hbm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.hbm.entity.BookingDetails;
import com.cg.hbm.entity.Hotel;
import com.cg.hbm.entity.User;

@Repository
public interface IBookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {
	List<BookingDetails> findByUser(User user);
	List<BookingDetails> findByHotel(Hotel hotel);
}
