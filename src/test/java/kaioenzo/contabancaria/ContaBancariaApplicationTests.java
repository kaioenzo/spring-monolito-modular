package kaioenzo.contabancaria;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ContaBancariaApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void verificarModularidade() {
        ApplicationModules.of(ContaBancariaApplication.class).verify();
    }

    @Test
    void createModuleDocumentation() {
        ApplicationModules modules = ApplicationModules.of(ContaBancariaApplication.class);
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }
}
