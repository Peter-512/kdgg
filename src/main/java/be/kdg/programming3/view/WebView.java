package be.kdg.programming3.view;


import be.kdg.programming3.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller ("webView")
@RequestMapping ("chat")
public class WebView implements View {
	private final ChatService chatService;

	@Autowired
	public WebView(ChatService chatService) {
		this.chatService = chatService;
	}

	@Override
	public void show() {
		showWeb();
	}

	@GetMapping
	public ModelAndView showWeb() {
		final ModelAndView modelAndView = new ModelAndView("chat");
		modelAndView.addObject("chatService", chatService);
		return modelAndView;
	}
}
