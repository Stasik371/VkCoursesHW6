package recordForReports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordForFifthReport {
    private final String organizationName;
    private final Integer indTaxpayerNum;
    private final Integer checkingAccount;
    private final String nameOfProduct;
    private final Integer productCode;
}
