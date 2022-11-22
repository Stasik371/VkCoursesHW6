
import commons.FlywayInitializer;
import commons.JDBCCredentials;
import dataManagers.DaoInvoicePositions;
import generated.tables.records.InvoicePositionsRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DaoInvoicePositionsTests {
    private static DaoInvoicePositions dao;
    private static final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final InvoicePositionsRecord testInvoicePosition = new InvoicePositionsRecord(11, 1, 120, 300, 1200);

    @BeforeAll
    public static void creatingDao() {
        FlywayInitializer.initDB();
        try {
            var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password());
            dao = new DaoInvoicePositions(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Checking get method if entity in DataBase")
    void getMethodTestTrue() {
        InvoicePositionsRecord invoicepos = new InvoicePositionsRecord(1, 1, 999, 500, 1200);
        assertThat(invoicepos, is(dao.get(invoicepos.getInvoiceId())));
    }

    @Test
    @DisplayName("Checking get method if entity not in DataBase")
    void getMethodTestFalse() {
        Assertions.assertThrows(IllegalStateException.class, () -> dao.get(testInvoicePosition.getId()));
    }

    @Test
    @DisplayName("Checking all method when entities in DataBase")
    void allMethodTest() {
        var listOfInvoicePositions = new ArrayList<InvoicePositionsRecord>();
        listOfInvoicePositions.add(new InvoicePositionsRecord(1, 1, 999, 500, 1200));
        listOfInvoicePositions.add(new InvoicePositionsRecord(2, 1, 899, 150, 1201));
        listOfInvoicePositions.add(new InvoicePositionsRecord(3, 2, 4999, 300, 1300));
        listOfInvoicePositions.add(new InvoicePositionsRecord(4, 2, 12999, 400, 1400));
        listOfInvoicePositions.add(new InvoicePositionsRecord(5, 3, 11999, 200, 1400));
        listOfInvoicePositions.add(new InvoicePositionsRecord(6, 3, 7999, 100, 1401));
        listOfInvoicePositions.add(new InvoicePositionsRecord(7, 4, 17999, 20, 1301));
        listOfInvoicePositions.add(new InvoicePositionsRecord(8, 4, 1499, 250, 1201));
        assertThat(listOfInvoicePositions, is(dao.all()));
    }

    @Test
    @DisplayName("Checking save method")
    void saveMethodTestTrue() {
        dao.save(testInvoicePosition);
        assertThat(testInvoicePosition, is(dao.get(testInvoicePosition.getId())));
        dao.delete(testInvoicePosition);
    }

    @Test
    @DisplayName("Checking update method")
    void updateMethodTestTrue() {
        dao.save(testInvoicePosition);
        testInvoicePosition.setAmount(15000);
        dao.update(testInvoicePosition);
        assertThat(testInvoicePosition, is(dao.get(testInvoicePosition.getId())));
        dao.delete(testInvoicePosition);
    }

    @Test
    @DisplayName("Checking delete method")
    void deleteMethodTestTrue() {
        dao.save(testInvoicePosition);
        dao.delete(testInvoicePosition);
        Throwable throwable = Assertions.assertThrows(IllegalStateException.class,
                () -> dao.get(testInvoicePosition.getId()));
        assertThat(throwable.getMessage(), is("No record in Table"));
    }
}

