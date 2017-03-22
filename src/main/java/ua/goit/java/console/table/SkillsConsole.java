package ua.goit.java.console.table;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.dao.SkillsDao;
import ua.goit.java.entity.Skill;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bulov on 07.03.2017.
 */
public class SkillsConsole extends TableConsole{

    private PlatformTransactionManager txManager;
    private Scanner scanner = new Scanner(System.in);
    private SkillsDao skillsDao;

    @Override
    public void runConsole() {
        System.out.println("Please chose what you want to do:");
        System.out.println("Press 1 - ADD SKILL");
        System.out.println("Press 2 - DELETE SKILL");
        System.out.println("Press 3 - UPDATE SKILL");
        System.out.println("Press 4 - GET ALL SKILLS");
        System.out.println("Press 5 - GET SKILL BY ID");
        System.out.println("Press 9 - RETURN TO START MENU");
        System.out.println("Press 0 - EXIT");
        System.out.print("Your choice > ");
        try{
            int choice = scanner.nextInt();
            checkChoice(this, choice);
        } catch (InputMismatchException e){
            System.out.print("\n!!! INPUT NOT CORRECT !!!\n");
            new SkillsConsole().runConsole();
        }
    }

    @Override
    public void add(){
        Skill skill = new Skill();
        System.out.print("Insert skill name: ");
        skill.setSkillName(scanner.next());
        skillsDao.save(skill);
    }

    @Override
    public void delete(){
        System.out.print("Insert skill id: ");
        skillsDao.delete(scanner.nextInt());
    }

    @Override
    public void update(){
        System.out.print("Insert id of skill that you want update: ");
        Skill skill = skillsDao.getById(scanner.nextInt());
        System.out.println("You chose: " + skill.toString());
        System.out.print("Insert new name for skill: ");
        skill.setSkillName(scanner.next());
        skillsDao.update(skill);
    }

    @Override
    public void getAll(){
        List<Skill> skills;
        skills = skillsDao.getAll();
        skills.forEach(System.out::println);
    }

    @Override
    public void getById(){
        System.out.print("Insert id of skill: ");
        System.out.println(skillsDao.getById(scanner.nextInt()));
    }

    public void setSkillsDao(SkillsDao skillsDao) {
        this.skillsDao = skillsDao;
    }
}
