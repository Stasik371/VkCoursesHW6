import commons.FlywayInitializer;
import commons.JDBCCredentials;
import controllers.ReportCreator;
import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import recordForReports.RecordOrganizationProducts;
import recordForReports.RecordProductPrice;
import recordForReports.RecordDateProductsAmountPrice;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReportTests {
    private static ReportCreator rc;
    private static final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeAll
    public static void creatingReportCreator() {
        try {
            var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password());
            rc = new ReportCreator(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void createDB(){
        FlywayInitializer.initDB();
    }

    @Test
    @DisplayName("Выбрать первые 10 поставщиков по количеству поставленного товара")
    void firstTenOrganizations() {
        var testProduct = new ProductsRecord("Jacket  Puma", 1401);
        var listOfOrganizations = new ArrayList<OrganizationsRecord>();
        listOfOrganizations.add(new OrganizationsRecord("Decathlon", 345345, 4444));
        assertThat(listOfOrganizations, is(rc.first10OrganizationsByProduct(testProduct)));
    }

    @Test
    @DisplayName("Выбрать поставщиков с количеством поставленного товара выше указанного значения.")
    void organizationsWithAmountAndPrice() {
        var hashMapAmountProductCode = new HashMap<Integer, Integer>();
        hashMapAmountProductCode.put(898, 1201);
        hashMapAmountProductCode.put(900, 1201);
        var listOfOrganizations = new ArrayList<OrganizationsRecord>();
        listOfOrganizations.add(new OrganizationsRecord("StreetBeat", 123123, 2222));
        listOfOrganizations.add(new OrganizationsRecord("SportTovary", 456456, 5555));
        listOfOrganizations.add(null);
        listOfOrganizations.add(new OrganizationsRecord("SportTovary", 456456, 5555));
        listOfOrganizations.add(null);
        assertThat(listOfOrganizations, is(rc.organizationsWithMoreAmountThenParams(hashMapAmountProductCode)));
    }

    @Test
    @DisplayName("За каждый день для каждого товара рассчитать количество и сумму " +
            "полученного товара в указанном периоде, посчитать итоги за период")
    void sumPerDay() {
        var listOfEntities = new ArrayList<RecordDateProductsAmountPrice>();
        listOfEntities.add(new RecordDateProductsAmountPrice(LocalDateTime.parse("2022-07-10 12:00", formatter),
                "T-shirt Nike", 1201, BigDecimal.valueOf(250), 1499));
        listOfEntities.add(new RecordDateProductsAmountPrice(LocalDateTime.parse("2022-07-10 12:00", formatter),
                "Sneakers Reebok", 1301, BigDecimal.valueOf(20), 17999));
        var queryResult = rc.getSumPerDay(LocalDateTime.parse("2022-07-10 12:00", formatter),
                LocalDateTime.parse("2022-08-09 16:00", formatter));
        assertThat(listOfEntities, is(queryResult));
    }

    @Test
    @DisplayName("Рассчитать среднюю цену по каждому товару за период")
    void avgByPeriod() {
        var listOfEntities = new ArrayList<RecordProductPrice>();
        listOfEntities.add(new RecordProductPrice(1201, "T-shirt Nike", 1499.0));
        listOfEntities.add(new RecordProductPrice(1301, "Sneakers Reebok", 17999.0));
        var queryResult = rc.averageValueBetween(LocalDateTime.parse("2022-07-10 12:00", formatter),
                LocalDateTime.parse("2022-08-09 16:00", formatter));
        assertThat(listOfEntities, is(queryResult));
    }

    @Test
    @DisplayName("Вывести список товаров, поставленных организациями за период. Если организация товары не поставляла, " +
            "то она все равно должна быть отражена в списке.")
    void listOfProductsByPeriod() {
        var listOfEntities = new ArrayList<RecordOrganizationProducts>();
        listOfEntities.add(new RecordOrganizationProducts("StreetBeat", 123123, 2222, null, null));
        listOfEntities.add(new RecordOrganizationProducts("SportMaster", 234234, 3333, null, null));
        listOfEntities.add(new RecordOrganizationProducts("Decathlon", 345345, 4444, "Jacket Reebok", 1400));
        listOfEntities.add(new RecordOrganizationProducts("Decathlon", 345345, 4444, "Jacket  Puma", 1401));
        listOfEntities.add(new RecordOrganizationProducts("SportTovary", 456456, 5555, "T-shirt Nike", 1201));
        listOfEntities.add(new RecordOrganizationProducts("SportTovary", 456456, 5555, "Sneakers Reebok", 1301));
        var queryResult = rc.getAllProductByPeriod(LocalDateTime.parse("2022-07-10 11:59", formatter),
                LocalDateTime.parse("2022-08-10 16:01", formatter));
        assertThat(listOfEntities,is(queryResult));

    }

}
