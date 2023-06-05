/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.TMT;

import database.DBContext;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.TMT.MenuDaily;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;

public class HomeDAO extends DBContext {
    
    
    /*
    
    
    
    */
    public List<MenuDaily> getFoodMenu() {
        Connection con = DBContext.getConnection();
        List<MenuDaily> list = new ArrayList<>();
        //String sql="select * from Categories";
        String sql = "select food.id, "
                + "food.name_food, "
                + "food.describe_food, "
                + "food.price_sell, "
                + "food.img, "
                + "menudaily.discout, "
                + "menudaily.quantity, "
                + "food.price_sell*menudaily.discout price_final "
                + "from food, menudaily "
                + "where menudaily.id_food = food.id ";
        // + "order by menudaily.discout DESC";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new MenuDaily(
                        rs.getInt("id"),
                        rs.getString("name_food"),
                        rs.getString("describe_food"),
                        rs.getInt("price_sell"),
                        rs.getString("img"),
                        rs.getFloat("discout"),
                        rs.getInt("quantity"),
                        rs.getInt("price_final")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<MenuDaily> getFoodSale(List<MenuDaily> foodmenu) {

        ArrayList<MenuDaily> foodsale = new ArrayList<MenuDaily>();

        for (int i = 0; i < foodmenu.size(); i++) {
            if (foodmenu.get(i).getDiscout() < 1) {
                foodsale.add(new MenuDaily(foodmenu.get(i).getId(),
                        foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                        foodmenu.get(i).getDiscout()));
            }
        }

        Collections.sort(foodsale, new Comparator<MenuDaily>() {
            @Override
            public int compare(MenuDaily o1, MenuDaily o2) {
                return Float.compare(o1.getDiscout(), o2.getDiscout());
            }
        });
        return foodsale;
    }

    public List<MenuDaily> getFoodCheap(List<MenuDaily> foodmenu) {

        int priceneeded = getPriceNeeded(foodmenu);

        ArrayList<MenuDaily> foodcheap = new ArrayList<MenuDaily>();

        for (int i = 0; i < foodmenu.size(); i++) {
            if (foodmenu.get(i).getPrice_final() <= priceneeded) {
                foodcheap.add(new MenuDaily(foodmenu.get(i).getId(),
                        foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                        foodmenu.get(i).getPrice_final()));
            }
        }

        Collections.sort(foodcheap, new Comparator<MenuDaily>() {
            @Override
            public int compare(MenuDaily o1, MenuDaily o2) {
                return o1.getPrice_final() - o2.getPrice_final();
            }
        });
        return foodcheap;
    }

    public int getPriceNeeded(List<MenuDaily> foodmenu) {
        float count = 0;
        for (int i = 0; i < foodmenu.size(); i++) {
            count += (float) foodmenu.get(i).getPrice_final();
        }

        try {
            count = (((count / (foodmenu.size() + 1)) / 100) * 30);
        } catch (Exception e) {
            System.out.println("Error");
            count = 0;
        }
        //System.out.println("Gia re " + count);
        return (int) count;
    }

    public boolean checkInputSearch(String s) {

        boolean dk;

        if (s.isEmpty() || s.equals("")) {
            dk = false;
        } else if (s.length() < 3 && s.length() > 10) {
            dk = false;
        } else {
            dk = true;
        }
        return dk;
    }

    public List<MenuDaily> getSearch(List<MenuDaily> foodmenu, String s) {

        ArrayList<MenuDaily> foodsearch = new ArrayList<MenuDaily>();

        for (int i = 0; i < foodmenu.size(); i++) {
            if (foodmenu.get(i).getName_food().toLowerCase().contains(s.toLowerCase())) {
                foodsearch.add(new MenuDaily(foodmenu.get(i).getId(),
                        foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                        foodmenu.get(i).getDiscout()));
            }
        }

        Collections.sort(foodsearch, new Comparator<MenuDaily>() {
            @Override
            public int compare(MenuDaily o1, MenuDaily o2) {
                //return o1.get - o2.getPrice_final();
                return getTen(o1.getName_food().toLowerCase()).compareTo(o2.getName_food().toLowerCase());
            }
        });
        return foodsearch;
    }

    public String getTen(String s) {
        s = s.trim();
        if (s.indexOf(" ") >= 0) {
            int vt = s.lastIndexOf(" ");
            return s.substring(vt + 1);
        } else {
            return s;
        }
    }

    public String delSpace(String s) {
        s = s.trim();
        String[] t;
        String p = "";
        t = s.split("[ ]+");
        for (int i = 0; i < t.length; i++) {
            p += t[i] + " ";
        }
        return p;
    }

    public List<MenuDaily> setDisplay(List<MenuDaily> foodmenu, int a, int b) {
        a = ((a * 3) - 1);

        ArrayList<MenuDaily> displayfood = new ArrayList<MenuDaily>();

        if (b == 1) {
            if (a < foodmenu.size()) {
                for (int i = 0; i < a; i++) {
                    displayfood.add(new MenuDaily(foodmenu.get(i).getId(),
                            foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                            foodmenu.get(i).getDiscout()));
                    //System.out.println(foodmenu.get(i).getName_food()+ foodmenu.get(i).getPrice_final());

                }
            } else if (a >= foodmenu.size()) {
                a = foodmenu.size();
                for (int i = 0; i < a; i++) {
                    displayfood.add(new MenuDaily(foodmenu.get(i).getId(),
                            foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                            foodmenu.get(i).getDiscout()));
                    //System.out.println(foodmenu.get(i).getName_food()+ foodmenu.get(i).getPrice_final());
                }
            }
        } else if (b == 2) {
            if (a < foodmenu.size()) {
                for (int i = 0; i < a; i++) {
                    System.out.println(foodmenu.get(i).getName_food());
                    displayfood.add(new MenuDaily(foodmenu.get(i).getId(),
                            foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                            foodmenu.get(i).getPrice_final()));
                }

            } else if (a >= foodmenu.size()) {
                a = foodmenu.size();
                for (int i = 0; i < a; i++) {
                    System.out.println(foodmenu.get(i).getName_food());
                    displayfood.add(new MenuDaily(foodmenu.get(i).getId(),
                            foodmenu.get(i).getName_food(), foodmenu.get(i).getImg(),
                            foodmenu.get(i).getPrice_final()));
                }

            }

        }
        return displayfood;
    }

    public String getMenu(Model model) {
        List<MenuDaily> menuItems = menuDaialyDAO.getAll();
        model.addAttribute("menuItems", menuItems);
        return "menu";
    }

    public static void main(String[] args) {
        HomeDAO c = new HomeDAO();

        List<MenuDaily> list1 = c.getFoodMenu();
        List<MenuDaily> list2 = c.getFoodCheap(list1);
        List<MenuDaily> list3 = c.getFoodSale(list1);
        List<MenuDaily> list = c.setDisplay(list2, 1, 1);
        //System.out.println(" gia trung binh la " + c.getPriceNeeded(list1));
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(" id = " + list.get(i).getId() + " name = " + list.get(i).getName_food() + " img = " + list.get(i).getImg() + " dis = " + list.get(i).getDiscout());
//        }
//        System.out.println(c.delSpace("   bắp        cải                to     "));

    }

}
