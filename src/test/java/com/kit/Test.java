package com.kit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Created by luoyuanq on 2018/11/19.
 */
public class Test {

    class Employee {
        String name;
        String city;
        int sales;

        public Employee(String name, String city, int sales) {
            this.name = name;
            this.city = city;
            this.sales = sales;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        public int getSales() {
            return sales;
        }
    }

    @org.junit.Test
    public void test() {
       /* List<Employee> employees = Arrays.asList(new Employee("Alice" , "London", 150), new Employee("Bob", "London", 150), new Employee("Charles", "New York", 160), new Employee("Dorothy", "Hong Kong", 190));

        assertTrue( employees.stream().collect(Collectors.summingInt(Employee::getSales))==650);

         employees.stream().collect(Collectors.counting());
*/


    }

}
