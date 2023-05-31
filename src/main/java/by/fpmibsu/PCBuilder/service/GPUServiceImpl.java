package by.fpmibsu.PCBuilder.service;

import by.fpmibsu.PCBuilder.dao.*;
import by.fpmibsu.PCBuilder.dao.utils.PCComponents;
import by.fpmibsu.PCBuilder.entity.PC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GPUServiceImpl<GPU> implements ComponentService<by.fpmibsu.PCBuilder.entity.component.GPU> {
    private static Logger log = LogManager.getLogger(GPUServiceImpl.class);

    @Override
    public List<by.fpmibsu.PCBuilder.entity.component.GPU> getAllComponents() throws DaoException {
        log.info("GPUService calling getAllComponents");
        GPUDaoImpl gpuDao = new GPUDaoImpl();

        try {
            return gpuDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean selectComponent(int pcId, by.fpmibsu.PCBuilder.entity.component.GPU component) {
        PCDaoImpl dao = new PCDaoImpl();
        try {
            PCComponents c = dao.findPCById(pcId);
            c.setGPUID(component.getId());
            PC pc = new PC();
            pc.setId(c.getId());
            pc.setUserId(c.getUserID());
            pc.setCooler(new CoolerDaoImpl().findComponentById(c.getCoolerID()));
            pc.setCpu(new CPUDaoImpl().findComponentById(c.getCPUID()));
            pc.setGpu(new GPUDaoImpl().findComponentById(c.getGPUID()));
            pc.setHdd(new HDDDaoImpl().findComponentById(c.getHDDID()));
            pc.setMotherboard(new MotherboardDaoImpl().findComponentById(c.getMotherBoardID()));
            pc.setPCCase(new PCCaseDaoImpl().findComponentById(c.getPcCaseID()));
            pc.setPowerSupply(new PowerSupplyDaoImpl().findComponentById(c.getPowerSupplyID()));
            pc.setRam(new RAMDaoImpl().findComponentById(c.getRAMID()));
            pc.setSsd(new SSDDaoImpl().findComponentById(c.getSSDID()));
            dao.update(pc);
            return true;
        } catch (DaoException e) {
            return false;
        }
    }

    @Override
    public by.fpmibsu.PCBuilder.entity.component.GPU getComponentById(int componentId) {
        GPUDaoImpl dao = new GPUDaoImpl();
        try {
            return dao.findComponentById(componentId);
        } catch (DaoException e) {
            return null;
        }
    }
}