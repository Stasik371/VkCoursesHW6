
import commons.FlywayInitializer;
import commons.JDBCCredentials;
import dataManagers.DaoOrganizations;
import generated.tables.records.OrganizationsRecord;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DaoOrganizationsTests {
    private static DaoOrganizations dao;
    private static final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final OrganizationsRecord testOrganization = new OrganizationsRecord("Nothing", 788887, 4545);

    @BeforeAll
    public static void creatingDao() {
        try {
            var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password());
            dao = new DaoOrganizations(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @BeforeEach
    public void createDB() {
        FlywayInitializer.initDB();
    }

    @Test
    @DisplayName("Checking get method if entity in DataBase")
    void getMethodTestTrue() {
        var organization = new OrganizationsRecord("SportMaster", 234234, 3333);
        assertThat(organization, is(dao.get(organization.getIndTaxpayerNum())));
    }

    @Test
    @DisplayName("Checking get method if entity not in DataBase")
    void getMethodTestFalse() {
        Assertions.assertThrows(IllegalStateException.class, () -> dao.get(testOrganization.getIndTaxpayerNum()));
    }

    @Test
    @DisplayName("Checking all method when entities in DataBase")
    void allMethodTest() {
        var listOfOrganizations = new ArrayList<OrganizationsRecord>();
        listOfOrganizations.add(new OrganizationsRecord("StreetBeat", 123123, 2222));
        listOfOrganizations.add(new OrganizationsRecord("SportMaster", 234234, 3333));
        listOfOrganizations.add(new OrganizationsRecord("Decathlon", 345345, 4444));
        listOfOrganizations.add(new OrganizationsRecord("SportTovary", 456456, 5555));
        assertThat(listOfOrganizations, is(dao.all()));
    }

    @Test
    @DisplayName("Checking save method")
    void saveMethodTestTrue() {
        dao.save(testOrganization);
        assertThat(testOrganization, is(dao.get(testOrganization.getIndTaxpayerNum())));
        dao.delete(testOrganization);
    }

    @Test
    @DisplayName("Checking update method")
    void updateMethodTestTrue() {
        dao.save(testOrganization);
        testOrganization.setOrganizationsName("StreetUdar");
        dao.update(testOrganization);
        assertThat(testOrganization, is(dao.get(testOrganization.getIndTaxpayerNum())));
        dao.delete(testOrganization);
    }

    @Test
    @DisplayName("Checking delete method")
    void deleteMethodTestTrue() {
        dao.save(testOrganization);
        dao.delete(testOrganization);
        Throwable throwable = Assertions.assertThrows(IllegalStateException.class,
                () -> dao.get(testOrganization.getIndTaxpayerNum()));
        assertThat(throwable.getMessage(), is("No record in Table"));
    }
}

