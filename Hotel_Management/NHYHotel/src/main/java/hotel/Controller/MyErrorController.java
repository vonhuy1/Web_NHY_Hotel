package hotel.Controller;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller

public class MyErrorController implements ErrorController {

	
	@RequestMapping("/error")
    public String error() {
        return "redirect:/home";
    }

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/home";
	}

}
