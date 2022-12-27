package db2;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
@org.hibernate.annotations.NamedQuery(name = "Stuff.byId", query = "from Stuff d where d.id = ?1")
@org.hibernate.annotations.NamedQuery(name = "Stuff.bySurname", query = "From Stuff s where upper(s.surname) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Stuff.byAddress", query = "From Stuff s where upper(s.address) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Stuff.byBirthday", query = "from Stuff s where cast(s.dateOfBirth as string) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Stuff.byPosition", query = "From Stuff s where upper(s.position) like concat('%', upper(?1), '%')")
@org.hibernate.annotations.NamedQuery(name = "Stuff.byHigherSalary", query = "from Stuff s where s.salary > ?1")

@org.hibernate.annotations.NamedQuery(name = "Stuff.all", query = "from Stuff s")

public class Stuff {
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
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name = "salary")
    private int salary;
    @OneToMany(mappedBy = "stuffByStuff")
    private Collection<Drivers> driversById;
    @OneToMany(mappedBy = "stuffByStuffId")
    private Collection<MovingInfo> movingInfosById;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stuff stuff = (Stuff) o;

        if (id != stuff.id) return false;
        if (salary != stuff.salary) return false;
        if (surname != null ? !surname.equals(stuff.surname) : stuff.surname != null) return false;
        if (name != null ? !name.equals(stuff.name) : stuff.name != null) return false;
        if (middleName != null ? !middleName.equals(stuff.middleName) : stuff.middleName != null) return false;
        if (address != null ? !address.equals(stuff.address) : stuff.address != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(stuff.dateOfBirth) : stuff.dateOfBirth != null) return false;
        if (position != null ? !position.equals(stuff.position) : stuff.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + salary;
        return result;
    }

    public Collection<Drivers> getDriversById() {
        return driversById;
    }

    public void setDriversById(Collection<Drivers> driversById) {
        this.driversById = driversById;
    }

    public Collection<MovingInfo> getMovingInfosById() {
        return movingInfosById;
    }

    public void setMovingInfosById(Collection<MovingInfo> movingInfosById) {
        this.movingInfosById = movingInfosById;
    }

    public static String toString(List<Stuff> list){
        String res = "id | surmame | name | middle name | address | birthday | position | salary\n";
        for (int i = 0; i < list.size(); i++){
            res += list.get(i).toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return id + "  " +
                surname + ' ' +
                name + ' ' +
                middleName + ' ' +
                address + ' ' +
                dateOfBirth + ' ' +
                position + ' ' +
                salary;
    }
}
