package com.cg.hbm.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.hbm.dto.BookingDetailsRequestDTO;
import com.cg.hbm.dto.BookingDetailsResponseDTO;

@Service
public interface IBookingDetailsService {
	BookingDetailsResponseDTO createBookingDetails(BookingDetailsRequestDTO bookingDetailsDTO,int userId,int hotelId);
	BookingDetailsResponseDTO updateBookingDetails(BookingDetailsRequestDTO bookingDetailsDTO,int bookingId);
	void deleteBookingDetails(int bookingId);
	BookingDetailsResponseDTO getBookingDetailsById(int bookingId);
	List<BookingDetailsResponseDTO> getAllBookingDetails();
	List<BookingDetailsResponseDTO> getBookingsForUser(int userId);
	List<BookingDetailsResponseDTO> getBookingsForHotel(int hotelId);
	public List<BookingDetailsResponseDTO> getAllBookingDetailsSortedByAmount();
	
	List<BookingDetailsResponseDTO> getAllBookingDetailsBetweenBookedFromAndBookedTo(LocalDate bookedFrom,LocalDate bookedTo);
}
