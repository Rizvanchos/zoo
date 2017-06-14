package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;

import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class AnimalServiceIntegrationTest {

    private static final String NAME = "Name";
    private static final String TYPE = "Type";
    private static final String IMAGE_URL = "http://zoo.sandiegozoo.org";
    private static final String WRONG_IMAGE_URL = "WRONG";
    private static final String NEW_NAME = "NewName";

    private static final String DESCRIPTION = "Description";
    private static final int AVIARY_NUMBER = 1;
    private static final int TEMPERATURE = 2;
    private static final int WET = 65;
    private static final boolean CONTACT = true;
    private static final String EMPTY_STRING = "";

    @Autowired
    private AnimalService animalService;
    @Autowired
    private AviaryService aviaryService;

    private UUID animalId;

    @Before
    public void setUp() {
        animalId = animalService.create(NAME, TYPE, IMAGE_URL);
    }

    @Test
    public void shouldCreateAnimal_whenPassValidParameters() {
        AnimalDto animalDto = animalService.view(animalId);

        assertEquals(NAME, animalDto.getName());
        assertEquals(TYPE, animalDto.getType());
        assertEquals(IMAGE_URL, animalDto.getImageUrl());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateAnimalWithEmptyName() {
        animalService.create(EMPTY_STRING, TYPE, IMAGE_URL);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateAnimalWithEmptyType() {
        animalService.create(NAME, EMPTY_STRING, IMAGE_URL);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateAnimalWithEmptyImageUrl() {
        animalService.create(NAME, TYPE, WRONG_IMAGE_URL);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddDescriptionToNullAnimal() {
        animalService.addDescription(null, DESCRIPTION);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddDescriptionWithEmptyDescription() {
        animalService.addDescription(animalId, EMPTY_STRING);
    }

    @Test
    public void shouldAddDescriptionToAnimal_whenAddDescription() {
        animalService.addDescription(animalId, DESCRIPTION);

        AnimalDto animalDto = animalService.view(animalId);
        assertEquals(DESCRIPTION, animalDto.getDescription());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeNameToEmpty() {
        animalService.changeName(animalId, EMPTY_STRING);
    }

    @Test
    public void shouldChangeAnimalName_whenChangeName() {
        animalService.changeName(animalId, NEW_NAME);

        AnimalDto animalDto = animalService.view(animalId);
        assertEquals(NEW_NAME, animalDto.getName());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeAviaryToNull() {
        animalService.changeAviary(animalId, null);
    }

    @Test
    public void shouldChangeAnimalAviary_whenChangeAviary() {
        UUID aviaryId = aviaryService.create(AVIARY_NUMBER, TEMPERATURE, WET, CONTACT);
        animalService.changeAviary(animalId, aviaryId);

        AviaryDto aviaryDto = aviaryService.view(aviaryId);
        assertEquals(animalId, aviaryDto.getAnimals().get(0).getDomainId());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUploadAnimalPhotoIsInvalid() {
        animalService.uploadAnimalPhoto(animalId, WRONG_IMAGE_URL);
    }

    @Test
    public void shouldDeleteRation_whenCallDelete() {
        animalService.delete(animalId);
        assertThat(animalService.viewAll(), empty());
    }
}
