package lectures.com.goit.javadev5.annotation;

import java.lang.reflect.Field;

public class TestAnnotation {
    @Calculated("2+2")
    public int test;

    public static void main(String[] args) throws IllegalAccessException {
        TestAnnotation testAnnotation = new TestAnnotation();
        process(testAnnotation);
        System.out.println("testAnnotation.test = " + testAnnotation.test);
    }

    // рефлексія
    private static void process(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            Calculated calculated = field.getAnnotation(Calculated.class);

            if (calculated != null) {
                String value = calculated.value();

                String[] valueParts = value.split("");
                int number1 = Integer.parseInt(valueParts[0]);
                int number2 = Integer.parseInt(valueParts[2]);

                int result = number1 + number2;

                field.setInt(object, result);
            }
        }
    }
}