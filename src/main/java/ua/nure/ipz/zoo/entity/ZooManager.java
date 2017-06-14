package ua.nure.ipz.zoo.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ZooManager extends Account {
    @OneToMany
    private List<Provision> provisions = new ArrayList<>();

    public ZooManager() {
    }

    public ZooManager(String name, String email, String password) {
        super(name, email, password);
    }

    public void assignProvision(Provision provision) {
        provisions.add(provision);
    }

    public List<Provision> getProvisions() {
        return provisions;
    }

    public void setProvisions(List<Provision> provisions) {
        this.provisions = provisions;
    }
}
