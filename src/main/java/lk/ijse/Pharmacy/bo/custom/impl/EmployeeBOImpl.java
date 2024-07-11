package lk.ijse.Pharmacy.bo.custom.impl;
import lk.ijse.Pharmacy.bo.custom.EmployeeBO;
import lk.ijse.Pharmacy.dao.DAOFactory;
import lk.ijse.Pharmacy.dao.custom.EmployeeDAO;
import lk.ijse.Pharmacy.dto.EmployeeDTO;
import lk.ijse.Pharmacy.entity.Employee;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getE_id(),dto.getE_name(),dto.getIn_time(),dto.getOut_time(),dto.getDate()));
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmp= new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee c : all) {
            allEmp.add(new EmployeeDTO(c.getE_id(),c.getE_name(),c.getIn_time(), c.getOut_time(), c.getDate()));
        }
        return allEmp;
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getE_id(),dto.getE_name(),dto.getIn_time(),dto.getOut_time(),dto.getDate()));
    }

    @Override
    public boolean deleteEmployee(String e_name) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(e_name);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }
}
