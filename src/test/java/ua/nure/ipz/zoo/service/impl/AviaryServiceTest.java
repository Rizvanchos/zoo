package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.exception.NotContactAviaryException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;

import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class AviaryServiceTest {

    private static final String NAME = "Name";
    private static final String TYPE = "Type";
    private static final String IMAGE_URL = "http://zoo.sandiegozoo.org";

    private static final float NEW_TEMPERATURE = 5;
    private static final int TEMPERATURE = 2;
    private static final float NEW_WET = 66;
    private static final int WET = 65;
    private static final int AVIARY_NUMBER = 1;
    private static final boolean CONTACT = true;

    private static final int NEGATIVE_AVIARY_NUMBER = -1;
    private static final int OUT_OF_RANGE_VALUE = 100;
    private static final String VISIT_TIME = "10/10/2020";
    private static final float FLOAT_DELTA = 0;
    private static final String EMPTY_STRING = "";

    @Autowired
    private AviaryService aviaryService;
    @Autowired
    private AnimalService animalService;

    private UUID aviaryId;
    private UUID animalId;

    @Before
    public void setUp() {
        aviaryId = aviaryService.create(AVIARY_NUMBER, TEMPERATURE, WET, CONTACT);
        animalId = getAnimal();
    }

    private UUID getAnimal() {
        return animalService.create(NAME, TYPE, IMAGE_URL);
    }

    @Test
    public void shouldCreateNewAviary_whenPassValidParameters() {
        AviaryDto aviaryDto = aviaryService.view(aviaryId);
        assertEquals(AVIARY_NUMBER, aviaryDto.getNumber());
        assertEquals(TEMPERATURE, aviaryDto.getTemperature(), FLOAT_DELTA);
        assertEquals(WET, aviaryDto.getWet(), FLOAT_DELTA);
        assertEquals(CONTACT, aviaryDto.isContactFlag());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAviaryNumberIsNegative() {
        aviaryService.create(NEGATIVE_AVIARY_NUMBER, TEMPERATURE, WET, CONTACT);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAviaryTemperatureOutOfRange() {
        aviaryService.create(NEGATIVE_AVIARY_NUMBER, OUT_OF_RANGE_VALUE, WET, CONTACT);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAviaryWetOutOfRange() {
        aviaryService.create(NEGATIVE_AVIARY_NUMBER, TEMPERATURE, OUT_OF_RANGE_VALUE, CONTACT);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddNullAnimal() throws DuplicationEntityException {
        aviaryService.addAnimal(aviaryId, null);
    }

    @Test
    public void shouldAddAnimalToAviary_whenCallAddAviary() throws DuplicationEntityException {
        aviaryService.addAnimal(aviaryId, animalId);

        AviaryDto aviaryDto = aviaryService.view(aviaryId);
        assertEquals(animalId, aviaryDto.getAnimals().get(0).getDomainId());
    }

    @Test
    public void shouldReturnAviaryAnimals_whenViewAviaryAnimals() throws DuplicationEntityException {
        aviaryService.addAnimal(aviaryId, getAnimal());
        aviaryService.addAnimal(aviaryId, getAnimal());
        aviaryService.addAnimal(aviaryId, getAnimal());

        assertThat(aviaryService.viewAnimals(aviaryId), hasSize(3));
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeTemperatureOnOutOfRange() {
        aviaryService.changeTemperature(aviaryId, OUT_OF_RANGE_VALUE);
    }

    @Test
    public void shouldChangeAviaryTemperature_whenCallChangeTemperature() {
        aviaryService.changeTemperature(aviaryId, NEW_TEMPERATURE);

        AviaryDto aviaryDto = aviaryService.view(aviaryId);
        assertEquals(NEW_TEMPERATURE, aviaryDto.getTemperature(), FLOAT_DELTA);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeWetOnOutOfRange() {
        aviaryService.changeTemperature(aviaryId, OUT_OF_RANGE_VALUE);
    }

    @Test
    public void shouldChangeWet_whenCallChangeWet() {
        aviaryService.changeWet(aviaryId, NEW_WET);

        AviaryDto aviaryDto = aviaryService.view(aviaryId);
        assertEquals(NEW_WET, aviaryDto.getWet(), FLOAT_DELTA);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeAddEmptyVisitTime() throws DuplicationEntityException, NotContactAviaryException {
        aviaryService.addVisitTime(aviaryId, EMPTY_STRING);
    }

    @Test(expected = NotContactAviaryException.class)
    public void shouldThrowException_whenViewScheduleOfNotContactAviary() throws DuplicationEntityException, NotContactAviaryException {
        aviaryService.addVisitTime(aviaryId, VISIT_TIME);
        aviaryService.changeContact(aviaryId, Boolean.FALSE);

        aviaryService.viewSchedule(aviaryId);
    }

    @Test
    public void shouldReturnAviarySchedule_wheCallViewSchedule() throws DuplicationEntityException, NotContactAviaryException {
        aviaryService.addVisitTime(aviaryId, VISIT_TIME);
        assertEquals(VISIT_TIME + "\n", aviaryService.viewSchedule(aviaryId));
    }

    @Test
    public void shouldDeleteAviary_whenCallDeleteAviary() {
        aviaryService.delete(aviaryId);

        assertThat(aviaryService.viewAll(), empty());
    }
}
