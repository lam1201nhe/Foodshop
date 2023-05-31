package controller;


import dal.FoodDAO;
import database.DBContext;
import java.sql.Connection;
import java.util.List;
import model.Food;

public class TestJDBCUtil {
        public static void main(String[] args)
    {
        Connection connection = DBContext.getConnection();
        
        
        
    }
    
    
    
    
    
/*
    public static void main(String[] args)
    {
        Connection connection = DBContext.getConnection();
        System.out.println(connection);
        
    }
    */
    
    /*
    public static void main(String[] args) {
        FoodDAO c = new FoodDAO();
        List<Food> list = c.getAll();
        System.out.println(list.get(0).getId()+ "--" + list.get(0).getName_food());
    }
    */
}
