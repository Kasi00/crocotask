package com.example.crocotask.controller;

import com.example.crocotask.dto.*;
import com.example.crocotask.entity.Customer;
import com.example.crocotask.service.AddressService;
import com.example.crocotask.service.CustomerService;
import com.example.crocotask.service.NotificationService;
import com.example.crocotask.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUIController {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final NotificationService notificationService;
    private final UserService userService;





    @GetMapping("/login")
    public String login() {
        System.out.println("Successfully entered login");
        return "login"; // Thymeleaf template name (login.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,HttpServletRequest httpRequest) {
      try {
            // Create LoginRequest with email (or map email to username)
            LoginRequest request = new LoginRequest();
            request.setEmail(email);  // Or setEmail(email) if your LoginRequest has email field
            request.setPassword(password);
            System.out.println(request.getEmail()+request.getPassword());
            LoginResponse response = userService.login(request);
            System.out.println(response);
            if (response.getAccessToken() != null) {
                HttpSession session = httpRequest.getSession(true);
                session.setAttribute("JWT_TOKEN", response.getAccessToken());
                session.setAttribute("USER_EMAIL", email);
                System.out.println("Successfully logged in");
                return "redirect:/customers";
            } else {
                return "redirect:/login?error=true";  // This sets param.error
            }
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }

    }



    @GetMapping("/customers")
    public String viewCustomers(Model model) {
        System.out.println("===== GET /customers METHOD CALLED =====");

        try {
            List<CustomerResponse> customers = customerService.getAllCustomers();

            // Debug the customers data
            System.out.println("Customers is null: " + (customers == null));
            System.out.println("Customers size: " + (customers != null ? customers.size() : "N/A"));

            if (customers != null && !customers.isEmpty()) {
                CustomerResponse firstCustomer = customers.get(0);
                System.out.println("First customer: " + firstCustomer);
                System.out.println("First customer ID: " + firstCustomer.getId());
                System.out.println("First customer fullName: " + firstCustomer.getFullName());

            }

            model.addAttribute("customers", customers);
            return "customers";

        } catch (Exception e) {
            System.out.println("Error in controller: " + e.getMessage());
            e.printStackTrace();

            // Return empty list to test template
            model.addAttribute("customers", new ArrayList<>());
            return "customers";
        }
    }

    @GetMapping("/customers/new")
    public String createCustomerForm(Model model) {
        model.addAttribute("customerRequest", new CustomerRequest());
        return "customers-new";
    }

    @PostMapping("/customers/new")
    public String saveCustomer(@Valid @ModelAttribute CustomerRequest customer) {
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        System.out.println("shveba");
        CustomerPanelDto customer = customerService.getCustomerPanel(id);
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    @PostMapping("/customers/update/{id}")
    public String updateCustomer(@PathVariable Long id,@RequestParam String fullName,
                                 @RequestParam String phone,@RequestParam String email) {
        CustomerPanelUpdateDto request = new CustomerPanelUpdateDto(fullName, phone, email);
        customerService.updateCustomer(id, request);
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping("/customers/preferences/{id}")
    public String viewPreferences(@PathVariable Long id, Model model) {
        PreferenceResponse preferences = customerService.getPreferences(id);
        model.addAttribute("preferences", preferences);
        model.addAttribute("customerId", id);
        return "preferences";
    }

    @PostMapping("/customers/preferences/update/{id}")
    public String updatePreferences(@PathVariable Long id, @ModelAttribute UpdatePreferenceRequest request) {
        customerService.updatePreferences(id, request);
        return "redirect:/customers";
    }

    @GetMapping("/customers/{id}/addresses")
    public String viewAddresses(@PathVariable Long id, Model model) {
        List<AddressResponse> addresses = addressService.getAddressesByCustomer(id);
        model.addAttribute("addresses", addresses);
        model.addAttribute("customerId", id);
        return "addresses";
    }

    @GetMapping("/customers/{id}/notifications")
    public String viewNotificationStatuses(@PathVariable Long id, Model model) {
        List<NotificationStatusResponse> statuses = notificationService.getStatusesForCustomer(id);
        model.addAttribute("statuses", statuses);
        model.addAttribute("customerId", id);
        return "notifications";
    }

    @GetMapping("/admins")
    public String viewAdmins(Model model) {
        List<UserResponse> admins = userService.getAllAdmins();
        model.addAttribute("admins", admins);
        return "admins";
    }

    @GetMapping("/admins/register")
    public String registerAdminForm(Model model) {
        model.addAttribute("admin", new RegisterRequest());
        return "admin-form";
    }

    @PostMapping("/admins/register")
    public String registerAdmin(@ModelAttribute RegisterRequest request) {
        userService.registerAdmin(request);
        return "redirect:/admin/admins";
    }

    @GetMapping("/admins/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        userService.deleteAdmin(id);
        return "redirect:/admin/admins";
    }

    @GetMapping("/admins/update-password/{id}")
    public String updatePasswordForm(@PathVariable Long id, Model model) {
        model.addAttribute("updatePasswordRequest", new UpdatePasswordRequest());
        model.addAttribute("userId", id);
        return "update-password-form";
    }

    @PostMapping("/admins/update-password/{id}")
    public String updatePassword(@PathVariable Long id, @ModelAttribute UpdatePasswordRequest request) {
        userService.updateAdminPassword(id, request);
        return "redirect:/admin/admins";
    }


}
