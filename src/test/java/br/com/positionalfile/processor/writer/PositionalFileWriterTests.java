package br.com.positionalfile.processor.writer;

import br.com.positionalfile.Record;
import br.com.positionalfile.processor.Header;
import br.com.positionalfile.processor.PositionalFileTestsConfig;
import br.com.positionalfile.processor.reader.PixTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PositionalFileWriterTests extends PositionalFileTestsConfig {

    @Test
    public void when_entitiesAnnotatedWithOrder_then_recordsWroteInOrder() throws IOException {
        // Arrange
        final String expectedAccountBalance1 = "09876543210955566677788500.0";
        final String expectedAccountBalance2 = "12345678901211122233344-5000.0";

        final AccountBalance accountBalance1 = new AccountBalance("098765432109", "55566677788", 500.0);
        final AccountBalance accountBalance2 = new AccountBalance("123456789012", "11122233344", -5000.0);
        final List<AccountBalance> balances = Arrays.asList(accountBalance1, accountBalance2);

        final String outputFilePath = "src/test/resources/balances.txt";

        // Act
        new PositionalFileWriter<AccountBalance>().writeRecords(balances, outputFilePath);

        // Assert
        final List<String> actualRecords = readRecords(outputFilePath);
        Assertions.assertThat(actualRecords).containsExactly(expectedAccountBalance1, expectedAccountBalance2);
    }

    @Test
    public void when_differentEntityFromAccountBalance_then_recordsWroteInOrder() throws IOException {
        // Arrange
        final String expectedPixTransaction1 = "chave-remetente-123chave-destinatario-45615075.020230701João SilvaMaria Oliveira";
        final String expectedPixTransaction2 = "chave-remetente-123chave-destinatario-77720050.020230701José LuizBeatriz Duarte";

        final PixTransaction pixTransaction1 = new PixTransaction();
        pixTransaction1.setSenderKey("chave-remetente-123");
        pixTransaction1.setRecipientKey("chave-destinatario-456");
        pixTransaction1.setAmount(15075.0);
        pixTransaction1.setTransactionDate("20230701");
        pixTransaction1.setSenderName("João Silva");
        pixTransaction1.setRecipientName("Maria Oliveira");

        final PixTransaction pixTransaction2 = new PixTransaction();
        pixTransaction2.setSenderKey("chave-remetente-123");
        pixTransaction2.setRecipientKey("chave-destinatario-777");
        pixTransaction2.setAmount(20050.0);
        pixTransaction2.setTransactionDate("20230701");
        pixTransaction2.setSenderName("José Luiz");
        pixTransaction2.setRecipientName("Beatriz Duarte");

        final List<PixTransaction> pixTransactions = Arrays.asList(pixTransaction1, pixTransaction2);

        final String outputFilePath = "src/test/resources/sent_pixes_output.txt";

        // Act
        new PositionalFileWriter<PixTransaction>().writeRecords(pixTransactions, outputFilePath);

        // Assert
        final List<String> actualPixTransactions = readRecords(outputFilePath);
        Assertions.assertThat(actualPixTransactions).containsExactly(expectedPixTransaction1, expectedPixTransaction2);
    }

    @Test
    public void when_differentContentsMixed_then_recordsWroteInOrder() throws IOException {
        // Arrange
        final String expectedHeader = "PIX TRANSACTIONS";
        final String expectedPixTransaction1 = "chave-remetente-123chave-destinatario-45615075.020230701João SilvaMaria Oliveira";
        final String expectedPixTransaction2 = "chave-remetente-123chave-destinatario-77720050.020230701José LuizBeatriz Duarte";

        final Header header = new Header();
        header.setTitle("PIX TRANSACTIONS");

        final PixTransaction pixTransaction1 = new PixTransaction();
        pixTransaction1.setSenderKey("chave-remetente-123");
        pixTransaction1.setRecipientKey("chave-destinatario-456");
        pixTransaction1.setAmount(15075.0);
        pixTransaction1.setTransactionDate("20230701");
        pixTransaction1.setSenderName("João Silva");
        pixTransaction1.setRecipientName("Maria Oliveira");

        final PixTransaction pixTransaction2 = new PixTransaction();
        pixTransaction2.setSenderKey("chave-remetente-123");
        pixTransaction2.setRecipientKey("chave-destinatario-777");
        pixTransaction2.setAmount(20050.0);
        pixTransaction2.setTransactionDate("20230701");
        pixTransaction2.setSenderName("José Luiz");
        pixTransaction2.setRecipientName("Beatriz Duarte");

        final List<Record> content = List.of(header, pixTransaction1, pixTransaction2);

        final String outputFilePath = "src/test/resources/sent_pixes_output.txt";

        // Act
        new PositionalFileWriter<>().writeRecords(content, outputFilePath);

        // Assert
        final List<String> actualPixTransactions = readRecords(outputFilePath);
        Assertions.assertThat(actualPixTransactions).containsExactly(expectedHeader, expectedPixTransaction1, expectedPixTransaction2);
    }

    @Test
    public void when_entityWithAnnotatedTextFormatters_then_recordsWroteInOrder() throws IOException {
        // Arrange
        final Transfer transfer = new Transfer();
        transfer.setAmount(1000);
        transfer.setTransferDate("20230701");
        transfer.setSourceAccount("sourceAccount");
        transfer.setDestinationAccount("destinationAccount");

        final String expectedTransfer = "       sourceAccountdestinationAccount**1000.020230701";

        final String outputFilePath = "src/test/resources/transfers.txt";

        // Act
        new PositionalFileWriter<>().writeRecords(List.of(transfer), outputFilePath);

        // Assert
        final List<String> actualTransfers = readRecords(outputFilePath);
        Assertions.assertThat(actualTransfers).containsExactly(expectedTransfer);
    }

    @Test
    public void when_entityWithAnnotatedDecimalFormatter_then_recordsWroteWithDecimalFormatting() throws IOException {
        // Arrange
        final TransferWithDecimalFormatting transfer = new TransferWithDecimalFormatting();
        transfer.setAmount(new BigDecimal("1000.00"));
        transfer.setTransferDate("20230701");
        transfer.setSourceAccount("sourceAccount");
        transfer.setDestinationAccount("destinationAccount");

        final String expectedTransfer = "sourceAccountdestinationAccount+000000000000001000.0020230701";

        final String outputFilePath = "src/test/resources/transfers.txt";

        // Act
        new PositionalFileWriter<>().writeRecords(List.of(transfer), outputFilePath);

        // Assert
        final List<String> actualTransfers = readRecords(outputFilePath);
        Assertions.assertThat(actualTransfers).containsExactly(expectedTransfer);
    }
}
