package recordForReports;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public @Data
class RecordProductPrice {
    private final Integer productCode;
    private final String nameOfProduct;
    private final Double price;
}
