package ua.goit.java.dao;

import ua.goit.java.entity.Company;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface CompaniesDao {

    void save(Company company);

    void delete(int companyId);

    void update(Company company);

    List<Company> getAll();

    Company getById(int companyId);
}
