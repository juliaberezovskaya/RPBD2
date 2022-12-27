package db2;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@org.hibernate.annotations.NamedQuery(name = "MovingInfo.byId", query = "from MovingInfo d where d.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "MovingInfo.byPosition", query = "From MovingInfo m where upper(m.position) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "MovingInfo.byOrderNumber", query = "from MovingInfo m where m.orderNumber = ?1")
@org.hibernate.annotations.NamedQuery(name = "MovingInfo.byOrderDate", query = "from MovingInfo m where cast(m.orderDate as string) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "MovingInfo.all", query = "from MovingInfo m")

@Table(name = "moving_info", schema = "public", catalog = "db2")
public class MovingInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name = "reasons_for_transfer")
    private String reasonsForTransfer;
    @Basic
    @Column(name = "order_number")
    private int orderNumber;
    @Basic
    @Column(name = "order_date")
    private Date orderDate;
    @ManyToOne
    @JoinColumn(name = "stuff_id", referencedColumnName = "id", nullable = false)
    private Stuff stuffByStuffId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReasonsForTransfer() {
        return reasonsForTransfer;
    }

    public void setReasonsForTransfer(String reasonsForTransfer) {
        this.reasonsForTransfer = reasonsForTransfer;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovingInfo that = (MovingInfo) o;

        if (id != that.id) return false;
        if (orderNumber != that.orderNumber) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (reasonsForTransfer != null ? !reasonsForTransfer.equals(that.reasonsForTransfer) : that.reasonsForTransfer != null)
            return false;
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (reasonsForTransfer != null ? reasonsForTransfer.hashCode() : 0);
        result = 31 * result + orderNumber;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        return result;
    }

    public Stuff getStuffByStuffId() {
        return stuffByStuffId;
    }

    public void setStuffByStuffId(Stuff stuffByStuffId) {
        this.stuffByStuffId = stuffByStuffId;
    }

    public static String toString(List<MovingInfo> list){
        String res = "id | reasons for transfer | order number | order date | stuff id\n";
        for (int i = 0; i < list.size(); i++){
            res += (i + 1) + ". " +list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return  id + "  " + position + ' ' +
                reasonsForTransfer + ' ' +
                orderNumber + ' ' +
                orderDate + ' ' +
                stuffByStuffId.getId();
    }
}
