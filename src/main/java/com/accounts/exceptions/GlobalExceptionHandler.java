package com.accounts.exceptions;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Map<String, Object> extMap = new HashMap<>();

    @GraphQlExceptionHandler
    public GraphQLError handle(@NonNull TransactionExistsException ex, @NonNull DataFetchingEnvironment environment) {
        extMap.put("errorCode", "TXN_ALREADY_EXISTS");
        extMap.put("timestamp", Instant.now().toString());
        extMap.put("actionableSteps", "Please provide a new txn id and try again.");

        return GraphQLError
                .newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message("Sorry, this txn already exists : " + ex.getMessage())
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .extensions(extMap)
                .build();
    }
}
