package com.airbnb2.service;

import com.airbnb2.dto.BookingPdfDto;
import com.airbnb2.entity.Booking;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.exception.RecordNotFoundException;
import com.airbnb2.repository.BookingRepository;
import com.airbnb2.repository.PropertyRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BookingService {

    private BookingRepository bookingRepository;

    private PropertyRepository propertyRepo;

    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepo) {
        this.bookingRepository = bookingRepository;
        this.propertyRepo = propertyRepo;
    }


    public Booking createBooking(long propertyId, Booking booking , PropertyUserEntity user) {
        Property property = propertyRepo.findById(propertyId).orElseThrow(() ->
                new RecordNotFoundException("No Property Found"));

        Booking book = new Booking();
        book.setGuestName(booking.getGuestName());
        book.setTotalNights(booking.getTotalNights());
        book.setTotalPrice(property.getNightlyPrice() * (long)booking.getTotalNights());
        book.setProperty(property);
        book.setPropertyUser(user);
        book.setCheckIn(booking.getCheckIn());

        LocalDate localCheckInDate = booking.getCheckIn().toLocalDate();
        LocalDate localDate = localCheckInDate.plusDays((long)booking.getTotalNights());
        LocalDateTime checkOutTime = localDate.atTime(booking.getCheckIn().toLocalTime());
        book.setCheckOut(checkOutTime);

        Booking savedBooking = bookingRepository.save(book);

        return savedBooking;
    }


    public Boolean generatePdf(String s , Booking booked) {

        try {
//            BookingPdfDto bookingPdfDto = new BookingPdfDto();
//            bookingPdfDto.setPropertyName(booked.getProperty().getPropertyName());
//            bookingPdfDto.setGuestName(booked.getGuestName());
//            bookingPdfDto.setTotalNights(booked.getTotalNights());
//            bookingPdfDto.setTotalPrice(booked.getTotalPrice());
//            bookingPdfDto.setCheckIn(booked.getCheckIn());
//            bookingPdfDto.setCheckOut(booked.getCheckOut());

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(s + "booking confirmation.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);

//            Chunk property = new Chunk("Property Name: " + bookingPdfDto.getPropertyName(), font);
//            Chunk guestName = new Chunk("Guest Name: " + bookingPdfDto.getGuestName(), font);
//            Chunk totalNights = new Chunk("Total Nights: " + bookingPdfDto.getTotalNights().toString(), font);
//            Chunk totalPrice = new Chunk("Total Price: " + bookingPdfDto.getTotalPrice().toString(), font);
//            Chunk checkIn = new Chunk("CheckIn Time: " + bookingPdfDto.getCheckIn().toString(), font);
//            Chunk checkOut = new Chunk("CheckOut Time: " + bookingPdfDto.getCheckOut().toString(), font);
            Chunk allDetails = new Chunk("All Booking details: \n"+booked , font );

//            document.add(property);
//            document.add(new Paragraph("\n"));
//            document.add(guestName);
//            document.add(new Paragraph("\n"));
//            document.add(totalNights);
//            document.add(new Paragraph("\n"));
//            document.add(totalPrice);
//            document.add(new Paragraph("\n"));
//            document.add(checkIn);
//            document.add(new Paragraph("\n"));
//            document.add(checkOut);
            document.add(new Paragraph("\n"));
            document.add(allDetails);

            document.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
