package string;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName a
 * @Description TODO
 * @Author hike97
 * @Date 2021/3/30 13:10
 * @Version 1.0
 **/
public class AddressDeal {

    /**
     * 解析地址
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        /*
         * java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。它包括两个类：Pattern和Matcher Pattern
         *    一个Pattern是一个正则表达式经编译后的表现模式。 Matcher
         *    一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
         *    首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行字符串的匹配工作。
         */
        String regex=
                "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市|北京市|天津市|上海市|重庆市)" +
                        "?(?<city>[^市]+自治州|.*?行政单位|.+盟|.*?市|.*?县)" +
                        "(?<district>[^县].*?县|.*?区|.*?市|.*?旗|.*?海域|.*?岛|.*?旗|.*?特区|.*?林区)" +
                        "(?<town>[^街道].*?镇|.*?乡|.*?区公所|.*?苏木|.*?民族乡|.*?民族苏木|.*?街道|.*?林场)" +
                        "?(?<community>[^委会].*?员会|.*?村|.*?社区|.*?嘎查|.*?管理区|.*?队|.*?村委|.*?组|.*?牧场|.*?连)" +
                        "?(?<detail>.*)";
        String regex2=
                "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市|北京市|天津市|上海市|重庆市)" +
                        "?(?<city>[^市]市辖区)"+
                        "?(?<district>[^县].*?县|.*?区|.*?市|.*?旗|.*?海域|.*?岛|.*?旗|.*?特区|.*?林区)" +
                        "(?<town>[^街道].*?镇|.*?乡|.*?区公所|.*?苏木|.*?民族乡|.*?民族苏木|.*?街道|.*?林场)" +
                        "?(?<community>[^委会].*?员会|.*?村|.*?社区|.*?嘎查|.*?管理区|.*?队|.*?村委|.*?组|.*?牧场|.*?连)" +
                        "?(?<detail>.*)";
        if (address.contains("市辖区")){
            regex=regex2;
        }
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,city=null,district=null,town=null,community=null,detail=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            district=m.group("district");
            row.put("district", district==null?"":district.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            community=m.group("community");
            row.put("community", community==null?"":community.trim());
            detail=m.group("detail");
            row.put("detail", detail==null?"":detail.trim());
            table.add(row);
        }
        return table;
    }

    public static List<Map<String,String>> addressResolution2(String s){
        String provinceRegex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市|北京市|天津市|上海市|重庆市)";
        String cityRegex = "(?<city>[^市]+自治州|.*?行政单位|.+盟|.*?市|.*?县)";
        String districtRegex = "(?<district>[^县].*?县|.*?区|.*?市|.*?旗|.*?海域|.*?岛|.*?旗|.*?特区|.*?林区)";
        String townRegex = "(?<town>[^街道].*?镇|.*?乡|.*?区公所|.*?苏木|.*?民族乡|.*?民族苏木|.*?街道|.*?林场)";
        String communityRegex = "(?<community>[^委会].*?员会|.*?村|.*?社区|.*?嘎查|.*?管理区|.*?队|.*?村委|.*?组|.*?牧场|.*?连)";
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=new LinkedHashMap<>();
        s = s.replaceAll(" ", "");
        Matcher m=Pattern.compile(provinceRegex).matcher(s);
        String province=null,city=null,district=null,town=null,community=null,detail=null;
        //省
        if (m.find()){
            province = m.group("province");
            s = StringUtils.removeStart(s, province);
        }
        //市
        m=Pattern.compile(cityRegex).matcher(s);
        if (s.contains("市辖区")){
            city = "市辖区";
            s=StringUtils.remove(s,"市辖区");
        }else {
            if (m.find()){
                city = m.group("city");
                s = StringUtils.removeStart(s, city);
            }
        }
        //区县
        m=Pattern.compile(districtRegex).matcher(s);
        if (m.find()){
            district = m.group("district");
            s = StringUtils.removeStart(s, district);
        }
        //乡
        m=Pattern.compile(townRegex).matcher(s);
        if (m.find()){
            town = m.group("town");
            s = StringUtils.removeStart(s, town);
        }
        //小区
        m=Pattern.compile(communityRegex).matcher(s);
        if (m.find()){
            community = m.group("community");
            s = StringUtils.removeStart(s, community);
        }
        row.put("level1", province==null?"":province.trim());
        row.put("level2", city==null?"":city.trim());
        row.put("level3", district==null?"":district.trim());
        row.put("level4", town==null?"":town.trim());
        row.put("level5", community==null?"":community.trim());
        row.put("level6", s==null?"":s.trim());
        table.add(row);
        return table;
    }




    public static void main(String[] args) {
        String s = "北京市  海淀区 中关村19号楼一单元1106";
//        List<Map<String,String>> table = addressResolution(s.trim());
//        List<Map<String, String>> table = addressResolution2(s);
//        System.out.println(table);
//        System.out.println(table.get(0).get("province"));
//        System.out.println(table.get(0).get("city"));
//        System.out.println(table.get(0).get("district"));
//        System.out.println(table.get(0).get("town"));
//        System.out.println(table.get(0).get("community"));
        System.out.println(addressResolution2(s));


    }


}
