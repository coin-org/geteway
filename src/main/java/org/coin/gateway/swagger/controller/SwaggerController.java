package org.coin.gateway.swagger.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
public class SwaggerController implements SwaggerResourcesProvider {

	private final RouteLocator routeLocator;

	public SwaggerController(RouteLocator routeLocator) {
		this.routeLocator = routeLocator;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = routeLocator.getRoutes().stream().distinct().map(route -> {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(route.getId() + "-" + route.getLocation());
			swaggerResource.setLocation(route.getLocation() + route.getPath().replace("**", "v2/api-docs"));
			swaggerResource.setSwaggerVersion(DocumentationType.SWAGGER_2.getVersion());

			return swaggerResource;

		}).collect(Collectors.toList());

		return resources;
	}

}
