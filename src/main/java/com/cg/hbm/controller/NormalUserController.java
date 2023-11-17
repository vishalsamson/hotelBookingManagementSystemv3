package com.cg.hbm.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hbm.dto.BookingDetailsRequestDTO;
import com.cg.hbm.dto.BookingDetailsResponseDTO;
import com.cg.hbm.dto.HotelResponseDTO;
import com.cg.hbm.dto.ReviewDTO;
import com.cg.hbm.dto.ReviewRequestDTO;
import com.cg.hbm.dto.ReviewResponseDTO;
import com.cg.hbm.dto.RoomDetailsResponseDTO;
import com.cg.hbm.dto.UserRequestDTO;
import com.cg.hbm.dto.UserResponseDTO;
import com.cg.hbm.payload.ApiResponse;
import com.cg.hbm.service.IBookingDetailsService;
import com.cg.hbm.service.IHotelService;
import com.cg.hbm.service.IReviewService;
import com.cg.hbm.service.IRoomDetailsService;
import com.cg.hbm.service.IUserService;

@RestController
@RequestMapping("/normaluser")
public class NormalUserController {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IBookingDetailsService bookingDetailsService;
	
	@Autowired
	IHotelService hotelService;
	
	@Autowired
	IReviewService reveiwService;
	
	@Autowired
	IRoomDetailsService roomDetailsService;
	
	@Autowired
	IUserService userService;
	
	
	@PostMapping("/bookings/user/{userId}/hotel/{hotelId}")
	public ResponseEntity<BookingDetailsResponseDTO> createBookingDetails(
			@Valid @RequestBody BookingDetailsRequestDTO bookingDetailsDTO, @PathVariable int userId,
			@PathVariable int hotelId) {
		BookingDetailsResponseDTO bookingDto = bookingDetailsService.createBookingDetails(bookingDetailsDTO, userId,
				hotelId);
		logger.info("Booking details created successfully for user {} and hotel {}", userId, hotelId);
		return new ResponseEntity<BookingDetailsResponseDTO>(bookingDto, HttpStatus.CREATED);

	}
	
	@PutMapping("/bookings/{bookingId}")
	public ResponseEntity<BookingDetailsResponseDTO> updateBookingDetails(
			@Valid @RequestBody BookingDetailsRequestDTO bookingDetailsDTO, @PathVariable int bookingId) {
		logger.info("Updating booking details for bookingId: {}", bookingId);
		BookingDetailsResponseDTO bookingDto = bookingDetailsService.updateBookingDetails(bookingDetailsDTO, bookingId);
		logger.info("Booking details updated successfully for bookingId: {}", bookingId);
		return new ResponseEntity<BookingDetailsResponseDTO>(bookingDto, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/bookings/{bookingId}")
	public ResponseEntity<BookingDetailsResponseDTO> getBookingDetailsById(@PathVariable int bookingId) {
		logger.info("Attempting to retrieve booking details for bookingId: {}", bookingId);
		BookingDetailsResponseDTO bookingDetailsDTO = bookingDetailsService.getBookingDetailsById(bookingId);
		logger.info("Successfully retrieved booking details for bookingId: {}", bookingId);
		return new ResponseEntity<BookingDetailsResponseDTO>(bookingDetailsDTO, HttpStatus.OK);
	}
	
	@GetMapping("/bookings/user/{userId}")
	public ResponseEntity<List<BookingDetailsResponseDTO>> getBookingsForUser(@PathVariable int userId) {
		logger.info("Fetching bookings for user with ID: {}", userId);
		List<BookingDetailsResponseDTO> allBookingDetailsDTOs = bookingDetailsService.getBookingsForUser(userId);
		logger.info("Successfully retrieved {} bookings for user with ID: {}", allBookingDetailsDTOs.size(), userId);
		return new ResponseEntity<List<BookingDetailsResponseDTO>>(allBookingDetailsDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable int hotelId){
		logger.info("Fetching hotel with ID: {}", hotelId);
		HotelResponseDTO savedHotelDTO=hotelService.getHotelById(hotelId);
		logger.info("Hotel found with ID {}: {}", hotelId, savedHotelDTO);
		return new ResponseEntity<HotelResponseDTO>(savedHotelDTO,HttpStatus.OK);
	}
	
	@GetMapping("/hotels/searchbycity/{city}")
	public ResponseEntity<List<HotelResponseDTO>> searchHotelsByCity(@PathVariable String city){
		logger.info("Searching hotels by city: {}", city);
		List<HotelResponseDTO> allHotels=hotelService.searchHotelsByCity(city);
		logger.info("Found {} hotels for city: {}", allHotels.size(), city);
		return new ResponseEntity<List<HotelResponseDTO>>(allHotels,HttpStatus.OK);
	}
	
	@GetMapping("/hotels/averagerate")
	public ResponseEntity<List<HotelResponseDTO>> getHotelsByAverageRate(@RequestParam double minRate,@RequestParam  double maxRate){
		logger.info("Received request to get hotels by average rate. Min Rate: {}, Max Rate: {}", minRate, maxRate);
		List<HotelResponseDTO> allHotels=hotelService.getHotelsByAverageRate(minRate,maxRate);
		logger.info("Found {} hotels within the specified range", allHotels.size());
		return new ResponseEntity<List<HotelResponseDTO>>(allHotels,HttpStatus.OK);
	}
	

	@PostMapping("/reviews/new")
	public ResponseEntity<ReviewResponseDTO> createReview(@Valid @RequestBody ReviewRequestDTO reviewDto,@RequestParam int userId,@RequestParam int hotelId){
		logger.info("Received request to create a new review for hotelId {} by userId {}", hotelId, userId);
		ReviewResponseDTO savedReviewDto=reveiwService.createReview(reviewDto, userId, hotelId);
		logger.info("Review created successfully. Review ID: {}", savedReviewDto.getId());
		return new ResponseEntity<ReviewResponseDTO>(savedReviewDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/reviews/update/{reviewId}")
	ResponseEntity<ReviewResponseDTO> updateReview(@Valid @RequestBody ReviewDTO reviewDto,@PathVariable int reviewId){
		logger.info("Updating review with ID: {}", reviewId);
		ReviewResponseDTO savedReviewDto=reveiwService.updateReview(reviewDto,reviewId);
		logger.info("Review with ID {} updated successfully", reviewId);
		return new ResponseEntity<ReviewResponseDTO>(savedReviewDto,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/reviews/delete/{reviewId}")
	public ResponseEntity<ApiResponse> deleteReview(@PathVariable int reviewId){
		reveiwService.deleteReview(reviewId);
		logger.info("Review with ID {} deleted successfully", reviewId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable int reviewId){
		logger.info("Request received for getReviewById with reviewId: {}", reviewId);
		ReviewResponseDTO savedReviewDto=reveiwService.getReviewById(reviewId);
		logger.info("Successfully retrieved review with ID: {}", reviewId);
		return new ResponseEntity<ReviewResponseDTO>(savedReviewDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/reviews/")
	public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(){
		logger.info("Fetching all reviews");
		List<ReviewResponseDTO> savedReviews=reveiwService.getAllReviews();
		logger.info("Retrieved {} reviews successfully", savedReviews.size());
		return new ResponseEntity<List<ReviewResponseDTO>>(savedReviews,HttpStatus.OK);
	}
	
	@GetMapping("/reviews/hotel/{hotelId}")
	public ResponseEntity<List<ReviewResponseDTO>> getReviewsForHotel(@PathVariable int hotelId){
		List<ReviewResponseDTO> savedReviews=reveiwService.getReviewsForHotel(hotelId);
		return new ResponseEntity<List<ReviewResponseDTO>>(savedReviews,HttpStatus.OK);
	}
	
	@GetMapping("/reviews/rating/{hotelId}")
	public ResponseEntity<Double> getAverageRatingForHotel(@PathVariable int hotelId){
		double averageRating=reveiwService.getAverageRatingForHotel(hotelId);
		logger.info("Successfully retrieved reviews for hotel with ID: {}", hotelId);
		return new ResponseEntity<Double>(averageRating,HttpStatus.OK);
	}
	
	@GetMapping("/roomdetails/")
	public ResponseEntity<List<RoomDetailsResponseDTO>> getAllRoomDetails() {
		List<RoomDetailsResponseDTO> allRoomDetailsDTO = roomDetailsService.getAllRoomDetails();
		logger.info("Successfully retrieved all room details");
		return new ResponseEntity<List<RoomDetailsResponseDTO>>(allRoomDetailsDTO, HttpStatus.OK);
	}
	
	@GetMapping("/roomdetails/roomsinhotel/{hotelId}")
	public ResponseEntity<List<RoomDetailsResponseDTO>> getRoomsInHotel(@PathVariable int hotelId) {
		logger.debug("Fetching room details for hotelId: {}", hotelId);
		List<RoomDetailsResponseDTO> allRoomDetailsDTO = roomDetailsService.getRoomsInHotel(hotelId);
		logger.debug("Retrieved room details for hotelId: {}", hotelId);
		return new ResponseEntity<List<RoomDetailsResponseDTO>>(allRoomDetailsDTO, HttpStatus.OK);
	}

	@GetMapping("/roomdetails/availablerooms/{hotelId}")
	public ResponseEntity<List<RoomDetailsResponseDTO>> getAvailableRoomsInHotel(@PathVariable int hotelId) {
		logger.info("Fetching available rooms for hotelId: {}", hotelId);
		List<RoomDetailsResponseDTO> allRoomDetailsDTO = roomDetailsService.getAvailableRoomsInHotel(hotelId);
		logger.info("Successfully retrieved {} available rooms for hotelId: {}", allRoomDetailsDTO.size(), hotelId);
		return new ResponseEntity<List<RoomDetailsResponseDTO>>(allRoomDetailsDTO, HttpStatus.OK);
	}

	@GetMapping("/roomdetails/roomtype/{hotelId}")
	public ResponseEntity<List<RoomDetailsResponseDTO>> getRoomsByTypeInHotel(@PathVariable int hotelId,
			@RequestParam String roomType) {
		List<RoomDetailsResponseDTO> allRoomDetailsDTO = roomDetailsService.getRoomsByTypeInHotel(hotelId, roomType);
		logger.info("Successfully retrieved room details for hotelId: {} and roomType: {}", hotelId, roomType);
		return new ResponseEntity<List<RoomDetailsResponseDTO>>(allRoomDetailsDTO, HttpStatus.OK);
	}

	@GetMapping("/roomdetails/availableroomsbytype")
	public ResponseEntity<List<RoomDetailsResponseDTO>> getRoomsByAvailabilityAndType(@RequestParam boolean isAvailable,
			@RequestParam String roomType) {
		logger.info("Received request for rooms with availability={} and type={}", isAvailable, roomType);
		List<RoomDetailsResponseDTO> allRoomDetailsDTO = roomDetailsService.getRoomsByAvailabilityAndType(isAvailable,
				roomType);
		logger.info("Returning {} room details for availability={} and type={}", allRoomDetailsDTO.size(), isAvailable, roomType);
		return new ResponseEntity<List<RoomDetailsResponseDTO>>(allRoomDetailsDTO, HttpStatus.OK);
	}

	@GetMapping("/roomdetails/roomsbyprice")
	public ResponseEntity<List<RoomDetailsResponseDTO>> getRoomsByPriceRange(@RequestParam double minRate,
			@RequestParam double maxRate) {
		logger.info("Received request to get rooms by price range. Min rate: {}, Max rate: {}", minRate, maxRate);
		List<RoomDetailsResponseDTO> allRoomDetailsDTO = roomDetailsService.getRoomsByPriceRange(minRate, maxRate);
		logger.info("Returning room details: {}", allRoomDetailsDTO);
		return new ResponseEntity<List<RoomDetailsResponseDTO>>(allRoomDetailsDTO, HttpStatus.OK);
	}
	
	@PostMapping("/users/")
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userDto) {
		logger.info("Received request to create a new user: {}", userDto);
		UserResponseDTO user = userService.addUser(userDto);
		logger.info("User created successfully: {}", user);
		return new ResponseEntity<UserResponseDTO>(user, HttpStatus.CREATED);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO userDto, @PathVariable int id) {
		logger.info("Received request to update user with ID {}: {}", id, userDto);
		UserResponseDTO updatedUser = userService.updateUser(userDto, id);
		logger.info("User updated successfully: {}", updatedUser);
		return new ResponseEntity<UserResponseDTO>(updatedUser, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
		logger.info("Received request to get user by ID: {}", id);
		UserResponseDTO userDto = userService.getUserById(id);
		logger.info("Returning user details: {}", userDto);
		return new ResponseEntity<UserResponseDTO>(userDto, HttpStatus.OK);
	}

}
