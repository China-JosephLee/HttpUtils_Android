package top.gardel.httputils;

import java.util.TreeMap;
import java.util.Map;

public class ParamBuilder {
    private static String MiumLinkstr = "miwustudio";
    private static String MiumSecret = "miwustudio(TAT)!!!???";

    public static TreeMap<String, String> MiumSignPostData(TreeMap<String, String> param) {
        StringBuilder sbr = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            sbr.append(entry.getKey())
               .append(MiumLinkstr)
               .append(entry.getValue());
        }
        sbr.append(MiumSecret);
        param.put("sign", top.gardel.httputils.Md5Utils.Md5(sbr.toString()));
        return param;
    }
}
