package ua.goit.java.dao;

import ua.goit.java.entity.Company;

import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public interface CompaniesDao {

    void addCompany (Company company);

    void deleteCompany (int companyId);

    void updateCompany (Company company);

    List<Company> getAllCompanies();

    Company getCompanyById (int companyId);
}
