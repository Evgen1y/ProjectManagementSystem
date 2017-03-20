package ua.goit.java.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class Developer {

    private int developerId;
    private String name;
    private String surname;
    private int salary;
    private List<Skill> skills = new ArrayList<>();

    public void addSkill(Skill skill){
        skills.add(skill);
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "developerId=" + developerId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                ", skills=" + skills +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        if (getDeveloperId() != developer.getDeveloperId()) return false;
        if (getSalary() != developer.getSalary()) return false;
        if (getName() != null ? !getName().equals(developer.getName()) : developer.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(developer.getSurname()) : developer.getSurname() != null)
            return false;
        return getSkills() != null ? getSkills().equals(developer.getSkills()) : developer.getSkills() == null;
    }

    @Override
    public int hashCode() {
        int result = getDeveloperId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + getSalary();
        result = 31 * result + (getSkills() != null ? getSkills().hashCode() : 0);
        return result;
    }
}
