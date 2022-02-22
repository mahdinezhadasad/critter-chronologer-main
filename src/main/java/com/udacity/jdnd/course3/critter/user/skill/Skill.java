package com.udacity.jdnd.course3.critter.user.skill;

import com.udacity.jdnd.course3.critter.user.employee.EmployeeSkill;

import javax.persistence.*;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private EmployeeSkill skill;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public EmployeeSkill getSkill() {
        return skill;
    }

    public void setSkill(EmployeeSkill skill) {
        this.skill = skill;
    }
}
