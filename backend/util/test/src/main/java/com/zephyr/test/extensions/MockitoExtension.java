package com.zephyr.test.extensions;

import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Parameter;
import java.util.Optional;

public class MockitoExtension implements TestInstancePostProcessor, ParameterResolver {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        MockitoAnnotations.initMocks(testInstance);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().isAnnotationPresent(Mock.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return getMock(parameterContext.getParameter(), extensionContext);
    }

    private Object getMock(Parameter parameter, ExtensionContext extensionContext) {
        Class<?> mockType = parameter.getType();
        ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(MockitoExtension.class, mockType);
        ExtensionContext.Store mocks = extensionContext.getStore(namespace);

        return getMockName(parameter)
                .map(n -> mocks.getOrComputeIfAbsent(n, key -> mock(mockType, n)))
                .orElseGet(() -> mocks.getOrComputeIfAbsent(mockType.getCanonicalName(), key -> mock(mockType)));
    }

    private Optional<String> getMockName(Parameter parameter) {
        String explicitMockName = parameter.getAnnotation(Mock.class)
                .name()
                .trim();

        return Optional.of(explicitMockName)
                .filter(StringUtils::isNotEmpty)
                .or(() -> Optional.of(parameter).filter(Parameter::isNamePresent).map(Parameter::getName));
    }
}