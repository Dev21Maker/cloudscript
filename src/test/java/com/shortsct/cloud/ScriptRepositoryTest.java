package com.shortsct.cloud;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.shortsct.cloud.model.Script;
import com.shortsct.cloud.repository.ScriptsRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ScriptRepositoryTest {

    @MockBean
    private ScriptsRepository scriptsRepository;

    @Test
    public void testSaveAndFindScript() {
        Script script = new Script();
        // Mock the save method
        when(scriptsRepository.save(script)).thenReturn(script);
        // Mock the findById method
        when(scriptsRepository.findById(script.getId())).thenReturn(Optional.of(script));
        // Save the script
        scriptsRepository.save(script);
        // Find the script
        Optional<Script> foundScript = scriptsRepository.findById(script.getId());
        // Assertions
        assertTrue(foundScript.isPresent());
        assertEquals(script.getTitle(), foundScript.get().getTitle());
    }
}
