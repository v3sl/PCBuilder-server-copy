package by.fpmibsu.PCBuilder.dao;

import by.fpmibsu.PCBuilder.dao.utils.PCComponents;
import by.fpmibsu.PCBuilder.db.ConnectionCreator;
import by.fpmibsu.PCBuilder.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PCDaoImpl implements PCDao<Integer, PC> {

    private static final String SQL_SELECT_ALL_PC = "SELECT * FROM pc";
    private static final String SQL_SELECT_PC_BY_ID = "SELECT * FROM pc WHERE id = ?";

    private static final String SQL_UPDATE_PC = "UPDATE pc SET id = ?, userId = ?, powersupplyId = ?, ssdId = ?, hddId = ?,pccaseId = ?, motherboardId = ?, gpuId = ?, cpuId = ?, coolerId = ?, ramId = ? WHERE id = ?";

    private static final String SQl_DELETE_PC_BY_ID = "DELETE FROM pc WHERE id = ?";

    private static final String SQL_INSERT_EMPTY_PC = "INSERT INTO pc(id,userId) VALUES(?,?)";

    private static final String SQL_INSERT_PC = "INSERT INTO pc(id, userId, powersupplyId, ssdId, hddId, pccaseId, motherboardId, gpuId, cpuId, coolerId, ramId) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    @Override
    public List<PCComponents> findAll() throws DaoException {
        List<PCComponents> PCs = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PC);
            while (resultSet.next()){
                PCComponents c = new PCComponents();
                getPc(resultSet, c);
                PCs.add(c);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return PCs;
    }

    private void getPc(ResultSet resultSet, PCComponents c) throws SQLException {
        c.setId(resultSet.getInt("id"));
        c.setCPUID(resultSet.getInt("cpuId"));
        c.setGPUID(resultSet.getInt("gpuId"));
        c.setUserID(resultSet.getInt("userId"));
        c.setCoolerID(resultSet.getInt("coolerId"));
        c.setHDDID(resultSet.getInt("hddId"));
        c.setMotherBoardID(resultSet.getInt("motherboardId"));
        c.setPcCaseID(resultSet.getInt("pccaseId"));
        c.setPowerSupplyID(resultSet.getInt("powersupplyId"));
        c.setRAMID(resultSet.getInt("ramId"));
        c.setSSDID(resultSet.getInt("ssdId"));
    }

    @Override
    public PCComponents findPCById(Integer id) throws DaoException {
        PC pc = new PC();
        Connection connection = null;
        PreparedStatement statement = null;
        PCComponents c = new PCComponents();
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(SQL_SELECT_PC_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                getPc(resultSet, c);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return c;
    }

    @Override
    public PC update(PC pc) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PC);
            fillStatement(pc, statement);
            statement.setInt(12, pc.getId());
            statement.executeUpdate();
            return pc;
        } catch (SQLException e) {
            System.out.println(e);
            return  null;
        }
    }

    private static void fillStatement(PC pc, PreparedStatement statement) throws SQLException {
        statement.setInt(1, pc.getId());
        if (pc.getUserId() == 0) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setInt(2, pc.getUserId());
        }
        if(pc.getPowerSupply() == null || pc.getPowerSupply().getId() == 0) {
            statement.setNull(3, Types.INTEGER);
        }
        else {
            statement.setInt(3,pc.getPowerSupply().getId());
        }
        if(pc.getSsd() == null || pc.getSsd().getId() == 0) {
            statement.setNull(4, Types.INTEGER);
        }
        else {
            statement.setInt(4, pc.getSsd().getId());
        }
        if(pc.getHdd() == null || pc.getHdd().getId() == 0) {
            statement.setNull(5, Types.INTEGER);
        }
        else {
            statement.setInt(5, pc.getHdd().getId());
        }
        if(pc.getPCCase() == null || pc.getPCCase().getId() == 0) {
            statement.setNull(6, Types.INTEGER);
        }
        else {
            statement.setInt(6, pc.getPCCase().getId());
        }
        if(pc.getMotherboard() == null || pc.getMotherboard().getId() == 0) {
            statement.setNull(7, Types.INTEGER);
        }
        else {
            statement.setInt(7, pc.getMotherboard().getId());
        }
        if(pc.getGpu() == null || pc.getGpu().getId() == 0) {
            statement.setNull(8, Types.INTEGER);
        }
        else {
            statement.setInt(8, pc.getGpu().getId());
        }
        if( pc.getCpu() == null || pc.getCpu().getId() == 0) {

            statement.setNull(9, Types.INTEGER);
        }
        else {
            statement.setInt(9,pc.getCpu().getId());
        }
        if( pc.getCooler() == null || pc.getCooler().getId() == 0) {

            statement.setNull(10, Types.INTEGER);
        }
        else {
            statement.setInt(10,pc.getCooler().getId());
        }
        if( pc.getRam() == null || pc.getRam().getId() == 0) {

            statement.setNull(11, Types.INTEGER);
        }
        else {
            statement.setInt(11,pc.getRam().getId());
        }
    }

    @Override
    public boolean delete(PC pc) throws DaoException {
        return delete(pc.getId());
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(SQl_DELETE_PC_BY_ID);
            statement.setInt(1, id);
            statement.executeQuery();
            return  true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int insert(PC pc) throws DaoException{
        int rowsUpdate;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(SQL_INSERT_PC);
            fillStatement(pc, statement);
            rowsUpdate = statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return rowsUpdate;
    }

    @Override
    public int insertEmpty(PC pc) throws DaoException {
        int rowsUpdate;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionCreator.createConnection();
            statement = connection.prepareStatement(SQL_INSERT_EMPTY_PC);
            statement.setInt(1, 0);
            statement.setInt(2, pc.getUserId());
            rowsUpdate = statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return rowsUpdate;
    }
}