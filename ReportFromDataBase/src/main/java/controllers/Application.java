package controllers;


import commons.FlywayInitializer;
import commons.JDBCCredentials;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class Application {
    private static final @NotNull
    JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        FlywayInitializer.initDB();
        var rc = new ReportCreator();

        System.out.println("First Report");
        var testProduct = new ProductsRecord("T-shirt Nike", 1400);
        rc.first10OrganizationsByProduct(testProduct).forEach(System.out::println);
        System.out.println("\n\n");

        System.out.println("Second Report");
        Map<Integer, Integer> hashMapAmountProductCode = new HashMap<>();
        hashMapAmountProductCode.put(898, 1201);
        hashMapAmountProductCode.put(900, 1201);
        int counter = 0;
        var records = rc.organizationsWithMoreAmountThenParams(hashMapAmountProductCode);
        for (var organization : records) {
            if (counter == records.size() - 1) break;
            if (organization == null) System.out.println("nextRecord");
            else System.out.print(organization);
            counter++;
        }
        System.out.println("\n");

        System.out.println("Third Report");
        var listForThirdReport= rc.getSumPerDay(LocalDateTime.parse("2022-07-10 12:00", formatter),
                LocalDateTime.parse("2022-11-10 16:00", formatter));
        listForThirdReport.forEach(System.out::println);
        System.out.println("\n");

        System.out.println("Fourth Report");
        var listForFourthReport = rc.averageValueBetween(LocalDateTime.parse("2022-07-10 12:00", formatter),
                LocalDateTime.parse("2022-11-10 16:00", formatter));
        listForFourthReport.forEach(System.out::println);
        System.out.println("\n");

        System.out.println("FifthReport");
        var lisForFifthReport = rc.getAllProductByPeriod(LocalDateTime.parse("2022-07-10 12:00", formatter),
                LocalDateTime.parse("2022-09-10 16:00", formatter));
        lisForFifthReport.forEach(System.out::println);
    }
}

