package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductionConfigurationTests {

    @Autowired
    private VetRepository vets;

    @Test
    void testFindAll() throws Exception {
        vets.findAll();
        vets.findAll();
 // served from cache
    }
}
