package com.zephyr.task.assemblers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.errors.exceptions.InconsistentModelException;
import com.zephyr.task.clients.AgentServiceClient;
import com.zephyr.task.clients.LocationServiceClient;
import com.zephyr.task.data.TaskTestData;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.test.CommonTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.test.StepVerifier;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class QueryAssemblerTest {

    @Mock
    private AgentServiceClient agentServiceClient;

    @Mock
    private LocationServiceClient locationServiceClient;

    @Mock
    private ExtendedMapper mapper;

    @InjectMocks
    private QueryAssembler testInstance;

    @Before
    public void setUp() {
        when(agentServiceClient.findOneByExample(anyString(), anyString(), anyString()))
                .thenReturn(null);

        when(locationServiceClient.findByCountryIsoAndNameContains(anyString(), anyString()))
                .thenReturn(List.of());

        when(mapper.mapperFor(QueryDto.class))
                .thenReturn(t -> CommonTestData.queries().simple());
    }

    @Test
    public void shouldNotAssemble() {
        SearchCriteria simple = TaskTestData.criteria().simple();

        StepVerifier.create(testInstance.assemble(simple))
                .expectError(InconsistentModelException.class)
                .verify();
    }
}