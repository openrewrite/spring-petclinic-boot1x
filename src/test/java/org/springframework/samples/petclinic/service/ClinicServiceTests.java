package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided by the Spring
 * TestContext Framework: </p> <ul> <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li> <li><strong>Dependency Injection</strong> of test fixture instances, meaning that
 * we don't need to perform application context lookups. <li><strong>Transaction management</strong>, meaning each test method is executed in its own transaction,
 * which is automatically rolled back by default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script. <li> An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean lookup if necessary. </li> </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClinicServiceTests {

    @Autowired
    protected OwnerRepository owners;

    @Autowired
    protected PetRepository pets;

    @Autowired
    protected VisitRepository visits;

    @Autowired
    protected VetRepository vets;

    @Test
    void shouldFindOwnersByLastName() {
        Collection<Owner> owners = this.owners.findByLastName("Davis");
        Assertions.assertEquals(2, owners.size());

        owners = this.owners.findByLastName("Daviss");
        Assertions.assertTrue(owners.isEmpty());
    }

    @Test
    void shouldFindSingleOwnerWithPet() {
        Owner owner = this.owners.findById(1);
        Assertions.assertTrue(owner.getLastName().startsWith("Franklin"));
        Assertions.assertEquals(1, owner.getPets().size());
        Assertions.assertNotNull(owner.getPets().get(0).getType());
        Assertions.assertEquals(owner.getPets().get(0).getType().getName(), "cat");
    }

    @Test
    @Transactional
    void shouldInsertOwner() {
        Collection<Owner> owners = this.owners.findByLastName("Schultz");
        int found = owners.size();

        Owner owner = new Owner();
        owner.setFirstName("Sam");
        owner.setLastName("Schultz");
        owner.setAddress("4, Evans Street");
        owner.setCity("Wollongong");
        owner.setTelephone("4444444444");
        this.owners.save(owner);
        Assertions.assertNotEquals(0, owner.getId().longValue());

        owners = this.owners.findByLastName("Schultz");
        Assertions.assertEquals(found + 1, owners.size());
    }

    @Test
    @Transactional
    void shouldUpdateOwner() {
        Owner owner = this.owners.findById(1);
        String oldLastName = owner.getLastName();
        String newLastName = oldLastName + "X";

        owner.setLastName(newLastName);
        this.owners.save(owner);

        // retrieving new name from database
        owner = this.owners.findById(1);
        Assertions.assertEquals(newLastName, owner.getLastName());
    }

    @Test
    void shouldFindPetWithCorrectId() {
        Pet pet7 = this.pets.findById(7);
        Assertions.assertTrue(pet7.getName().startsWith("Samantha"));
        Assertions.assertEquals(pet7.getOwner().getFirstName(), "Jean");
    }

    @Test
    void shouldFindAllPetTypes() {
        Collection<PetType> petTypes = this.pets.findPetTypes();

        PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
        Assertions.assertEquals(petType1.getName(), "cat");
        PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
        Assertions.assertEquals(petType4.getName(), "snake");
    }

    @Test
    @Transactional
    void shouldInsertPetIntoDatabaseAndGenerateId() {
        Owner owner6 = this.owners.findById(6);
        int found = owner6.getPets().size();

        Pet pet = new Pet();
        pet.setName("bowser");
        Collection<PetType> types = this.pets.findPetTypes();
        pet.setType(EntityUtils.getById(types, PetType.class, 2));
        pet.setBirthDate(new Date());
        owner6.addPet(pet);
        Assertions.assertEquals(found + 1, owner6.getPets().size());

        this.pets.save(pet);
        this.owners.save(owner6);

        owner6 = this.owners.findById(6);
        Assertions.assertEquals(found + 1, owner6.getPets().size());
        // checks that id has been generated
        Assertions.assertNotNull(pet.getId());
    }

    @Test
    @Transactional
    void shouldUpdatePetName() throws Exception {
        Pet pet7 = this.pets.findById(7);
        String oldName = pet7.getName();

        String newName = oldName + "X";
        pet7.setName(newName);
        this.pets.save(pet7);

        pet7 = this.pets.findById(7);
        Assertions.assertEquals(newName, pet7.getName());
    }

    @Test
    void shouldFindVets() {
        Collection<Vet> vets = this.vets.findAll();

        Vet vet = EntityUtils.getById(vets, Vet.class, 3);
        Assertions.assertEquals(vet.getLastName(), "Douglas");
        Assertions.assertEquals(2, vet.getNrOfSpecialties());
        Assertions.assertEquals(vet.getSpecialties().get(0).getName(), "dentistry");
        Assertions.assertEquals(vet.getSpecialties().get(1).getName(), "surgery");
    }

    @Test
    @Transactional
    void shouldAddNewVisitForPet() {
        Pet pet7 = this.pets.findById(7);
        int found = pet7.getVisits().size();
        Visit visit = new Visit();
        pet7.addVisit(visit);
        visit.setDescription("test");
        this.visits.save(visit);
        this.pets.save(pet7);

        pet7 = this.pets.findById(7);
        Assertions.assertEquals(found + 1, pet7.getVisits().size());
        Assertions.assertNotNull(visit.getId());
    }

    @Test
    void shouldFindVisitsByPetId() throws Exception {
        Collection<Visit> visits = this.visits.findByPetId(7);
        Assertions.assertEquals(2, visits.size());
        Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
        Assertions.assertNotNull(visitArr[0].getDate());
        Assertions.assertEquals(7, visitArr[0].getPetId().longValue());
    }

}
