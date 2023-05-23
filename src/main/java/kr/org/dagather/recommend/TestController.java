package kr.org.dagather.recommend;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private static PythonInterpreter interpreter;

    @GetMapping("/pytest")
    public String pytest() {

        System.setProperty("python.import.site", "false");
        interpreter = new PythonInterpreter();
        interpreter.execfile("src/main/java/kr/org/dagather/recommend/test.py");
        interpreter.exec("print(testFunction(5,10))");

        PyFunction pyFunction = interpreter.get("testFunc", PyFunction.class);

        int a = 10;
        int b = 20;

        PyObject pyObject = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyObject.toString());

        return pyObject.toString();
    }
}
