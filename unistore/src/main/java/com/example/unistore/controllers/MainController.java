package com.example.unistore.controllers;

import com.example.unistore.enumm.Status;
import com.example.unistore.models.Cart;
import com.example.unistore.models.Order;
import com.example.unistore.models.Product;
import com.example.unistore.models.User;
import com.example.unistore.repositories.CartRepository;
import com.example.unistore.repositories.OrderRepository;
import com.example.unistore.repositories.ProductRepository;
import com.example.unistore.services.ProductService;
import com.example.unistore.services.UserService;
import com.example.unistore.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.unistore.security.UserDetailes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {
    private final UserValidator userValidator;

    private final UserService userService;

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public MainController(UserValidator userValidator, UserService userService, ProductService productService, ProductRepository productRepository, CartRepository cartRepository, OrderRepository orderRepository) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.productService = productService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/user account")
    public String userIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailes userDetailes = (UserDetailes) authentication.getPrincipal();
        String userRole = userDetailes.getUser().getRole();
        if (userRole.equals("ROLE_ADMIN")) {
            return "redirect:/users/adminIndex";
        }
        return "/users/userIndex";
    } // работает

    @GetMapping("/users/userRegistration")
    public String userRegistration(@ModelAttribute("user") User user) {
        return "/users/userRegistration";
    } // работает

    @PostMapping("/users/userRegistration")
    public String registrationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/users/userRegistration";
        }
        userService.registrationUser(user);
        return "redirect:/";
    } // работает

    @GetMapping("/products/userProductsList")
    public String userGetAllProducts(Model model) {
        model.addAttribute("product", productService.getAllProducts());
        return "/products/userProductsList";
    } // user get all products on page

    @GetMapping("/products/userProductView/{id}")
    public String userProductView(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "/products/userProductView";
    }

    @PostMapping("/users/userIndex")
    public String productSearch(@RequestParam("search") String search, @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "categorys", required = false, defaultValue = "") String categorys, Model model) {
        model.addAttribute("product", productService.getAllProducts());
        if (!from.isEmpty() & !to.isEmpty()) {
            if(!price.isEmpty()){
                if(price.equals("asc_price")) {
                    if (!categorys.isEmpty()) {
                        if (categorys.equals("category_a")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        } else if (categorys.equals("category_b")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        } else if (categorys.equals("category_c")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        }
                    } else {
                        model.addAttribute("search_product", productRepository.findByProductTitleOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                } else if(price.equals("desc_price")){
                    if(!categorys.isEmpty()){
                        if(categorys.equals("category_a")){
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        }else if (categorys.equals("category_b")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        } else if (categorys.equals("category_c")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        }
                    }  else {
                        model.addAttribute("search_product", productRepository.findByProductTitleOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                }
            } else {
                model.addAttribute("search_product", productRepository.findByProductTitleAndProductPriceGreaterThanEqualAndProductPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
            }
        } else {
            model.addAttribute("search_product", productRepository.findByProductTitleContainingIgnoreCase(search));
        }

        model.addAttribute("search_value", search);
        model.addAttribute("price_from", from);
        model.addAttribute("price_to", to);
        return "/users/userIndex";
    }

    @PostMapping("/index")
    public String guestProductSearch(@RequestParam("search") String search, @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "categorys", required = false, defaultValue = "") String categorys, Model model) {
        model.addAttribute("product", productService.getAllProducts());
        if (!from.isEmpty() & !to.isEmpty()) {
            if(!price.isEmpty()){
                if(price.equals("asc_price")) {
                    if (!categorys.isEmpty()) {
                        if (categorys.equals("category_a")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        } else if (categorys.equals("category_b")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        } else if (categorys.equals("category_c")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        }
                    } else {
                        model.addAttribute("search_product", productRepository.findByProductTitleOrderByProductPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                } else if(price.equals("desc_price")){
                    if(!categorys.isEmpty()){
                        if(categorys.equals("category_a")){
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        }else if (categorys.equals("category_b")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        } else if (categorys.equals("category_c")) {
                            model.addAttribute("search_product", productRepository.findByProductTitleAAndCategoryOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        }
                    }  else {
                        model.addAttribute("search_product", productRepository.findByProductTitleOrderByProductPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                }
            } else {
                model.addAttribute("search_product", productRepository.findByProductTitleAndProductPriceGreaterThanEqualAndProductPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
            }
        } else {
            model.addAttribute("search_product", productRepository.findByProductTitleContainingIgnoreCase(search));
        }

        model.addAttribute("search_value", search);
        model.addAttribute("price_from", from);
        model.addAttribute("price_to", to);
        return "/index";
    }

    @GetMapping("/products/guestProductView/{id}")
    public String guestProductView(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "/products/guestProductView";
    }

    @GetMapping("/orders/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        Product product = productService.getProductById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailes userDetails = (UserDetailes) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        Cart cart = new Cart(user_id, product.getId());
        cartRepository.save(cart);
        return "redirect:/orders/userCart";
    }

    @GetMapping("/orders/userCart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailes userDetails = (UserDetailes) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        List<Cart> cartList = cartRepository.findByUserId(user_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart: cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }
        float price = 0;
        for (Product product: productList) {
            price += product.getProductPrice();
        }
        model.addAttribute("price", price);
        model.addAttribute("cart_product", productList);
        return "/orders/userCart";
    }

    @GetMapping("/orders/userCart/delete/{id}")
    public String deleteProductFromCart(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailes userDetails = (UserDetailes) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        List<Cart> cartList = cartRepository.findByUserId(user_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart: cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }
        cartRepository.deleteCartByProductId(id);
        return "redirect:/orders/userCart";
    }

    @GetMapping("/orders/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailes userDetails = (UserDetailes) authentication.getPrincipal();
        int user_id = userDetails.getUser().getId();
        List<Cart> cartList = cartRepository.findByUserId(user_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart: cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }
        float price = 0;
        for (Product product: productList) {
            price += product.getProductPrice();
        }
        String uuid = UUID.randomUUID().toString();
        for(Product product : productList){
            Order newOrder = new Order(uuid, product, userDetails.getUser(), 1, product.getProductPrice(), Status.Оформлен);
            orderRepository.save(newOrder);
            cartRepository.deleteCartByProductId(product.getId());
        }
        return "redirect:/orders/userOrder";
    }

    @GetMapping("/orders/userOrder")
    public String orderUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailes personDetails = (UserDetailes) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByUser(personDetails.getUser());
        model.addAttribute("orders", orderList);
        return "/orders/userOrder";
    }
}



