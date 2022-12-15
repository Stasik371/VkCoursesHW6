package controllers;

import commons.JDBCCredentials;
import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;
import recordForReports.RecordOrganizationProducts;
import recordForReports.RecordProductPrice;
import recordForReports.RecordDateProductsAmountPrice;

import java.math.BigDecimal;
import java.sql.*;

import static generated.tables.Organizations.ORGANIZATIONS;
import static generated.tables.InvoicePositions.INVOICE_POSITIONS;
import static generated.tables.Invoices.INVOICES;
import static generated.tables.Products.PRODUCTS;

import java.time.LocalDateTime;
import java.util.*;


public class ReportCreator {
    private static final @NotNull
    JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private final Connection connection;
    private final @NotNull DSLContext context;

    public ReportCreator(Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }


    public ReportCreator() {
        try {
            this.connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password());
            this.context = DSL.using(connection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    //Выбрать первые 10 поставщиков по количеству поставленного товара
    public ArrayList<OrganizationsRecord> first10OrganizationsByProduct(@NotNull ProductsRecord product) {
        var result = context.select()
                .from(ORGANIZATIONS)
                .join(INVOICES)
                .on(ORGANIZATIONS.IND_TAXPAYER_NUM.eq(INVOICES.ORGANIZATION_NUM))
                .join(INVOICE_POSITIONS)
                .on(INVOICES.INVOICE_ID.eq(INVOICE_POSITIONS.INVOICE_ID))
                .join(PRODUCTS)
                .on(INVOICE_POSITIONS.PRODUCT_CODE.eq(PRODUCTS.PRODUCT_CODE))
                .where(PRODUCTS.PRODUCT_CODE.eq(product.getProductCode()))
                .orderBy(INVOICE_POSITIONS.AMOUNT.desc())
                .limit(10);
        var listOfOrganization = new ArrayList<OrganizationsRecord>();
        for (var record : result) {
            var bufOrganization=record.into(ORGANIZATIONS);
            listOfOrganization.add(bufOrganization);
        }
        return listOfOrganization;
    }

    //Выбрать поставщиков с суммой поставленного товара выше указанного количества
    //(товар и его количество должны допускать множественное указание).
    public ArrayList<OrganizationsRecord> organizationsWithMoreAmountThenParams(@NotNull Map<Integer, Integer> mapWithProducts) {
        var listOfOrganization = new ArrayList<OrganizationsRecord>();
        for (Map.Entry<Integer, Integer> record : mapWithProducts.entrySet()) {
            var result = context
                    .select()
                    .from(ORGANIZATIONS)
                    .join(INVOICES)
                    .on(INVOICES.ORGANIZATION_NUM.eq(ORGANIZATIONS.IND_TAXPAYER_NUM))
                    .join(INVOICE_POSITIONS)
                    .on(INVOICES.INVOICE_ID.eq(INVOICE_POSITIONS.INVOICE_ID))
                    .where((INVOICE_POSITIONS.PRICE).gt(record.getKey()))
                    .and(INVOICE_POSITIONS.PRODUCT_CODE.eq(record.getValue()))
                    .fetch();
            for (var recordFromOneQuery : result) {
                var bufOrganization=recordFromOneQuery.into(ORGANIZATIONS);
                listOfOrganization.add(bufOrganization);
            }
            listOfOrganization.add(null);
        }
        return listOfOrganization;
    }

    //За каждый день для каждого товара рассчитать количество и
    //сумму полученного товара в указанном периоде, посчитать итоги за период
    public ArrayList<RecordDateProductsAmountPrice> getSumPerDay(LocalDateTime firstDate, LocalDateTime secondDate) {
        var result = context
                .select(INVOICES.DATE_OF_INVOICE, PRODUCTS.NAME_OF_PRODUCT, PRODUCTS.PRODUCT_CODE,
                        DSL.sum(INVOICE_POSITIONS.AMOUNT).as("amount"), INVOICE_POSITIONS.PRICE)
                .from(PRODUCTS)
                .join(INVOICE_POSITIONS)
                .on(PRODUCTS.PRODUCT_CODE.eq(INVOICE_POSITIONS.PRODUCT_CODE))
                .join(INVOICES)
                .on(INVOICE_POSITIONS.INVOICE_ID.eq(INVOICES.INVOICE_ID))
                .where(INVOICES.DATE_OF_INVOICE.between(firstDate, secondDate))
                .groupBy(INVOICES.DATE_OF_INVOICE,
                        PRODUCTS.NAME_OF_PRODUCT, INVOICE_POSITIONS.PRICE,
                        PRODUCTS.PRODUCT_CODE, INVOICE_POSITIONS.PRICE)
                .fetch();
        var listOfEntitiesInReport = new ArrayList<RecordDateProductsAmountPrice>();
        for (var record : result) {
            listOfEntitiesInReport.add(new RecordDateProductsAmountPrice(
                    record.getValue(INVOICES.DATE_OF_INVOICE),
                    record.getValue(PRODUCTS.NAME_OF_PRODUCT),
                    record.getValue(INVOICE_POSITIONS.PRODUCT_CODE),
                    (BigDecimal) record.getValue("amount"),
                    record.getValue(INVOICE_POSITIONS.PRICE)));
        }
        return listOfEntitiesInReport;

    }


    //Рассчитать среднюю цену по каждому товару за период
    public ArrayList<RecordProductPrice> averageValueBetween(LocalDateTime firstDate, LocalDateTime secondDate) {
        var result = context
                .select(PRODUCTS.PRODUCT_CODE, PRODUCTS.NAME_OF_PRODUCT, DSL.avg(INVOICE_POSITIONS.PRICE).as("price"))
                .from(INVOICE_POSITIONS)
                .join(INVOICES)
                .on(INVOICE_POSITIONS.INVOICE_ID.eq(INVOICES.INVOICE_ID))
                .fullOuterJoin(PRODUCTS)
                .on(INVOICE_POSITIONS.PRODUCT_CODE.eq(PRODUCTS.PRODUCT_CODE))
                .where(INVOICES.DATE_OF_INVOICE.between(firstDate, secondDate))
                .groupBy(PRODUCTS.PRODUCT_CODE, PRODUCTS.PRODUCT_CODE, PRODUCTS.PRODUCT_CODE)
                .fetch();
        var listOfEntities = new ArrayList<RecordProductPrice>();
        for (var record : result) {
            var price = (BigDecimal) record.getValue("price");
            var intPrice= price.doubleValue();
            listOfEntities.add(new RecordProductPrice(
                    record.getValue(INVOICE_POSITIONS.PRODUCT_CODE),
                    record.getValue(PRODUCTS.NAME_OF_PRODUCT),
                    intPrice));
        }
        return listOfEntities;
    }

    //Вывести список товаров, поставленных организациями за период.
    //Если организация товары не поставляла, то она все равно должна быть отражена в списке.
    public ArrayList<RecordOrganizationProducts> getAllProductByPeriod(LocalDateTime firstDate, LocalDateTime secondDate) {
        var result = context
                .select(ORGANIZATIONS.ORGANIZATIONS_NAME, ORGANIZATIONS.IND_TAXPAYER_NUM,
                        ORGANIZATIONS.CHECKING_ACCOUNT, PRODUCTS.NAME_OF_PRODUCT,
                        PRODUCTS.PRODUCT_CODE)
                .from(PRODUCTS)
                .rightJoin(INVOICE_POSITIONS)
                .on(PRODUCTS.PRODUCT_CODE.eq(INVOICE_POSITIONS.PRODUCT_CODE))
                .rightJoin(INVOICES)
                .on(INVOICES.INVOICE_ID.eq(INVOICE_POSITIONS.INVOICE_ID))
                .and(INVOICES.DATE_OF_INVOICE.between(firstDate, secondDate))
                .rightJoin(ORGANIZATIONS)
                .on(ORGANIZATIONS.IND_TAXPAYER_NUM.eq(INVOICES.ORGANIZATION_NUM))
                .orderBy(ORGANIZATIONS.IND_TAXPAYER_NUM, PRODUCTS.PRODUCT_CODE)
                .fetch();
        var listOfRecords = new ArrayList<RecordOrganizationProducts>();
        for (var record: result) {
            listOfRecords.add(new RecordOrganizationProducts(
                    record.getValue(ORGANIZATIONS.ORGANIZATIONS_NAME),
                    record.getValue(ORGANIZATIONS.IND_TAXPAYER_NUM),
                    record.getValue(ORGANIZATIONS.CHECKING_ACCOUNT),
                    record.getValue(PRODUCTS.NAME_OF_PRODUCT),
                    record.getValue(PRODUCTS.PRODUCT_CODE)));
        }
        return listOfRecords;
    }


}