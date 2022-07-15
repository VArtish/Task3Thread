import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> strFuture = executorService.submit(new FileReaderThread("src/main/resources/file"));
        Future<String> xmlFuture = executorService.submit(new RemoteResourcesReaderThread("https://www.nbrb.by/services/xmlexrates.aspx?ondate=01/31/2011"));
        Future<String> jsonFuture = executorService.submit(new RemoteResourcesReaderThread("https://www.nbrb.by/api/exrates/currencies"));

        executorService.shutdown();

        try {
            System.out.println("Locale file result: " + strFuture.get());
            System.out.println("Json file result: " + jsonFuture.get());
            System.out.println("Xml file result: " + xmlFuture.get());
        } catch(ExecutionException executionException) {
            executionException.printStackTrace();
        } catch(InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
