package sbrf;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CityUtils {

    private CityUtils() {
        // Private constructor to hide the implicit public one.
    }

    /**
     * Загрузка данных из csv файла
     *
     * @return массив с данными о городах
     */
    public static List<City> parseFromCsv() throws FileException {
        List<City> cityList = new ArrayList<>();
        Path cityPath = Path.of("./src/sbrf/city_ru.csv");
        try (Scanner scan = new Scanner(cityPath)) {
            while (scan.hasNext()) {
                cityList.add(parseCity(scan.nextLine()));
            }
        } catch (IOException e) {
                throw new FileException(e);
            }
        return cityList;
    }

    /**
     * Разбор строки с данными о городе
     *
     * @param line строка с данными
     * @return {@link City}
     */
    private static City parseCity(String line) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";")  // Задается разделитель в строке с данными
                .skip("\\d*");  // Необходимо пропустить значение номера строки
        String name = scanner.next();
        String region = scanner.next();
        String district = scanner.next();
        int population = scanner.nextInt();
        String foundation = (scanner.hasNext()) ? scanner.next() : null; // Могут отсутствовать данные значения
        scanner.close();

        return new City(name, region, district, population, foundation);
    }

    /**
     * Вывод в консоль списка городов
     *
     * @param cityList список городов
     */


    public static void print(List<City> cityList) {
        cityList.forEach(System.out::println);
    }

    public static void printList(List<String> cityList) {
        cityList.forEach(System.out::println);
    }

    public static void print(String str) {
        System.out.println(str);
    }

    /**
     * Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра;
     *
     * @param cityList список городов
     *
     */
    public static void sortByNameV1(List<City> cityList) {
        cityList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName())); // используя lambda-выражения
    }

    /**
     * Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа
     * в алфавитном порядке по убыванию с учетом регистра;
     *
     * @param cityList список городов
     *                 Отсортирован массив.
     */
    public static void sortByDistrictByName(List<City> cityList) {
        cityList.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
    }

    /**
     * Путем перебора массива найти индекс элемента и значение с наибольшим количеством жителей города.
     *
     * @param city массив городов
     * @return строку [индекс элемента] = количество.
     */
    public static String getIdCount(City[] city) {
        int maxCount = 0;
        int id = 0;
        for (int i = 0; i < city.length; i++) {
            if (city[i].getPopulation() > maxCount) {
                maxCount = city[i].getPopulation();
                id = i;
            }
        }
        return "[" + id + "]" + " = " + new DecimalFormat("###,###.##").format(maxCount);
    }

    /**
     * Поиск количества городов в разрезе регионов
     *
     * @param cityList список городов
     *                 Печать региона и количество городов в нем.
     */
    public static List<String> findCountCityInRegions(List<City> cityList) {
        cityList.sort(Comparator.comparing(City::getRegion)); // Сортировка по регионам
        List<String> countCityInRegions = new ArrayList<>();  // Создание списка регионов с количеством городов.
        String tempRegion = "";
        int count = 0;
        for (City city : cityList) {
            if (tempRegion.equals(city.getRegion())) {
                count++;
            } else {
                if (!tempRegion.equals("")) countCityInRegions.add(tempRegion + " - " + count);
                tempRegion = city.getRegion();
                count = 1;
            }
        }
        return countCityInRegions;
    }

}

