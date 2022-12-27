package db2;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

// Только поиск по фамилии, потому что смысла искать по отчеству и имени нет
@Entity
@org.hibernate.annotations.NamedQuery(name = "Clients.byId", query = "from Clients c where c.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "Clients.bySurname", query = "From Clients c where upper(c.surname) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Clients.byPhoneNumber", query = "From Clients c where upper(c.phoneNumber) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Clients.all", query = "from Clients c")
public class Clients {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "middle_name")
    private String middleName;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "clientsByClient")
    private Collection<Orders> ordersById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clients clients = (Clients) o;

        if (id != clients.id) return false;
        if (surname != null ? !surname.equals(clients.surname) : clients.surname != null) return false;
        if (name != null ? !name.equals(clients.name) : clients.name != null) return false;
        if (middleName != null ? !middleName.equals(clients.middleName) : clients.middleName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(clients.phoneNumber) : clients.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public Collection<Orders> getOrdersById() {
        return ordersById;
    }

    public void setOrdersById(Collection<Orders> ordersById) {
        this.ordersById = ordersById;
    }

    public static String toString(List<Clients> list){
        String res = "id | surname | name | middle name | phone number\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return  id + "  " + surname + ' ' +
                name + ' ' +
                middleName + ' ' +
                phoneNumber;
    }
}
