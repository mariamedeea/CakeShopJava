package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "Order")
public class Order extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name = "totalPrice")
    private int totalPrice;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    public Order(Client client, Location location) {
        this.client = client;
        this.products = new ArrayList<>();
        this.totalPrice = 0;
        this.location = location;
        client.addOrder(this);
    }

    public Order() {
        this.totalPrice = 0;
        this.client = new Client();
        this.products = new ArrayList<>();
        this.location = new Location();
    }

    public void addProduct(Product product) {
        products.add(product);
        updateTotalPrice();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
    }

    public void displayOrderDetailsWithClient() {
        System.out.println("Client Name: " + client.getFirstName() + " " + client.getLastName());
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Products in the order:");
        for (Product product : products) {
            System.out.println("- " + product.getName());
        }
    }


    public Client getClient() {
        return client;
    }

    public Location getLocation() {
        return location;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }


}
