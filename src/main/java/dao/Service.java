package dao;

import dto.Product;

import java.util.List;

public interface Service {
     List<Product> displayAllProduct() ;
     List<Product> displayProductByName(String productName );
}
