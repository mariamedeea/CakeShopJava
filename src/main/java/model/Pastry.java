package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Pastry")
public class Pastry extends Product {

    @Column(name = "size")
    private String size;

    @Column(name = "isFilled")
    private boolean isFilled;

    @Column(name = "fillingType")
    private String fillingType;

    public Pastry(int price, String name, String size, boolean isFilled, String fillingType) {
        super(price, name);
        this.size = size;
        this.isFilled = isFilled;
        this.fillingType = isFilled ? fillingType : "None";

    }

    public Pastry() {
        super();
        this.size = null;
        this.isFilled = false;
        this.fillingType = "";
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public String getFillingType() {
        return fillingType;
    }

    public void setFillingType(String fillingType) {
        this.fillingType = fillingType;
    }

    // Override toString() if needed
    @Override
    public String toString() {
        return super.getName() + ", Type: " + size + ", Filled: " + isFilled + ", Filling: " + fillingType;
    }
}
