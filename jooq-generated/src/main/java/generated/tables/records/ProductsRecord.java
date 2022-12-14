/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Products;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductsRecord extends UpdatableRecordImpl<ProductsRecord> implements Record2<String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.products.name_of_product</code>.
     */
    public ProductsRecord setNameOfProduct(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.products.name_of_product</code>.
     */
    public String getNameOfProduct() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.products.product_code</code>.
     */
    public ProductsRecord setProductCode(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.products.product_code</code>.
     */
    public Integer getProductCode() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Products.PRODUCTS.NAME_OF_PRODUCT;
    }

    @Override
    public Field<Integer> field2() {
        return Products.PRODUCTS.PRODUCT_CODE;
    }

    @Override
    public String component1() {
        return getNameOfProduct();
    }

    @Override
    public Integer component2() {
        return getProductCode();
    }

    @Override
    public String value1() {
        return getNameOfProduct();
    }

    @Override
    public Integer value2() {
        return getProductCode();
    }

    @Override
    public ProductsRecord value1(String value) {
        setNameOfProduct(value);
        return this;
    }

    @Override
    public ProductsRecord value2(Integer value) {
        setProductCode(value);
        return this;
    }

    @Override
    public ProductsRecord values(String value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductsRecord
     */
    public ProductsRecord() {
        super(Products.PRODUCTS);
    }

    /**
     * Create a detached, initialised ProductsRecord
     */
    public ProductsRecord(String nameOfProduct, Integer productCode) {
        super(Products.PRODUCTS);

        setNameOfProduct(nameOfProduct);
        setProductCode(productCode);
    }

    /**
     * Create a detached, initialised ProductsRecord
     */
    public ProductsRecord(generated.tables.pojos.Products value) {
        super(Products.PRODUCTS);

        if (value != null) {
            setNameOfProduct(value.getNameOfProduct());
            setProductCode(value.getProductCode());
        }
    }
}
