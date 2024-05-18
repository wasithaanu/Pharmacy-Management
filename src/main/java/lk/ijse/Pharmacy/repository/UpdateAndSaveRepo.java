package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.StockAndVetmeds;
import lk.ijse.Pharmacy.model.Vetmeds;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateAndSaveRepo {
    public static boolean updateAndSave(StockAndVetmeds po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isVetmedSaved = VetmedsRepo.save(po.getVetmedDetails());
            System.out.println(isVetmedSaved +"1");
            if (isVetmedSaved) {
                boolean isOrderDetailSaved = StockRepo.update(po.getStockUpdate());
                if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
