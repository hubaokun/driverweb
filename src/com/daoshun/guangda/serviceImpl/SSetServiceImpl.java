package com.daoshun.guangda.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.guangda.pojo.FeedBackInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.service.ISSetService;

@Service("ssetService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SSetServiceImpl extends BaseServiceImpl implements ISSetService {

	@Override
	public List<NoticesInfo> getNoticesList(String studentid, String pagenum) {
				StringBuffer cuserhql = new StringBuffer();
				cuserhql.append(" from NoticesInfo where noticeid in (select noticeid from NoticesUserInfo where userid =:studentid )");
				cuserhql.append(" and type = 2 order by addtime desc ");
				String[] params ={"studentid"};
				List<NoticesInfo> datalist = (List<NoticesInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0)+1, params,CommonUtils.parseInt(studentid, 0));
				if(datalist!=null&&datalist.size()>0){
					for(NoticesInfo notices:datalist){
						StringBuffer cuserhql1 = new StringBuffer();
						cuserhql1.append(" from NoticesUserInfo where noticeid =:noticeid )");
						String[] params1 ={"noticeid"};
						NoticesUserInfo noticesuser = (NoticesUserInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1,notices.getNoticeid());
						if(noticesuser!=null){
							notices.setReadstate(noticesuser.getReadstate());
						}
					}
				}
				return datalist;
	}
	
	@Override
	public Integer getNoticesMore(String studentid, String pagenum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from NoticesInfo where noticeid in (select noticeid from NoticesUserInfo where userid =:studentid )");
		cuserhql.append(" and type = 2 order by addtime desc ");
		String[] params ={"studentid"};
		List<NoticesInfo> datalist = (List<NoticesInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0)+2, params,CommonUtils.parseInt(studentid, 0));
		if(datalist!=null&&datalist.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Override
	public void delNotice(String studentid, String noticeid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from NoticesUserInfo where noticeid =:noticeid ");
		cuserhql.append(" and userid =:studentid ");
		String[] params ={"noticeid","studentid"};
		NoticesUserInfo noticesUser = (NoticesUserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(noticeid, 0),CommonUtils.parseInt(studentid, 0));
		dataDao.deleteObject(noticesUser);
		StringBuffer cuserhql1 = new StringBuffer();
		cuserhql1.append(" from NoticesInfo where noticeid =:noticeid ");
		cuserhql1.append(" and type = 2 ");
		NoticesInfo notices = (NoticesInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params, CommonUtils.parseInt(noticeid, 0));
		dataDao.deleteObject(notices);
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Override
	public void readNotice(String studentid, String noticeid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from NoticesUserInfo where noticeid =:noticeid ");
		cuserhql.append(" and userid =:studentid ");
		String[] params ={"noticeid","studentid"};
		NoticesUserInfo noticesUser = (NoticesUserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(noticeid, 0),CommonUtils.parseInt(studentid, 0));
		noticesUser.setReadstate(1);
		dataDao.updateObject(noticesUser);
	}

	@Override
	public Integer getMessageCount(String studentid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from NoticesUserInfo where userid =:studentid and readstate = 0");
		String[] params ={"studentid"};
		List<NoticesUserInfo> list = (List<NoticesUserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseInt(studentid, 0));
		if(list != null)
			return list.size();
		return 0;
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	@Override
	public void addFeedBack(String studentid, String content,String type) {
		FeedBackInfo feedback = new FeedBackInfo();
		feedback.setFromid(CommonUtils.parseInt(studentid, 0));
		feedback.setContent(content);
		feedback.setFrom_type(CommonUtils.parseInt(type, 0));
		feedback.setAddtime(new Date());
		dataDao.addObject(feedback);
	}

}
