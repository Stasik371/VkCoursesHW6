/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Organizations;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrganizationsRecord extends UpdatableRecordImpl<OrganizationsRecord> implements Record3<String, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.organizations.organizations_name</code>.
     */
    public OrganizationsRecord setOrganizationsName(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.organizations.organizations_name</code>.
     */
    public String getOrganizationsName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.organizations.ind_taxpayer_num</code>.
     */
    public OrganizationsRecord setIndTaxpayerNum(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.organizations.ind_taxpayer_num</code>.
     */
    public Integer getIndTaxpayerNum() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.organizations.checking_account</code>.
     */
    public OrganizationsRecord setCheckingAccount(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.organizations.checking_account</code>.
     */
    public Integer getCheckingAccount() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, Integer, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Organizations.ORGANIZATIONS.ORGANIZATIONS_NAME;
    }

    @Override
    public Field<Integer> field2() {
        return Organizations.ORGANIZATIONS.IND_TAXPAYER_NUM;
    }

    @Override
    public Field<Integer> field3() {
        return Organizations.ORGANIZATIONS.CHECKING_ACCOUNT;
    }

    @Override
    public String component1() {
        return getOrganizationsName();
    }

    @Override
    public Integer component2() {
        return getIndTaxpayerNum();
    }

    @Override
    public Integer component3() {
        return getCheckingAccount();
    }

    @Override
    public String value1() {
        return getOrganizationsName();
    }

    @Override
    public Integer value2() {
        return getIndTaxpayerNum();
    }

    @Override
    public Integer value3() {
        return getCheckingAccount();
    }

    @Override
    public OrganizationsRecord value1(String value) {
        setOrganizationsName(value);
        return this;
    }

    @Override
    public OrganizationsRecord value2(Integer value) {
        setIndTaxpayerNum(value);
        return this;
    }

    @Override
    public OrganizationsRecord value3(Integer value) {
        setCheckingAccount(value);
        return this;
    }

    @Override
    public OrganizationsRecord values(String value1, Integer value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrganizationsRecord
     */
    public OrganizationsRecord() {
        super(Organizations.ORGANIZATIONS);
    }

    /**
     * Create a detached, initialised OrganizationsRecord
     */
    public OrganizationsRecord(String organizationsName, Integer indTaxpayerNum, Integer checkingAccount) {
        super(Organizations.ORGANIZATIONS);

        setOrganizationsName(organizationsName);
        setIndTaxpayerNum(indTaxpayerNum);
        setCheckingAccount(checkingAccount);
    }

    /**
     * Create a detached, initialised OrganizationsRecord
     */
    public OrganizationsRecord(generated.tables.pojos.Organizations value) {
        super(Organizations.ORGANIZATIONS);

        if (value != null) {
            setOrganizationsName(value.getOrganizationsName());
            setIndTaxpayerNum(value.getIndTaxpayerNum());
            setCheckingAccount(value.getCheckingAccount());
        }
    }
}
