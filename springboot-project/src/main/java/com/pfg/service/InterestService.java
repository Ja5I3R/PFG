package com.pfg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfg.interfaceService.IInterestService;
import com.pfg.interfaces.IInterest;
import com.pfg.models.Interest;

@Service
public class InterestService implements IInterestService{
	
    @Autowired
	private IInterest repository;

	@Override
	public Interest getInterestById(Long id){
		return repository.getById(id);
	}

    @Override
	@Transactional(readOnly = true)
	public List<Interest> listAllInterest() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Interest>listByIndexes(List<Long> list){
		List<Interest> result = new ArrayList<>();

		for(Long id : list){
			Interest actualInt = repository.findById(id).get();
			result.add(actualInt);
		}

		return result;
	}
}
