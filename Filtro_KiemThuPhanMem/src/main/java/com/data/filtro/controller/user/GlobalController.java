package com.data.filtro.controller.user;

import com.data.filtro.model.*;
import com.data.filtro.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ControllerAdvice
@RequestMapping({"/", "/product", "/category", "/search"})
public class GlobalController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    OriginService flavorService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        List<Category> categories = categoryService.get5Categories();
        return categories;
    }

    @ModelAttribute("products")
    public List<Product> getProducts() {
        List<Product> productList = productService.getAll();
        return productList;
    }

    @ModelAttribute("flavors")
    public List<Origin> getFlavors() {
        List<Origin> flavors = flavorService.getAll();
        return flavors;
    }

    @ModelAttribute("cartItemList")
    public List<CartItem> cartItemList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        GuestCart guestCart = (GuestCart) session.getAttribute("guestCart");
        if (user != null) {
            Cart cart = cartService.getCurrentCartByUserId(user.getId());
            if (cart != null) {
                List<CartItem> cartItemList = cart.getCartItemList();
                return cartItemList;
            }
        } else if (guestCart != null) {
            List<CartItem> cartItemList = guestCart.getCartItemList();
            return cartItemList;
        }
        return null;
    }

}
