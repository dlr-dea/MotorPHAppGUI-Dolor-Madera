import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AccountFileReader {

    private ArrayList<Account> accounts;

    public Boolean isAccountPasswordCombinationCorrect(String id, String password) {
        Boolean isFound = false;
        for(Account account: this.accounts) {
            if(id.matches(account.getId()) && password.matches(account.getPassword())) {
                isFound = true;
            }
        }
        return isFound;
    }

    public AccountFileReader() {
        this.accounts = new ArrayList<Account>();
        this.readCSVContents();
    }

    private void readCSVContents() {

        String basePath = new File("src").getAbsolutePath();
        String pathToFile = basePath + "/accounts.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                Account account = new Account(
                    values[0],
                    values[1]
                );

                this.accounts.add(account);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
