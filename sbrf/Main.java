package sbrf;

import java.util.List;

import static sbrf.CityUtils.*;

public class Main {
    public static void main(String[] args) {
        try {
            List<City> guide = parseFromCsv();

            print(guide);

            sortByNameV1(guide);  // Сортировка массива по наименованию городов, используя lambda - выражения
            print(guide);

            sortByDistrictByName(guide);  // Сортировка массива по федеральным округам и наименваниям городов в них
            print(guide);

            City[] city = guide.toArray(new City[0]);  // Преобразование списка в массив обьектов.
            print(getIdCount(city));  // Печать индекс элемента с наибольшим количеством жителей города и количество жителей

            printList(findCountCityInRegions(guide));  // Поиск и печать количества городов в разрезе регионов

        } catch (FileException e) {
            System.out.println("File not found");
        }
    }
}

