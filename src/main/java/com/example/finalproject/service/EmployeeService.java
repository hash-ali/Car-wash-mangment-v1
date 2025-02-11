package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.BranchRepository;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.MerchantRepository;
import com.example.finalproject.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final MyUserRepository myUserRepository;
    private final MerchantRepository merchantRepository;

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }
    public Employee getById(Integer id){
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new ApiException("Employee not found");
        }
        return employee;
    }

//    public List<Employee> getMyEmployeeByMerchant(Integer auth){
//        MyUser myUser = myUserRepository.findMyUserById(auth);
//        Merchant merchant = merchantRepository.findMerchantById(myUser.getMerchant().getId());
//        List<Employee> employees = employeeRepository.findAllEmployeeByMerchant(merchant);
//        if (employees.isEmpty()){
//            throw new ApiException("Employee not found");
//        }
//        return employees;
//    }
    public List<Employee> getMyEmployeeByBranchId(Integer auth,Integer branchId){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        Branch branch = branchRepository.findBranchById(branchId);
        List<Employee> employees = employeeRepository.findAllEmployeeByBranchId(branchId);
        if (employees.isEmpty()){
            throw new ApiException("Employee not found");
        }else if(branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority!");
        }
        return employees;
    }


    public Branch getById(Integer id,Integer auth){
        Branch branch = branchRepository.findBranchById(id);
        if (branch == null) {
            throw new ApiException("Branch not found");
        }else if(branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority!");
        }
        return branch;
    }

    public void add(Employee employee){
        employeeRepository.save(employee);
    }

    public void update(Employee employee,Integer employeeId,Integer auth) {

        Employee oldEmp= employeeRepository.findEmployeeById(employeeId);
        if(oldEmp == null){
            throw new ApiException("Employee ID not found");
        }else if(oldEmp.getBranch().getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority !");
        }
//        oldEmp.setId(employee.getId());
        oldEmp.setName(employee.getName());
        oldEmp.setPhone(employee.getPhone());
        employeeRepository.save(oldEmp);
    }

    public void delete(Integer id,Integer auth) {
        Employee oldEmp= employeeRepository.findEmployeeById(id);
        if(oldEmp == null){
            throw new ApiException("Employee ID not found");
        }else if(oldEmp.getBranch().getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority !");
        }
        employeeRepository.delete(oldEmp);
    }

    ///////////////////////////////////////////////////
    // assign here

    public void addEmployeeToBranch(Employee employee, Integer branchId,Integer auth){
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null ) {
            throw new ApiException("Branch ID not found");
        }else if(branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority !");
        }
        employee.setBranch(branch);
        employeeRepository.save(employee);
    }
}
