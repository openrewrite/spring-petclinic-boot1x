package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Michael Isvy
 *         Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
public class ValidatorTests {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void shouldNotValidateWhenFirstNameEmpty() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertEquals(1, constraintViolations.size());
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertEquals("firstName", violation.getPropertyPath().toString());
        assertEquals("may not be empty", violation.getMessage());
    }

}
