
package com.cg.hbm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hbm.dto.HotelResponseDTO;
import com.cg.hbm.dto.HotelRequestDTO;
import com.cg.hbm.entity.Hotel;
import com.cg.hbm.exception.ResourceNotFoundException;
import com.cg.hbm.repository.IHotelRepository;
import com.cg.hbm.service.IHotelService;

@Service
public class HotelServiceImpl implements IHotelService {

	@Autowired
	IHotelRepository hotelRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public HotelResponseDTO createHotel(HotelRequestDTO hotelDto) {
		Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
		Hotel savedHotel = hotelRepository.save(hotel);
		return modelMapper.map(savedHotel, HotelResponseDTO.class);
	}

	@Override
	public HotelResponseDTO updateHotel(HotelRequestDTO hotelDto, int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));

		hotel.setCity(hotelDto.getCity());
		hotel.setHotelName(hotelDto.getHotelName());
		hotel.setAddress(hotelDto.getAddress());
		hotel.setDescription(hotelDto.getDescription());
		hotel.setAverageRatePerDay(hotelDto.getAverageRatePerDay());
		hotel.setEmail(hotelDto.getEmail());
		hotel.setPhone1(hotelDto.getPhone1());
		hotel.setPhone2(hotelDto.getPhone2());
		hotel.setWebsite(hotelDto.getWebsite());

		Hotel savedHotel = hotelRepository.save(hotel);
		return modelMapper.map(savedHotel, HotelResponseDTO.class);
	}

	@Override
	public void deleteHotel(int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
		hotelRepository.delete(hotel);

	}

	@Override
	public HotelResponseDTO getHotelById(int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
		return modelMapper.map(hotel, HotelResponseDTO.class);
	}

	@Override
	public List<HotelResponseDTO> getAllHotels() {
		List<Hotel> allHotels = hotelRepository.findAll();
		return allHotels.stream().map(hotel -> modelMapper.map(hotel, HotelResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<HotelResponseDTO> searchHotelsByCity(String city) {
		List<Hotel> hotelsInCity = hotelRepository.findByCity(city);
		return hotelsInCity.stream().map(hotel -> modelMapper.map(hotel, HotelResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<HotelResponseDTO> getHotelsByAverageRate(double minRate, double maxRate) {
		List<Hotel> hotelsWithinRates = hotelRepository.findByAverageRatePerDayBetween(minRate, maxRate);
		return hotelsWithinRates.stream().map(hotel -> modelMapper.map(hotel, HotelResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<HotelResponseDTO> getAllBookedHotels() {
		List<Hotel> allHotels = hotelRepository.findAll();

		return allHotels.stream().filter(Hotel::isBooked)
				.map(hotel -> this.modelMapper.map(hotel, HotelResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<HotelResponseDTO> getAllNonBookedHotels() {
		List<Hotel> allHotels = hotelRepository.findAll();

		return allHotels.stream()
				.filter(hotel -> !hotel.isBooked())
				.map(hotel -> this.modelMapper.map(hotel, HotelResponseDTO.class))
				.collect(Collectors.toList());
	}

}
