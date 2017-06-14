package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnimalTest {

    private static final String NAME = "Name";
    private static final String TYPE = "Type";
    private static final String IMAGE_URL = "ImageUrl";
    private static final String DESCRIPTION = "Description";

    @Mock
    private Aviary mockAviary;

    @Test
    public void shouldCreateAnimalWithDefaultValues_whenCallDefaultConstructor(){
        Animal animal = new Animal();
        assertNull(animal.getName());
        assertNull(animal.getType());
        assertNull(animal.getAviary());
        assertNull(animal.getImageUrl());
        assertNull(animal.getDescription());
    }

    @Test
    public void shouldCreateAnimalWithParameters_whenConstructorWithParameters() {
        Animal animal = new Animal(NAME, TYPE, IMAGE_URL);

        assertEquals(NAME, animal.getName());
        assertEquals(TYPE, animal.getType());
        assertEquals(IMAGE_URL, animal.getImageUrl());
    }

    @Test
    public void shouldSetNewAnimalName_whenSetNewName(){
        Animal animal = new Animal();
        animal.setName(NAME);

        assertEquals(NAME, animal.getName());
    }

    @Test
    public void shouldSetNewAnimalType_whenSetNewType(){
        Animal animal = new Animal();
        animal.setType(TYPE);

        assertEquals(TYPE, animal.getType());
    }

    @Test
    public void shouldSetNewAnimalAviary_whenSetNewAviary(){
        Animal animal = new Animal();
        animal.setAviary(mockAviary);

        assertEquals(mockAviary, animal.getAviary());
    }

    @Test
    public void shouldSetNewAnimalImageUrl_whenSetNewImageUrl(){
        Animal animal = new Animal();
        animal.setImageUrl(IMAGE_URL);

        assertEquals(IMAGE_URL, animal.getImageUrl());
    }

    @Test
    public void shouldSetNewAnimalDescription_whenSetNewDescription(){
        Animal animal = new Animal();
        animal.setDescription(DESCRIPTION);

        assertEquals(DESCRIPTION, animal.getDescription());
    }
}
