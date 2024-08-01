package br.com.positionalfile.processor.reader;

import br.com.positionalfile.Record;
import br.com.positionalfile.processor.writer.Order;

import java.util.Objects;

public class PixTransaction implements Record {

    @FieldPosition(begin = 0, end = 19)
    @Order(1)
    private String senderKey;
    @FieldPosition(begin = 19, end = 41)
    @Order(2)
    private String recipientKey;
    @FieldPosition(begin = 41, end = 46)
    @Order(3)
    private Double amount;
    @FieldPosition(begin = 46, end = 54)
    @Order(4)
    private String transactionDate;
    @FieldPosition(begin = 54, end = 65)
    @Order(5)
    private String senderName;
    @FieldPosition(begin = 65, end = 79)
    @Order(6)
    private String recipientName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixTransaction that = (PixTransaction) o;
        return Objects.equals(senderKey, that.senderKey) && Objects.equals(recipientKey, that.recipientKey) && Objects.equals(amount, that.amount) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(senderName, that.senderName) && Objects.equals(recipientName, that.recipientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderKey, recipientKey, amount, transactionDate, senderName, recipientName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PixTransaction{");
        sb.append("senderKey='").append(senderKey).append('\'');
        sb.append(", recipientKey='").append(recipientKey).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", transactionDate='").append(transactionDate).append('\'');
        sb.append(", senderName='").append(senderName).append('\'');
        sb.append(", recipientName='").append(recipientName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private String removeDecimalPlacesFromAmount() {
        return String.valueOf(amount).split("\\.")[0];
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public String getRecipientKey() {
        return recipientKey;
    }

    public void setRecipientKey(String recipientKey) {
        this.recipientKey = recipientKey;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
