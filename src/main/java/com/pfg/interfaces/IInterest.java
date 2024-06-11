package com.pfg.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfg.models.Interest;

@Repository
public interface IInterest extends JpaRepository<Interest, Long>{

}
