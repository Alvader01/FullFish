package com.github.alvader01.Test;

import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.entity.FishTank;


public class testUpdateFishtank {
    public static void main(String[] args) {
        FishTank f = new FishTank();
        f.setId(122);
        f.setName("Updated");
        f.setCapacity(3);
        f.setLengthy(4);
        f.setWidth(5);
        f.setHeight(8);
        FishTankDAO fishTankDAO = new FishTankDAO();
        FishTank updatedFishTank = fishTankDAO.update(f);
        if (updatedFishTank != null) {
            System.out.println("Acuario actualizado correctamente:");
            System.out.println("Name: " + updatedFishTank.getName());
            System.out.println("Capacity: " + updatedFishTank.getCapacity());
            System.out.println("Lengthy: " + updatedFishTank.getLengthy());
            System.out.println("Width: " + updatedFishTank.getWidth());
            System.out.println("Height: " + updatedFishTank.getHeight());
        } else {
            System.out.println("La actualización del usuario falló");
        }
    }
}
