package ua.goit.java.entity;

/**
 * Created by bulov on 03.03.2017.
 */
public class Skill {

    private int skillId;
    private String skillName;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (getSkillId() != skill.getSkillId()) return false;
        return getSkillName() != null ? getSkillName().equals(skill.getSkillName()) : skill.getSkillName() == null;
    }

    @Override
    public int hashCode() {
        int result = getSkillId();
        result = 31 * result + (getSkillName() != null ? getSkillName().hashCode() : 0);
        return result;
    }
}
