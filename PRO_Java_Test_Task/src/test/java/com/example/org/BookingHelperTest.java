package com.example.org;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookingHelperTest {

     @Mock
     static UnitOfWork unitOfWork;

     @InjectMocks
     BookingHelper bookingHelper;

    @Test
    public void overlappingBookingsExist_WhenCalled_ReturnEmptyString() {
        var booking = new Booking();
        booking.setStatus("Cancelled");

        var result = bookingHelper.OverlappingBookingsExist(booking);

        assert result.isEmpty();
    }


    @Test
    public void overlappingBookingsExist_WhenCalled_ReturnOverlappingBookingIsEmptyString() throws ParseException {

        var booking = new Booking();
        booking.setStatus("Applied");
        booking.setArrivalDate(LocalDateTime.of(2001, Month.NOVEMBER, 16, 23, 48));
        booking.setDepartureDate(LocalDateTime.of(2001, Month.NOVEMBER, 10, 23, 48));
        booking.setId(1);

        var booking2 = new Booking();
        booking2.setStatus("Realization");
        booking2.setArrivalDate(LocalDateTime.of(2001, Month.NOVEMBER, 16, 23, 48));
        booking2.setDepartureDate(LocalDateTime.of(2001, Month.NOVEMBER, 14, 23, 48));
        booking2.setId(2);
        booking2.setReference("Success");

        when(unitOfWork.newCollection()).thenReturn(List.of(booking2));

        var result = bookingHelper.OverlappingBookingsExist(booking);

        assert result.equals("");

    }

    @Test
    public void overlappingBookingsExist_WhenCalled_ReturnOverlappingBookingReference() throws ParseException {

        var booking = new Booking();
        booking.setStatus("Applied");
        booking.setArrivalDate(LocalDateTime.of(2001, Month.NOVEMBER, 27, 23, 48));
        booking.setDepartureDate(LocalDateTime.of(2001, Month.NOVEMBER, 21, 23, 48));
        booking.setId(1);

        var booking2 = new Booking();
        booking2.setStatus("Realization");
        booking2.setArrivalDate(LocalDateTime.of(2001, Month.NOVEMBER, 27, 22, 48));
        booking2.setDepartureDate(LocalDateTime.of(2001, Month.NOVEMBER, 28, 23, 48));
        booking2.setId(2);
        booking2.setReference("Success");

        when(unitOfWork.newCollection()).thenReturn(List.of(booking2));

        //when
        var tested = bookingHelper.OverlappingBookingsExist(booking);

        //then
        assert tested.equals(booking2.getReference());

    }
}