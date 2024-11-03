package telran.employee.dao;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

import java.util.function.Predicate;

public class CompanyImpl implements Company {
    private Employee[] employees;
    private int size;

    public CompanyImpl(int capacity) {
       employees = new Employee[capacity];
    }

    @Override
    public boolean addEmployee(Employee employee) {
        if(employee == null || size == employees.length || findEmployee(employee.getId()) != null) {
            return false;
        }
        employees[size++] = employee;
        return true;
    }

    @Override
    public Employee removeEmployee(int id) {
        for (int i = 0; i < size; i++) {
            if(employees[i].getId() == id){
                Employee removed = employees[i];
                for (int j = i; j < size - 1; j++) {
                    employees[j] = employees[j+1];
                }
                employees[size-1] = null;
                size--;
                return removed;
            }
        }
        return null;
    }

//    @Override
//    public Employee removeEmployee(int id) {
//        for (int i = 0; i < size; i++) {
//            if(employees[i].getId() == id){
//                Employee removed = employees[i];
//                employees[i] = employees[--size];
//                employees[size] = null;
//                return removed;
//            }
//        }
//        return null;
//    }

    @Override
    public Employee findEmployee(int id) {
        for (int i = 0; i < size; i++) {
            if(employees[i].getId() == id){
                return employees[i];
            }
        }
        return null;
    }

    @Override
    public int quantity() {
        return size;
    }

    @Override
    public double totalSalary() {
        double sum = 0;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                sum += employees[i].calcSalary();
            }
        }
        return sum;
    }

    @Override
    public double totalSales() {
        double sum = 0;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] instanceof SalesManager salesManager) {
                sum += salesManager.getSalesValue();
            }
        }
        return sum;
    }

    @Override
    public void printEmployees() {
        System.out.println("=== " + Company.COUNTRY + " ===");
        for (int i = 0; i < size; i++) {
            if(employees[i] != null) {
                System.out.println(employees[i]);
            }
        }
    }

    @Override
    public Employee[] findEmployeesHoursGreaterThan(int hours) {
        Predicate<Employee> predicate = e -> e.getHours() > hours;
        return findEmployeesByPredicate(predicate);
    }

    @Override
    public Employee[] findEmployeesSalaryBetween(int minSalary, int maxSalary) {
        return findEmployeesByPredicate(e -> e.calcSalary() >= maxSalary && e.calcSalary() < maxSalary);
    }

    private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if(predicate.test(employees[i])) {
                count++;
            }
        }
        Employee[] res = new Employee[count];
        for (int i = 0, j = 0; j < res.length; i++) {
            if(predicate.test(employees[i])) {
                res[j++] = employees[i];
            }
        }
        return res;
    }
}
