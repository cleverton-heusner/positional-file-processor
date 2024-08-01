package br.com.positionalfile.processor.writer;

import br.com.positionalfile.Record;
import br.com.positionalfile.formatter.Alignment;
import br.com.positionalfile.formatter.text.Text;

public class Transfer implements Record {

    @Order(1)
    @Text(maxSize = 20)
    private String sourceAccount;
    @Order(2)
    @Text(maxSize = 20, paddingChar = '*', alignment = Alignment.LEFT)
    private String destinationAccount;
    @Order(3)
    private double amount;
    @Order(4)
    private String transferDate;

    @Override
    public String toString() {
        return sourceAccount + destinationAccount + amount + transferDate;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}
