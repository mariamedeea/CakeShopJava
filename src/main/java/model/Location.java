package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Location")
public class Location extends BaseEntity{

    @Column(name = "city")
    private String city;

    public Location(String city) {
        this.city = city;
    }

    public Location() {
        this.city = "";
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
