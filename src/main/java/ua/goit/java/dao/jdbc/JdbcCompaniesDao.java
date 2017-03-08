package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.entity.Company;
import ua.goit.java.dao.CompaniesDao;
import ua.goit.java.entity.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcCompaniesDao implements CompaniesDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCompaniesDao.class);

    @Override
    public void addCompany(Company company) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                            .prepareStatement("INSERT INTO companies VALUES (?, ?)");
            statement.setInt(1, company.getCompanyId());
            statement.setString(2, company.getCompanyName());
            statement.execute();
            LOGGER.info("In table Companies was added " + company);
        } catch(SQLException e){
            LOGGER.error("Something wrong with add company in companies");
        }
    }

    @Override
    public void deleteCompany(int companyId) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("DELETE FROM companies WHERE company_id = ?");
            statement.setInt(1, companyId);
            statement.execute();
            LOGGER.info("From table Companies was deleting company with id = " + companyId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete company in companies. " + e);
        }
    }

    @Override
    public void updateCompany(Company company) {
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("UPDATE companies SET company_name = ? WHERE company_id = ?");
            statement.setString(1, company.getCompanyName());
            statement.setInt(2, company.getCompanyId());
            statement.execute();
            LOGGER.info("In table Companies was updating company with id = " + company.getCompanyId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating company in companies");
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try{
            Statement statement = new ConnectionFactory().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM companies");
            while(resultSet.next()) {
                companies.add(createCompany(resultSet));
            }
            LOGGER.info("All companies was received");
        } catch(SQLException e){
            LOGGER.error("Something wrong with receiving of all companies");
        }
        return companies;
    }

    @Override
    public Company getCompanyById(int companyId) {
        Company company = new Company();
        try{
            PreparedStatement statement = new ConnectionFactory().getConnection()
                    .prepareStatement("SELECT company_id, company_name FROM companies " +
                            "WHERE company_id = ?");
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                company.setCompanyName(resultSet.getString("company_name"));
                company.setCompanyId(resultSet.getInt("company_id"));
                LOGGER.info("Company with id = " + companyId + " is received");
            }
        } catch(SQLException e){
            LOGGER.error("Something wrong with getting company from companies with id = " + companyId);
        }
        return company;
    }

    private Company createCompany(ResultSet resultSet) throws SQLException{
        Company company = new Company();
        company.setCompanyId(resultSet.getInt("company_id"));
        company.setCompanyName(resultSet.getString("company_name"));
        return company;
    }

}


