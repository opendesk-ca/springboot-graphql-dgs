package com.accounts.config;

import com.accounts.service.AddAccountDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    private final AddAccountDataFetcher addAccountDataFetcher;

    public GraphQLConfig(AddAccountDataFetcher addAccountDataFetcher) {
        this.addAccountDataFetcher = addAccountDataFetcher;
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                        .dataFetcher("addAccount", addAccountDataFetcher));
    }
}