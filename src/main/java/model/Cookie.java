package model;

import liquibase.pro.packaged.E;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Cookie")
public class Cookie extends Product {
    @Column(name = "packSize")
    private int packSize;
    @Column(name = "shape")
    private String shape;

    public Cookie(int price, String name, int packSize, String shape) {
        super(price, name);
        this.packSize = packSize;
        this.shape = shape;
    }


    public Cookie() {
        super();
        this.packSize = 0;
        this.shape = "";
    }


    public int getPackSize() {
        return packSize;
    }

    public void setPackSize(int packSize) {
        this.packSize = packSize;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    // Override toString() method to include new attributes
    @Override
    public String toString() {
        return super.getPrice() +  ", Pack Size: " + packSize + ", Shape: " + shape;
    }
}
