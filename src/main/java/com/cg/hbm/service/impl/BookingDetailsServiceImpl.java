package com.cg.hbm.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.hbm.dto.BookingDetailsRequestDTO;
import com.cg.hbm.dto.BookingDetailsResponseDTO;
import com.cg.hbm.entity.BookingDetails;
import com.cg.hbm.entity.Hotel;
import com.cg.hbm.entity.User;
import com.cg.hbm.exception.ResourceNotFoundException;
import com.cg.hbm.repository.IBookingDetailsRepository;
import com.cg.hbm.repository.IHotelRepository;
import com.cg.hbm.repository.IUserRepository;
import com.cg.hbm.service.IBookingDetailsService;

@Service
public class BookingDetailsServiceImpl implements IBookingDetailsService {
	
	@Autowired
	IBookingDetailsRepository bookingDetailsRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IHotelRepository hotelRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public BookingDetailsResponseDTO createBookingDetails(BookingDetailsRequestDTO bookingDetailsDTO,int userId,int hotelId) {
		User user=userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
		
		Hotel hotel=hotelRepository.findById(hotelId)
				.orElseThrow(()->new ResourceNotFoundException("Hotel","hotelId",hotelId));
		
		BookingDetails bookingDetails=modelMapper.map(bookingDetailsDTO, BookingDetails.class);

		bookingDetails.setUser(user);
		bookingDetails.setHotel(hotel);
		bookingDetails.setPayment(bookingDetailsDTO.getPayment());
		
		BookingDetails savedBookingDetails=bookingDetailsRepository.save(bookingDetails);
		return modelMapper.map(savedBookingDetails, BookingDetailsResponseDTO.class);
	}

	@Override
	public BookingDetailsResponseDTO updateBookingDetails(BookingDetailsRequestDTO bookingDetailsDTO, int bookingId) {
		BookingDetails bookingDetails=bookingDetailsRepository.findById(bookingId)
				.orElseThrow(()->new ResourceNotFoundException("BookingDetails","bookingId",bookingId));
		
		bookingDetails.setBookedFrom(bookingDetailsDTO.getBookedFrom());
		bookingDetails.setBookedTo(bookingDetailsDTO.getBookedTo());
		bookingDetails.setNoOfAdults(bookingDetailsDTO.getNoOfAdults());
		bookingDetails.setNoOfChildren(bookingDetailsDTO.getNoOfChildren());
		bookingDetails.setAmount(bookingDetailsDTO.getAmount());
		bookingDetails.setPayment(bookingDetailsDTO.getPayment());
		
		BookingDetails savedBookingDetails=bookingDetailsRepository.save(bookingDetails);
		return modelMapper.map(savedBookingDetails, BookingDetailsResponseDTO.class);
	}

	@Override
	public void deleteBookingDetails(int bookingId) {
		BookingDetails bookingDetails=bookingDetailsRepository.findById(bookingId)
				.orElseThrow(()->new ResourceNotFoundException("BookingDetails","bookingId",bookingId));
		bookingDetailsRepository.delete(bookingDetails);
	}

	@Override
	public BookingDetailsResponseDTO getBookingDetailsById(int bookingId) {
		BookingDetails bookingDetails=bookingDetailsRepository.findById(bookingId)
				.orElseThrow(()->new ResourceNotFoundException("BookingDetails","bookingId",bookingId));
		return modelMapper.map(bookingDetails, BookingDetailsResponseDTO.class);
	}

	@Override
	public List<BookingDetailsResponseDTO> getAllBookingDetails() {
		List<BookingDetails> allbookingDetails=bookingDetailsRepository.findAll();
		return allbookingDetails.stream()
				.map(bookingDetails->modelMapper.map(bookingDetails, BookingDetailsResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookingDetailsResponseDTO> getBookingsForUser(int userId) {
		 User user = userRepository.findById(userId)
		            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		    List<BookingDetails> userBookings = bookingDetailsRepository.findByUser(user);
		    return userBookings.stream()
		            .map(booking -> modelMapper.map(booking, BookingDetailsResponseDTO.class))
		            .collect(Collectors.toList());
	}
	
	@Override
	public List<BookingDetailsResponseDTO> getBookingsForHotel(int hotelId) {
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
	    List<BookingDetails> hotelBookings = bookingDetailsRepository.findByHotel(hotel);
	    return hotelBookings.stream()
	            .map(booking -> modelMapper.map(booking, BookingDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public List<BookingDetailsResponseDTO> getAllBookingDetailsSortedByAmount() {
		List<BookingDetails> allBookingDetails = bookingDetailsRepository.findAll(Sort.by(Sort.Direction.ASC, "amount"));
	    return allBookingDetails.stream()
	            .map(booking -> modelMapper.map(booking, BookingDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public List<BookingDetailsResponseDTO> getAllBookingDetailsBetweenBookedFromAndBookedTo(LocalDate bookedFrom,
			LocalDate bookedTo) {
		List<BookingDetails> allBookingDetails=bookingDetailsRepository.findAll();
		return allBookingDetails.stream()
				.filter(booking->(booking.getBookedFrom().isAfter(bookedFrom.minusDays(1)) && booking.getBookedTo().isBefore(bookedTo.plusDays(1))))
				.map(booking->this.modelMapper.map(booking, BookingDetailsResponseDTO.class))
				.collect(Collectors.toList());
	}



}
