package br.com.positionalfile.processor.reader;

import br.com.positionalfile.RecordLayout;

import java.util.Objects;

@Delimiter(value = "***", matcher = Matcher.NOT_START_WITH)
public class TransactionWithStarsDelimiter implements RecordLayout {

    @FieldPosition(begin = 0, end = 10)
    private String transactionId;
    @FieldPosition(begin = 10, end = 18)
    private String transactionDate;
    @FieldPosition(begin = 18, end = 19)
    private String transactionType;
    @FieldPosition(begin = 19, end = 31)
    private String accountNumber;
    @FieldPosition(begin = 31, end = 41)
    private Double transactionAmount;
    @FieldPosition(begin = 41, end = 71)
    private String description;
    @FieldPosition(begin = 71, end = 91)
    private String beneficiaryName;
    @FieldPosition(begin = 91, end = 97)
    private String authorizationCode;
    @FieldPosition(begin = 97, end = 108)
    private String cpf;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionWithStarsDelimiter that = (TransactionWithStarsDelimiter) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(transactionType, that.transactionType) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(transactionAmount, that.transactionAmount) && Objects.equals(description, that.description) && Objects.equals(beneficiaryName, that.beneficiaryName) && Objects.equals(authorizationCode, that.authorizationCode) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, transactionDate, transactionType, accountNumber, transactionAmount, description, beneficiaryName, authorizationCode, cpf);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-8s %-1s %-12s %-10.2f %-30s %-20s %-6s %-11s",
                transactionId,
                transactionDate,
                transactionType,
                accountNumber,
                transactionAmount,
                description,
                beneficiaryName,
                authorizationCode,
                cpf);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}