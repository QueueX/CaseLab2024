package org.greenatom.filestorageservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "File Storage Service",
                description = "Сервис работы с файлами. " +
                        "Разработано в рамках CaseLab 2024 Java",
                version = "1.0.0",
                contact = @Contact(
                        name = "Иван \"Kinok0\" Запара",
                        email = "zaparakinok0@gmail.com"
                )
        )
)
public class SwaggerConfiguration {
}
