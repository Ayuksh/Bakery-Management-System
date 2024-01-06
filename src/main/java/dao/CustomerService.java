package dao;

import dto.Order;
import dto.Product;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerService extends ServiceImpl{
    @Override
    public List<Product> displayAllProduct() {
        String selectQuery = "SELECT product_name , product_price FROM product_info ";
        List<Product> productList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                String name = rs.getString(1);
                double price = rs.getDouble(2);
                Product pro = new Product(name , price);
                productList.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
    }

    @Override
    public List<Product> displayProductByName(String productName) {
        return null;
    }

    public boolean placeOrder(Order ord) throws SQLException {
        String insertUserProcedure = "{call insertUser(?)}";
        String placeOrderProcedure = "{call placeOrder(? , ? , ?)}";
        CallableStatement cstmt = conn.prepareCall(insertUserProcedure);
        cstmt.setInt(1 , ord.getUser().getUserId());
        cstmt.execute() ;
       ResultSet rs = cstmt.getResultSet();
        int ordId = 0 ;
        while (rs.next())
            ordId = rs.getInt(1);

        cstmt = conn.prepareCall(placeOrderProcedure);

        for (Product p : ord.getProductList()) {
            cstmt.setInt(1, ordId);
            cstmt.setString(2, p.getProductName() );
            cstmt.setInt(3 , p.getProductQty());
            cstmt.execute();
        }
        return true ;
    }
}
