package model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Product")
public class Product extends BaseEntity{
    @Column(name = "price")
    private int price;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
    @Column(name = "name")
    private String name;

    public Product(int price, String name){
        this.price = price;
        this.name = name;
    }

    public Product() {
        this.price = 0;
        this.name = "";
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }


}
