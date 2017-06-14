package ua.nure.ipz.zoo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ua.nure.ipz.zoo.util.DomainEntity;

@Entity
public class Aviary extends DomainEntity {
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "aviary")
    private List<Animal> animals = new ArrayList<>();
    @OneToOne(cascade = CascadeType.PERSIST)
    private Schedule schedule = new Schedule();
    private int number;
    private boolean contact;
    private float temperature;
    private float wet;

    public Aviary() {
    }

    public Aviary(int number, float temperature, float wet, boolean contact) {
        this.number = number;
        this.temperature = temperature;
        this.wet = wet;
        this.contact = contact;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean contains(Animal animal) {
        return animals.contains(animal);
    }

    public String viewSchedule() {
        return schedule.generateSchedule();
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWet() {
        return wet;
    }

    public void setWet(float wet) {
        this.wet = wet;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
