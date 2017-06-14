package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.util.DomainEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Provision extends DomainEntity {
    @ElementCollection
    private List<Ration> rations = new ArrayList<>();

    public Provision() {
    }

    public boolean contains(Ration ration) {
        return rations.contains(ration);
    }

    public List<Food> getNeedsList() {
        List<Food> needsList = new ArrayList<>();
        for (Ration ration : rations) {
            ration.getFoods().forEach(food -> {
                if (needsList.contains(food)) {
                    int existingFoodIndex = needsList.indexOf(food);
                    Food existingFood = needsList.get(existingFoodIndex);
                    existingFood.setQuantity(existingFood.getQuantity() + food.getQuantity());
                } else {
                    needsList.add(food);
                }
            });
        }
        return needsList;
    }

    public List<Ration> getRations() {
        return rations;
    }

    public void setRations(List<Ration> rations) {
        this.rations = rations;
    }
}
