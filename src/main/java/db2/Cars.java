package db2;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@org.hibernate.annotations.NamedQuery(name = "Cars.byId", query = "from Cars c where c.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "Cars.byBrand", query = "From Cars c where upper(c.brand) like concat('%', upper(?1), '%')")

@org.hibernate.annotations.NamedQuery(name = "Cars.byNumber", query = "from Cars c where c.number = ?1")
@org.hibernate.annotations.NamedQuery(name = "Cars.byHigherCapacity", query = "from Cars c where c.loadCapacity > ?1")

// тут уточнить
@org.hibernate.annotations.NamedQuery(name = "Cars.byCargoType", query = "From Cars c where upper(c.cargoTypeByCargoType.cargo) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Cars.byCondition", query = "From Cars c where upper(c.conditionByCondition.condition) like concat('%', upper(?1), '%')")

@org.hibernate.annotations.NamedQuery(name = "Cars.all", query = "from Cars c")

public class Cars {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "brand")
    private String brand;
    @Basic
    @Column(name = "number")
    private int number;
    @Basic
    @Column(name = "load_capacity")
    private double loadCapacity;
    @ManyToOne
    @JoinColumn(name = "cargo_type", referencedColumnName = "id", nullable = false)
    private CargoType cargoTypeByCargoType;
    @ManyToOne
    @JoinColumn(name = "condition", referencedColumnName = "id", nullable = false)
    private Condition conditionByCondition;
    @OneToMany(mappedBy = "carsByCar")
    private Collection<Drivers> driversById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cars cars = (Cars) o;

        if (id != cars.id) return false;
        if (number != cars.number) return false;
        if (Double.compare(cars.loadCapacity, loadCapacity) != 0) return false;
        if (brand != null ? !brand.equals(cars.brand) : cars.brand != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + number;
        temp = Double.doubleToLongBits(loadCapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public CargoType getCargoTypeByCargoType() {
        return cargoTypeByCargoType;
    }

    public void setCargoTypeByCargoType(CargoType cargoTypeByCargoType) {
        this.cargoTypeByCargoType = cargoTypeByCargoType;
    }

    public Condition getConditionByCondition() {
        return conditionByCondition;
    }

    public void setConditionByCondition(Condition conditionByCondition) {
        this.conditionByCondition = conditionByCondition;
    }

    public Collection<Drivers> getDriversById() {
        return driversById;
    }

    public void setDriversById(Collection<Drivers> driversById) {
        this.driversById = driversById;
    }

    public static String toString(List<Cars> list){
        String res = "brand | number | load capacity | cargo type | condition type\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return  id + "  " +
                " " + number +
                " " + loadCapacity +
                " " + cargoTypeByCargoType.toString() +
                " " + conditionByCondition.toString();
    }
}
