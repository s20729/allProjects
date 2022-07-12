package com.example.org;


public class BookingHelper {

    public UnitOfWork unitOfWork;


    public String OverlappingBookingsExist(Booking booking){
        if (booking.getStatus().equals("Cancelled"))
            return "";

        var bookings = unitOfWork.newCollection().stream().filter(b -> b.id != booking.id && !b.getStatus().equals("Cancelled")
        ).toList();

        var overlappingBooking =
                bookings.stream().filter(b ->
                        booking.getArrivalDate().isAfter(b.getArrivalDate())
                        && booking.getArrivalDate().isBefore(b.getDepartureDate())
                        || booking.getDepartureDate().isAfter(b.getArrivalDate())
                        && booking.getDepartureDate().isBefore(b.getDepartureDate())).findFirst();

        System.out.println(overlappingBooking);
        System.out.println(booking);
        System.out.println(bookings);

        return !overlappingBooking.isPresent() ? "" : overlappingBooking.get().getReference();
    }

}
