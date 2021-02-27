package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for {@link CrashController}
 *
 * @author Colin But
 */
@Disabled
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CrashController.class)
public class CrashControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testTriggerException() throws Exception {
        mockMvc.perform(get("/oups")).andExpect(view().name("exception"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(forwardedUrl("exception")).andExpect(status().isOk());
    }
}
