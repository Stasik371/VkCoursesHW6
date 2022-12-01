import commons.FlywayInitializer;
import commons.JDBCCredentials;
import dataManagers.DaoProducts;
import generated.tables.records.ProductsRecord;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DaoProductsTests {
    private static DaoProducts dao;
    private static final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final ProductsRecord testProduct = new ProductsRecord("Nothning", 9999);

    @BeforeAll
    public static void creatingDao() {
        try {
            var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password());
            dao = new DaoProducts(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @BeforeEach
    public void createDB(){
        FlywayInitializer.initDB();
    }

    @Test
    @DisplayName("Checking get method if entity in DataBase")
    void getMethodTestTrue() {
        var product = new ProductsRecord("T-shirt Adidas", 1200);
        assertThat(product, is(dao.get(product.getProductCode())));
    }

    @Test
    @DisplayName("Checking get method if entity not in DataBase")
    void getMethodTestFalse() {
        Assertions.assertThrows(IllegalStateException.class, () -> dao.get(testProduct.getProductCode()));
    }

    @Test
    @DisplayName("Checking all method when entities in DataBase")
    void allMethodTest() {
        var listOfProducts = new ArrayList<ProductsRecord>();
        listOfProducts.add(new ProductsRecord("T-shirt Adidas", 1200));
        listOfProducts.add(new ProductsRecord("T-shirt Nike", 1201));
        listOfProducts.add(new ProductsRecord("Sneakers Nike", 1300));
        listOfProducts.add(new ProductsRecord("Sneakers Reebok", 1301));
        listOfProducts.add(new ProductsRecord("Jacket Reebok", 1400));
        listOfProducts.add(new ProductsRecord("Jacket  Puma", 1401));
        assertThat(listOfProducts, is(dao.all()));
    }

    @Test
    @DisplayName("Checking save method")
    void saveMethodTestTrue() {
        dao.save(testProduct);
        assertThat(testProduct, is(dao.get(testProduct.getProductCode())));
        dao.delete(testProduct);
    }

    @Test
    @DisplayName("Checking update method")
    void updateMethodTestTrue() {
        dao.save(testProduct);
        testProduct.setNameOfProduct("Abibas");
        dao.update(testProduct);
        assertThat(testProduct, is(dao.get(testProduct.getProductCode())));
        dao.delete(testProduct);

    }

    @Test
    @DisplayName("Checking delete method")
    void deleteMethodTestTrue() {
        dao.save(testProduct);
        dao.delete(testProduct);
        Throwable throwable = Assertions.assertThrows(IllegalStateException.class,
                () -> dao.get(testProduct.getProductCode()));
        assertThat(throwable.getMessage(), is("No record in Table"));
    }
}
