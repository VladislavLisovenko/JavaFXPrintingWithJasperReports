package ru.vlsoft;

import ru.vlsoft.models.Country;
import ru.vlsoft.models.Product;
import ru.vlsoft.models.TableDetails;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneralUtils {

    public static final List<Country> countries;
    public static final List<Product> products;
    public static final List<TableDetails> details;

    static {
        countries = IntStream.rangeClosed(1, 20).mapToObj(i -> {
            Country element = new Country();
            element.setName("Country " + i);
            element.setCode(i);
            return element;
        }).collect(Collectors.toList());

        products = IntStream.rangeClosed(1, 100).mapToObj(i -> {
            Product element = new Product();
            element.setName("Product " + i);
            element.setPrice((double) i * 10);
            return element;
        }).collect(Collectors.toList());

        details = IntStream.rangeClosed(1, 30).mapToObj(i -> {

                    Random rand = new Random();
                    TableDetails detail = new TableDetails();
                    detail.setCountry(countries.get(rand.nextInt(countries.size() - 1)));
                    detail.setProduct(products.get(rand.nextInt(products.size() - 1)));
                    detail.setPrice(rand.nextInt(1000));
                    detail.setAmount(rand.nextInt(100));
                    return detail;

                }
        ).collect(Collectors.toList());
    }
}
