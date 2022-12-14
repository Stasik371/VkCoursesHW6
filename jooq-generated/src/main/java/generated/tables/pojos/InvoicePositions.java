/*
 * This file is generated by jOOQ.
 */
package generated.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InvoicePositions implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final Integer invoiceId;
    private final Integer price;
    private final Integer amount;
    private final Integer productCode;

    public InvoicePositions(InvoicePositions value) {
        this.id = value.id;
        this.invoiceId = value.invoiceId;
        this.price = value.price;
        this.amount = value.amount;
        this.productCode = value.productCode;
    }

    public InvoicePositions(
        Integer id,
        Integer invoiceId,
        Integer price,
        Integer amount,
        Integer productCode
    ) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.price = price;
        this.amount = amount;
        this.productCode = productCode;
    }

    /**
     * Getter for <code>public.invoice_positions.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Getter for <code>public.invoice_positions.invoice_id</code>.
     */
    public Integer getInvoiceId() {
        return this.invoiceId;
    }

    /**
     * Getter for <code>public.invoice_positions.price</code>.
     */
    public Integer getPrice() {
        return this.price;
    }

    /**
     * Getter for <code>public.invoice_positions.amount</code>.
     */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * Getter for <code>public.invoice_positions.product_code</code>.
     */
    public Integer getProductCode() {
        return this.productCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final InvoicePositions other = (InvoicePositions) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.invoiceId == null) {
            if (other.invoiceId != null)
                return false;
        }
        else if (!this.invoiceId.equals(other.invoiceId))
            return false;
        if (this.price == null) {
            if (other.price != null)
                return false;
        }
        else if (!this.price.equals(other.price))
            return false;
        if (this.amount == null) {
            if (other.amount != null)
                return false;
        }
        else if (!this.amount.equals(other.amount))
            return false;
        if (this.productCode == null) {
            if (other.productCode != null)
                return false;
        }
        else if (!this.productCode.equals(other.productCode))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.invoiceId == null) ? 0 : this.invoiceId.hashCode());
        result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
        result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
        result = prime * result + ((this.productCode == null) ? 0 : this.productCode.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InvoicePositions (");

        sb.append(id);
        sb.append(", ").append(invoiceId);
        sb.append(", ").append(price);
        sb.append(", ").append(amount);
        sb.append(", ").append(productCode);

        sb.append(")");
        return sb.toString();
    }
}
