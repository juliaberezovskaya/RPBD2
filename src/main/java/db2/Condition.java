package db2;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@org.hibernate.annotations.NamedQuery(name = "Condition.byCondition", query = "From Condition c where upper(c.condition) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Condition.all", query = "from Condition c")
public class Condition {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "condition")
    private String condition;
    @OneToMany(mappedBy = "conditionByCondition")
    private Collection<Cars> carsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Condition condition1 = (Condition) o;

        if (id != condition1.id) return false;
        if (condition != null ? !condition.equals(condition1.condition) : condition1.condition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        return result;
    }

    public Collection<Cars> getCarsById() {
        return carsById;
    }

    public void setCarsById(Collection<Cars> carsById) {
        this.carsById = carsById;
    }

    public static String toString(List<Condition> list){
        String res = "id | condition\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return  id + "  " + condition;
    }
}
