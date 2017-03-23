package designer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lilige on 17/3/21.
 */
@Controller
@RequestMapping("/hello-word")
public class HelloWordController {

    @RequestMapping("")
    public String helloWord(){
        return "hello-word";
    }
}
