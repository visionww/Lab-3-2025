import functions.*;

public class main {
    public static void main(String[] args) {
        System.out.println("=== Тестирование ArrayTabulatedFunction ===");
        testArrayTabulatedFunction();

        System.out.println("\n=== Тестирование LinkedListTabulatedFunction ===");
        testLinkedListTabulatedFunction();

        System.out.println("\n=== Тестирование исключений ===");
        testExceptions();
    }

    private static void testArrayTabulatedFunction() {
        try {
            // Создадим функцию параболы y = x^2 через массив значений Y
            double[] values = { 0.0, 1.0, 4.0, 9.0, 16.0 }; // y = x^2 для x = 0, 1, 2, 3, 4
            TabulatedFunction arrayFunc = new ArrayTabulatedFunction(0.0, 4.0, values);

            System.out.println("ArrayTabulatedFunction (парабола y = x^2):");
            System.out.println("Область определения: [" + arrayFunc.getLeftDomainBorder() + ", "
                    + arrayFunc.getRightDomainBorder() + "]");
            System.out.println("Количество точек: " + arrayFunc.getPointsCount());

            // Выведем все точки
            for (int i = 0; i < arrayFunc.getPointsCount(); i++) {
                FunctionPoint point = arrayFunc.getPoint(i);
                System.out.println("Точка " + i + ": (" + point.getX() + ", " + point.getY() + ")");
            }

            // Протестируем вычисленные значения
            System.out.println("\nВычисление значений функции (y = x^2):");
            for (double x = 0.0; x <= 4.0; x += 0.5) {
                double y = arrayFunc.getFunctionValue(x);
                System.out.println("f(" + x + ") = " + y);
            }

            // Протестируем за пределами области определения
            System.out.println("f(-1.0) = " + arrayFunc.getFunctionValue(-1.0));
            System.out.println("f(5.0) = " + arrayFunc.getFunctionValue(5.0));

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void testLinkedListTabulatedFunction() {
        try {
            // Создадим функцию параболы y = x^2 через количество точек
            TabulatedFunction linkedListFunc = new LinkedListTabulatedFunction(0.0, 2.0, 5);

            System.out.println("LinkedListTabulatedFunction (парабола y = x^2):");
            System.out.println("Область определения: [" + linkedListFunc.getLeftDomainBorder() + ", "
                    + linkedListFunc.getRightDomainBorder() + "]");
            System.out.println("Количество точек: " + linkedListFunc.getPointsCount());

            // Установим значения Y для параболы y = x^2
            for (int i = 0; i < linkedListFunc.getPointsCount(); i++) {
                double x = linkedListFunc.getPoint(i).getX();
                linkedListFunc.setPointY(i, x * x); // y = x^2
            }

            // Выведем все точки
            for (int i = 0; i < linkedListFunc.getPointsCount(); i++) {
                FunctionPoint point = linkedListFunc.getPoint(i);
                System.out.println("Точка " + i + ": (" + point.getX() + ", " + point.getY() + ")");
            }

            
            System.out.println("\nВычисление значений функции (y = x^2):");
            for (double x = 0.0; x <= 2.0; x += 0.25) {
                double y = linkedListFunc.getFunctionValue(x);
                System.out.println("f(" + x + ") = " + y);
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void testExceptions() {
        System.out.println("Тестирование исключительных ситуаций:");

        // Протестируем некорректные параметры конструктора
        try {
            TabulatedFunction func1 = new ArrayTabulatedFunction(5.0, 1.0, 3);
            System.out.println("ОШИБКА: Должно было быть выброшено исключение для leftX >= rightX");
        } catch (IllegalArgumentException e) {
            System.out.println("Поймали исключение: " + e.getMessage());
        }

        try {
            TabulatedFunction func2 = new LinkedListTabulatedFunction(0.0, 1.0, 1);
            System.out.println("ОШИБКА: Должно было быть выброшено исключение для pointsCount < 2");
        } catch (IllegalArgumentException e) {
            System.out.println("Поймали исключение: " + e.getMessage());
        }

        // Тестирование работы с точками
        try {
            double[] values = { 0.0, 1.0, 4.0 }; // y = x^2 для x = 0, 1, 2
            TabulatedFunction func = new ArrayTabulatedFunction(0.0, 2.0, values);

            // Попытка получить точку с неверным индексом
            try {
                func.getPoint(-1);
                System.out.println("ОШИБКА: Должно было быть выброшено исключение для отрицательного индекса");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("Поймали исключение: " + e.getMessage());
            }

            try {
                func.getPoint(10);
                System.out.println("ОШИБКА: Должно было быть выброшено исключение для слишком большого индекса");
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("Поймали исключение: " + e.getMessage());
            }

            // Установим некорректную координату X
            try {
                func.setPointX(1, 0.0); // Должно быть между предыдущей и следующей точкой
                System.out.println("ОШИБКА: Должно было быть выброшено исключение для некорректной координаты X");
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Поймали исключение: " + e.getMessage());
            }

            // Попытаемся добавить точку с существующей координатой X
            try {
                func.addPoint(new FunctionPoint(1.0, 5.0));
                System.out.println("ОШИБКА: Должно было быть выброшено исключение для дублирующейся координаты X");
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Поймали исключение: " + e.getMessage());
            }

            // Удалим точку если останется меньше 2 
            try {
                func.deletePoint(0);
                func.deletePoint(0);
                func.deletePoint(0); // Должно вызвать исключение
                System.out.println("ОШИБКА: Должно было быть выброшено исключение при удалении последней точки");
            } catch (IllegalStateException e) {
                System.out.println("Поймали исключение: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }

        // Тестируем добавление и удаление точек
        try {
            TabulatedFunction func = new LinkedListTabulatedFunction(0.0, 2.0, 3);

            // Установка значений для параболы y = x^2
            for (int i = 0; i < func.getPointsCount(); i++) {
                double x = func.getPoint(i).getX();
                func.setPointY(i, x * x);
            }
            System.out.println("\nИсходные точки:");
            for (int i = 0; i < func.getPointsCount(); i++) {
                FunctionPoint point = func.getPoint(i);
                System.out.println("Точка " + i + ": (" + point.getX() + ", " + point.getY() + ")");
            }
            System.out.println("\nТестирование добавления точек:");
            func.addPoint(new FunctionPoint(0.5, 0.25)); 
            func.addPoint(new FunctionPoint(1.5, 2.25)); 

            System.out.println("Количество точек после добавления: " + func.getPointsCount());
            for (int i = 0; i < func.getPointsCount(); i++) {
                FunctionPoint point = func.getPoint(i);
                System.out.println("Точка " + i + ": (" + point.getX() + ", " + point.getY() + ")");
            }

            System.out.println("\nТестирование удаления точек:");
            func.deletePoint(2); // Удаление средней точки
            System.out.println("Количество точек после удаления: " + func.getPointsCount());

        } catch (Exception e) {
            System.out.println("Ошибка при тестировании добавления/удаления: " + e.getMessage());
        }
    }
}