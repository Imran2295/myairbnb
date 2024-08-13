package com.airbnb2.controller;

import com.airbnb2.entity.Booking;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.service.BookingService;
import com.airbnb2.service.S3BucketService;
import com.airbnb2.service.TwillioSmsService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v2/booking")
public class BookingController {

    private BookingService bookingService;
    private S3BucketService bucketService;
    private TwillioSmsService smsService;

    public BookingController(BookingService bookingService, S3BucketService bucketService, TwillioSmsService smsService) {
        this.bookingService = bookingService;
        this.bucketService = bucketService;
        this.smsService = smsService;
    }


    // http://localhost:8080/api/v2/booking/createBooking/propertyId
    @PostMapping("/createBooking/propertyId/{propertyId}")
    public ResponseEntity<?> createBooking(@PathVariable long propertyId ,
                                                 @RequestBody Booking booking,
                                                 @AuthenticationPrincipal PropertyUserEntity user) throws DocumentException, IOException {

        // doing booking and saving in db.   // 1 //
        Booking hotelBooking = bookingService.createBooking(propertyId, booking, user);
        // if saved in db successful and it is not null.
        if(hotelBooking != null){
            // generating pdf by passing path where pdf will get stored.
            // passing booking details so we can set the value in the pdf.  // 2 //
            Boolean b = bookingService.generatePdf("F:\\Airbnb Project Pdf\\"+hotelBooking.getId(), hotelBooking);
            // if pdf generated successfully it will return true else false.
            if(b){
                // now we want to save the pdf into Multipart file so it can be saved in the S3 bucket.
                // we will pass the path of the pdf then it will convert that file to binary/multipart file. // 3 //
                MultipartFile multipartFile = bucketService.convert("F:\\Airbnb Project Pdf\\" + hotelBooking.getId()+"booking confirmation.pdf");
                // Now saving it into the S3 bucket by passing the multipart file and bucket name.
                // it will return a URL which is use to access the pdf or whatever the you saved. // 4 //
                String uploadedFile = bucketService.uploadFile(multipartFile, "myairbnb007");
                // we will send confirmation message to the customer on there registered number with pdf file link.
                // passing the number of customer and the msg body+link of PDF. // 5 //
                smsService.sendSms("+918850932377" , "Testing click link for pdf "+uploadedFile);
                System.out.println(uploadedFile);
            }else{
                return new ResponseEntity<>("Something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(hotelBooking , HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// after doing above 5 step your booking module is approximately completed.// only EMAIL is remaining //
