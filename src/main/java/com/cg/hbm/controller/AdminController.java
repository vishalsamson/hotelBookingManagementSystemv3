package com.cg.hbm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.cg.hbm.dto.BookingDetailsResponseDTO;
import com.cg.hbm.dto.HotelRequestDTO;
import com.cg.hbm.dto.HotelResponseDTO;
import com.cg.hbm.dto.RoomDetailsRequestDTO;
import com.cg.hbm.dto.RoomDetailsResponseDTO;
import com.cg.hbm.dto.UserResponseDTO;
import com.cg.hbm.payload.ApiResponse;
import com.cg.hbm.repository.IRoomDetailsRepository;
import com.cg.hbm.service.IBookingDetailsService;
import com.cg.hbm.service.IFileService;
import com.cg.hbm.service.IHotelService;
import com.cg.hbm.service.IRoomDetailsService;
import com.cg.hbm.service.IUserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	IUserService userService;

	@Autowired
	IHotelService hotelService;

	@Autowired
	IBookingDetailsService bookingDetailsService;
	
	@Autowired
	IRoomDetailsService roomDetailsService;
	
	@Autowired
	IFileService fileService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Value("${project.image}")
	private String path;

	// admin

	@GetMapping("/users/")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		logger.info("Fetching all users");
		List<UserResponseDTO> savedUsers = userService.getAllUsers();
		logger.info("Retrieved {} users", savedUsers.size());
		return ResponseEntity.ok(savedUsers);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
		userService.removeUser(userId);
		logger.info("User with ID {} deleted successfully", userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfully", true), HttpStatus.OK);
	}

	// admin
	@PostMapping("/hotels/")
	public ResponseEntity<HotelResponseDTO> createHotel(@Valid @RequestBody HotelRequestDTO hotelDto) {
		logger.info("Received request to create a new hotel with data: {}", hotelDto);
		HotelResponseDTO savedHotelDto = hotelService.createHotel(hotelDto);
		logger.info("Hotel created successfully. ID: {}", savedHotelDto.getHotelId());
		return new ResponseEntity<HotelResponseDTO>(savedHotelDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/hotels/{hotelId}")
	public ResponseEntity<HotelResponseDTO> updateHotel(@Valid @RequestBody HotelRequestDTO hotelDto,
			@PathVariable int hotelId) {
		HotelResponseDTO savedHotelDto = hotelService.updateHotel(hotelDto, hotelId);
		logger.info("Hotel with ID {} updated successfully", hotelId);
		return new ResponseEntity<HotelResponseDTO>(savedHotelDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/hotels/{hotelId}")
	ResponseEntity<ApiResponse> deleteHotel(@PathVariable int hotelId) {
		hotelService.deleteHotel(hotelId);
		logger.info("Hotel with ID {} deleted successfully", hotelId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully", true), HttpStatus.OK);
	}

	// admin
	@GetMapping("/hotels/")
	public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {
		logger.info("Request received to fetch all hotels.");
		List<HotelResponseDTO> allHotels = hotelService.getAllHotels();
		logger.info("Returning {} hotels in the response.", allHotels.size());
		return new ResponseEntity<List<HotelResponseDTO>>(allHotels, HttpStatus.OK);
	}
	
	@GetMapping("hotels/booked")
	public ResponseEntity<List<HotelResponseDTO>> getAllBookedHotels(){
		logger.info("Request received to get all booked hotels.");
		List<HotelResponseDTO> allBookedHotels=hotelService.getAllBookedHotels();
		logger.info("Successfully retrieved all booked hotels.");
		return new ResponseEntity<List<HotelResponseDTO>>(allBookedHotels,HttpStatus.OK);
	}
	
	@GetMapping("hotels/nonbooked")
	public ResponseEntity<List<HotelResponseDTO>> getAllNonBookedHotels(){
		logger.info("Request received to get all non booked hotels.");
		List<HotelResponseDTO> allNonBookedHotels=hotelService.getAllNonBookedHotels();
		logger.info("Successfully retrieved all non booked hotels.");
		return new ResponseEntity<List<HotelResponseDTO>>(allNonBookedHotels,HttpStatus.OK);
	}
	
	@DeleteMapping("/bookings/{bookingId}")
	public ResponseEntity<ApiResponse> deleteBookingDetails(@PathVariable int bookingId) {
		bookingDetailsService.deleteBookingDetails(bookingId);
		logger.info("Booking details with ID {} deleted successfully.", bookingId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/bookings/")
	public ResponseEntity<List<BookingDetailsResponseDTO>> getAllBookingDetails() {
		List<BookingDetailsResponseDTO> allBookingDetailsDTOs = bookingDetailsService.getAllBookingDetails();
		logger.info("Successfully fetched all booking details.");
		return new ResponseEntity<List<BookingDetailsResponseDTO>>(allBookingDetailsDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/bookings/hotel/{hotelId}")
	public ResponseEntity<List<BookingDetailsResponseDTO>> getBookingsForHotel(@PathVariable int hotelId) {
		logger.info("Fetching bookings for hotelId: {}", hotelId);
		List<BookingDetailsResponseDTO> allBookingDetailsDTOs = bookingDetailsService.getBookingsForHotel(hotelId);
		logger.info("Successfully retrieved bookings for hotelId: {}", hotelId);
		return new ResponseEntity<List<BookingDetailsResponseDTO>>(allBookingDetailsDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/bookings/sortbyamount")
	public ResponseEntity<List<BookingDetailsResponseDTO>> getAllBookingDetailsSortedByAmount() {
		logger.info("Fetching all booking details sorted by amount.");
		List<BookingDetailsResponseDTO> allBookingDetailsDTOs = bookingDetailsService
				.getAllBookingDetailsSortedByAmount();
		 logger.info("Successfully retrieved {} booking details sorted by amount.", allBookingDetailsDTOs.size());
		return new ResponseEntity<List<BookingDetailsResponseDTO>>(allBookingDetailsDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/bookings/withindate")
	public ResponseEntity<List<BookingDetailsResponseDTO>> getAllBookingDetailsBetweenBookedFromAndBookedTo(@RequestParam LocalDate bookedFrom,
			@RequestParam LocalDate bookedTo){
		logger.info("Request received to get all booking details between {} and {}", bookedFrom, bookedTo);
		List<BookingDetailsResponseDTO> allBookingDetails=bookingDetailsService.getAllBookingDetailsBetweenBookedFromAndBookedTo(bookedFrom, bookedTo);
		logger.info("Successfully retrieved all booking details between {} and {}", bookedFrom, bookedTo);
		return new ResponseEntity<List<BookingDetailsResponseDTO>>(allBookingDetails,HttpStatus.OK);
	}
	
	@PostMapping("/roomdetails/add/{hotelId}")
	public ResponseEntity<RoomDetailsResponseDTO> createRoomDetails(@Valid @RequestBody RoomDetailsRequestDTO roomDetailsDTO,
			@PathVariable int hotelId) {
		logger.info("Received request to create room details for hotelId: {}", hotelId);
		RoomDetailsResponseDTO savedRoomDetailsDTO = roomDetailsService.createRoomDetails(roomDetailsDTO, hotelId);
		logger.info("Room details created successfully for hotelId: {}", hotelId);
		return new ResponseEntity<RoomDetailsResponseDTO>(savedRoomDetailsDTO, HttpStatus.CREATED);
	}

	// admin
	@PutMapping("/roomdetails/{roomId}")
	public ResponseEntity<RoomDetailsRequestDTO> updateRoomDetails(@Valid @RequestBody RoomDetailsRequestDTO roomDetailsDTO,
			@PathVariable int roomId) {
		RoomDetailsRequestDTO savedRoomDetailsDTO = roomDetailsService.updateRoomDetails(roomDetailsDTO, roomId);
		logger.info("Room details updated successfully. Room ID: {}", roomId);
		return new ResponseEntity<RoomDetailsRequestDTO>(savedRoomDetailsDTO, HttpStatus.ACCEPTED);
	}

	// admin
	@DeleteMapping("/roomdetails/{roomId}")
	public ResponseEntity<ApiResponse> deleteRoomDetails(@PathVariable int roomId) {
		roomDetailsService.deleteRoomDetails(roomId);
		logger.info("Room details with ID {} deleted successfully", roomId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully", true), HttpStatus.OK);

	}

	// admin
	@GetMapping("/roomdetails/{roomId}")
	public ResponseEntity<RoomDetailsResponseDTO> getRoomDetailsById(@PathVariable int roomId) {
		logger.info("Fetching room details for roomId: {}", roomId);
		RoomDetailsResponseDTO savedRoomDetailsDTO = roomDetailsService.getRoomDetailsById(roomId);
		logger.info("Room details found for roomId: {}", roomId);
		return new ResponseEntity<RoomDetailsResponseDTO>(savedRoomDetailsDTO, HttpStatus.OK);
	}

	// admin
	@PostMapping("/roomdetails/room/image/upload/{roomId}")
	public ResponseEntity<RoomDetailsRequestDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable int roomId) throws IOException {
		
		logger.info("Uploading image for Room ID: {}", roomId);
		RoomDetailsResponseDTO savedRoomDetailsDTO = roomDetailsService.getRoomDetailsById(roomId);
		String fileName = fileService.uploadImage(path, image);

		savedRoomDetailsDTO.setFileName(fileName);
		String[] fileNames = fileName.split("\\.");
		savedRoomDetailsDTO.setFileType(fileNames[fileNames.length - 1]);
		RoomDetailsRequestDTO updatedRoomDetailsDTO = roomDetailsService
				.updateRoomDetails(modelMapper.map(savedRoomDetailsDTO, RoomDetailsRequestDTO.class), roomId);
		logger.info("Image uploaded successfully for Room ID: {}. File Name: {}", roomId, fileName);
		return new ResponseEntity<RoomDetailsRequestDTO>(updatedRoomDetailsDTO, HttpStatus.OK);

	}

	// admin
	@GetMapping(value = "/roomdetails/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		logger.info("Downloading image: {}", imageName);
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		logger.info("Image download successful: {}", imageName);
	}

}
