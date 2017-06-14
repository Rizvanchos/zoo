package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AviaryTest {

    private static final int AVIARY_NUMBER = 1;
    private static final int TEMPERATURE = 10;
    private static final int WET = 15;
    private static final boolean CONTACT_STATE = true;
    private static final int FLOAT_DELTA = 0;

    @Mock
    private Animal mockAnimal;
    @Mock
    private Schedule mockSchedule;

    @Test
    public void shouldCreateNewAviaryWithParameters_whenCallConstructorParameters(){
        Aviary aviary = new Aviary(AVIARY_NUMBER, TEMPERATURE, WET, CONTACT_STATE);

        assertEquals(AVIARY_NUMBER, aviary.getNumber());
        assertEquals(TEMPERATURE, aviary.getTemperature(), FLOAT_DELTA);
        assertEquals(WET, aviary.getWet(), FLOAT_DELTA);
        assertEquals(AVIARY_NUMBER, aviary.getNumber());
        assertEquals(CONTACT_STATE, aviary.isContact());
    }

    @Test
    public void shouldSetNewAviaryNumber_whenSetNewNumber(){
        Aviary aviary = new Aviary();
        aviary.setNumber(AVIARY_NUMBER);

        assertEquals(AVIARY_NUMBER, aviary.getNumber());
    }

    @Test
    public void shouldSetNewAviarySchedule_whenSetNewSchedule(){
        Aviary aviary = new Aviary();
        aviary.setSchedule(mockSchedule);

        assertEquals(mockSchedule, aviary.getSchedule());
    }

    @Test
    public void shouldSetNewAviaryContactState_whenSetNewContactState(){
        Aviary aviary = new Aviary();
        aviary.setContact(CONTACT_STATE);

        assertEquals(CONTACT_STATE, aviary.isContact());
    }

    @Test
    public void shouldSetNewAviaryAnimals_whenSetNewAnimals(){
        Aviary aviary = new Aviary();
        List<Animal> animals = Collections.singletonList(mockAnimal);
        aviary.setAnimals(animals);

        assertEquals(animals, aviary.getAnimals());
    }

    @Test
    public void shouldSetNewAviaryTemperature_whenSetNewTemperature(){
        Aviary aviary = new Aviary();
        aviary.setTemperature(TEMPERATURE);

        assertEquals(TEMPERATURE, aviary.getTemperature(), FLOAT_DELTA);
    }

    @Test
    public void shouldSetNewAviaryWet_whenSetNewWet(){
        Aviary aviary = new Aviary();
        aviary.setWet(WET);

        assertEquals(WET, aviary.getWet(), FLOAT_DELTA);
    }

    @Test
    public void shouldContainAnimal_whenAddAnimal() {
        Aviary aviary = new Aviary();
        aviary.setAnimals(Collections.singletonList(mockAnimal));

        assertTrue(aviary.contains(mockAnimal));
    }

    @Test
    public void shouldCallGenerateSchedule_whenPerformViewAviarySchedule() {
        Aviary aviary = new Aviary();
        aviary.setSchedule(mockSchedule);
        aviary.viewSchedule();

        verify(mockSchedule).generateSchedule();
    }
}
