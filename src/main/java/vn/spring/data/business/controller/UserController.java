package vn.spring.data.business.controller;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import vn.spring.data.business.entity.User;
import vn.spring.data.business.service.impl.UserServiceImpl;

/**
 * Annotation @Controller: Đánh dấu class này là một Spring MVC Controller.
 * Annotation @RequiredArgsConstructor: Tự động sinh constructor với các trường final (dùng cho dependency injection).
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    // Inject UserService để thao tác với dữ liệu user
    private final UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    // Biến tạm lưu thông tin user đăng ký
    User userInfo = new User();

    /**
     * @GetMapping("/home"): Xử lý request GET tới /home, trả về trang home.
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * @GetMapping("/register"): Hiển thị form đăng ký user.
     * @param model Model để truyền dữ liệu sang view
     * @return Tên view register
     */
    @GetMapping("/register")
    public String registerUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * @GetMapping("/login"): Hiển thị form đăng nhập user.
     * @param model Model để truyền dữ liệu sang view
     * @return Tên view login
     */
    @GetMapping("/login")
    public String loginUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    /**
     * @PostMapping("/register"): Xử lý đăng ký user mới.
     * Kiểm tra username hợp lệ, kiểm tra trùng username, lưu thông tin tạm thời và chuyển sang bước xác nhận.
     * @param user User nhận từ form
     * @param model Model để truyền dữ liệu sang view
     * @return Redirect hoặc view register nếu có lỗi
     */
    @PostMapping("/register")
    public String saveUsers(@ModelAttribute("user") User user, Model model) {
        // Regex chỉ cho phép chữ cái Latin, số
        String usernamePattern = "^[a-zA-Z0-9]+$";
        if (!Pattern.matches(usernamePattern, user.getUsername())) {
            model.addAttribute("error", "Username chỉ được dùng chữ cái không dấu và số!");
            return "register";
        }
        // Kiểm tra username đã tồn tại chưa
        List<User> existingUsers = userService.getAllUser();
        boolean exists = existingUsers.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
        if (exists) {
            model.addAttribute("error", "Username đã tồn tại!");
            return "register";
        }
        // Lưu tạm user
        userInfo = userInfo.toBuilder().id(user.getId()).username(user.getUsername()).password(user.getPassword())
                .email(user.getEmail()).build();
        // Chuyển hướng đến trang xác nhận với username vừa nhập
        return "redirect:/confirm/user/" + user.getUsername();
    }

    /**
     * @GetMapping("/confirm/user/{username}"): Hiển thị form xác nhận mật khẩu khi đăng ký.
     * @param username Username vừa đăng ký
     * @param model Model để truyền dữ liệu sang view
     * @return Tên view confirm
     */
    @GetMapping("/confirm/user/{username}")
    public String confirmUserForm(@PathVariable String username, Model model) {
        model.addAttribute("user", new User());
        return "confirm";
    }

    /**
     * @PostMapping("/confirm/{username}"): Xử lý xác nhận mật khẩu khi đăng ký.
     * Nếu đúng, lưu user vào database, nếu sai trả về trang xác nhận với lỗi.
     * @param username Username xác nhận
     * @param user User nhận từ form xác nhận
     * @param model Model để truyền dữ liệu sang view
     * @return Redirect hoặc view confirm nếu có lỗi
     */
    @PostMapping("/confirm/{username}")
    public String confirmUsers(@PathVariable String username, @ModelAttribute User user, Model model) {
        if (user.getPassword().equals(userInfo.getPassword())) {
            // Lưu user mới vào database
        	userInfo.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(userInfo);
            return "redirect:/home";
        }
        model.addAttribute("error", "Password không khớp!");
        return "redirect:/confirm/user/" + username;
    }

    /**
     * @PostMapping("/login"): Xử lý đăng nhập user.
     * Kiểm tra username và password, nếu đúng chuyển hướng đến trang students, nếu sai trả về trang login với lỗi.
     * @param user User nhận từ form đăng nhập
     * @param model Model để truyền dữ liệu sang view
     * @return Redirect hoặc view login nếu có lỗi
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        List<User> userList = userService.getAllUser();
        boolean flag = userList.stream().anyMatch(it -> Objects.equals(it.getUsername(), user.getUsername())
                && Objects.equals(it.getPassword(), user.getPassword()));
        if (flag) {
            return "redirect:/students";
        }
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
        model.addAttribute("user", user);
        return "login";
    }
}