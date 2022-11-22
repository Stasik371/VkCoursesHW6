package dataManagers;

import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static generated.tables.Organizations.ORGANIZATIONS;

public class DaoOrganizations implements DAO<OrganizationsRecord> {
    private final @NotNull Connection connection;
    private final @NotNull DSLContext context;

    public DaoOrganizations(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public @NotNull OrganizationsRecord get(int id) {
        var record = context
                .select()
                .from(ORGANIZATIONS)
                .where(ORGANIZATIONS.IND_TAXPAYER_NUM.eq(id))
                .fetch();
        try {
            return new OrganizationsRecord(record.get(0).getValue(ORGANIZATIONS.ORGANIZATIONS_NAME),
                    record.get(0).getValue(ORGANIZATIONS.IND_TAXPAYER_NUM),
                    record.get(0).getValue(ORGANIZATIONS.CHECKING_ACCOUNT));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("No record in Table");
        }
    }

    public @NotNull List<OrganizationsRecord> all() {
        var listOfOrganizations = new ArrayList<OrganizationsRecord>();
        var result = context
                .select()
                .from(ORGANIZATIONS)
                .fetch();
        for (var record : result) {
            listOfOrganizations.add(new OrganizationsRecord(record.getValue(ORGANIZATIONS.ORGANIZATIONS_NAME),
                    record.getValue(ORGANIZATIONS.IND_TAXPAYER_NUM),
                    record.getValue(ORGANIZATIONS.CHECKING_ACCOUNT)));
        }
        return listOfOrganizations;
    }

    public void save(@NotNull OrganizationsRecord organization) {
        context.insertInto(ORGANIZATIONS, ORGANIZATIONS.ORGANIZATIONS_NAME,
                        ORGANIZATIONS.IND_TAXPAYER_NUM, ORGANIZATIONS.CHECKING_ACCOUNT)
                .values(organization.getOrganizationsName(),
                        organization.getIndTaxpayerNum(), organization.getCheckingAccount())
                .execute();
    }

    public void delete(@NotNull OrganizationsRecord organization) {
        context.delete(ORGANIZATIONS)
                .where(ORGANIZATIONS.IND_TAXPAYER_NUM.eq(organization.getIndTaxpayerNum()))
                .execute();

    }

    public void update(@NotNull OrganizationsRecord organization) {
        context.update(ORGANIZATIONS)
                .set(ORGANIZATIONS.ORGANIZATIONS_NAME, organization.getOrganizationsName())
                .set(ORGANIZATIONS.CHECKING_ACCOUNT, organization.getCheckingAccount())
                .where(ORGANIZATIONS.IND_TAXPAYER_NUM.eq(organization.getIndTaxpayerNum()))
                .execute();
    }
}
