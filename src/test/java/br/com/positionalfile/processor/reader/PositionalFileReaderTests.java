package br.com.positionalfile.processor.reader;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.processor.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public class PositionalFileReaderTests extends PositionalFileTestsConfig {

    private static Transaction expectedTransaction1;
    private static Transaction expectedTransaction2;
    private static TransactionWithNotStartWithDelimiter expectedTransactionWithNotStartWithDelimiter1;
    private static TransactionWithNotStartWithDelimiter expectedTransactionWithNotStartWithDelimiter2;

    @BeforeAll
    public static void initialize() {
        expectedTransaction1 = new Transaction();
        expectedTransaction1.setTransactionId("0000000001");
        expectedTransaction1.setTransactionDate("20230701");
        expectedTransaction1.setTransactionType("D");
        expectedTransaction1.setAccountNumber("123456789012");
        expectedTransaction1.setTransactionAmount(10000.0);
        expectedTransaction1.setDescription("Pagamento de aluguel          ");
        expectedTransaction1.setBeneficiaryName("João Silva          ");
        expectedTransaction1.setAuthorizationCode("A12345");
        expectedTransaction1.setCpf("11122233344");

        expectedTransaction2 = new Transaction();
        expectedTransaction2.setTransactionId("0000000002");
        expectedTransaction2.setTransactionDate("20230701");
        expectedTransaction2.setTransactionType("C");
        expectedTransaction2.setAccountNumber("123456789012");
        expectedTransaction2.setTransactionAmount(5000.0);
        expectedTransaction2.setDescription("Depósito em dinheiro          ");
        expectedTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedTransaction2.setAuthorizationCode("B23456");
        expectedTransaction2.setCpf("11122233344");

        expectedTransactionWithNotStartWithDelimiter1 = new TransactionWithNotStartWithDelimiter();
        expectedTransactionWithNotStartWithDelimiter1.setTransactionId("0000000001");
        expectedTransactionWithNotStartWithDelimiter1.setTransactionDate("20230701");
        expectedTransactionWithNotStartWithDelimiter1.setTransactionType("D");
        expectedTransactionWithNotStartWithDelimiter1.setAccountNumber("123456789012");
        expectedTransactionWithNotStartWithDelimiter1.setTransactionAmount(10000.0);
        expectedTransactionWithNotStartWithDelimiter1.setDescription("Pagamento de aluguel          ");
        expectedTransactionWithNotStartWithDelimiter1.setBeneficiaryName("João Silva          ");
        expectedTransactionWithNotStartWithDelimiter1.setAuthorizationCode("A12345");
        expectedTransactionWithNotStartWithDelimiter1.setCpf("11122233344");

        expectedTransactionWithNotStartWithDelimiter2 = new TransactionWithNotStartWithDelimiter();
        expectedTransactionWithNotStartWithDelimiter2.setTransactionId("0000000002");
        expectedTransactionWithNotStartWithDelimiter2.setTransactionDate("20230701");
        expectedTransactionWithNotStartWithDelimiter2.setTransactionType("C");
        expectedTransactionWithNotStartWithDelimiter2.setAccountNumber("123456789012");
        expectedTransactionWithNotStartWithDelimiter2.setTransactionAmount(5000.0);
        expectedTransactionWithNotStartWithDelimiter2.setDescription("Depósito em dinheiro          ");
        expectedTransactionWithNotStartWithDelimiter2.setBeneficiaryName("Maria Souza         ");
        expectedTransactionWithNotStartWithDelimiter2.setAuthorizationCode("B23456");
        expectedTransactionWithNotStartWithDelimiter2.setCpf("11122233344");
    }

    @Test
    public void when_inputFilePath_then_recordsReadInOrder() {

        // Arrange
        final String inputFilePath = "src/test/resources/reader/transactions[0].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, Transaction.class);
        final var actualTransactions = (List<Transaction>) getSublist(records, Transaction.class, 2);

        // Assert
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2);
    }

    @Test
    public void when_inputFileStream_then_recordsReadInOrder() {

        // Arrange
        final String inputFilePath = "reader/transactions[0].txt";
        final InputStream inputFileStream = getClass().getClassLoader().getResourceAsStream(inputFilePath);

        // Act
        final var records = new PositionalFileReader().readRecords(inputFileStream, Transaction.class);
        final var actualTransactions = (List<Transaction>) getSublist(records, Transaction.class, 2);

        // Assert
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2);
    }

    @Test
    public void when_differentEntityFromTransaction_then_recordsReadInOrder() {

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

        final String inputFilePath = "src/test/resources/reader/pixes.txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, PixTransaction.class);
        final var actualPixTransactions = (List<PixTransaction>) getSublist(records, PixTransaction.class, 2);

        // Assert
        assertThat(actualPixTransactions).containsExactly(expectedPixTransaction1, expectedPixTransaction2);
    }

    @Test
    public void when_layoutAsHeaderBody_then_recordsReadInOrder() {

        // Arrange
        final Header expectedHeader = new Header("*** HEADER ***");
        final int expectedRecordsSize = 2;
        final String inputFilePath = "src/test/resources/reader/header[1]_transactions[0].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, Header.class, Transaction.class);
        final var actualTransactions = (List<Transaction>) getSublist(records, Transaction.class, 2);
        final var actualHeader = records.get(Header.class).get(0);

        // Assert
        assertThat(actualHeader).isEqualTo(expectedHeader);
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_layoutAsBodyFooter_then_recordsReadInOrder() {

        // Arrange
        final Footer expectedFooter = new Footer("*** FOOTER ***");
        final int expectedRecordsSize = 2;
        final String inputFilePath = "src/test/resources/reader/transactions[0]_footer[1].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, TransactionWithNotStartWithDelimiter.class,
                Footer.class);
        final var actualTransactions = (List<TransactionWithNotStartWithDelimiter>) getSublist(records,
                TransactionWithNotStartWithDelimiter.class, 2);
        final var actualFooter = records.get(Footer.class).get(0);

        // Assert
        assertThat(actualTransactions).containsExactly(
                expectedTransactionWithNotStartWithDelimiter1,
                expectedTransactionWithNotStartWithDelimiter2
        );
        assertThat(actualFooter).isEqualTo(expectedFooter);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_layoutAsHeaderWithSize1_And_BodyWithUnknownSize_AndFooterWithSize1_then_recordsReadInOrder() {

        // Arrange
        final var expectedHeader = new HeaderWithHeaderDelimiter("HEADER");

        final var expectedTransaction1 = new TransactionWithNotEqualsFooterDelimiter();
        expectedTransaction1.setTransactionId("0000000001");
        expectedTransaction1.setTransactionDate("20230701");
        expectedTransaction1.setTransactionType("D");
        expectedTransaction1.setAccountNumber("123456789012");
        expectedTransaction1.setTransactionAmount(10000.0);
        expectedTransaction1.setDescription("Pagamento de aluguel          ");
        expectedTransaction1.setBeneficiaryName("João Silva          ");
        expectedTransaction1.setAuthorizationCode("A12345");
        expectedTransaction1.setCpf("11122233344");

        final var expectedTransaction2 = new TransactionWithNotEqualsFooterDelimiter();
        expectedTransaction2.setTransactionId("0000000002");
        expectedTransaction2.setTransactionDate("20230701");
        expectedTransaction2.setTransactionType("C");
        expectedTransaction2.setAccountNumber("123456789012");
        expectedTransaction2.setTransactionAmount(5000.0);
        expectedTransaction2.setDescription("Depósito em dinheiro          ");
        expectedTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedTransaction2.setAuthorizationCode("B23456");
        expectedTransaction2.setCpf("11122233344");

        final var expectedFooter = new FooterWithTitleHavingSixSize("FOOTER");

        final int expectedRecordsSize = 3;

        final String inputFilePath = "src/test/resources/reader/header[1]_transactions[0]_footer[1].txt";

        // Act
        final Map<Class<?>, List<RecordLayout>> records = new PositionalFileReader().readRecords(
                inputFilePath,
                HeaderWithHeaderDelimiter.class,
                TransactionWithNotEqualsFooterDelimiter.class,
                FooterWithTitleHavingSixSize.class
        );

        // Assert
        final var actualHeader = (HeaderWithHeaderDelimiter) records.get(HeaderWithHeaderDelimiter.class).get(0);
        final var actualTransactions = (List<TransactionWithNotEqualsFooterDelimiter>) getSublist(records,
                TransactionWithNotEqualsFooterDelimiter.class, 2);
        var actualFooter = (FooterWithTitleHavingSixSize) records.get(FooterWithTitleHavingSixSize.class).get(0);

        assertThat(actualHeader).isEqualTo(expectedHeader);
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2);
        assertThat(actualFooter).isEqualTo(expectedFooter);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_layoutAsHeaderWithSize3_And_BodyWithUnknownSize_AndFooterWithSize3_then_recordsReadInOrder() {

        // Arrange
        final var expectedHeader1 = new HeaderWithStartWihDelimiter("**************");
        final var expectedHeader2 = new HeaderWithStartWihDelimiter("*** HEADER ***");
        final var expectedHeader3 = new HeaderWithStartWihDelimiter("**************");

        final var expectedFooter1 = new Footer("**************");
        final var expectedFooter2 = new Footer("*** FOOTER ***");
        final var expectedFooter3 = new Footer("**************");

        final int expectedRecordsSize = 3;

        final String inputFilePath = "src/test/resources/reader/header[3]_transactions[0]_footer[3].txt";

        // Act
        final Map<Class<?>, List<RecordLayout>> records = new PositionalFileReader().readRecords(
                inputFilePath,
                HeaderWithStartWihDelimiter.class,
                TransactionWithNotStartWithDelimiter.class,
                Footer.class
        );

        // Assert
        final var actualHeader = (List<HeaderWithStartWihDelimiter>) getSublist(records, HeaderWithStartWihDelimiter.class,
                3);
        final var actualTransactions = (List<TransactionWithNotStartWithDelimiter>) getSublist(records,
                TransactionWithNotStartWithDelimiter.class, 2);
        final var actualFooter = (List<Footer>) getSublist(records, Footer.class, 3);

        assertThat(actualHeader).containsExactly(expectedHeader1, expectedHeader2, expectedHeader3);
        assertThat(actualTransactions).containsExactly(expectedTransactionWithNotStartWithDelimiter1,
                expectedTransactionWithNotStartWithDelimiter2);
        assertThat(actualFooter).containsExactly(expectedFooter1, expectedFooter2, expectedFooter3);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_layoutAsHeaderWithSize3_And_HeaderWithSize1_And_HeaderWithSize1_And_BodyWithUnknownSize_AndFooterWithSize3_then_recordsReadInOrder() {

        // Arrange
        final var expectedHeader1 = new HeaderWithStartWihDelimiter("**************");
        final var expectedHeader2 = new HeaderWithStartWihDelimiter("*** HEADER ***");
        final var expectedHeader3 = new HeaderWithStartWihDelimiter("**************");

        final var expectedDebitsHeader = new HeaderWithDebitsDelimiter("DEBITOS");

        final var expectedCreditTransaction1 = new TransactionWithNotEqualsCreditsDelimiter();
        expectedCreditTransaction1.setTransactionId("0000000001");
        expectedCreditTransaction1.setTransactionDate("20230701");
        expectedCreditTransaction1.setTransactionType("D");
        expectedCreditTransaction1.setAccountNumber("123456789012");
        expectedCreditTransaction1.setTransactionAmount(10000.0);
        expectedCreditTransaction1.setDescription("Pagamento de aluguel          ");
        expectedCreditTransaction1.setBeneficiaryName("João Silva          ");
        expectedCreditTransaction1.setAuthorizationCode("A12345");
        expectedCreditTransaction1.setCpf("11122233344");

        final var expectedCreditTransaction2 = new TransactionWithNotEqualsCreditsDelimiter();
        expectedCreditTransaction2.setTransactionId("0000000002");
        expectedCreditTransaction2.setTransactionDate("20230701");
        expectedCreditTransaction2.setTransactionType("C");
        expectedCreditTransaction2.setAccountNumber("123456789012");
        expectedCreditTransaction2.setTransactionAmount(5000.0);
        expectedCreditTransaction2.setDescription("Depósito em dinheiro          ");
        expectedCreditTransaction2.setBeneficiaryName("Maria Souza         ");
        expectedCreditTransaction2.setAuthorizationCode("B23456");
        expectedCreditTransaction2.setCpf("11122233344");

        final var expectedCreditsHeader = new HeaderWithCreditsDelimiter("CREDITOS");

        final var expectedTransactionWithStarsDelimiter1 = new TransactionWithNotStartWithDelimiter();
        expectedTransactionWithStarsDelimiter1.setTransactionId("0000000001");
        expectedTransactionWithStarsDelimiter1.setTransactionDate("20230701");
        expectedTransactionWithStarsDelimiter1.setTransactionType("D");
        expectedTransactionWithStarsDelimiter1.setAccountNumber("123456789012");
        expectedTransactionWithStarsDelimiter1.setTransactionAmount(10000.0);
        expectedTransactionWithStarsDelimiter1.setDescription("Recebimento de aluguel        ");
        expectedTransactionWithStarsDelimiter1.setBeneficiaryName("Patrik              ");
        expectedTransactionWithStarsDelimiter1.setAuthorizationCode("A12345");
        expectedTransactionWithStarsDelimiter1.setCpf("11122233344");

        final var expectedTransactionWithStarsDelimiter2 = new TransactionWithNotStartWithDelimiter();
        expectedTransactionWithStarsDelimiter2.setTransactionId("0000000002");
        expectedTransactionWithStarsDelimiter2.setTransactionDate("20230701");
        expectedTransactionWithStarsDelimiter2.setTransactionType("C");
        expectedTransactionWithStarsDelimiter2.setAccountNumber("123456789012");
        expectedTransactionWithStarsDelimiter2.setTransactionAmount(5000.0);
        expectedTransactionWithStarsDelimiter2.setDescription("Recebimento de PIX            ");
        expectedTransactionWithStarsDelimiter2.setBeneficiaryName("Ana Souza           ");
        expectedTransactionWithStarsDelimiter2.setAuthorizationCode("B23456");
        expectedTransactionWithStarsDelimiter2.setCpf("11122233344");

        final var expectedFooter1 = new Footer("**************");
        final var expectedFooter2 = new Footer("*** FOOTER ***");
        final var expectedFooter3 = new Footer("**************");

        final int expectedRecordsSize = 6;

        final String inputFilePath = "src/test/resources/reader/header[3]_header[1]_transactions[0]_header[1]_" +
                "transactions[0]_footer[3].txt";

        // Act
        final Map<Class<?>, List<RecordLayout>> records = new PositionalFileReader().readRecords(
                inputFilePath,
                HeaderWithStartWihDelimiter.class,
                HeaderWithDebitsDelimiter.class,
                TransactionWithNotEqualsCreditsDelimiter.class,
                HeaderWithCreditsDelimiter.class,
                TransactionWithNotStartWithDelimiter.class,
                Footer.class
        );

        // Assert
        final var actualHeader = (List<HeaderWithStartWihDelimiter>) getSublist(records,
                HeaderWithStartWihDelimiter.class, 3);
        final var actualDebitsHeader = (HeaderWithDebitsDelimiter) records.get(HeaderWithDebitsDelimiter.class).get(0);
        final var actualTransactionsWithCreditsDelimiter = (List<TransactionWithNotEqualsCreditsDelimiter>)getSublist(records,
                TransactionWithNotEqualsCreditsDelimiter.class, 2);
        final var actualCreditsHeader = (HeaderWithCreditsDelimiter) records.get(HeaderWithCreditsDelimiter.class).get(0);
        final var actualTransactionsWithStarsDelimiter = (List<TransactionWithNotStartWithDelimiter>) getSublist(records,
                TransactionWithNotStartWithDelimiter.class, 2);
        final var actualFooter = (List<Footer>) getSublist(records, Footer.class, 3);

        assertThat(actualHeader).containsExactly(expectedHeader1, expectedHeader2, expectedHeader3);
        assertThat(actualDebitsHeader).isEqualTo(expectedDebitsHeader);
        assertThat(actualTransactionsWithCreditsDelimiter).containsExactly(
                expectedCreditTransaction1,
                expectedCreditTransaction2
        );
        assertThat(actualCreditsHeader).isEqualTo(expectedCreditsHeader);
        assertThat(actualTransactionsWithStarsDelimiter).containsExactly(
                expectedTransactionWithStarsDelimiter1,
                expectedTransactionWithStarsDelimiter2
        );
        assertThat(actualFooter).containsExactly(expectedFooter1, expectedFooter2, expectedFooter3);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_delimiterAsEndWith_then_recordsReadInOrder() {

        // Arrange
        final HeaderWithEndWihDelimiter expectedHeader = new HeaderWithEndWihDelimiter("*** HEADER ***");
        final int expectedRecordsSize = 2;
        final String inputFilePath = "src/test/resources/reader/header[1]_transactions[0].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, HeaderWithEndWihDelimiter.class,
                Transaction.class);
        final var actualTransactions = (List<Transaction>) getSublist(records, Transaction.class, 2);
        final var actualHeader = records.get(HeaderWithEndWihDelimiter.class).get(0);

        // Assert
        assertThat(actualHeader).isEqualTo(expectedHeader);
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_delimiterAsNotEndWith_then_recordsReadInOrder() {

        // Arrange
        final var expectedTransactionWithStarsDelimiter1 = new TransactionWithNotEndWithDelimiter();
        expectedTransactionWithStarsDelimiter1.setTransactionId("0000000001");
        expectedTransactionWithStarsDelimiter1.setTransactionDate("20230701");
        expectedTransactionWithStarsDelimiter1.setTransactionType("D");
        expectedTransactionWithStarsDelimiter1.setAccountNumber("123456789012");
        expectedTransactionWithStarsDelimiter1.setTransactionAmount(10000.0);
        expectedTransactionWithStarsDelimiter1.setDescription("Pagamento de aluguel          ");
        expectedTransactionWithStarsDelimiter1.setBeneficiaryName("João Silva          ");
        expectedTransactionWithStarsDelimiter1.setAuthorizationCode("A12345");
        expectedTransactionWithStarsDelimiter1.setCpf("11122233344");

        final var expectedTransactionWithStarsDelimiter2 = new TransactionWithNotEndWithDelimiter();
        expectedTransactionWithStarsDelimiter2.setTransactionId("0000000002");
        expectedTransactionWithStarsDelimiter2.setTransactionDate("20230701");
        expectedTransactionWithStarsDelimiter2.setTransactionType("C");
        expectedTransactionWithStarsDelimiter2.setAccountNumber("123456789012");
        expectedTransactionWithStarsDelimiter2.setTransactionAmount(5000.0);
        expectedTransactionWithStarsDelimiter2.setDescription("Depósito em dinheiro          ");
        expectedTransactionWithStarsDelimiter2.setBeneficiaryName("Maria Souza         ");
        expectedTransactionWithStarsDelimiter2.setAuthorizationCode("B23456");
        expectedTransactionWithStarsDelimiter2.setCpf("11122233344");

        final Footer expectedFooter = new Footer("*** FOOTER ***");
        final int expectedRecordsSize = 2;
        final String inputFilePath = "src/test/resources/reader/transactions[0]_footer[1].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, TransactionWithNotEndWithDelimiter.class,
                Footer.class);
        final var actualTransactions = (List<TransactionWithNotEndWithDelimiter>) getSublist(records,
                TransactionWithNotEndWithDelimiter.class, 2);
        final var actualFooter = records.get(Footer.class).get(0);

        // Assert
        assertThat(actualTransactions).containsExactly(
                expectedTransactionWithStarsDelimiter1,
                expectedTransactionWithStarsDelimiter2
        );
        assertThat(actualFooter).isEqualTo(expectedFooter);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_delimiterAsSizeEqual_then_recordsReadInOrder() {

        // Arrange
        final HeaderWithSizeEqualDelimiter expectedHeader = new HeaderWithSizeEqualDelimiter("*** HEADER ***");
        final int expectedRecordsSize = 2;
        final String inputFilePath = "src/test/resources/reader/header[1]_transactions[0].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, HeaderWithSizeEqualDelimiter.class,
                Transaction.class);
        final var actualTransactions = (List<Transaction>) getSublist(records, Transaction.class, 2);
        final var actualHeader = records.get(HeaderWithSizeEqualDelimiter.class).get(0);

        // Assert
        assertThat(actualHeader).isEqualTo(expectedHeader);
        assertThat(actualTransactions).containsExactly(expectedTransaction1, expectedTransaction2);
        assertThat(records).hasSize(expectedRecordsSize);
    }

    @Test
    public void when_delimiterAsSizeBiggerThan_then_recordsReadInOrder() {

        // Arrange
        final var expectedTransactionWithStarsDelimiter1 = new TransactionWithSizeBiggerThanDelimiter();
        expectedTransactionWithStarsDelimiter1.setTransactionId("0000000001");
        expectedTransactionWithStarsDelimiter1.setTransactionDate("20230701");
        expectedTransactionWithStarsDelimiter1.setTransactionType("D");
        expectedTransactionWithStarsDelimiter1.setAccountNumber("123456789012");
        expectedTransactionWithStarsDelimiter1.setTransactionAmount(10000.0);
        expectedTransactionWithStarsDelimiter1.setDescription("Pagamento de aluguel          ");
        expectedTransactionWithStarsDelimiter1.setBeneficiaryName("João Silva          ");
        expectedTransactionWithStarsDelimiter1.setAuthorizationCode("A12345");
        expectedTransactionWithStarsDelimiter1.setCpf("11122233344");

        final var expectedTransactionWithStarsDelimiter2 = new TransactionWithSizeBiggerThanDelimiter();
        expectedTransactionWithStarsDelimiter2.setTransactionId("0000000002");
        expectedTransactionWithStarsDelimiter2.setTransactionDate("20230701");
        expectedTransactionWithStarsDelimiter2.setTransactionType("C");
        expectedTransactionWithStarsDelimiter2.setAccountNumber("123456789012");
        expectedTransactionWithStarsDelimiter2.setTransactionAmount(5000.0);
        expectedTransactionWithStarsDelimiter2.setDescription("Depósito em dinheiro          ");
        expectedTransactionWithStarsDelimiter2.setBeneficiaryName("Maria Souza         ");
        expectedTransactionWithStarsDelimiter2.setAuthorizationCode("B23456");
        expectedTransactionWithStarsDelimiter2.setCpf("11122233344");

        final Footer expectedFooter = new Footer("*** FOOTER ***");
        final int expectedRecordsSize = 2;
        final String inputFilePath = "src/test/resources/reader/transactions[0]_footer[1].txt";

        // Act
        final var records = new PositionalFileReader().readRecords(inputFilePath, TransactionWithSizeBiggerThanDelimiter.class,
                Footer.class);
        final var actualTransactions = (List<TransactionWithSizeBiggerThanDelimiter>) getSublist(records,
                TransactionWithSizeBiggerThanDelimiter.class, 2);
        final var actualFooter = records.get(Footer.class).get(0);

        // Assert
        assertThat(actualTransactions).containsExactly(
                expectedTransactionWithStarsDelimiter1,
                expectedTransactionWithStarsDelimiter2
        );
        assertThat(actualFooter).isEqualTo(expectedFooter);
        assertThat(records).hasSize(expectedRecordsSize);
    }
}