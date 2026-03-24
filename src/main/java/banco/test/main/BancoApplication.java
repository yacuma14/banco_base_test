package banco.test.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication(scanBasePackages = {
        "banco.test.*"
})

public class BancoApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(BancoApplication.class).
                build().
                run(args);
    }


}
