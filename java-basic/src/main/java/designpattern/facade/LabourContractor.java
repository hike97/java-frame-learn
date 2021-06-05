package designpattern.facade;

import designpattern.facade.subClasses.BrickLayer;
import designpattern.facade.subClasses.BrickWorker;
import designpattern.facade.subClasses.Mason;

/**
 * @author hike97
 * @create 2021-06-05 11:48
 * @desc 包工头 门面
 **/
public class LabourContractor {
    private Mason mason = new Mason ();
    private BrickWorker brickWorker = new BrickWorker ();
    private BrickLayer brickLayer = new BrickLayer ();

    public void buildHouse () {
        mason.mix ();
        brickWorker.carry ();
        brickLayer.neat ();
    }
}

