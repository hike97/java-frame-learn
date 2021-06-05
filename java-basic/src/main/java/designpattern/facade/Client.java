package designpattern.facade;

/**
 * @author hike97
 * @create 2021-06-05 11:51
 * @desc 客户端
 **/
public class Client {
    public static void main (String[] args) {
        LabourContractor labourContractor = new LabourContractor ();
        labourContractor.buildHouse ();
    }
}
