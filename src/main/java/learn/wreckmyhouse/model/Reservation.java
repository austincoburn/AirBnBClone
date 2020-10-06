package learn.wreckmyhouse.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Reservation {
    int id;
    Guest guest;
    Host host;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal totalPrice;


    public Reservation(int id, Guest guest, Host host, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice) {
        this.id = id;
        this.guest = guest;
        this.host = host;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;

    }

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        if(startDate.isAfter(endDate)) {
            return BigDecimal.ZERO;
        }
        if(startDate == null || endDate == null) {
            return BigDecimal.ZERO;
        }

        for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalPrice = totalPrice.add(host.getWeekend_rate());
            } else {
                totalPrice = totalPrice.add(host.getStandard_rate());
            }
        }

        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

}
