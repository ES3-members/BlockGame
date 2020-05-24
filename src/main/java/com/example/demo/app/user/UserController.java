package com.example.demo.app.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.score.ScoreForm;
import com.example.demo.entity.User;
import com.example.demo.entity.UserScore;
import com.example.demo.service.UserScoreService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;


@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final UserScoreService userscoreService;
 	
 	@Autowired
 	public UserController(UserServiceImpl userService, UserScoreService userscoreService){
 		this.userService = userService;
 		this.userscoreService = userscoreService;
 	}
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Home Page");
		return "user/index";
	}
	
	@PostMapping("/index")
	public String indexGoBack(Model model) {
		model.addAttribute("title", "Home Page");
		return "user/index";
	}
	
	@PostMapping("/logInForm")
	public String logInForm(UserForm userForm, Model model) {
		model.addAttribute("title", "LogIn Page");
		return "user/logInForm";
	}
	
	@PostMapping("/entrance")
	public String entrance(@Validated UserForm userForm,
			BindingResult result,
			Model model,ScoreForm scoreForm) {
		
		// 入力された値が想定外の時
		if(result.hasErrors()) {
			model.addAttribute("caution", "Please write");
			return "user/logInForm";
		}
		
		//入力されたアカウントが有る場合（データベースでアカウントを探索）コードはserviceに頼む
		//入力されたアカウントがDBに存在すればtrue
		if(userService.certificate(userForm.getUserName(), userForm.getPassword())) {
			List<UserScore> list = userscoreService.getAll();
			model.addAttribute("title", "Entrance Page");
			model.addAttribute("userscoreList", list);
//			model.addAttribute("userName", userForm.getUserName());
			return "user/entrance";
		}
		
		//loginするアカウントが無い場合
		model.addAttribute("title", "LogIn Page");
		model.addAttribute("caution", "The account does not exist");
		 return "user/logInForm";
	}
	
	// start game 
	@PostMapping("/game")
	public String game(ScoreForm scoreForm, Model model) {
		model.addAttribute("title", "Game Start");
		return "user/game";
	}
	
	// finish game
	@PostMapping("/result")
	public String result(ScoreForm scoreForm, Model model) {
		UserScore userscore = new UserScore();
		userscore.setUserName(scoreForm.getUserName());
		userscore.setScore(scoreForm.getScore());
		userscoreService.save(userscore);
		
		
		model.addAttribute("title", "Result Page");
//		model.addAttribute("userName", scoreForm.getUserName());
//		model.addAttribute("score", scoreForm.getScore());
		return "user/result";
	}
	
	@PostMapping("/create")
	public String create(UserForm userForm, Model model) {
		model.addAttribute("title", "Create Account");
		return "user/create";
	}
	
	@PostMapping("/confirm")
	public String confirm(@Validated UserForm userForm,
			BindingResult result, 
			Model model) {
		
		// 入力された値が想定外の時
		if(result.hasErrors()) {
			model.addAttribute("title", "Create Account");
			return "user/create";
		}
		
		// 入力されたユーザ名が既に使われていた場合(データベースでユーザ名を探索）
		if(userService.userExist(userForm.getUserName())) {
			model.addAttribute("title", "Create Account");
			model.addAttribute("caution", "The userName is already in use");
			 return "user/create";
		}
	
		// パスワードと確認用パスワードが一致しない場合
		if(!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
			model.addAttribute("caution", "Password does not match");
			return "user/create";
		}
		
		// 入力されたアカウントが正常の場合
		int passwordSize = userForm.getPassword().length();
		model.addAttribute("secretpass", "*".repeat(passwordSize));
		model.addAttribute("title", "Confirm Account");
		return "user/confirm";
	}
	
	
	@PostMapping("/complete")
	public String complete(@Validated UserForm userForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Create Account");
			return "user/create";
		}
		
		//hands-on
		User user = new User();
		user.setUserName(userForm.getUserName());
		user.setPassword(userForm.getPassword());
		userService.save(user);
		
		redirectAttributes.addFlashAttribute("complete", "Registerd Account");
		model.addAttribute("title", "Home Page");
		return "redirect:/user/index";
	}
	
	
}
