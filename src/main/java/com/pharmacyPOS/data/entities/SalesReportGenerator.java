//package com.pharmacyPOS.data.entities;
//
//import com.pharmacyPOS.data.dao.InventoryDao;
//import com.pharmacyPOS.data.dao.ProductDao;
//import com.pharmacyPOS.data.dao.SalesDao;
//import com.pharmacyPOS.data.database.DatabaseConnection;
//import com.pharmacyPOS.service.ProductService;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//import java.util.List;
//
//public class SalesReportGenerator {
//    private DatabaseConnection dbConnection;
//    private SalesDao salesDao;
//    private ProductService productService;
//
//    public SalesReportGenerator(DatabaseConnection dbConnection) {
//        this.dbConnection = dbConnection;
//        this.salesDao = new SalesDao(dbConnection);
//        this.productService = new ProductService(new ProductDao(dbConnection));
//    }
//
//    public void generateAndDisplayReports() {
//        List<Sale> saleList = salesDao.getAllSales();
//        for (Sale sale : saleList) {
//            Product product = productService.getProductById(sale.getProductId());
//            String productName = (product != null) ? product.getName() : "Unknown Product";
//            double price = (product != null) ? product.getPrice() : 0.0;
//
//            SalesReport report = new SalesReport(
//                    sale.getProductId(),
//                    productName,
//                    sale.getQuantity(),
//                    price
//            );
//
//            report.display();
//        }
//    }
//
//    public void generateAndWriteReportToFile() {
//        String fileName = createFileNameWithCurrentDate();
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            List <Sale> saleList = salesDao.getAllSales();
//            for (Sale sale : saleList) {
//                Product product = productService.getProductById(sale.getProductId());
//                String productName = (product != null) ? product.getName() : "Unknown Product";
//                double price = (product != null) ? product.getPrice() : 0.0;
//
//                String reportLine = String.format(
//                        "Product ID: %d, Name: %s, Quantity: %d, Price: %.2f",
//                        sale.getProductId(), productName, sale.getQuantity(), price);
//                writer.write(reportLine);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String createFileNameWithCurrentDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String currentDate = sdf.format(new Date());
//        return "SaleReport - " + currentDate + ".txt";
//    }
//
//    public static void main(String[] args) {
//            /*DatabaseConnection dbConnection = new DatabaseConnection();
//            dbConnection.connect();
//            InventoryReportGenerator reportGenerator = new InventoryReportGenerator(dbConnection);
//            reportGenerator.generateAndDisplayReports();
//
//             */
//
//        DatabaseConnection dbConnection = new DatabaseConnection();
//        dbConnection.connect();
//        SalesReportGenerator reportGenerator = new SalesReportGenerator(dbConnection);
//        reportGenerator.generateAndWriteReportToFile();
//    }
//
//}
