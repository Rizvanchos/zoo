package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.util.DomainEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Schedule extends DomainEntity {
    @ElementCollection
    private List<String> visitTimes = new ArrayList<>();

    public void addTime(String time) {
        visitTimes.add(time);
    }

    public String generateSchedule() {
        return visitTimes.stream().map(time -> time + "\n").reduce(String::concat).orElse("No schedule");
    }

    public List<String> getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(List<String> visitTimes) {
        this.visitTimes = visitTimes;
    }
}
