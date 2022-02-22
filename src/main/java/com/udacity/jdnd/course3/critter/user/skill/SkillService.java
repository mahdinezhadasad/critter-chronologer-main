package com.udacity.jdnd.course3.critter.user.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    public Skill create(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> findAll() {
        return (List<Skill>) skillRepository.findAll();
    }

    public Skill findById(Long id) {
        return skillRepository.findById(id).orElseThrow(UnsupportedOperationException::new);
    }
}
