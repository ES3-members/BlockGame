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
		
		// 蜈･蜉帙＆繧後◆蛟､縺梧Φ螳壼､悶�ｮ譎�
		if(result.hasErrors()) {
			model.addAttribute("caution", "Please write");
			return "user/logInForm";
		}
		
		//蜈･蜉帙＆繧後◆繧｢繧ｫ繧ｦ繝ｳ繝医′譛峨ｋ蝣ｴ蜷茨ｼ医ョ繝ｼ繧ｿ繝吶�ｼ繧ｹ縺ｧ繧｢繧ｫ繧ｦ繝ｳ繝医ｒ謗｢邏｢�ｼ峨さ繝ｼ繝峨�ｯservice縺ｫ鬆ｼ繧�
		//蜈･蜉帙＆繧後◆繧｢繧ｫ繧ｦ繝ｳ繝医′DB縺ｫ蟄伜惠縺吶ｌ縺ｰtrue
		if(userService.certificate(userForm.getUserName(), userForm.getPassword())) {
			model.addAttribute("title", "Entrance Page");
//			model.addAttribute("userName", userForm.getUserName());
			return "user/entrance";
		}
		
		//login縺吶ｋ繧｢繧ｫ繧ｦ繝ｳ繝医′辟｡縺�蝣ｴ蜷�
		model.addAttribute("title", "LogIn Page");
		model.addAttribute("caution", "Wrong Information! Please Try Again!");
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
	public String result(ScoreForm scoreForm, UserForm userForm, Model model) {
		
		if(userscoreService.userExist(scoreForm.getUserName())) {
			UserScore userscore = new UserScore();
			userscore.setUserName(scoreForm.getUserName());
			userscore.setScore(scoreForm.getScore());
			userscoreService.update(userscore);
		}else {
			UserScore userscore = new UserScore();
			userscore.setUserName(scoreForm.getUserName());
			userscore.setScore(scoreForm.getScore());
			userscoreService.save(userscore);

		}
			
		List<UserScore> list = userscoreService.getAll();
		model.addAttribute("userscoreList", list);
		model.addAttribute("title", "Result Page");
		
//		model.addAttribute("userName", scoreForm.getUserName());
//		model.addAttribute("score", scoreForm.getScore());
		return "user/result";
	}
	
	
	@PostMapping("/entranceFromInside")
	public String entranceFromInside(UserForm userForm, Model model) {
		model.addAttribute("title", "Entrance Page");
		return "user/entrance";
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
		
		// 蜈･蜉帙＆繧後◆蛟､縺梧Φ螳壼､悶�ｮ譎�
		if(result.hasErrors()) {
			model.addAttribute("title", "Create Account");
			return "user/create";
		}
		
		// 蜈･蜉帙＆繧後◆繝ｦ繝ｼ繧ｶ蜷阪′譌｢縺ｫ菴ｿ繧上ｌ縺ｦ縺�縺溷�ｴ蜷�(繝�繝ｼ繧ｿ繝吶�ｼ繧ｹ縺ｧ繝ｦ繝ｼ繧ｶ蜷阪ｒ謗｢邏｢�ｼ�
		if(userService.userExist(userForm.getUserName())) {
			model.addAttribute("title", "Create Account");
			model.addAttribute("caution", "The userName is already in use");
			 return "user/create";
		}
	
		// 繝代せ繝ｯ繝ｼ繝峨→遒ｺ隱咲畑繝代せ繝ｯ繝ｼ繝峨′荳�閾ｴ縺励↑縺�蝣ｴ蜷�
		if(!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
			model.addAttribute("caution", "Password does not match");
			return "user/create";
		}
		
		// 蜈･蜉帙＆繧後◆繧｢繧ｫ繧ｦ繝ｳ繝医′豁｣蟶ｸ縺ｮ蝣ｴ蜷�
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
	
	@PostMapping("/delete")
	public String delete(UserForm userForm, Model model) {
		model.addAttribute("title", "Delete Confirm");
		return "user/delete";
	}
	
	@PostMapping("/deleteComplete")
	public String deleteComplete(UserForm userForm, 
			Model model){
		User user = new User();
		user.setUserName(userForm.getUserName());
		userService.delete(user);
		model.addAttribute("complete", "Deleted Account");
		model.addAttribute("title", "Home Page");
		return "/user/index";
	}
	
	@PostMapping("ranking")
	public String ranking(UserForm userForm, Model model) {
	// This is for test
	// You can reuse this by replacing  "GetMapping" to "PostMapping" or something
		List<Ranking> rank = userscoreService.getRanking();
		model.addAttribute("ranking", rank);
		return "user/ranking";
	}
	
}
