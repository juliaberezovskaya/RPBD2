package db2;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@org.hibernate.annotations.NamedQuery(name = "Orders.byId", query = "from Orders d where d.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "Orders.byClientId", query = "from Orders d where d.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "Orders.bySubmissionAddress", query = "From Orders o where upper(o.submissionAddress) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Orders.byDestinationAddress", query = "From Orders o where upper(o.destinationAddress) like concat('%', upper(?1), '%')")

@org.hibernate.annotations.NamedQuery(name = "Orders.byHigherPrice", query = "from Orders o where o.price > ?1")

@org.hibernate.annotations.NamedQuery(name = "Orders.byDate", query = "from Orders o where cast(o.date as string) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Orders.byDateOrderExecution", query = "from Orders o where cast(o.dateOrderExecution as string) like concat('%', upper(?1), '%')")

@org.hibernate.annotations.NamedQuery(name = "Orders.all", query = "from Orders o")

public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "submission_address")
    private String submissionAddress;
    @Basic
    @Column(name = "destination_address")
    private String destinationAddress;
    @Basic
    @Column(name = "date_order_execution")
    private Timestamp dateOrderExecution;
    @Basic
    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "driver", referencedColumnName = "id", nullable = false)
    private Drivers driversByDriver;
    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id", nullable = false)
    private Clients clientsByClient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubmissionAddress() {
        return submissionAddress;
    }

    public void setSubmissionAddress(String submissionAddress) {
        this.submissionAddress = submissionAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Timestamp getDateOrderExecution() {
        return dateOrderExecution;
    }

    public void setDateOrderExecution(Timestamp dateOrderExecution) {
        this.dateOrderExecution = dateOrderExecution;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (id != orders.id) return false;
        if (price != orders.price) return false;
        if (date != null ? !date.equals(orders.date) : orders.date != null) return false;
        if (submissionAddress != null ? !submissionAddress.equals(orders.submissionAddress) : orders.submissionAddress != null)
            return false;
        if (destinationAddress != null ? !destinationAddress.equals(orders.destinationAddress) : orders.destinationAddress != null)
            return false;
        if (dateOrderExecution != null ? !dateOrderExecution.equals(orders.dateOrderExecution) : orders.dateOrderExecution != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (submissionAddress != null ? submissionAddress.hashCode() : 0);
        result = 31 * result + (destinationAddress != null ? destinationAddress.hashCode() : 0);
        result = 31 * result + (dateOrderExecution != null ? dateOrderExecution.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }

    public Drivers getDriversByDriver() {
        return driversByDriver;
    }

    public void setDriversByDriver(Drivers driversByDriver) {
        this.driversByDriver = driversByDriver;
    }

    public Clients getClientsByClient() {
        return clientsByClient;
    }

    public void setClientsByClient(Clients clientsByClient) {
        this.clientsByClient = clientsByClient;
    }

    public static String toString(List<Orders> list){
        String res = "id | submission address | destination address | date order execution | price | driver's surname | client's surname\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return id + "  " + date.toString() + ' ' +
               submissionAddress + ' ' +
               destinationAddress + ' ' +
               dateOrderExecution.toString() + ' ' +
               price + ' ' +
               driversByDriver.getStuffByStuff().getSurname() + ' ' +
               clientsByClient.getSurname();
    }
}
