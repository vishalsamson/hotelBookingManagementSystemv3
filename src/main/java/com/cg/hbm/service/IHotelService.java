package com.cg.hbm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.hbm.dto.HotelResponseDTO;
import com.cg.hbm.dto.HotelRequestDTO;

@Service
public interface IHotelService {
	HotelResponseDTO createHotel(HotelRequestDTO hotelDto);
	HotelResponseDTO updateHotel(HotelRequestDTO hotelDto,int hotelId);
	void deleteHotel(int hotelId);
	HotelResponseDTO getHotelById(int hotelId);
	List<HotelResponseDTO> getAllHotels();
	List<HotelResponseDTO> searchHotelsByCity(String city);
	List<HotelResponseDTO> getHotelsByAverageRate(double minRate, double maxRate);
	
	List<HotelResponseDTO> getAllBookedHotels();
	List<HotelResponseDTO> getAllNonBookedHotels();
}
