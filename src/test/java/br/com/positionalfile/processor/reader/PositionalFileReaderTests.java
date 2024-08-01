package br.com.positionalfile.processor.reader;

import br.com.positionalfile.Record;
import br.com.positionalfile.processor.Footer;
import br.com.positionalfile.processor.Header;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionalFileReaderTests {

    @Test
    public void when_inputFilePath_then_recordsReadInOrder() {
        // Arrange
        final Transaction expectedTransaction1 = new Transaction();
        expectedTransaction1.setTransactionId("0000000001");
        expectedTransaction1.setTransactionDate("20230701");
        expectedTransaction1.setTransactionType("D");
        expectedTransaction1.setAccountNumber("123456789012");
        expectedTransaction1.setTransactionAmount(10000.0);
        expectedTransaction1.setDescription("Pagamento de aluguel          ");
        expectedTransaction1.setBeneficiaryName("João Silva          ");
        expectedTransaction1.setAuthorizationCode("A12345");
        expectedTransaction1.setCpf("11122233344");

        final Transaction expectedTransaction2 = new Transaction();
        expectedTransaction2.setTransactionId("0000000002");
        expectedTransaction2.setTransactionDate("20230701");
        expectedTransaction2.setTransactionType("C");
        expectedTransaction2.setAccountNumber("123456789012");
        expectedTransaction2.setTransactionAmount(5000.0);
        expectedTransaction2.setDescription("Depósito em dinheiro          ");
        expectedTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedTransaction2.setAuthorizationCode("B23456");
        expectedTransaction2.setCpf("11122233344");

        final Transaction expectedTransaction3 = new Transaction();
        expectedTransaction3.setTransactionId("0000000003");
        expectedTransaction3.setTransactionDate("20230702");
        expectedTransaction3.setTransactionType("D");
        expectedTransaction3.setAccountNumber("098765432109");
        expectedTransaction3.setTransactionAmount(1500.0);
        expectedTransaction3.setDescription("Compra no supermercado        ");
        expectedTransaction3.setBeneficiaryName("Supermercado XYZ    ");
        expectedTransaction3.setAuthorizationCode("C34567");
        expectedTransaction3.setCpf("55566677788");

        final Transaction expectedTransaction4 = new Transaction();
        expectedTransaction4.setTransactionId("0000000004");
        expectedTransaction4.setTransactionDate("20230703");
        expectedTransaction4.setTransactionType("C");
        expectedTransaction4.setAccountNumber("098765432109");
        expectedTransaction4.setTransactionAmount(2000.0);
        expectedTransaction4.setDescription("Transferência recebida        ");
        expectedTransaction4.setBeneficiaryName("Pedro Almeida       ");
        expectedTransaction4.setAuthorizationCode("D45678");
        expectedTransaction4.setCpf("55566677788");

        final String inputFilePath = "src/test/resources/transactions.txt";

        // Act
        final List<Record> actualTransactions = new PositionalFileReader().readRecords(inputFilePath, Transaction.class);

        // Assert
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2, expectedTransaction3, expectedTransaction4);
    }

    @Test
    public void when_inputFileStream_then_recordsReadInOrder() {
        // Arrange
        final Transaction expectedTransaction1 = new Transaction();
        expectedTransaction1.setTransactionId("0000000001");
        expectedTransaction1.setTransactionDate("20230701");
        expectedTransaction1.setTransactionType("D");
        expectedTransaction1.setAccountNumber("123456789012");
        expectedTransaction1.setTransactionAmount(10000.0);
        expectedTransaction1.setDescription("Pagamento de aluguel          ");
        expectedTransaction1.setBeneficiaryName("João Silva          ");
        expectedTransaction1.setAuthorizationCode("A12345");
        expectedTransaction1.setCpf("11122233344");

        final Transaction expectedTransaction2 = new Transaction();
        expectedTransaction2.setTransactionId("0000000002");
        expectedTransaction2.setTransactionDate("20230701");
        expectedTransaction2.setTransactionType("C");
        expectedTransaction2.setAccountNumber("123456789012");
        expectedTransaction2.setTransactionAmount(5000.0);
        expectedTransaction2.setDescription("Depósito em dinheiro          ");
        expectedTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedTransaction2.setAuthorizationCode("B23456");
        expectedTransaction2.setCpf("11122233344");

        final Transaction expectedTransaction3 = new Transaction();
        expectedTransaction3.setTransactionId("0000000003");
        expectedTransaction3.setTransactionDate("20230702");
        expectedTransaction3.setTransactionType("D");
        expectedTransaction3.setAccountNumber("098765432109");
        expectedTransaction3.setTransactionAmount(1500.0);
        expectedTransaction3.setDescription("Compra no supermercado        ");
        expectedTransaction3.setBeneficiaryName("Supermercado XYZ    ");
        expectedTransaction3.setAuthorizationCode("C34567");
        expectedTransaction3.setCpf("55566677788");

        final Transaction expectedTransaction4 = new Transaction();
        expectedTransaction4.setTransactionId("0000000004");
        expectedTransaction4.setTransactionDate("20230703");
        expectedTransaction4.setTransactionType("C");
        expectedTransaction4.setAccountNumber("098765432109");
        expectedTransaction4.setTransactionAmount(2000.0);
        expectedTransaction4.setDescription("Transferência recebida        ");
        expectedTransaction4.setBeneficiaryName("Pedro Almeida       ");
        expectedTransaction4.setAuthorizationCode("D45678");
        expectedTransaction4.setCpf("55566677788");

        final String inputFilePath = "transactions.txt";
        final InputStream inputFileStream = getClass().getClassLoader().getResourceAsStream(inputFilePath);

        // Act
        final List<Record> actualTransactions = new PositionalFileReader().readRecords(inputFileStream, Transaction.class);

        // Assert
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2, expectedTransaction3, expectedTransaction4);
    }

    @Test
    public void when_differentEntity_then_recordsReadInOrder() {
        // Arrange
        final PixTransaction expectedPixTransaction1 = new PixTransaction();
        expectedPixTransaction1.setSenderKey("chave-remetente-123");
        expectedPixTransaction1.setRecipientKey("chave-destinatario-456");
        expectedPixTransaction1.setAmount(15075.0);
        expectedPixTransaction1.setTransactionDate("20230701");
        expectedPixTransaction1.setSenderName("João Silva ");
        expectedPixTransaction1.setRecipientName("Maria Oliveira");

        final PixTransaction expectedPixTransaction2 = new PixTransaction();
        expectedPixTransaction2.setSenderKey("chave-remetente-123");
        expectedPixTransaction2.setRecipientKey("chave-destinatario-777");
        expectedPixTransaction2.setAmount(20050.0);
        expectedPixTransaction2.setTransactionDate("20230701");
        expectedPixTransaction2.setSenderName("José Luiz  ");
        expectedPixTransaction2.setRecipientName("Beatriz Duarte");

        final String inputFilePath = "src/test/resources/sent_pixes_input.txt";

        // Act
        final List<Record> actualPixTransactions = new PositionalFileReader().readRecords(inputFilePath, PixTransaction.class);

        // Assert
        assertThat(actualPixTransactions).containsExactly(expectedPixTransaction1, expectedPixTransaction2);
    }

    @Test
    public void when_layoutAsHeaderBodyFooter_then_recordsReadInOrder() {
        // Arrange
        final Header expectedHeader = new Header();
        expectedHeader.setTitle("*** HEADER ***");

        final Transaction expectedTransaction1 = new Transaction();
        expectedTransaction1.setTransactionId("0000000001");
        expectedTransaction1.setTransactionDate("20230701");
        expectedTransaction1.setTransactionType("D");
        expectedTransaction1.setAccountNumber("123456789012");
        expectedTransaction1.setTransactionAmount(10000.0);
        expectedTransaction1.setDescription("Pagamento de aluguel          ");
        expectedTransaction1.setBeneficiaryName("João Silva          ");
        expectedTransaction1.setAuthorizationCode("A12345");
        expectedTransaction1.setCpf("11122233344");

        final Transaction expectedTransaction2 = new Transaction();
        expectedTransaction2.setTransactionId("0000000002");
        expectedTransaction2.setTransactionDate("20230701");
        expectedTransaction2.setTransactionType("C");
        expectedTransaction2.setAccountNumber("123456789012");
        expectedTransaction2.setTransactionAmount(5000.0);
        expectedTransaction2.setDescription("Depósito em dinheiro          ");
        expectedTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedTransaction2.setAuthorizationCode("B23456");
        expectedTransaction2.setCpf("11122233344");

        final Transaction expectedTransaction3 = new Transaction();
        expectedTransaction3.setTransactionId("0000000003");
        expectedTransaction3.setTransactionDate("20230702");
        expectedTransaction3.setTransactionType("D");
        expectedTransaction3.setAccountNumber("098765432109");
        expectedTransaction3.setTransactionAmount(1500.0);
        expectedTransaction3.setDescription("Compra no supermercado        ");
        expectedTransaction3.setBeneficiaryName("Supermercado XYZ    ");
        expectedTransaction3.setAuthorizationCode("C34567");
        expectedTransaction3.setCpf("55566677788");

        final Transaction expectedTransaction4 = new Transaction();
        expectedTransaction4.setTransactionId("0000000004");
        expectedTransaction4.setTransactionDate("20230703");
        expectedTransaction4.setTransactionType("C");
        expectedTransaction4.setAccountNumber("098765432109");
        expectedTransaction4.setTransactionAmount(2000.0);
        expectedTransaction4.setDescription("Transferência recebida        ");
        expectedTransaction4.setBeneficiaryName("Pedro Almeida       ");
        expectedTransaction4.setAuthorizationCode("D45678");
        expectedTransaction4.setCpf("55566677788");

        final Footer expectedFooter = new Footer();
        expectedFooter.setTitle("*** FOOTER ***");

        final int expectedRecordsSize = 6;

        final String inputFilePath = "src/test/resources/header_transactions_footer.txt";

        // Act
        final List<? extends Record> records = new PositionalFileReader().readRecords(
                inputFilePath,
                Header.class,
                Transaction.class,
                Footer.class
        );
        final Header actualHeader = (Header) records.get(0);
        final List<Transaction> actualTransactions = records.subList(1, 5)
                .stream()
                .map(Transaction.class::cast)
                .collect(Collectors.toList());
        final Footer actualFooter = (Footer) records.get(5);

        // Assert
        assertThat(actualHeader).isEqualTo(expectedHeader);
        assertThat(actualTransactions).containsExactly(
                expectedTransaction1,
                expectedTransaction2,
                expectedTransaction3,
                expectedTransaction4
        );
        assertThat(actualFooter).isEqualTo(expectedFooter);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_layoutAsHeaderBody_then_recordsReadInOrder() {
        // Arrange
        final Header expectedHeader = new Header();
        expectedHeader.setTitle("*** HEADER ***");

        final Transaction expectedTransaction1 = new Transaction();
        expectedTransaction1.setTransactionId("0000000001");
        expectedTransaction1.setTransactionDate("20230701");
        expectedTransaction1.setTransactionType("D");
        expectedTransaction1.setAccountNumber("123456789012");
        expectedTransaction1.setTransactionAmount(10000.0);
        expectedTransaction1.setDescription("Pagamento de aluguel          ");
        expectedTransaction1.setBeneficiaryName("João Silva          ");
        expectedTransaction1.setAuthorizationCode("A12345");
        expectedTransaction1.setCpf("11122233344");

        final Transaction expectedTransaction2 = new Transaction();
        expectedTransaction2.setTransactionId("0000000002");
        expectedTransaction2.setTransactionDate("20230701");
        expectedTransaction2.setTransactionType("C");
        expectedTransaction2.setAccountNumber("123456789012");
        expectedTransaction2.setTransactionAmount(5000.0);
        expectedTransaction2.setDescription("Depósito em dinheiro          ");
        expectedTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedTransaction2.setAuthorizationCode("B23456");
        expectedTransaction2.setCpf("11122233344");

        final Transaction expectedTransaction3 = new Transaction();
        expectedTransaction3.setTransactionId("0000000003");
        expectedTransaction3.setTransactionDate("20230702");
        expectedTransaction3.setTransactionType("D");
        expectedTransaction3.setAccountNumber("098765432109");
        expectedTransaction3.setTransactionAmount(1500.0);
        expectedTransaction3.setDescription("Compra no supermercado        ");
        expectedTransaction3.setBeneficiaryName("Supermercado XYZ    ");
        expectedTransaction3.setAuthorizationCode("C34567");
        expectedTransaction3.setCpf("55566677788");

        final Transaction expectedTransaction4 = new Transaction();
        expectedTransaction4.setTransactionId("0000000004");
        expectedTransaction4.setTransactionDate("20230703");
        expectedTransaction4.setTransactionType("C");
        expectedTransaction4.setAccountNumber("098765432109");
        expectedTransaction4.setTransactionAmount(2000.0);
        expectedTransaction4.setDescription("Transferência recebida        ");
        expectedTransaction4.setBeneficiaryName("Pedro Almeida       ");
        expectedTransaction4.setAuthorizationCode("D45678");
        expectedTransaction4.setCpf("55566677788");

        final int expectedRecordsSize = 5;

        final String inputFilePath = "src/test/resources/header_transactions.txt";

        // Act
        final List<? extends Record> records = new PositionalFileReader().readRecords(
                inputFilePath,
                Header.class,
                Transaction.class
        );
        final Header actualHeader = (Header) records.get(0);
        final List<Transaction> actualTransactions = records.subList(1, 5)
                .stream()
                .map(Transaction.class::cast)
                .collect(Collectors.toList());

        // Assert
        assertThat(actualHeader).isEqualTo(expectedHeader);
        assertThat(actualTransactions).containsExactly(
                expectedTransaction1,
                expectedTransaction2,
                expectedTransaction3,
                expectedTransaction4
        );
        assertThat(records).hasSize(expectedRecordsSize);
    }
}