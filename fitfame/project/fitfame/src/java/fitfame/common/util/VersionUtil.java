package fitfame.common.util;

public class VersionUtil {
	
	public static double Version = 1.0;
	public static String DownLordUrl = "http://112.124.52.165";
	public static String NewFunction = "软件已更新，请更新您的软件！";

	/**
	 * 版本管理
	 * @param vp
	 * @param json
	 * @return
	 */
	public static boolean IsVpValidate(String vp)
	{
		if(vp == null || vp.trim().equals(""))
			return false;
		String [] vpStr = vp.split("_");
		if(vpStr.length != 2)
			return false;
		return true;
	}
	
	public static boolean IsVersionLastest(String vp)
	{
		String [] vpStr = vp.split("_");
		double version = Double.parseDouble(vpStr[0]);
		String platForm = vpStr[1];
		boolean result = false;
		if(version == Version)
			result = true;
		return result;
	}
	
	public static String GetNewFunction(String vp)
	{
		String [] vpStr = vp.split("_");
		String platForm = vpStr[1];
		return NewFunction;
	}
	
	public static String getDownLordUrl(String vp)
	{
		String [] vpStr = vp.split("_");
		double version = Double.parseDouble(vpStr[0]);
		String platForm = vpStr[1];
		return DownLordUrl;
	}

}
