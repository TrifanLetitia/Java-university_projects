package com.example.socialnetworkjava.domain;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;


public class Friendship extends Entity<Tuple<Long,Long>> {

    Date date;
    String status;


    public Friendship() {
        /*
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        Instant instant1 = ldt.toInstant(ZoneOffset.UTC);
        java.util.Date date = Date.from(instant1);
        this.date = (Date) date;
         */
    }

    public Friendship(Long id1, Long id2) {
        super();
    }

    public Friendship(LocalDateTime now) {
        super();
    }

    /**
     * @return the date when the friendship was created
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Friendship(Long id1, Long id2, LocalDateTime date) {
        this.date = (Date) java.util.Date
                .from(date.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public Friendship(Date date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(date, that.date) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, status);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
