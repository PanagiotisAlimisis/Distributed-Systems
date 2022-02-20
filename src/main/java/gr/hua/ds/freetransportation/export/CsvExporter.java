package gr.hua.ds.freetransportation.export;

import gr.hua.ds.freetransportation.entities.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CsvExporter extends AbstractExporter {

    /**
     * Export a list of users in csv format.
     * @param listUsers
     * @param response
     * @throws IOException
     */
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "Email", "First Name", "Last Name", "Role", "Enabled"};
        String[] fieldMapping = {"id", "email", "firstName", "lastName", "roleName", "enabled"};
        csvWriter.writeHeader(csvHeader);

        for (User user : listUsers) {
            csvWriter.write(user, fieldMapping);
        }

        csvWriter.close();
    }
}