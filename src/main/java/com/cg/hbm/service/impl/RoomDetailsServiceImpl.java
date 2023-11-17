package com.cg.hbm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hbm.dto.RoomDetailsRequestDTO;
import com.cg.hbm.dto.RoomDetailsResponseDTO;
import com.cg.hbm.entity.Hotel;
import com.cg.hbm.entity.RoomDetails;
import com.cg.hbm.exception.ResourceNotFoundException;
import com.cg.hbm.repository.IHotelRepository;
import com.cg.hbm.repository.IRoomDetailsRepository;
import com.cg.hbm.service.IRoomDetailsService;

@Service
public class RoomDetailsServiceImpl implements IRoomDetailsService {
	
	@Autowired
	IRoomDetailsRepository roomDetailsRepository;
	
	@Autowired
	IHotelRepository hotelRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public RoomDetailsResponseDTO createRoomDetails(RoomDetailsRequestDTO roomDetailsDTO, int hotelId) {
		Hotel hotel=hotelRepository.findById(hotelId)
				.orElseThrow(()->new ResourceNotFoundException("Hotel","hotelId",hotelId));
		
		RoomDetails roomDetails=modelMapper.map(roomDetailsDTO, RoomDetails.class);
		roomDetails.setHotel(hotel);
		RoomDetails savedRoomDetails=roomDetailsRepository.save(roomDetails);
		return modelMapper.map(savedRoomDetails, RoomDetailsResponseDTO.class);
	}

	@Override
	public RoomDetailsRequestDTO updateRoomDetails(RoomDetailsRequestDTO roomDetailsDTO, int roomId) {
		RoomDetails roomDetails=roomDetailsRepository.findById(roomId)
				.orElseThrow(()->new ResourceNotFoundException("RoomDetails","roomId",roomId));
		roomDetails.setRoomNo(roomDetailsDTO.getRoomNo());
		roomDetails.setRoomType(roomDetailsDTO.getRoomType());
		roomDetails.setRatePerDay(roomDetailsDTO.getRatePerDay());
		roomDetails.setIsavailable(roomDetailsDTO.isIsavailable());
		roomDetails.setFileName(roomDetailsDTO.getFileName());
		roomDetails.setFileType(roomDetailsDTO.getFileType());
		
		RoomDetails savedRoomDetails=roomDetailsRepository.save(roomDetails);
		return modelMapper.map(savedRoomDetails, RoomDetailsRequestDTO.class);
		
	}

	@Override
	public void deleteRoomDetails(int roomId) {
		RoomDetails roomDetails=roomDetailsRepository.findById(roomId)
				.orElseThrow(()->new ResourceNotFoundException("RoomDetails","roomId",roomId));
		
		roomDetailsRepository.delete(roomDetails);
		
	}

	@Override
	public RoomDetailsResponseDTO getRoomDetailsById(int roomId) {
		RoomDetails roomDetails=roomDetailsRepository.findById(roomId)
				.orElseThrow(()->new ResourceNotFoundException("RoomDetails","roomId",roomId));
		return modelMapper.map(roomDetails, RoomDetailsResponseDTO.class);
	}

	@Override
	public List<RoomDetailsResponseDTO> getAllRoomDetails() {
		List<RoomDetails> allRoomDetails=roomDetailsRepository.findAll();
		return allRoomDetails.stream()
				.map(roomDetails->modelMapper.map(roomDetails, RoomDetailsResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<RoomDetailsResponseDTO> getRoomsInHotel(int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
	    List<RoomDetails> roomDetails = hotel.getRoomDetailsList();
	    return roomDetails.stream()
	            .map(room -> modelMapper.map(room, RoomDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public List<RoomDetailsResponseDTO> getAvailableRoomsInHotel(int hotelId) {
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
	    List<RoomDetails> availableRooms = hotel.getRoomDetailsList().stream()
	            .filter(RoomDetails::isIsavailable)
	            .collect(Collectors.toList());
	    return availableRooms.stream()
	            .map(room -> modelMapper.map(room, RoomDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public List<RoomDetailsResponseDTO> getRoomsByTypeInHotel(int hotelId, String roomType) {
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
	    List<RoomDetails> roomsByType = hotel.getRoomDetailsList().stream()
	            .filter(room -> room.getRoomType().equalsIgnoreCase(roomType))
	            .collect(Collectors.toList());
	    return roomsByType.stream()
	            .map(room -> modelMapper.map(room, RoomDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}
	
	@Override
	public List<RoomDetailsResponseDTO> getRoomsByAvailabilityAndType(boolean isAvailable, String roomType) {
	    List<RoomDetails> rooms = roomDetailsRepository.findByIsavailableAndRoomType(isAvailable, roomType);
	    return rooms.stream()
	            .map(room -> modelMapper.map(room, RoomDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public List<RoomDetailsResponseDTO> getRoomsByPriceRange(double minRate, double maxRate) {
	    List<RoomDetails> rooms = roomDetailsRepository.findByRatePerDayBetween(minRate, maxRate);
	    return rooms.stream()
	            .map(room -> modelMapper.map(room, RoomDetailsResponseDTO.class))
	            .collect(Collectors.toList());
	}
	
	

}
