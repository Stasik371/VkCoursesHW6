package recordForReports;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public @Data
@AllArgsConstructor
class RecordForThirdReport {
    private final LocalDateTime dateOfInvoice;
    private final String productName;
    private final Integer productCode;
    private final BigDecimal amount;
    private final Integer price;
}
