package com.cg.hbm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.hbm.dto.RoomDetailsRequestDTO;
import com.cg.hbm.dto.RoomDetailsResponseDTO;

@Service
public interface IRoomDetailsService {
	RoomDetailsResponseDTO createRoomDetails(RoomDetailsRequestDTO roomDetailsDTO,int hotelId);
	RoomDetailsRequestDTO updateRoomDetails(RoomDetailsRequestDTO roomDetailsDTO,int roomId);
	void deleteRoomDetails(int roomId);
	RoomDetailsResponseDTO getRoomDetailsById(int roomId);
	List<RoomDetailsResponseDTO> getAllRoomDetails();
	List<RoomDetailsResponseDTO> getRoomsInHotel(int hotelId);
	List<RoomDetailsResponseDTO> getAvailableRoomsInHotel(int hotelId);
	List<RoomDetailsResponseDTO> getRoomsByTypeInHotel(int hotelId, String roomType);
	List<RoomDetailsResponseDTO> getRoomsByAvailabilityAndType(boolean isAvailable, String roomType);
	List<RoomDetailsResponseDTO> getRoomsByPriceRange(double minRate, double maxRate);

}
