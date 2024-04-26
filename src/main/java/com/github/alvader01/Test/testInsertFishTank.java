package com.github.alvader01.Test;


import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.entity.FishTank;

public class testInsertFishTank {

    public static void main(String[] args) {
        FishTank f  = new FishTank(123,"aguanegra",122,123,12,123,null);
        FishTank f1 = new FishTank(122,"aguaroja",142,12,183,189,null);
        FishTank f2 = new FishTank(121,"amazonico",122,10,175,145,null);
        FishTankDAO fishTankDAO = new FishTankDAO();
        fishTankDAO.save(f);
        fishTankDAO.save(f1);
        fishTankDAO.save(f2);

    }
}
