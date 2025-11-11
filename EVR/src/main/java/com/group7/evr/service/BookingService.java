package com.group7.evr.service;

import com.group7.evr.entity.Booking;
import com.group7.evr.entity.User;

import java.util.Map;

public interface BookingService {
    public Booking createBooking(Booking booking, User user);
    public Booking getBookingById(Integer bookingId);
    public Map<String, Object> getUserBookingsWithFilters(Integer userId, String status, String fromDate, String toDate, int page, int size);
    public Booking checkIn(Integer bookingId, User user, User staff);
    public Booking returnVehicle(Integer bookingId, User user, User staff);
    public java.util.List<Booking> getUserHistory(Integer userId);
    public Map<String, Object> getUserAnalytics(Integer userId);
    public Map<String, Object> getAdvancedUserAnalytics(Integer userId);
    public Booking modifyBooking(Integer bookingId, Booking updates, User actor);
    public Booking cancelBooking(Integer bookingId, User actor);
    public Map<String, Object> settleBooking(Integer bookingId, User actor);
}
