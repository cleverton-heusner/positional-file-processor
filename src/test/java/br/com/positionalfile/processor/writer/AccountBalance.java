package br.com.positionalfile.processor.writer;

import br.com.positionalfile.RecordLayout;

public class AccountBalance implements RecordLayout {

    @Order(1)
    private final String accountNumber;
    @Order(2)
    private final String cpf;
    @Order(3)
    private double balance;

    public AccountBalance(String accountNumber, String cpf, double balance) {
        this.accountNumber = accountNumber;
        this.cpf = cpf;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountBalance{");
        sb.append("accountNumber='").append(accountNumber).append('\'');
        sb.append(", cpf='").append(cpf).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}