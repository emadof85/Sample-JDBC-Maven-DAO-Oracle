package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DepartmentDAO extends DBConnection{
    public DepartmentDAO() {
        super();
    }

    public List<SampleDepartment> getAllDepartments(){
        List<SampleDepartment> departments = new ArrayList<>();
        SampleDepartment department;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from departments");
            while (rs.next()){
                department = new SampleDepartment();
                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                department.setManager_id(rs.getInt("manager_id"));
                department.setLocation_id(rs.getInt("location_id"));
                departments.add(department);
            }
        } catch (SQLException e) {
            System.out.println("Unable to fetch records !!");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Unable to close the statement !!");
                }
            }
        }
        return departments;
    }
    public SampleDepartment getDepartmentById(int id){
        SampleDepartment department = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM departments WHERE department_id = ?");
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                department = new SampleDepartment();
                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                department.setManager_id(rs.getInt("manager_id"));
                department.setLocation_id(rs.getInt("location_id"));
            } else {
                System.out.println(String.format("No department found for id = %d", id));
            }
        } catch (SQLException e) {
            System.out.println(String.format("Unable to query department by ID(%d)", id));
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Unable to close statement !!");
                }
            }
        }
        return department;
    }
    public boolean insertDepartment(SampleDepartment department){
        boolean success = false;

        if (department != null) {
            if (conn != null) {
                PreparedStatement pStmt = null;

                try {
                    pStmt = conn.prepareStatement("INSERT INTO departments VALUES (?, ?, ?, ?)");
                    pStmt.setInt(1, department.getDepartment_id());
                    pStmt.setString(2, department.getDepartment_name());
                    pStmt.setInt(3, department.getManager_id());
                    pStmt.setInt(4, department.getLocation_id());

                    success = (pStmt.executeUpdate() == 1);
                } catch (SQLException e) {
                    System.out.println(String.format("Unable to insert department %d into the table", department.getDepartment_id()));
                } finally {
                    if (pStmt != null) {
                        try {
                            pStmt.close();
                        } catch (SQLException e) {
                            System.out.println("Unable to close statement !!");
                        }
                    }
                }
            }
        }
        return success;
    }
    public boolean updateDepartment(SampleDepartment department){
        boolean success = false;

        if (department != null) {
            if (conn != null) {
                PreparedStatement pStmt = null;

                try {
                    pStmt = conn.prepareStatement("update departments set DEPARTMENT_NAME=?, MANAGER_ID=?, LOCATION_ID=? where DEPARTMENT_ID=?");
                    pStmt.setString(1, department.getDepartment_name());
                    pStmt.setInt(2, department.getManager_id());
                    pStmt.setInt(3, department.getLocation_id());
                    pStmt.setInt(4, department.getDepartment_id());

                    success = (pStmt.executeUpdate() == 1);
                } catch (SQLException e) {
                    System.out.println(String.format("Unable to update department %d", department.getDepartment_id()));
                } finally {
                    if (pStmt != null) {
                        try {
                            pStmt.close();
                        } catch (SQLException e) {
                            System.out.println("Unable to close statement !!");
                        }
                    }
                }
            }
        }
        return success;
    }
    public void example_MetaData(){
        try {
            PreparedStatement ps=conn.prepareStatement("select * from departments");
            ResultSet rs=ps.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int cols_num = rsmd.getColumnCount();
            System.out.println("Total columns: "+cols_num);
            for (int i = 1; i <= cols_num; i++){
                System.out.println("ColName: "+rsmd.getColumnName(i) + ", ColType: "+rsmd.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            System.out.println("Unable to fetch records !!");
        }

    }
    public void example_DB_MetaData(){
        try {
            DatabaseMetaData dbmd = conn.getMetaData();

            System.out.println("\nDriver Info\n-------------------");
            System.out.println("Driver Name: "+dbmd.getDriverName());
            System.out.println("Driver Version: "+dbmd.getDriverVersion());
            System.out.println("UserName: "+dbmd.getUserName());
            System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
            System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());

            System.out.println("\nAvailable Schemas\n-------------------");
            try(ResultSet schemas = dbmd.getSchemas()){
                while (schemas.next()){
                    String table_schem = schemas.getString("TABLE_SCHEM");
                    String table_catalog = schemas.getString("TABLE_CATALOG");
                    System.out.println(table_schem + ", " + table_catalog);
                }
            }

            String table[]={"TABLE"};
            ResultSet rs=dbmd.getTables(null,"HR",null,table);
            System.out.println("\nHR Tables\n-------------------");
            while(rs.next()){
                System.out.println(rs.getString("TABLE_NAME"));
            }

            System.out.println("\n Department PKs\n-------------------");
            ResultSet rs_pk = dbmd.getPrimaryKeys(null, "HR", "DEPARTMENTS");
            while(rs_pk.next()){
                System.out.println(rs_pk.getString("KEY_SEQ") + ", "
                        + rs_pk.getString("COLUMN_NAME"));
            }

            System.out.println("\n Department FKs\n-------------------");
            try(ResultSet foreignKeys = dbmd.getImportedKeys(null, "HR", "DEPARTMENTS")){
                while(foreignKeys.next()){
                    String pkTableName = foreignKeys.getString("PKTABLE_NAME");
                    String fkTableName = foreignKeys.getString("FKTABLE_NAME");
                    String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                    String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                    System.out.println( "ref_table: " + pkTableName + ", ref_key: " + pkColumnName
                            + ", " + "fk_table: " + fkTableName + ", fk_key: " + fkColumnName);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
