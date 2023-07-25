package com.luv2code.springbootlibrary.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.entity.Message;
import com.luv2code.springbootlibrary.entity.Review;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	private String theAllowedOrigins = "http://localhost:3000";
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] theUnsupportedActions = {
				HttpMethod.POST, 
				HttpMethod.PATCH, 
				HttpMethod.DELETE, 
				HttpMethod.PUT};
		config.exposeIdsFor(Book.class);
		config.exposeIdsFor(Review.class);
		config.exposeIdsFor(Message.class);
		disableHttpMethods(Book.class, config, theUnsupportedActions);
		/* Configure CORS Mapping*/
		disableHttpMethods(Review.class, config, theUnsupportedActions);
		cors.addMapping(config.getBasePath()+"/**")
		.allowedOrigins(theAllowedOrigins);
		disableHttpMethods(Message.class, config, theUnsupportedActions);
	}
	
	private void disableHttpMethods(Class theClass, 
									RepositoryRestConfiguration config, 
									HttpMethod[] theUnsupportedMethods) {
		config.getExposureConfiguration()
			.forDomainType(theClass)
			.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedMethods))
			.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedMethods));
	}
}
