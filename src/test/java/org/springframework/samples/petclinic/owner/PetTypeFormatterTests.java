package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
public class PetTypeFormatterTests {

    @Mock
    private PetRepository pets;

    private PetTypeFormatter petTypeFormatter;

    @BeforeEach
    void setup() {
        this.petTypeFormatter = new PetTypeFormatter(pets);
    }

    @Test
    void testPrint() {
        PetType petType = new PetType();
        petType.setName("Hamster");
        String petTypeName = this.petTypeFormatter.print(petType, Locale.ENGLISH);
        assertEquals("Hamster", petTypeName);
    }

    @Test
    void shouldParse() throws ParseException {
        Mockito.when(this.pets.findPetTypes()).thenReturn(makePetTypes());
        PetType petType = petTypeFormatter.parse("Bird", Locale.ENGLISH);
        assertEquals("Bird", petType.getName());
    }

    @Test
    void shouldThrowParseException() throws ParseException {
        assertThrows(ParseException.class, () -> {
            Mockito.when(this.pets.findPetTypes()).thenReturn(makePetTypes());
            petTypeFormatter.parse("Fish", Locale.ENGLISH);
        });
    }

    /**
     * Helper method to produce some sample pet types just for test purpose
     *
     * @return {@link Collection} of {@link PetType}
     */
    private List<PetType> makePetTypes() {
        List<PetType> petTypes = new ArrayList<>();
        petTypes.add(new PetType(){
                    {
                        setName("Dog");
                    }
                });
        petTypes.add(new PetType(){
                    {
                        setName("Bird");
                    }
                });
        return petTypes;
    }

}
