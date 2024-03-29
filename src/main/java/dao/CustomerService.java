package dao;

import dto.Order;
import dto.Product;
import dto.User;

import java.sql.*;
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

    public  List<Order> displayAllOrders(User user)
    {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT order_id from order_info where user_id = "+user.getUserId() ;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next())
            {
                int ordId = rs.getInt(1);
                Order o1 = new Order();
                o1.setOrderId(ordId);
                orderList.add(o1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    public List<Product> displayAllProduct(int ordId) {
        List<Product> productList = new ArrayList<>();

        String selectProductQuery = "select p.product_id , p.product_name \n" +
                "from order_product op inner join product_info p\n" +
                "on op.product_id = p.product_id \n" +
                "where order_id = ? ;";

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectProductQuery);
            pstmt.setInt(1 , ordId);
            ResultSet rs = pstmt.executeQuery() ;

            while (rs.next())
            {
                int pId = rs.getInt(1);
                String pname = rs.getString(2);
                Product p = new Product(pId , pname);
                productList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }
}
