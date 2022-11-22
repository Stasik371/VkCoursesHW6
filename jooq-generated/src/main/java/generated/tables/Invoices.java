/*
 * This file is generated by jOOQ.
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.InvoicesRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Invoices extends TableImpl<InvoicesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.invoices</code>
     */
    public static final Invoices INVOICES = new Invoices();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InvoicesRecord> getRecordType() {
        return InvoicesRecord.class;
    }

    /**
     * The column <code>public.invoices.invoice_id</code>.
     */
    public final TableField<InvoicesRecord, Integer> INVOICE_ID = createField(DSL.name("invoice_id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.invoices.date_of_invoice</code>.
     */
    public final TableField<InvoicesRecord, LocalDateTime> DATE_OF_INVOICE = createField(DSL.name("date_of_invoice"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.invoices.organization_num</code>.
     */
    public final TableField<InvoicesRecord, Integer> ORGANIZATION_NUM = createField(DSL.name("organization_num"), SQLDataType.INTEGER.nullable(false), this, "");

    private Invoices(Name alias, Table<InvoicesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Invoices(Name alias, Table<InvoicesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.invoices</code> table reference
     */
    public Invoices(String alias) {
        this(DSL.name(alias), INVOICES);
    }

    /**
     * Create an aliased <code>public.invoices</code> table reference
     */
    public Invoices(Name alias) {
        this(alias, INVOICES);
    }

    /**
     * Create a <code>public.invoices</code> table reference
     */
    public Invoices() {
        this(DSL.name("invoices"), null);
    }

    public <O extends Record> Invoices(Table<O> child, ForeignKey<O, InvoicesRecord> key) {
        super(child, key, INVOICES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<InvoicesRecord, Integer> getIdentity() {
        return (Identity<InvoicesRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<InvoicesRecord> getPrimaryKey() {
        return Keys.INVOICE_PK;
    }

    @Override
    public List<ForeignKey<InvoicesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.INVOICES__INVOICES_ORGANIZATION_NUM_FKEY);
    }

    private transient Organizations _organizations;

    /**
     * Get the implicit join path to the <code>public.organizations</code>
     * table.
     */
    public Organizations organizations() {
        if (_organizations == null)
            _organizations = new Organizations(this, Keys.INVOICES__INVOICES_ORGANIZATION_NUM_FKEY);

        return _organizations;
    }

    @Override
    public Invoices as(String alias) {
        return new Invoices(DSL.name(alias), this);
    }

    @Override
    public Invoices as(Name alias) {
        return new Invoices(alias, this);
    }

    @Override
    public Invoices as(Table<?> alias) {
        return new Invoices(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoices rename(String name) {
        return new Invoices(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoices rename(Name name) {
        return new Invoices(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoices rename(Table<?> name) {
        return new Invoices(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, LocalDateTime, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super Integer, ? super LocalDateTime, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super Integer, ? super LocalDateTime, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}