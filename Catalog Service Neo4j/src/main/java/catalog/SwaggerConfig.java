package catalog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("beta")
public class SwaggerConfig {
	private String appName;
	private String appDescription;
	private String contactName;
	private String contactEmail;
	private String contactUrl;
	private String version;
	
	@Value("${spring.application.name:demo}")
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Value("${myservice.meta.description:unknown}")
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	@Value("${myservice.meta.version:unknown}")
	public void setVersion(String version) {
		this.version = version;
	}

	
	@Value("${myservice.meta.contact.name:unknown}")
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Value("${myservice.meta.contact.email:unknown@mail.mail}")
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	
	@Value("${myservice.meta.contact.url:unknown}")
	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(
					new ApiInfoBuilder()
						.title(appName)
						.description(appDescription)
						.contact(new Contact(
								contactName, 
								contactUrl, 
								contactEmail))
						.version(version)
						.build())
				.select()
				.build();
	}
	
}
