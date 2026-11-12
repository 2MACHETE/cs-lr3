import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface HaveArea {
    String name();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ReturnArea { }

@HaveArea(name = "Квартира")
class Apartment {
    double area;
    Apartment(double area) { this.area = area; }
    @ReturnArea
    public double getArea() { return area; }
}

@HaveArea(name = "Стол")
class Table {
    double area;
    Table(double area) { this.area = area; }
    @ReturnArea
    public double getArea() { return area; }
}

@HaveArea(name = "Фигура")
class Shape {
    public double getArea() { return 5.0; }
}

class Chair {
    public double getArea() { return 1.0; }
}

public class Main {

    public static void printAreaInfo(Object obj) {
        Class<?> class1 = obj.getClass();

        if (!class1.isAnnotationPresent(HaveArea.class)) {
            System.out.println(class1.getSimpleName() + " не помечен @HaveArea");
            return;
        }
        String name = class1.getAnnotation(HaveArea.class).name();
        for (Method m : class1.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ReturnArea.class)) {
                try {
                    System.out.println("Имя: " + name + ", площадь = " + m.invoke(obj));
                } catch (Exception e) {
                    System.out.println("Ошибка вызова: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("У \"" + name + "\" нет метода с @ReturnArea");
    }

    public static void main(String[] args) {
        printAreaInfo(new Apartment(43.2));
        printAreaInfo(new Table(2.0));
        printAreaInfo(new Shape());
        printAreaInfo(new Chair());
    }
}