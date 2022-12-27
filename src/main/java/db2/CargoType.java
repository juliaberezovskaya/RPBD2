package db2;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@org.hibernate.annotations.NamedQuery(name = "CargoType.byId", query = "from CargoType c where c.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "CargoType.byCargo", query = "From CargoType c where upper(c.cargo) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "CargoType.all", query = "from CargoType c")
@Table(name = "cargo_type", schema = "public", catalog = "db2")
public class CargoType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "cargo")
    private String cargo;
    @OneToMany(mappedBy = "cargoTypeByCargoType")
    private Collection<Cars> carsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoType cargoType = (CargoType) o;

        if (id != cargoType.id) return false;
        if (cargo != null ? !cargo.equals(cargoType.cargo) : cargoType.cargo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cargo != null ? cargo.hashCode() : 0);
        return result;
    }

    public Collection<Cars> getCarsById() {
        return carsById;
    }

    public void setCarsById(Collection<Cars> carsById) {
        this.carsById = carsById;
    }

    public static String toString(List<CargoType> list){
        String res = "id | cargo type\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return id + " " + cargo ;
    }
}
