package com.harera.ecommerce.framework.service.city;


import java.util.List;
import java.util.stream.Collectors;

import com.harera.ecommerce.framework.exception.EntityNotFoundException;
import com.harera.ecommerce.framework.repository.city.StateRepository;
import com.harera.ecommerce.framework.util.ErrorCode;
import com.harera.ecommerce.framework.model.city.State;
import com.harera.ecommerce.framework.model.city.StateResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class StateService {

    private final StateRepository stateRepository;
    private final ModelMapper mapper;

    @Autowired
    public StateService(StateRepository stateRepository, ModelMapper mapper) {
        this.stateRepository = stateRepository;
        this.mapper = mapper;
    }

    @Cacheable("states#id")
    public StateResponse get(long id) {
        State state = stateRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                                        ErrorCode.NOT_FOUND_STATE_ID));
        return mapper.map(state, StateResponse.class);
    }

    @Cacheable("states")
    public List<StateResponse> list() {
        List<State> stateList = stateRepository.findAll();
        return stateList.stream().map(state -> mapper.map(state,
                StateResponse.class)).collect(Collectors.toList());
    }
}
