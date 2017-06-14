package ua.nure.ipz.zoo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AviaryDto extends DomainEntityDto<AviaryDto> {
    private List<AnimalDto> animals;
    private int number;
    private List<String> schedules;
    private boolean contactFlag;
    private float temperature;
    private float wet;

    public AviaryDto(UUID domainId, List<AnimalDto> animals, int number, List<String> schedules, boolean contactFlag, float temperature, float wet) {
        super(domainId);
        this.animals = animals;
        this.number = number;
        this.schedules = schedules;
        this.contactFlag = contactFlag;
        this.temperature = temperature;
        this.wet = wet;
    }

    public List<AnimalDto> getAnimals() {
        return animals;
    }

    public List<String> getSchedule() {
        return schedules;
    }

    public int getNumber() {
        return number;
    }

    public boolean isContactFlag() {
        return contactFlag;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWet() {
        return wet;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        List<Object> attributes = Arrays.asList(getDomainId(), getAnimals(), getSchedule(), getNumber(), isContactFlag(), getTemperature(), getWet());
        return attributes;
    }

    @Override
    public String toString() {
        List<String> animalNames = animals.stream().map(AnimalDto::getName).collect(Collectors.toList());
        return String.format("ID = %s\nAnimals = %s\nSchedules : \n%s\nNumber = %s\nContactFlag = %s\nTemperature = %s\nWet = %s",
                getDomainId(), animalNames, schedules, number, contactFlag, temperature, wet);
    }
}
