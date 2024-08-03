package br.com.positionalfile.processor.writer;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.formatter.decimal.Decimal;

import java.math.BigDecimal;

public class TransferWithDecimalFormatting implements RecordLayout {

    @Order(1)
    private String sourceAccount;
    @Order(2)
    private String destinationAccount;
    @Order(3)
    @Decimal(isSigned = true, maxSize = 20, paddingChar = '0')
    private BigDecimal amount;
    @Order(4)
    private String transferDate;

    @Override
    public String toString() {
        return sourceAccount + destinationAccount + amount + transferDate;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}