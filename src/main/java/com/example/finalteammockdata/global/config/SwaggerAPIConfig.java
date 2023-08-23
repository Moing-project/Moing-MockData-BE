package com.example.finalteammockdata.global.config;

import com.example.finalteammockdata.global.jwt.JwtAuthenticationFilter;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.Optional;

@Configuration
public class SwaggerAPIConfig {

    private final ApplicationContext applicationContext;

    public SwaggerAPIConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(springSecurityLoginEndpointCustomizer())
                .build();
    }
    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointCustomizer() {
        FilterChainProxy filterChainProxy = applicationContext.getBean(
                AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<JwtAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(JwtAuthenticationFilter.class::isInstance)
                                .map(JwtAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    JwtAuthenticationFilter JwtAuthenticationFilter = optionalFilter.get();
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperties("email", new StringSchema())
                            .addProperties("password", new StringSchema());
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                    new MediaType().schema(schema)));
                    operation.requestBody(requestBody);
                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                    operation.responses(apiResponses);
                    operation.addTagsItem("auth-controller");
                    PathItem pathItem = new PathItem().post(operation);
                    openAPI.getPaths().addPathItem("/auth/login", pathItem);
                }
            }
        };
    }
}
