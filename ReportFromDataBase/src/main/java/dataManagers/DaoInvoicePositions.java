package dataManagers;

import generated.tables.records.InvoicePositionsRecord;
import generated.tables.records.OrganizationsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static generated.tables.InvoicePositions.INVOICE_POSITIONS;

public class DaoInvoicePositions {

    private final @NotNull Connection connection;
    private final @NotNull DSLContext context;

    public DaoInvoicePositions(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public @NotNull InvoicePositionsRecord get(int id) {
        var record = context
                .select()
                .from(INVOICE_POSITIONS)
                .where(INVOICE_POSITIONS.ID.eq(id))
                .fetch();
        try {
            return new InvoicePositionsRecord(record.get(0).getValue(INVOICE_POSITIONS.ID),
                    record.get(0).getValue(INVOICE_POSITIONS.INVOICE_ID),
                    record.get(0).getValue(INVOICE_POSITIONS.PRICE),
                    record.get(0).getValue(INVOICE_POSITIONS.AMOUNT),
                    record.get(0).getValue(INVOICE_POSITIONS.PRODUCT_CODE));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("No record in Table");
        }
    }

    public @NotNull List<InvoicePositionsRecord> all() {
        var listOfInvoicePositions = new ArrayList<InvoicePositionsRecord>();
        var result = context
                .select()
                .from(INVOICE_POSITIONS)
                .fetch();
        for (var record : result) {
            Integer test = record.getValue(INVOICE_POSITIONS.ID);
            listOfInvoicePositions.add(new InvoicePositionsRecord(
                    record.getValue(INVOICE_POSITIONS.ID),
                    record.getValue(INVOICE_POSITIONS.INVOICE_ID),
                    record.getValue(INVOICE_POSITIONS.PRICE),
                    record.getValue(INVOICE_POSITIONS.AMOUNT),
                    record.getValue(INVOICE_POSITIONS.PRODUCT_CODE)));
        }
        return listOfInvoicePositions;
    }

    public void save(@NotNull InvoicePositionsRecord invoicePosition) {
        context.insertInto(INVOICE_POSITIONS, INVOICE_POSITIONS.ID, INVOICE_POSITIONS.INVOICE_ID,
                        INVOICE_POSITIONS.PRICE, INVOICE_POSITIONS.AMOUNT, INVOICE_POSITIONS.PRODUCT_CODE)
                .values(invoicePosition.getId(), invoicePosition.getInvoiceId(), invoicePosition.getPrice(),
                        invoicePosition.getAmount(), invoicePosition.getProductCode())
                .execute();
    }

    public void delete(@NotNull InvoicePositionsRecord invoicePosition) {
        context.delete(INVOICE_POSITIONS)
                .where(INVOICE_POSITIONS.ID.eq(invoicePosition.getId()))
                .execute();

    }

    public void update(@NotNull InvoicePositionsRecord invoicePosition) {
        context.update(INVOICE_POSITIONS)
                .set(INVOICE_POSITIONS.INVOICE_ID, invoicePosition.getInvoiceId())
                .set(INVOICE_POSITIONS.PRICE, invoicePosition.getPrice())
                .set(INVOICE_POSITIONS.AMOUNT, invoicePosition.getAmount())
                .set(INVOICE_POSITIONS.PRODUCT_CODE, invoicePosition.getProductCode())
                .where(INVOICE_POSITIONS.ID.eq(invoicePosition.getId()))
                .execute();
    }
}
