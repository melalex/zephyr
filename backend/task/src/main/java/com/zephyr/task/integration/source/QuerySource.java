package com.zephyr.task.integration.source;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.services.SearchCriteriaService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class QuerySource implements MessageSource<Flux<QueryDto>> {

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private Assembler<SearchCriteria, QueryDto> queryAssembler;

    @Override
    public Message<Flux<QueryDto>> receive() {
        return new GenericMessage<>(getQueries());
    }

    private Flux<QueryDto> getQueries() {
        return searchCriteriaService.findAllForUpdate()
                .flatMap(queryAssembler::assemble);
    }
}
