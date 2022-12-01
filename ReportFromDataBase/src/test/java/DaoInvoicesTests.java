
import commons.FlywayInitializer;
import commons.JDBCCredentials;
import dataManagers.DaoInvoices;
import generated.tables.records.InvoicesRecord;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DaoInvoicesTests {
    private static DaoInvoices dao;
    private static final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final InvoicesRecord testInvoice = new InvoicesRecord(100,
            LocalDateTime.parse("2022-07-10 12:00", formatter), 123123);

    @BeforeAll
    public static void creatingDao() {

        try {
            var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password());
            dao = new DaoInvoices(connection);
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
        InvoicesRecord invoice = new InvoicesRecord(1, LocalDateTime.parse("2022-11-10 15:00", formatter), 123123);
        assertThat(invoice, is(dao.get(invoice.getInvoiceId())));
    }

    @Test
    @DisplayName("Checking get method if entity not in DataBase")
    void getMethodTestFalse() {
        Assertions.assertThrows(IllegalStateException.class, () -> dao.get(testInvoice.getInvoiceId()));
    }

    @Test
    @DisplayName("Checking all method when entities in DataBase")
    void allMethodTest() {
        var listOfInvoices = new ArrayList<InvoicesRecord>();
        listOfInvoices.add(new InvoicesRecord(1, LocalDateTime.parse("2022-11-10 15:00", formatter), 123123));
        listOfInvoices.add(new InvoicesRecord(2, LocalDateTime.parse("2022-09-10 16:00", formatter), 234234));
        listOfInvoices.add(new InvoicesRecord(3, LocalDateTime.parse("2022-08-10 14:00", formatter), 345345));
        listOfInvoices.add(new InvoicesRecord(4, LocalDateTime.parse("2022-07-10 12:00", formatter), 456456));
        assertThat(listOfInvoices, is(dao.all()));
    }

    @Test
    @DisplayName("Checking save method")
    void saveMethodTestTrue() {
        dao.save(testInvoice);
        assertThat(testInvoice, is(dao.get(testInvoice.getInvoiceId())));
        dao.delete(testInvoice);
    }

    @Test
    @DisplayName("Checking update method")
    void updateMethodTestTrue() {
        dao.save(testInvoice);
        testInvoice.setOrganizationNum(456456);
        dao.update(testInvoice);
        assertThat(testInvoice, is(dao.get(testInvoice.getInvoiceId())));
        dao.delete(testInvoice);
    }

    @Test
    @DisplayName("Checking delete method")
    void deleteMethodTestTrue() {
        dao.save(testInvoice);
        dao.delete(testInvoice);
        Throwable throwable = Assertions.assertThrows(IllegalStateException.class,
                () -> dao.get(testInvoice.getInvoiceId()));
        assertThat(throwable.getMessage(), is("No record in Table"));
    }
}

