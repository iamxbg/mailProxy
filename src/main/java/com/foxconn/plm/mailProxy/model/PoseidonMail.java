package com.foxconn.plm.mailProxy.model;

import java.util.Map;

import com.foxconn.plm.mailProxy.util.HostChecker;

public class PoseidonMail extends Mail{
	


	
	public static String[] props_a=new String[]{"apply_date","apply_department","invoice_no","pre_apply_no","supplier","goods_name","pallet_count","package_count","count","plate_num","customs_broker_out","customs_broker_in","applicant","purchasing_agent","truker_phone","comment"};
	public static String[] props_c=new String[]{"apply_date","apply_department","invoice_no","pre_apply_no","goods_name","package_count","count","plate_num","truker_phone","customs_broker_out","customs_broker_in","applicant","purchasing_agent","comment"};
	
	public static String[] header_a=new String[]{"报关日期","课别","INVOICE_NO","预报关单号","厂商","货品名称","栈板数","箱数"
			,"数量(PCS)","车牌号","报关行（报出)","报关行（报进）","申报人员","采购","司机电话","备注"};
	public static String[] header_c=new String[]{"报关日期","申请课别","INVOICE_NO","预报关单号","货品名称","件数","数量","车牌号"
			,"司机电话","报关行（报出）","报关行（报进）","申报人","采购","备注"};
	
	private Map<String, String> params;

	public PoseidonMail(Map<String, String> pa) {
		super(pa.get("subject")+" >>> "+pa.get("pre_apply_no"), pa.get("mail_to"), pa.get("mail_from"),null);
		this.params=pa;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		
		StringBuilder sb=new StringBuilder();
		
					sb.append("<div>Hi 現場物流:<div>");
					sb.append("<div><span style='marign-left:30px;'>请确认表单的（删除|修改操作)</span></div>");
					
					//create orginal form for delete
					String prefix=null;
					sb.append("<div>原始表单内容:<div>");
					buildProcessFormTable(sb, params.get("area"), prefix);
					//add update form
					if("update".equalsIgnoreCase(params.get("type"))){
						sb.append("<div>表单内容:<div>");
						buildProcessFormTable(sb, params.get("area"),"temp_");
					}
					
						
					
					//update reason
					sb.append("<div>修改原因:</div>");
					sb.append("<div><span>");
					sb.append(params.get("reason"));
					sb.append("</span></div>");
					
					//check link address
					sb.append("<div>确认链接:</div>");
					String actionType="checkUpdate";
					if("delete".equals(params.get("type")))
						actionType="checkDelete";
					
						sb.append("<a href='http://"+HostChecker.hostProps.getProperty("poseidon").split(";")[0]+"/poseidon/form_update/"+actionType+"/");
					sb.append(params.get("sn_code"));
					sb.append("'>");
					sb.append("点击确认");
					sb.append("</a>");
					
					//add requester info.
					sb.append("<div><span>申请人: </span><span>");
					sb.append(params.get("sender_work_id"));
					sb.append("<span style='width:10px;'></span>");
					sb.append(params.get("sender_name"));
					sb.append("</span></div>");
					sb.append("<div><span>部门: </span><span>");
					sb.append(params.get("sender_department"));
					sb.append(" </span></div>");
					sb.append("<div><span>职务: </span><span>");
					sb.append(params.get("sender_charge"));
					sb.append(" </span></div>");
					sb.append("<div><span>PHONE:</span><span>");
					sb.append(params.get("sender_phone"));
					sb.append("</span></div>");
					
		return sb.toString();					
	}
	
	private StringBuilder buildProcessFormTable(StringBuilder sb,String area,String prefix){
			
			String[] header=null;
			String[] props=null;
			
			System.out.println("Area:"+area);
			
			if("A".equalsIgnoreCase(area)){
				header=header_a;
				props=props_a;
			}else if("C".equalsIgnoreCase(area)){
				header=header_c;
				props=props_c;
			}
			
				sb.append("<table border='1px' style='font-size:12px;cellspacing:2px;'>");
				sb.append("<colgroup>");
				for(int i=0;i<props.length;i++){
					sb.append("<col></col>");
				}
				sb.append("</colgroup>");

				
				sb.append("<tr style='background:lightblue;'>");
				for(int i=0;i<header.length;i++){
					sb.append("<td><span>").append(header[i]).append("</span></td>");
				}
				sb.append("</tr>");
				

				
							
					sb.append("<tr >");

					for(int i=0;i<props.length;i++){
						if(prefix!=null && params.containsKey(prefix+props[i])){

							 sb.append("<td><span style='background:#FFFF7F'>").append(params.get(prefix+props[i])).append("</span></td>");
						}
						else sb.append("<td><span>").append(params.get(props[i])).append("</span></td>");
					}
					
					sb.append("</tr>");
				
				
				
				sb.append("</table>");

			return sb;
	}


	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}
