package db2;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity

@org.hibernate.annotations.NamedQuery(name = "Drivers.byId", query = "from Drivers d where d.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "Drivers.byCategory", query = "From Drivers d where upper(d.category) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Drivers.byExperience", query = "from Drivers d where d.experience = ?1")
@org.hibernate.annotations.NamedQuery(name = "Drivers.all", query = "from Drivers c")

public class Drivers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "experience")
    private int experience;
    @ManyToOne
    @JoinColumn(name = "car", referencedColumnName = "id", nullable = false)
    private Cars carsByCar;
    @ManyToOne
    @JoinColumn(name = "stuff", referencedColumnName = "id", nullable = false)
    private Stuff stuffByStuff;
    @OneToMany(mappedBy = "driversByDriver")
    private Collection<Orders> ordersById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drivers drivers = (Drivers) o;

        if (id != drivers.id) return false;
        if (experience != drivers.experience) return false;
        if (category != null ? !category.equals(drivers.category) : drivers.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + experience;
        return result;
    }

    public Cars getCarsByCar() {
        return carsByCar;
    }

    public void setCarsByCar(Cars carsByCar) {
        this.carsByCar = carsByCar;
    }

    public Stuff getStuffByStuff() {
        return stuffByStuff;
    }

    public void setStuffByStuff(Stuff stuffByStuff) {
        this.stuffByStuff = stuffByStuff;
    }

    public Collection<Orders> getOrdersById() {
        return ordersById;
    }

    public void setOrdersById(Collection<Orders> ordersById) {
        this.ordersById = ordersById;
    }

    public static String toString(List<Drivers> list){
        String res = "id | surname | category | experience | car number\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return id + "  " + stuffByStuff.getSurname() + ' ' +
               category + ' ' +
               experience + ' ' +
               carsByCar.getId();
    }
}
