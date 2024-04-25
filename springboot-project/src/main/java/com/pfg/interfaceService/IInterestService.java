package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.Interest;

public interface IInterestService {

    public List<Interest>listAllInterest();

    public List<Interest>listByIndexes(List<Long> list);

}
