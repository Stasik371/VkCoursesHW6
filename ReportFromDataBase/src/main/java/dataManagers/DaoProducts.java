package dataManagers;

import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static generated.tables.Products.PRODUCTS;

public class DaoProducts implements DAO<ProductsRecord>{

    private final @NotNull Connection connection;
    private final @NotNull DSLContext context;

    public DaoProducts(@NotNull Connection connection) {
        this.connection = connection;
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public @NotNull ProductsRecord get(int id) {
        var record = context
                .select()
                .from(PRODUCTS)
                .where(PRODUCTS.PRODUCT_CODE.eq(id))
                .fetch();
        try {
            return new ProductsRecord(record.get(0).getValue(PRODUCTS.NAME_OF_PRODUCT),
                    record.get(0).getValue(PRODUCTS.PRODUCT_CODE));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("No record in Table");
        }
    }

    public @NotNull List<ProductsRecord> all() {
        var listOfProducts = new ArrayList<ProductsRecord>();
        var result = context
                .select()
                .from(PRODUCTS)
                .fetch();
        for (var record : result) {
            listOfProducts.add(new ProductsRecord(record.getValue(PRODUCTS.NAME_OF_PRODUCT),
                    record.getValue(PRODUCTS.PRODUCT_CODE)));
        }
        return listOfProducts;
    }

    public void save(@NotNull ProductsRecord product) {
        context.insertInto(PRODUCTS, PRODUCTS.NAME_OF_PRODUCT, PRODUCTS.PRODUCT_CODE)
                .values(product.getNameOfProduct(), product.getProductCode())
                .execute();
    }

    public void delete(@NotNull ProductsRecord product){
        context.delete(PRODUCTS)
                .where(PRODUCTS.PRODUCT_CODE.eq(product.getProductCode()))
                .execute();
    }

    public void update(@NotNull ProductsRecord product) {
        context.update(PRODUCTS)
                .set(PRODUCTS.NAME_OF_PRODUCT,product.getNameOfProduct())
                .where(PRODUCTS.PRODUCT_CODE.eq(product.getProductCode()))
                .execute();
    }
}
