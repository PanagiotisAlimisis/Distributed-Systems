package gr.hua.ds.freetransportation.export;

import gr.hua.ds.freetransportation.entities.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractExporter {

    public void setResponseHeader(HttpServletResponse response, String contentType, String extension) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = "users_" + timestamp + extension;
        response.setContentType(contentType);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
    }

    public abstract void export(List<User> listUsers, HttpServletResponse response) throws IOException;

}