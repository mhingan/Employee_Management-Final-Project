/**@author Mihaita Hingan*/
package com.example.proiectfinal.controller;

import com.example.proiectfinal.dto.UserDTO;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

@Controller
public class ExportController {

    /**
     * Handles the export of users to CSV.
     *
     * @param model The Spring Model.
     * @return A ResponseEntity containing the exported CSV file.
     */
    @GetMapping("/export-csv")
    public ResponseEntity<byte[]> exportUsersCsv(Model model) {
        return exportCsv("users", "Data exported to CSV successfully!", model);
    }

    /**
     * Handles the export of account requests to CSV.
     *
     * @param model The Spring Model.
     * @return A ResponseEntity containing the exported CSV file.
     */
    @GetMapping("/export-req-csv")
    public ResponseEntity<byte[]> exportAccountsRequestsCsv(Model model) {
        return exportCsv("accounts_requests", "CSV exported successfully!", model);
    }

    private ResponseEntity<byte[]> exportCsv(String tableName, String successMessage, Model model) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = "postgres";
        String dbPassword = "m4XGW:u2Y6";
        String query = "SELECT id, email, first_name, last_name, gender, phone_number, department, job_title, hiring_date FROM " + tableName;

        String csvFileName = "all-" + tableName + "-" + UUID.randomUUID() + ".csv";

        try {
            FileWriter fileWriter = new FileWriter(csvFileName);

            // Configure CSVWriter with custom settings
            CSVWriter csvWriter = new CSVWriter(fileWriter,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    ICSVWriter.DEFAULT_LINE_END);

            try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                String[] header = {"user_id", "email", "first_name", "last_name", "gender", "phone_number", "department", "job_title", "hiring_date"};
                csvWriter.writeNext(header);

                while (resultSet.next()) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUser_id(resultSet.getString("id"));
                    userDTO.setEmail(resultSet.getString("email"));
                    userDTO.setFirst_name(resultSet.getString("first_name"));
                    userDTO.setLast_name(resultSet.getString("last_name"));
                    userDTO.setGender(resultSet.getString("gender"));
                    userDTO.setPhone_number(resultSet.getString("phone_number"));
                    userDTO.setDepartment(resultSet.getString("department"));
                    userDTO.setJob_title(resultSet.getString("job_title"));
                    userDTO.setHiring_date(resultSet.getString("hiring_date"));

                    String[] rowData = {
                            userDTO.getUser_id(),
                            userDTO.getEmail(),
                            userDTO.getFirst_name(),
                            userDTO.getLast_name(),
                            userDTO.getGender(),
                            userDTO.getPhone_number(),
                            userDTO.getDepartment(),
                            userDTO.getJob_title(),
                            userDTO.getHiring_date()
                    };
                    csvWriter.writeNext(rowData);
                }

                model.addAttribute("message", successMessage);
            }

            csvWriter.flush();
            csvWriter.close();
            fileWriter.close();

            byte[] fileContent = Files.readAllBytes(Paths.get(csvFileName));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", csvFileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private static String[] getResultSetColumnNames(ResultSet resultSet) throws Exception {
        int columnCount = resultSet.getMetaData().getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = resultSet.getMetaData().getColumnName(i);
        }
        return columnNames;
    }
}
