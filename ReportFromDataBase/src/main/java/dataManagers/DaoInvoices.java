package dataManagers;

import generated.tables.records.InvoicesRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static generated.tables.Invoices.INVOICES;

public class DaoInvoices {

    private final @NotNull Connection connection;
    private final @NotNull DSLContext context;

    public DaoInvoices(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public @NotNull InvoicesRecord get(int id) {
        var record = context
                .select()
                .from(INVOICES)
                .where(INVOICES.INVOICE_ID.eq(id))
                .fetch();
        try {
            return new InvoicesRecord(record.get(0).getValue(INVOICES.INVOICE_ID),
                    record.get(0).getValue(INVOICES.DATE_OF_INVOICE),
                    record.get(0).getValue(INVOICES.ORGANIZATION_NUM));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("No record in Table");
        }
    }

    public @NotNull List<InvoicesRecord> all() {
        var listOfInvoices = new ArrayList<InvoicesRecord>();
        var result = context
                .select()
                .from(INVOICES)
                .fetch();
        for (var record : result) {
            listOfInvoices.add(new InvoicesRecord(record.getValue(INVOICES.INVOICE_ID),
                    record.getValue(INVOICES.DATE_OF_INVOICE),
                    record.getValue(INVOICES.ORGANIZATION_NUM)));
        }
        return listOfInvoices;
    }

    public void save(@NotNull InvoicesRecord invoice) {

        context.insertInto(INVOICES, INVOICES.INVOICE_ID,
                        INVOICES.DATE_OF_INVOICE, INVOICES.ORGANIZATION_NUM)
                .values(invoice.getInvoiceId(), invoice.getDateOfInvoice(),
                        invoice.getOrganizationNum())
                .execute();
    }

    public void delete(@NotNull InvoicesRecord invoice) {

        context.delete(INVOICES)
                .where(INVOICES.INVOICE_ID.eq(invoice.getInvoiceId()))
                .execute();

    }

    public void update(@NotNull InvoicesRecord invoice) {
        context.update(INVOICES)
                .set(INVOICES.DATE_OF_INVOICE, invoice.getDateOfInvoice())
                .set(INVOICES.ORGANIZATION_NUM, invoice.getOrganizationNum())
                .where(INVOICES.INVOICE_ID.eq(invoice.getInvoiceId()))
                .execute();
    }
}
