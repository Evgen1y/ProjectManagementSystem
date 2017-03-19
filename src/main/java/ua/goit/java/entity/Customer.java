package ua.goit.java.entity;

/**
 * Created by bulov on 03.03.2017.
 */
public class Customer {

    private int customerId;
    private String customerName;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (getCustomerId() != customer.getCustomerId()) return false;
        return getCustomerName() != null ? getCustomerName().equals(customer.getCustomerName()) : customer.getCustomerName() == null;
    }

    @Override
    public int hashCode() {
        int result = getCustomerId();
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        return result;
    }
}
