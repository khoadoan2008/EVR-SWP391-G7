package com.group7.evr.service;

import com.group7.evr.entity.Booking;
import com.group7.evr.entity.User;

public interface EmailService {

    /**
     * Send registration confirmation email to the given user.
     *
     * @param user the user who has just registered
     */
    void sendRegistrationConfirmation(User user, String verificationLink);

    /**
     * Send booking confirmation email after a booking is created.
     *
     * @param booking the booking to include in the email
     */
    void sendBookingConfirmation(Booking booking);
}

