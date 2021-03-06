package com.yjl.mybatis;


import com.yjl.mybatis.entity.Emp;
import com.yjl.mybatis.entity.TbOrder;
import com.yjl.mybatis.mapper.EmployeeMapper;
import com.yjl.mybatis.mapper.OrderMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImprovedMybatisTest {

    private SqlSession session;

    @Test
    public void testGetOrderWithCustomer() {
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        TbOrder order = orderMapper.selectOrderWithCustomer(1);
        System.out.println("order = " + order);
    }

    @Test
    public void testSelectWithResultMap() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        List<Emp> empList = employeeMapper.selectWithResultMap();
        for (Emp emp : empList) {
            System.out.println("emp = " + emp);
        }
    }

    @Test
    public void testGetGeneratedKey() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Emp emp = new Emp(null, "Jojo", 666.11);
        int rowCount = employeeMapper.insertWithKey(emp);
        System.out.println("rowCount = " + rowCount);
        Long empId = emp.getEmpId();
        System.out.println("emp_id = " + empId);
    }

    @Test
    public void testSelectForList() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        List<Emp> empList = employeeMapper.selectAll();
        for (Emp emp : empList) {
            System.out.println("emp = " + emp);
        }
    }

    @Test
    public void testSelectForMap() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Map<String, Object> mapResult = employeeMapper.selectForMap(1);
        Set<String> keySet = mapResult.keySet();
        for (String key : keySet) {
            Object value = mapResult.get(key);
            System.out.println(key + " = " + value);
        }
    }

    @Test
    public void testSelectCount() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Integer count = employeeMapper.selectCount();
        System.out.println("count = " + count);
    }

    @Test
    public void testUpdateEmpByMap() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("emp_idKey", 1L);
        paramMap.put("emp_nameKey", "Justin");
        paramMap.put("emp_salaryKey", 7777.77);
        employeeMapper.updateByMap(paramMap);
    }

    @Test
    public void testUpdateSalaryById() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Long emp_id = 1L;
        Double salary = 666.66;
        employeeMapper.updateSalaryById(emp_id, salary);
    }

    @Test
    public void testDollar() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Emp emp = employeeMapper.selectEmpByName("o");
        System.out.println("emp = " + emp);
    }

    @Test
    public void testUpdate() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Emp empById = employeeMapper.selectEmpById(5L);
        if (null == empById) {
            return;
        }
        empById.setEmpName("harry");
        empById.setEmpSalary(999.99);
        employeeMapper.updateEmp(empById);

    }

    @Test
    public void testDelete() {
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        int rowCount = employeeMapper.deleteById(4);
        System.out.println("rowCount = " + rowCount);
    }

    @Test
    public void testInsert() {
        // 1.??????Mapper??????
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        Emp emp = new Emp();
        emp.setEmpName("jerry");
        emp.setEmpSalary(1111.111);
        // 2.??????Mapper???????????????
        int rowCount = employeeMapper.insertEmp(emp);
        // 3.????????????????????????
        System.out.println("rowCount = " + rowCount);
    }

    @Test
    public void testUsrMapperInterface() {
        // 1.??????EmployeeMapper?????????Class????????????Mapper?????????????????????
        EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
        System.out.println("employeeMapper.getClass().getName() = " + employeeMapper.getClass().getName());
        // 2.??????EmployeeMapper??????????????????????????????????????????
        Emp emp = employeeMapper.selectEmpById(1L);
        // 3.??????????????????
        System.out.println("emp = " + emp);
    }

    // junit???????????????@Test???????????????@Before??????
    @Before
    public void init() throws IOException {
        session = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession();
    }

    // junit???????????????@Test???????????????@After??????
    @After
    public void clear() {
        session.commit();
        session.close();
    }

}
