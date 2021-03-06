package ua.goit.java.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by bulov on 03.03.2017.
 */

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "company_id")
    private int companyId;

    @Column(name = "company_name")
    private String companyName;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (getCompanyId() != company.getCompanyId()) return false;
        return getCompanyName() != null ? getCompanyName().equals(company.getCompanyName()) : company.getCompanyName() == null;
    }

    @Override
    public int hashCode() {
        int result = getCompanyId();
        result = 31 * result + (getCompanyName() != null ? getCompanyName().hashCode() : 0);
        return result;
    }
}
