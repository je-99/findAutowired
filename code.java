import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class SpringTest {
    public static void main(String[] args) {
        Set<Class> set = findAllClassesUsingReflectionsLibrary("com.sample");
        set.forEach(aClass -> {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().contains("set")){
                    boolean isAutowired = false;
                    Annotation[] annos = method.getAnnotations();
                    for (Annotation anno : annos) {
                        if (anno.toString().contains("Autowired")){
                            isAutowired = true;
                        }
                    }
                    if (!isAutowired)
                        System.out.println("X\t" + method.getName() + "\t" + aClass.getName() + " - ");
                    else
                        System.out.println("✓\t" + method.getName() + "\t" + aClass.getName() + " - ");

                }
            }
        });
    }

    public static Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toSet());
    }
}
