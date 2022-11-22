package recordForReports;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public @Data
class RecordForFourthReport {
    private final Integer productCode;
    private final String nameOfProduct;
    private final Double price;
}
