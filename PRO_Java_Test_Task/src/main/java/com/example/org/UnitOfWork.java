package com.example.org;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UnitOfWork {

    public  List<Booking> newCollection(){
        List list = new ArrayList<Booking>();
        return list;
    }
}
