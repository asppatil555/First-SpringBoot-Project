package com.Spring.repository;

import com.Spring.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
                                                        //EntityClass, wrapperclass
public interface DeveloperRepository extends JpaRepository<Developer, Integer>
{


}
