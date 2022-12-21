package passport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.PassportDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PassportController {

	@Autowired
	PassportService passportService;
	
	@GetMapping("/makepassport")
	public String makePassport(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_email = "";
		
		if(session.getAttribute("user_email")!=null) {
			user_email = (String) session.getAttribute("user_email");
		}

		return "passport/make_passport";
	}
	
	@GetMapping("/viewpassport")
	public ModelAndView getPassport(int passport_num, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String user_email = "";
		ModelAndView mv= new ModelAndView();
		
		if(session.getAttribute("user_email")!=null) {
			user_email = (String) session.getAttribute("user_email");
		}
		
		PassportDTO passportDto = passportService.findById(passport_num);
		
		if(passportDto != null) {
			if(passportDto.getUser_email().equals(user_email)) {
				// 내 여권
				System.out.println("내 여권");
			}
			
			mv.setViewName("passport/view_passport");
			mv.addObject("passport_dto", passportDto);
		}
		else {
			mv.setViewName("error");
		}
		
		return mv;
	}
	
	
}