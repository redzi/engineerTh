package controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class GreetingsController
{
    @RequestMapping(method = RequestMethod.GET)
    public String entryPage(Model model)
    {
        return ("firstPage/greetings");
    }
}
