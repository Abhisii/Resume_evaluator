package com.resumechecker.resumeevaluator;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Transaction Rules API 1",
                version = "1.0",
                description = """
                This API allows you to:
                - Upload and evaluate transactions
                - Apply custom rule sets via Drools
                - Fetch rule violation history and scoring details
                
                Built using Spring Boot 3.4.1 and MySQL, and integrated with Swagger UI using springdoc-openapi.
                """,
                termsOfService = "https://frm.com/terms",
                contact = @Contact(
                        name = "Abhishek Sharma",
                        email = "abhishek.sharma@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Full Project Documentation",
                url = "https://github.com/yourusername/transaction-rules-docs"
        )

)
public class SwaggerConfig {
    // All configuration is via annotation — no need for methods
}
