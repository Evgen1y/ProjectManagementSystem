package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.console.table.CompaniesConsole;
import ua.goit.java.entity.Company;
import ua.goit.java.dao.CompaniesDao;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulov on 03.03.2017.
 */
public class JdbcCompaniesDao implements CompaniesDao {

    private DataSource dataSource;
    private CompaniesConsole companiesConsole;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCompaniesDao.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void save(Company company) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO companies VALUES (?, ?)")){
            statement.setInt(1, company.getCompanyId());
            statement.setString(2, company.getCompanyName());
            statement.execute();
            LOGGER.info("In table Companies was saved " + company);
        } catch(SQLException e){
            LOGGER.error("Something wrong with saving company in companies");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(int companyId) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM companies WHERE company_id = ?")){
            statement.setInt(1, companyId);
            statement.execute();
            LOGGER.info("From table Companies was deleting company with id = " + companyId);
        } catch(SQLException e){
            LOGGER.error("Something wrong with delete company in companies. " + e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void update(Company company) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE companies SET company_name = ? WHERE company_id = ?")){
            statement.setString(1, company.getCompanyName());
            statement.setInt(2, company.getCompanyId());
            statement.execute();
            LOGGER.info("In table Companies was updating company with id = " + company.getCompanyId());
        } catch(SQLException e){
            LOGGER.error("Something wrong with updating company in companies");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection
                    .createStatement()){
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Company getById(int companyId) {
        Company company = new Company();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT company_id, company_name FROM companies " +
                            "WHERE company_id = ?")){
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                company = createCompany(resultSet);
                LOGGER.info("Company with id = " + companyId + " is received");
            }
        } catch(SQLException e){
            LOGGER.error("Something wrong with getting company from companies with id = " + companyId);
        }

        if(company.equals(new Company())){
            System.out.println("Company with this id doesn't exist.\nPlease, try again");
            companiesConsole.question(companiesConsole);
        }

        return company;
    }

    private Company createCompany(ResultSet resultSet) throws SQLException{
        Company company = new Company();
        company.setCompanyId(resultSet.getInt("company_id"));
        company.setCompanyName(resultSet.getString("company_name"));
        return company;
    }

    public void setCompaniesConsole(CompaniesConsole companiesConsole) {
        this.companiesConsole = companiesConsole;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}


