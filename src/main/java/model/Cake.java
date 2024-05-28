package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "Cake")
public class Cake extends Product {
    @Column(name = "flavor")
    private String flavor;

    public Cake(int price, String name, String flavor) {
        super(price, name);
        this.flavor = flavor;
    }

    public Cake() {
        super();
        this.flavor = null;
    }
    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }


    @Override
    public String toString() {
        return super.getName() + ", Flavor: " + flavor;
    }
}
