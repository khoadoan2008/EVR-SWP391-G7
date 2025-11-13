package com.group7.evr.service.impl;

import com.group7.evr.entity.Booking;
import com.group7.evr.entity.User;
import com.group7.evr.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateProvider templateProvider;

    @Value("${app.mail.from:no-reply@evr.vn}")
    private String defaultSender;

    @Value("${app.mail.registration-subject:Chào mừng đến EVR}")
    private String registrationSubject;

    @Override
    public void sendRegistrationConfirmation(User user, String verificationLink) {
        if (user == null || !StringUtils.hasText(user.getEmail())) {
            log.warn("Skip sending registration email because user email is missing");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom(defaultSender);
        message.setSubject(registrationSubject);
        message.setText(templateProvider.buildRegistrationContent(user, verificationLink));

        try {
            mailSender.send(message);
            log.info("Sent registration confirmation email to {}", user.getEmail());
        } catch (MailException ex) {
            log.error("Failed to send registration email to {}", user.getEmail(), ex);
        }
    }

    @Value("${app.mail.booking-subject:Xác nhận đặt xe EVR}")
    private String bookingSubject;

    @Override
    public void sendBookingConfirmation(Booking booking) {
        if (booking == null || booking.getUser() == null || !StringUtils.hasText(booking.getUser().getEmail())) {
            log.warn("Skip sending booking confirmation email because user email is missing");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUser().getEmail());
        message.setFrom(defaultSender);
        message.setSubject(bookingSubject);
        message.setText(templateProvider.buildBookingConfirmationContent(booking));

        try {
            mailSender.send(message);
            log.info("Sent booking confirmation email to {} for booking {}", booking.getUser().getEmail(), booking.getBookingId());
        } catch (MailException ex) {
            log.error("Failed to send booking confirmation email to {}", booking.getUser().getEmail(), ex);
        }
    }

}

